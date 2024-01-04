package cl.ravenhill.scum

import asm.*
import ast.unary.{Decrement, Doubled, Increment}
import generators.{expr, num}
import org.scalacheck.Gen

class CompileTest extends AbstractScumTest {
//  "Compiling a numeric expression" - {
//    "should return a list containing the expression" in {
//      forAll(Gen.num()) { i =>
//        val result = compileExpression(i)
//        result should have size 1
//        result.head should be(Mov(Reg(Rax), Const(i.n)))
//      }
//    }
//  }
//
//  "Compiling an increment expression" - {
//    "compiles the subexpression and appends an increment to the list" in {
//      forAll(Gen.expr()) { e =>
//        val result = compileExpression(Increment(e))
//        result should have size(compileExpression(e).size + 1)
//        result.last should be(Inc(Reg(Rax)))
//      }
//    }
//  }
//
//  "Compiling a decrement expression" - {
//    "compiles the subexpression and appends a decrement to the list" in {
//      forAll(Gen.expr()) { e =>
//        val result = compileExpression(Decrement(e))
//        result should have size(compileExpression(e).size + 1)
//        result.last should be(Dec(Reg(Rax)))
//      }
//    }
//  }
//
//  "Compiling a doubled expression" - {
//    "compiles the subexpression and appends an addition to the list" in {
//      forAll(Gen.expr()) { e =>
//        val result = compileExpression(Doubled(e))
//        result should have size (compileExpression(e).size + 1)
//        result.last should be(Add(Reg(Rax), Reg(Rax)))
//      }
//    }
//  }
//
//  "Compiling a program" - {
//    "should return the expected assembly" in {
//      forAll(Gen.expr()) { i =>
//        val result = compileProgram(i)
//        result.split(System.lineSeparator) should have size (4 + compileExpression(i).size)
//        result should (startWith("""section .text
//                                  |global our_code_starts_here
//                                  |our_code_starts_here:""".stripMargin)
//          and endWith("  ret"))
//        result.contains(compileExpression(i).mkString(s"${System.lineSeparator}  ")) should be(true)
//      }
//    }
//  }
}
