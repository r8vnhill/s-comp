package cl.ravenhill.scum
package ast.unary

import generators.AstGenerators
import ast.Increment

import org.scalacheck.Gen

class IncrementTest extends AbstractScumTest with AstGenerators {
  "An Increment operation" - {
    "should have an expr property that stores the value passed to the constructor" in {
      forAll(generateExpression()) { expr =>
        Increment(expr).expr should be(expr)
      }
    }

    "can be converted to a String" in {
      forAll(generateExpression()) { expr =>
        val prefix = Increment(expr).toString
        prefix should have length (expr.toString.length + 4) // 4 = "++()"
        prefix should (startWith("++(") and endWith(")"))
        prefix should include(expr.toString)
      }
    }
  }
}
