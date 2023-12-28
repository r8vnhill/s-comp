
package cl.ravenhill.scomp
package ast.unary

import org.scalacheck.Gen

class DoubledTest extends AbstractScompTest {
  
  "A Doubled expression" - {
    "should store the sub-expression passed to the constructor" in {
      forAll(Gen.expr()) { e =>
        Doubled(e).expr should be (e)
      }
    }
  }
}
