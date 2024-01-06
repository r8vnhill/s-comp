package cl.ravenhill.scum
package compiler

import asm.*
import ast.*

import cl.ravenhill.scum.asm.registry.-
import cl.ravenhill.scum.exceptions.{NumberOverflowException, NumberUnderflowException}

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
def compileProgram[A](program: ast.Expression[A]): String = {
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
  val suffix = s"  $Return"
  s"""$prelude
     |$asmString
     |$suffix""".stripMargin
}

/** Compiles an AST expression into a sequence of assembly instructions.
  *
  * This function takes an expression from the abstract syntax tree (AST) and an environment mapping variable names to
  * their respective stack slots. It recursively compiles the expression into a sequence of low-level instructions.
  * Supported expressions include variable declarations ([[ast.Let]]), variable references ([[ast.Var]]), numeric
  * literals ([[ast.Num]]), unary operations like increment ([[ast.Increment]]), decrement ([[ast.Decrement]]), doubling
  * ([[ast.Doubled]]), binary operations ([[ast.BinaryOperation]]), and conditional expressions ([[ast.If]]).
  *
  * @param expr
  *   The AST expression to compile.
  * @param environment
  *   The environment mapping variable names to stack slots.
  * @return
  *   A sequence of instructions representing the compiled form of the expression.
  * @tparam A
  *   The type of metadata associated with the AST expression.
  */
private[compiler] def compileExpression[A](
    expr: Expression[A],
    environment: Environment = Environment.empty
): Try[Seq[Instruction]] = {
  val minNum = Long.MinValue / 2
  val maxNum = Long.MaxValue / 2
  expr match {
    case ast.Var(sym) => environment(sym).map(offset => Seq(Move(Rax(), Rsp - offset))) // mov rax, [rsp - <offset>]
    case ast.Num(n) =>
      n match {
        // (!) Check for overflow/underflow statically, this does not prevent runtime errors
        case n if n < minNum => Failure(NumberUnderflowException(n, minNum))
        case n if n > maxNum => Failure(NumberOverflowException(n, maxNum))
        // n is left-shifted by 1 to account for the tag bit
        case _               => Success(Seq(Move(Rax(), Constant(n << 1)))) // mov rax, <n>
      }
    case e: ast.UnaryOperation[A]  => compileUnaryOperation(e, environment)
    case e: ast.BinaryOperation[A] => compileBinaryOperation(e, environment)
    case ast.Let(sym, expr, body)  => compileLetExpression(sym, expr, body, environment)
    case ifExpr: ast.If[A]         => compileIfExpression(ifExpr, environment)
  }
}

/** Compiles a unary operation expression into a sequence of machine instructions.
  *
  * This function takes a `UnaryOperation` and an `Environment`, and compiles the unary operation into a sequence of
  * assembly instructions. The specific type of unary operation (Increment, Decrement, Doubled) determines the exact
  * sequence of instructions generated. The compilation process involves first compiling the expression contained within
  * the unary operation and then appending the appropriate assembly instruction that performs the unary operation
  * (Increment, Decrement, or Doubling).
  *
  * @param value
  *   The unary operation expression to be compiled.
  * @param environment
  *   The environment context used during compilation, which may contain variable bindings, function definitions, or
  *   other relevant contextual information.
  * @return
  *   A `Try` containing a sequence of `Instruction`s representing the compiled unary operation, or a failure if the
  *   compilation is unsuccessful.
  */
private def compileUnaryOperation(value: UnaryOperation[_], environment: Environment): Try[Seq[Instruction]] = {
  value match {
    case ast.Increment(e) => compileExpression(e, environment).map(_ :+ asm.Increment(Rax()))
    case ast.Decrement(e) => compileExpression(e, environment).map(_ :+ asm.Decrement(Rax()))
    case ast.Doubled(e)   => compileExpression(e, environment).map(_ :+ asm.Add(Rax(), Rax()))
  }
}

private def compileBinaryOperation(value: BinaryOperation[_], environment: Environment): Try[Seq[Instruction]] = {
  value match {
    case ast.Plus(e1, e2)  => ???
    case ast.Minus(e1, e2) => ???
    case ast.Times(e1, e2) => ???
  }
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
    bindingExpr: Expression[A],
    bodyExpr: Expression[A],
    environment: Environment
): Try[Seq[Instruction]] = {
  val extendedEnv     = environment + sym                       // Ensure sym exists in extendedEnv
  val compiledBinding = compileExpression(bindingExpr, environment)
  val moveToStack     = Move(Rsp - extendedEnv(sym).get, Rax()) // mov [rsp - <offset>], rax
  val compiledBody    = compileExpression(bodyExpr, extendedEnv)
  for {
    binding <- compiledBinding
    body    <- compiledBody
  } yield binding ++ Seq(moveToStack) ++ body
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
      asm.Label(ifLabel),
      asm.Compare(Rax(), Constant(0)), // cmp rax, 0
      asm.JumpIfEqual(elseLabel)       // jmp <else_label>
    ) ++ thenBranch ++
    Seq(
      asm.Jump(endLabel),  // jmp <end_label>
      asm.Label(elseLabel) // <else_label>:
    ) ++
    elseBranch ++
    Seq(asm.Label(endLabel)) // <end_label>:
}
