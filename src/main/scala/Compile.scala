package cl.ravenhill.scomp

def compile(program: Int): String =
  s"""
    |section .text
    |global our_code_starts_here
    |our_code_starts_here:
    |  mov rax, $program
    |  ret
    |""".stripMargin

@main def main(args: String*): Unit =
  val inputFile    = scala.io.Source.fromFile(args(0))
  val inputProgram = inputFile.mkString
  inputFile.close()
  val program = compile(inputProgram.toInt)
  println(program)
