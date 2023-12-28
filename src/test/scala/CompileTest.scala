package cl.ravenhill.scomp

import ass.{Const, Mov, Rax, Reg}

import org.scalacheck.Gen

class CompileTest extends AbstractScompTest {
  "Compiling an expression" - {
    "should return a list containing the expression" in {
      forAll(Gen.int()) { i =>
        val result = compileExpression(i)
        result should have size 1
        result.head should be(Mov(Reg(Rax), Const(i)))
      }
    }
  }

  "Compiling a program" - {
    "should return the expected assembly" in {
      forAll(Gen.int()) { i =>
        val result = compileProgram(i)
        result.split(System.lineSeparator) should have size (4 + 1) // 5 lines + the compiled instructions
        result should (startWith("""section .text
                                  |global our_code_starts_here
                                  |our_code_starts_here:""".stripMargin)
          and endWith("  ret"))
        result.contains(compileExpression(i).mkString(s"${System.lineSeparator}  ")) should be(true)
      }
    }
  }
}
