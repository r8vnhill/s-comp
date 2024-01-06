package cl.ravenhill.scum
package ast.terminal

import generators.*

import cl.ravenhill.scum.ast.IdLiteral
import org.scalacheck.Gen

class IdLiteralTest extends AbstractScumTest with CommonGenerators {

  "Id Literal instance" - {
    "has a 'symbol' set in the constructor" in {
      forAll(generateStringLabel) { sym =>
        IdLiteral(sym).sym should be(sym)
      }
    }
    
    "can be converted to String" in {
      forAll(generateStringLabel) { sym =>
        IdLiteral(sym).toString should be(sym)
      }
    }
  }
}
