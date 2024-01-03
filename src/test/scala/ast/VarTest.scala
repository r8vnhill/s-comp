package cl.ravenhill.scum
package ast

import ast.terminal.Var

class VarTest extends AbstractScumTest {

  "A Var expression" - {
    "should have a sym property that is set to the symbol passed in the constructor" in {
      forAll { (sym: String) =>
        Var(sym).sym should be(sym)
      }
    }

    "can be converted to prefix notation" in {
      forAll { (sym: String) =>
        Var(sym).toString should be(sym)
      }
    }
  }
}
