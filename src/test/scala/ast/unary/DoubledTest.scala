package cl.ravenhill.scum
package ast.unary

import generators.expr

import org.scalacheck.Gen

class DoubledTest extends AbstractScumTest {

  "A Doubled expression" - {
    "should store the sub-expression passed to the constructor" in {
      forAll(Gen.expr()) { e =>
        Doubled(e, 0).expr should be(e)
      }
    }
  }
}
