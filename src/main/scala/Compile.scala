package cl.ravenhill.scum

import ass.*
import ast.terminal.{Num, Var}
import ast.unary.{Decrement, Doubled, Increment}
import ast.{Expr, If, Let}

import scala.util.{Failure, Success, Try}

/** Compiles an integer program into a string representation of its assembly instructions.
  *
  * This function compiles an integer program by first converting it into a sequence of assembly instructions using the
  * `compileExpression` function. It then converts these instructions into a string format suitable for assembly
  * language. The compiled instructions are wrapped in a standard assembly prelude and suffix. The prelude sets up the
  * necessary assembly language scaffolding, and the suffix includes the `ret` instruction for returning from the
  * function.
  *
  * @param program
  *   The integer program to be compiled.
  * @return
  *   A string representing the assembly language code for the compiled program.
  */
def compileProgram[A](program: ast.Expr[A]): String = {
  val instructions = compileExpression(program, Environment.empty)
  val asmString = instructions match {
    case Success(instructions) =>
      instructions
        .map {
          case Label(label) => s"$label:"
          case instruction  => s"  $instruction"
        }
        .mkString("\n")
    case Failure(exception) => throw exception
  }
  val prelude =
    """section .text
      |global our_code_starts_here
      |our_code_starts_here:""".stripMargin
  val suffix = s"  $Ret"
  s"""$prelude
     |$asmString
     |$suffix""".stripMargin
}

/** Compiles an AST expression into a sequence of assembly instructions.
  *
  * This function takes an expression from the abstract syntax tree (AST) and an environment mapping variable names to
  * their respective stack slots. It recursively compiles the expression into a sequence of low-level instructions.
  * Supported expressions include variable declarations ([[Let]]), variable references ([[Var]]), numeric literals
  * ([[Num]]), and unary operations like increment ([[Increment]]), decrement ([[Decrement]]), and doubling
  * ([[Doubled]]).
  *
  * @param expr
  *   The AST expression to compile.
  * @param environment
  *   The environment mapping variable names to stack slots.
  * @return
  *   A sequence of instructions representing the compiled form of the expression.
  */
private[scum] def compileExpression[A](
    expr: Expr[A],
    environment: Environment = Environment.empty
): Try[Seq[Instruction]] =
  expr match {
    case Let(sym, expr, body) => compileLetExpression(sym, expr, body, environment)
    case Var(sym)     => Success(Seq(Mov(Reg(Rax), RegOffset(Rsp, -environment(sym).get)))) // mov rax, [rsp - <offset>]
    case Num(n)       => Success(Seq(Mov(Reg(Rax), Const(n))))                              // mov rax, <n>
    case Increment(e) => compileExpression(e, environment).map(_ :+ ass.Increment(Reg(Rax)))
    case Decrement(e) => compileExpression(e, environment).map(_ :+ ass.Decrement(Reg(Rax)))
    case Doubled(e)   => compileExpression(e, environment).map(_ :+ ass.Add(Reg(Rax), Reg(Rax)))
    case ifExpr: If[A] => compileIfExpression(ifExpr, environment)
    case _             => Failure[Seq[Instruction]](UnknownExpressionException(s"Unknown expression: $expr"))
  }

/** Compiles a 'Let' expression in an abstract syntax tree (AST) into assembly instructions.
  *
  * This method handles the compilation of 'Let' expressions, which are used to bind a value to a symbol (variable) and
  * then use this symbol within the body of another expression. The method extends the environment with the new symbol,
  * compiles the binding expression, moves the result to the stack, and then compiles the body expression with the
  * updated environment.
  *
  * The result of the binding expression is first stored in the RAX register and then moved to the appropriate stack
  * slot corresponding to the symbol. This is followed by the compilation of the body expression, which can now use the
  * value bound to the symbol.
  *
  * @param sym
  *   The symbol (variable name) to which the binding expression's result will be bound.
  * @param bindingExpr
  *   The expression whose result will be bound to the symbol.
  * @param bodyExpr
  *   The body expression where the symbol will be used.
  * @param environment
  *   The current compilation environment mapping variable names to stack slots.
  * @tparam A
  *   The type of the annotation associated with the AST expressions.
  * @return
  *   A `Try[Seq[Instruction]]` representing the compiled form of the 'Let' expression, which may fail if any part of
  *   the expression compilation fails.
  */
