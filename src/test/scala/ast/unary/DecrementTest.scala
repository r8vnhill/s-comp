package cl.ravenhill.scum
package ast.unary

import org.scalacheck.Gen
import generators.expr

import cl.ravenhill.scum.ast.Decrement
class DecrementTest extends AbstractScumTest {

  "A Decrement expression" - {
    "should have an expr property that is set accordingly to the constructor" in {
      forAll(Gen.expr()) { expr =>
        Decrement(expr).expr should be(expr)
      }
    }

    "can be converted to a String" in {
      forAll(Gen.expr()) { expr =>
        val str = Decrement(expr).toString
        str should have length(expr.toString.length + 4) // 4 = 2 for the ++ and 2 for the ()
        str should startWith("--(")
        str should endWith(")")
        str should include(expr.toString)
      }
    }
  }
}
