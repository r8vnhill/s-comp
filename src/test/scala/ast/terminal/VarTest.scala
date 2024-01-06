package cl.ravenhill.scum
package ast.terminal

import generators.*

import cl.ravenhill.scum.ast.IdLiteral
import org.scalacheck.Gen

class VarTest extends AbstractScumTest with CommonGenerators {

  "A Var expression" - {
    "should have a sym property that is set to the symbol passed in the constructor" in {
      forAll(generateStringLabel) { sym =>
        IdLiteral(sym).sym should be(sym)
      }
    }

    "can be converted to prefix notation" in {
      forAll(generateStringLabel) { sym =>
        IdLiteral(sym).toString should be(sym)
      }
    }
  }
}
