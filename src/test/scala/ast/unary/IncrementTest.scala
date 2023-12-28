package cl.ravenhill.scomp
package ast.unary

import org.scalacheck.Gen

class IncrementTest extends AbstractScompTest {
  "An Increment operation" - {
    "should have an expr property that stores the value passed to the constructor" in {
      forAll(Gen.expr()) { expr =>
        Increment(expr).expr should be(expr)
      }
    }

    "can be converted to Prefix notation" in {
      forAll(Gen.expr()) { expr =>
        val prefix = Increment(expr).toPrefix
        prefix should have length (expr.toPrefix.length + 6)  // 6 = "(inc )".length
        prefix should (startWith("(inc ") and endWith(")"))
        prefix should include(expr.toPrefix)
      }
    }
  }
}
