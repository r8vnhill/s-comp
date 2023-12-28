package cl.ravenhill.scomp

import ast.*
import ast.binary.{Plus, Times}
import ast.terminal.{Num, Var}

import cl.ravenhill.scomp.ast.unary.{Decrement, Increment}

class InterpreterTest extends AbstractScompTest {
  "An interpreter" - {
    "should be able to interpret a number" in {
      interpret(Environment.empty, Num(1)) should matchPattern { case util.Success(1) =>
      }
    }

    "should be able to interpret a sum" in {
      val environment = Environment(Map("x" -> 1, "y" -> 2))
      interpret(environment, Plus(Var("x"), Var("y"))) should matchPattern { case util.Success(3) =>
      }
    }

    "should be able to interpret a product" in {
      val environment = Environment(Map("x" -> 1, "y" -> 2))
      interpret(environment, Times(Var("x"), Var("y"))) should matchPattern { case util.Success(2) =>
      }
    }
  }

  "Interpreting" - {
    "a Number" - {
      "should return the number" in {
        forAll { (n: Int) =>
          interpret(Environment.empty, Num(n)) should matchPattern { case util.Success(`n`) =>
          }
        }
      }
    }

    "an Increment" - {
      "should return the number plus one" in {
        forAll { (n: Int) =>
          val interpreted = interpret(Environment.empty, Increment(Num(n)))
          interpreted match {
            case util.Success(result) => result should be(n + 1)
            case _ => fail(s"Unexpected result: $interpreted")
          }
        }
      }
    }

    "a Decrement" - {
      "should return the number minus one" in {
        forAll { (n: Int) =>
          val interpreted = interpret(Environment.empty, Decrement(Num(n)))
          interpreted match {
            case util.Success(result) => result should be(n - 1)
            case _ => fail(s"Unexpected result: $interpreted")
          }
        }
      }
    }
  }

  "A simplifier" - {
    "should simplify expressions with zero" - {
      "in addition on the left" in {
        simplify(binary.Plus(Num(0), Var("x"))) should be(Var("x"))
      }

      "in addition on the right" in {
        simplify(binary.Plus(Var("x"), Num(0))) should be(Var("x"))
      }

      "in multiplication on the left" in {
        simplify(binary.Times(Num(0), Var("x"))) should be(Num(0))
      }

      "in multiplication on the right" in {
        simplify(binary.Times(Var("x"), Num(0))) should be(Num(0))
      }
    }

    "should simplify expressions with one in multiplication" - {
      "on the left" in {
        simplify(binary.Times(Num(1), Var("x"))) should be(Var("x"))
      }

      "on the right" in {
        simplify(binary.Times(Var("x"), Num(1))) should be(Var("x"))
      }
    }

    "should not change already simplified expressions" in {
      val expr = Var("x")
      simplify(expr) should be(expr)
    }

    "should simplify nested expressions" in {
      val expr = binary.Plus(Num(0), binary.Plus(Var("x"), Num(0)))
      simplify(expr) should be(Var("x"))
    }
  }
}
