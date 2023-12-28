package cl.ravenhill.scomp

import ass.*
import ast.terminal.Num
import ast.unary.{Decrement, Increment}

/** Compiles an integer expression into a sequence of assembly instructions.
  *
  * This function takes an integer expression and generates a corresponding sequence of assembly language instructions.
  * Currently, it generates a single `Mov` instruction that moves the given integer expression into the `Rax` register.
  *
  * @param expr
  *   The integer expression to be compiled.
  * @return
  *   A sequence of `Instruction` objects representing the compiled expression.
  */
def compileExpression(expr: ast.Expr): Seq[Instruction] = expr match {
  case ast.terminal.Num(n) => Seq(Mov(Reg(Rax), Const(n)))
  case ast.unary.Increment(e) => compileExpression(e) :+ Add(Reg(Rax), Const(1))
  case ast.unary.Decrement(e) => compileExpression(e) :+ Add(Reg(Rax), Const(-1))
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
def compileProgram(program: ast.Expr): String = {
  val instructions = compileExpression(program)
  val asmString    = instructions.mkString(s"${System.lineSeparator}  ")
  val prelude =
    """section .text
      |global our_code_starts_here
      |our_code_starts_here:""".stripMargin
  val suffix = "ret"
  s"""$prelude
     |  $asmString
     |  $suffix""".stripMargin
}

@main def main(args: String*): Unit = {
  val inputFile    = scala.io.Source.fromFile(args(0))
  val input = inputFile.mkString
  inputFile.close()
  val program = compileProgram(Decrement(Increment(Increment(Num(input.toInt)))))
  println(program)
}
