package cl.ravenhill.scum
package ast.unary

import ast.Decrement
import generators.AstGenerators

import org.scalacheck.Gen

class DecrementTest extends AbstractScumTest with AstGenerators {

  "A Decrement expression" - {
    "should have an expr property that is set accordingly to the constructor" in {
      forAll(generateExpression()) { expr =>
        Decrement(expr).expr should be(expr)
      }
    }

    "can be converted to a String" in {
      forAll(generateExpression()) { expr =>
        val str = Decrement(expr).toString
        str should have length (expr.toString.length + 4) // 4 = 2 for the ++ and 2 for the ()
        str should startWith("--(")
        str should endWith(")")
        str should include(expr.toString)
      }
    }
  }
}
