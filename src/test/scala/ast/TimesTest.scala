package cl.ravenhill.scomp
package ast

class TimesTest extends AbstractScompTest {

  private val expressions = Table(
    ("left", "right", "prefix"),
    (Num(1), Num(2), "(* 1 2)"),
    (Plus(Num(1), Num(2)), Num(3), "(* (+ 1 2) 3)"),
    (Num(1), Times(Num(2), Num(3)), "(* 1 (* 2 3))"),
    (Plus(Num(1), Num(2)), Times(Num(3), Var("x")), "(* (+ 1 2) (* 3 x))")
  )

  "A Times operation" - {
    "should have a left operand that is set according to the constructor parameter" in {
      forAll(expressions) { (left, right, _) =>
        Times(left, right).left should be(left)
      }
    }

    "should have a right operand that is set according to the constructor parameter" in {
      forAll(expressions) { (left, right, _) =>
        Times(left, right).right should be(right)
      }
    }

    "can be converted to prefix notation" in {
      forAll(expressions) { (left, right, prefix) =>
        Times(left, right).toPrefix should be(prefix)
      }
    }
  }
}
