package cl.ravenhill.scum

import ast.*

import cl.ravenhill.scum.ToStringMode.DEBUG

private given intToNumericLiteral: Conversion[Int, NumericLiteral[Int]] with {
  override def apply(v1: Int): NumericLiteral[Int] = NumericLiteral(v1.toLong)
}

private given stringToIdLiteral: Conversion[String, IdLiteral[Int]] with {
  override def apply(v1: String): IdLiteral[Int] = IdLiteral(v1)
}

@main def main(args: String*): Unit = {
//  val inputFile = scala.io.Source.fromFile(args(0))
//  val input     = inputFile.mkString
//  inputFile.close()
toStringMode = DEBUG
  val ast     = Plus(Decrement(Minus(4, 3)), Let("x", Times(4, 5), Plus(1, "x")))
  val program = compiler.compileProgram(ast)
  println(program) 
}
