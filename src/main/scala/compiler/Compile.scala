package cl.ravenhill.scum
package compiler

import asm.*
import ast.*

import cl.ravenhill.scum.asm.registry.-

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
def compileProgram(program: ast.Expression[Int]): String = {
  val annotated    = annotate(program)
  val anfProgram   = toAnf(annotated)
  val instructions = compileExpression(anfProgram, Environment.empty)
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
    s"""${Comment(program.toString)}
      |section .text
      |global our_code_starts_here
      |our_code_starts_here:""".stripMargin
  val suffix = s"  $Return"
  s"""$prelude
     |$asmString
     |$suffix""".stripMargin
}

private[compiler] def compileExpression[A](
    expr: Expression[A],
    environment: Environment = Environment.empty
): Try[Seq[Instruction]] = {
  val minNum = Long.MinValue / 2
  val maxNum = Long.MaxValue / 2
  expr match {
    case ast.IdLiteral(sym, _) =>
      environment(sym).map(offset => Seq(Move(Rax(), Rsp - offset))) // mov rax, [rsp - <offset>]
    case ast.NumericLiteral(n, _) =>
      n match {
        // (!) Check for overflow/underflow statically, this does not prevent runtime errors
//        case n if n < minNum => Failure(NumberUnderflowException(n, minNum))
//        case n if n > maxNum => Failure(NumberOverflowException(n, maxNum))
        // n is left-shifted by 1 to account for the tag bit
        case _ => Success(Seq(Move(Rax(), Constant(n)))) // mov rax, <n>
      }
    case e: ast.UnaryOperation[A]    => compileUnaryOperation(e, environment)
    case e: ast.BinaryOperation[A]   => compileBinaryOperation(e, environment)
    case ast.Let(sym, expr, body, _) => compileLetExpression(sym, expr, body, environment)
    case ifExpr: ast.If[A]           => compileIfExpression(ifExpr, environment)
  }
}

private def compileUnaryOperation[A](value: UnaryOperation[A], environment: Environment): Try[Seq[Instruction]] = {
  value match {
    case ast.Increment(e, _) => compileExpression(e, environment).map(_ :+ asm.Increment(Rax()))  // inc rax
    case ast.Decrement(e, _) => compileExpression(e, environment).map(_ :+ asm.Decrement(Rax()))  // dec rax
    case ast.Doubled(e, _)   => compileExpression(e, environment).map(_ :+ asm.Add(Rax(), Rax())) // add rax, rax
  }
}

private def compileBinaryOperation[A](value: BinaryOperation[A], environment: Environment): Try[Seq[Instruction]] = {
  def compileOp(left: Expression[A], right: Expression[A], op: (Arg, Arg) => Instruction): Try[Seq[Instruction]] = {
    for {
      leftInstr  <- compileExpression(left, environment)
      rightInstr <- compileExpression(right, environment)
      rightSlot = environment.lastSlot
    } yield rightInstr ++ Seq(Move(Rsp - rightSlot, Rax())) ++ leftInstr ++ Seq(op(Rax(), Rsp - rightSlot))
  }

  value match {
    case Plus(left, right, _)  => compileOp(left, right, Add.apply)
    case Minus(left, right, _) => compileOp(left, right, Subtract.apply)
    case Times(left, right, _) =>
      for {
        leftInstr  <- compileExpression(left, environment)
        rightInstr <- compileExpression(right, environment)
        rightSlot = environment.lastSlot
      } yield rightInstr ++
        Seq(Move(Rsp - rightSlot, Rax())) ++
        leftInstr ++
        Seq(
          Move(Rbx(), Rsp - rightSlot), // mov rbx, [rsp - <offset>]
          Multiply(Rbx())
        )
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
private def compileIfExpression[A](
    ifExpr: If[A],
    environment: Environment
): Try[Seq[Instruction]] = {
  val If(pred, thenExpr, elseExpr, _) = ifExpr
  val annotation                      = ifExpr.metadata
  val ifLabel                         = s"if_${annotation.get}"
  val elseLabel                       = s"else_${annotation.get}"
  val endLabel                        = s"endif_${annotation.get}"
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
