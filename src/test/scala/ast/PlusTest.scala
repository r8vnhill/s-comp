
package cl.ravenhill.scomp
package ast

class PlusTest extends AbstractScompTest {

  private val expressions = Table(
    ("left", "right", "prefix"),
    (Num(1), Num(2), "(+ 1 2)"),
    (Plus(Num(1), Num(2)), Num(3), "(+ (+ 1 2) 3)"),
  )

  "A Plus operation" - {
    "should have a left operand that is set according to the constructor" in {
      forAll(expressions) { (left, right, _) =>
        Plus(left, right).left should be (left)
      }
    }

    "should have a right operand that is set according to the constructor" in {
      forAll(expressions) { (left, right, _) =>
        Plus(left, right).right should be (right)
      }
    }

    "can be converted to prefix notation" in {
      forAll(expressions) { (left, right, prefix) =>
        Plus(left, right).toPrefix should be (prefix)
      }
    }
  }
}
