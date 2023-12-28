package cl.ravenhill.scomp
package ast.unary

import org.scalacheck.Gen

class DecrementTest extends AbstractScompTest {

  "A Decrement expression" - {
    "should have an expr property that is set accordingly to the constructor" in {
      forAll(Gen.expr()) { expr =>
        Decrement(expr).expr should be(expr)
      }
    }

    "can be converted to Prefix notation" in {
      forAll(Gen.expr()) { expr =>
        val prefix = Decrement(expr).toPrefix
        prefix should have length(expr.toPrefix.length + 6) // 6 = "(dec )".length
        prefix should startWith("(dec ")
        prefix should endWith(")")
        prefix should include(expr.toPrefix)
      }
    }
  }
}
