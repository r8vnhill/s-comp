package cl.ravenhill.scum
package ast

import generators.*

import org.scalacheck.Gen

class VarTest extends AbstractScumTest {

  "A Var expression" - {
    "should have a sym property that is set to the symbol passed in the constructor" in {
      forAll(Gen.stringLabel) { sym =>
        Var(sym).sym should be(sym)
      }
    }

    "can be converted to prefix notation" in {
      forAll(Gen.stringLabel) { sym =>
        Var(sym).toString should be(sym)
      }
    }
  }
}
