package cl.ravenhill.scum
package ast.unary

import generators.AstGenerators

import ast.Doubled
import org.scalacheck.Gen

class DoubledTest extends AbstractScumTest with AstGenerators {

  "A Doubled expression" - {
    "should store the sub-expression passed to the constructor" in {
      forAll(generateExpression()) { e =>
        Doubled(e).expr should be(e)
      }
    }
  }
}
