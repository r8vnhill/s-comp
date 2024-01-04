package cl.ravenhill.scum
package ast.unary

import generators.expr

import org.scalacheck.Gen

class IncrementTest extends AbstractScumTest {
  "An Increment operation" - {
    "should have an expr property that stores the value passed to the constructor" in {
      forAll(Gen.expr()) { expr =>
        IncrementImpl(expr).expr should be(expr)
      }
    }

    "can be converted to a String" in {
      forAll(Gen.expr()) { expr =>
        val prefix = IncrementImpl(expr).toString
        prefix should have length (expr.toString.length + 4) // 4 = "++()"
        prefix should (startWith("++(") and endWith(")"))
        prefix should include(expr.toString)
      }
    }
  }
}
