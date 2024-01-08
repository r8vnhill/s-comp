package cl.ravenhill.scum
package ast

import generators.*

import org.scalacheck.Gen

class LetTest extends AbstractScumTest with AstGenerators {
  "A Let expression" - {
    "should store the symbol passed to the constructor" in {
      forAll(generateStringLabel, generateExpression(), generateExpression()) { (sym, e1, e2) =>
        val let = Let(sym, e1, e2)
        let.sym should be(sym)
      }
    }

    "should store the first expression passed to the constructor" in {
      forAll(generateStringLabel, generateExpression(), generateExpression()) { (sym, e1, e2) =>
        val let = Let(sym, e1, e2)
        let.expr should be(e1)
      }
    }

    "should store the second expression passed to the constructor" in {
      forAll(generateStringLabel, generateExpression(), generateExpression()) { (sym, e1, e2) =>
        val let = Let(sym, e1, e2)
        let.body should be(e2)
      }
    }

    "can be converted to a String" in {
      forAll(generateStringLabel, generateExpression(), generateExpression()) { (sym, e1, e2) =>
        val let = Let(sym, e1, e2)
        let.toString should be(s"($e1).let { $sym -> $e2 }")
      }
    }
  }
}
