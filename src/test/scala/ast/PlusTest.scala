
package cl.ravenhill.scomp
package ast

import ast.terminal.{Num, Var}

import cl.ravenhill.scomp.ast.binary.{Plus, Times}

class PlusTest extends AbstractScompTest {

  private val expressions = Table(
    ("left", "right", "prefix"),
    (Num(1), Num(2), "(+ 1 2)"),
    (Plus(Num(1), Num(2)), Num(3), "(+ (+ 1 2) 3)"),
    (Num(1), Times(Num(2), Num(3)), "(+ 1 (* 2 3))"),
    (binary.Plus(Num(1), Num(2)), binary.Times(Num(3), Var("x")), "(+ (+ 1 2) (* 3 x))")
  )

  "A Plus operation" - {
    "should have a left operand that is set according to the constructor" in {
      forAll(expressions) { (left, right, _) =>
        binary.Plus(left, right).left should be (left)
      }
    }

    "should have a right operand that is set according to the constructor" in {
      forAll(expressions) { (left, right, _) =>
        binary.Plus(left, right).right should be (right)
      }
    }

    "can be converted to prefix notation" in {
      forAll(expressions) { (left, right, prefix) =>
        binary.Plus(left, right).toPrefix should be (prefix)
      }
    }
  }
}
