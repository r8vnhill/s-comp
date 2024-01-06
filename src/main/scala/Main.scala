package cl.ravenhill.scum

import ast.{IdLiteral, If, Let, NumericLiteral}

import java.util.UUID

given Metadata[String] with {

  /** Retrieves the metadata as a unique UUID string.
    *
    * @return
    *   A string representation of a UUID, serving as unique metadata.
    */
  override def data: String = UUID.randomUUID().toString

  /** Returns a string representation of the metadata suitable for assembly labels.
    *
    * This method modifies the standard UUID string format by replacing hyphens with underscores, making it compliant
    * with the syntactical rules for assembly labels, where hyphens are not legal characters.
    *
    * @return
    *   The metadata string with hyphens replaced by underscores, suitable for use as an assembly label.
    */
  override def toString: String = data.replace("-", "_")
}

given intToNumericLiteral: Conversion[Int, NumericLiteral[String]] with {
  override def apply(v1: Int): NumericLiteral[String] = NumericLiteral(v1.toLong)
}

given stringToIdLiteral: Conversion[String, IdLiteral[String]] with {
  override def apply(v1: String): IdLiteral[String] = IdLiteral(v1)
}

@main def main(args: String*): Unit = {
  val inputFile = scala.io.Source.fromFile(args(0))
  val input     = inputFile.mkString
  inputFile.close()
  val ast     = Let("x", If(10, 2, 0), If("x", input.toInt, 999))
  val program = compiler.compileProgram(ast)
  println(s"; $ast")
  println(program)
}