private def compileLetExpression[A](
    sym: String,
    bindingExpr: Expr[A],
    bodyExpr: Expr[A],
    environment: Environment
): Try[Seq[Instruction]] = {
  val extendedEnv     = environment + sym                                         // Ensure sym exists in extendedEnv
  val compiledBinding = compileExpression(bindingExpr, environment)
  val moveToStack     = Seq(Mov(RegOffset(Rsp, -extendedEnv(sym).get), Reg(Rax))) // mov [rsp - <offset>], rax
  val compiledBody    = compileExpression(bodyExpr, extendedEnv)
  for {
    binding <- compiledBinding
    body    <- compiledBody
  } yield binding ++ moveToStack ++ body
}

/** Compiles an 'If' expression in an abstract syntax tree (AST) into a sequence of assembly instructions.
  *
  * This method handles the compilation of 'If' expressions, which are conditional constructs commonly used in
  * programming. The 'If' expression consists of a predicate expression and two branches: 'then' and 'else'. The method
  * compiles the predicate and then uses jump instructions to execute either the 'then' branch or the 'else' branch
  * based on the evaluation of the predicate.
  *
  * The compilation process involves generating labels for conditional jumps and sequencing the compiled instructions
  * for each part of the 'If' expression. It first compiles the predicate expression, then conditionally jumps to the
  * 'else' branch if the predicate evaluates to false (zero). Otherwise, it continues with the 'then' branch and jumps
  * to the end label after executing it to avoid executing the 'else' branch.
  *
  * @param ifExpr
  *   The 'If' expression to be compiled, consisting of a predicate, a 'then' expression, and an 'else' expression.
  * @param environment
  *   The current compilation environment mapping variable names to stack slots.
  * @tparam A
  *   The type of the annotation associated with the AST expressions.
  * @return
  *   A `Try[Seq[Instruction]]` representing the compiled form of the 'If' expression, which may fail if any part of the
  *   expression compilation fails.
  */
def compileIfExpression[A](
    ifExpr: If[A],
    environment: Environment
): Try[Seq[Instruction]] = {
  val If(pred, thenExpr, elseExpr) = ifExpr
  val annotation                   = ifExpr.metadata
  val ifLabel                      = s"if_$annotation"
  val elseLabel                    = s"else_$annotation"
  val endLabel                     = s"endif_$annotation"
  for {
    predicate  <- compileExpression(pred, environment)
    thenBranch <- compileExpression(thenExpr, environment)
    elseBranch <- compileExpression(elseExpr, environment)
  } yield predicate ++
    Seq(
      ass.Label(ifLabel),
      ass.Compare(Reg(Rax), Const(0)), // cmp rax, 0
      ass.JumpIfEqual(elseLabel)       // jmp <else_label>
    ) ++ thenBranch ++
    Seq(
      ass.Jump(endLabel),  // jmp <end_label>
      ass.Label(elseLabel) // <else_label>:
    ) ++
    elseBranch ++
    Seq(ass.Label(endLabel)) // <end_label>:
}

/** Annotates an expression with additional metadata.
  *
  * @param expression
  *   The expression to be annotated.
  * @return
  *   The annotated expression.
  */
private def annotate(expression: Expr[String]): Expr[String] = {
  expression match {
    case Let(sym, expr, body) => Let(sym, annotate(expr), annotate(body))
    case Var(sym)             => Var(sym)
    case Num(n)               => Num(n)
    case Increment(e)         => Increment(annotate(e))
    case Decrement(e)         => Decrement(annotate(e))
    case Doubled(e)           => Doubled(annotate(e))
    case _                    => throw UnknownExpressionException(s"Unknown expression: $expression")
  }
}
