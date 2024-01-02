package cl.ravenhill.scum

import ass.*
import ast.terminal.{Num, Var}
import ast.unary.{Decrement, Doubled, Increment}
import ast.{Expr, Let}

import scala.util.{Failure, Success, Try}

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
    case Let(sym, expr, body, _) =>
      val extendedEnv     = environment + sym
      val compiledBinding = compileExpression(expr, environment)
      val moveToStack     = Seq(Mov(RegOffset(Rsp, -extendedEnv(sym).get), Reg(Rax)))
      val compiledBody    = compileExpression(body, extendedEnv)
      for {
        binding <- compiledBinding
        body    <- compiledBody
      } yield binding ++ moveToStack ++ body
    case Var(sym, _)     => Success(Seq(Mov(Reg(Rax), RegOffset(Rsp, -environment(sym).get))))
    case Num(n, _)       => Success(Seq(Mov(Reg(Rax), Const(n))))
    case Increment(e, _) => compileExpression(e, environment).map(_ :+ Inc(Reg(Rax)))
    case Decrement(e, _) => compileExpression(e, environment).map(_ :+ Dec(Reg(Rax)))
    case Doubled(e, _)   => compileExpression(e, environment).map(_ :+ Add(Reg(Rax), Reg(Rax)))
    case _               => Failure[Seq[Instruction]](UnknownExpressionException(s"Unknown expression: $expr"))
  }

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
  val asmString    = instructions.map(_.mkString(s"${System.lineSeparator}  "))
  val prelude =
    """section .text
      |global our_code_starts_here
      |our_code_starts_here:""".stripMargin
  val suffix = Ret
  s"""$prelude
     |  $asmString
     |  $suffix""".stripMargin
}

@main def main(args: String*): Unit = {
  val inputFile = scala.io.Source.fromFile(args(0))
  val input     = inputFile.mkString
  inputFile.close()
  val ast     = Let("x", Num(input.toInt, 0), Increment(Increment(Decrement(Doubled(Var("x", 1), 2), 3), 4), 5), 6)
  val program = compileProgram(ast)
  println(s"; $ast")
  println(program)
}
