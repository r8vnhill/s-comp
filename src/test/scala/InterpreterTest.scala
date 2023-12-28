package cl.ravenhill.scomp

import ast.*
import ast.binary.{Plus, Times}
import ast.terminal.{False, Num, True, Var}

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
    "a Variable" - {
      "should return the value of the variable it is defined" in {
        val environment = Environment(Map("x" -> 1))
        interpret(environment, Var("x")) should matchPattern { case util.Success(1) =>
        }
      }

      "should fail if the variable is not defined" in {
        val environment = Environment(Map("x" -> 1))
        interpret(environment, Var("y")) should matchPattern { case util.Failure(_) =>
        }
      }
    }

    "a Boolean expression" - {
      "should return 1 when true" in {
        val environment = Environment(Map("x" -> 1, "y" -> 2))
        interpret(environment, True) should matchPattern { case util.Success(1) => }
      }

      "should return 0 when false" in {
        val environment = Environment(Map("x" -> 1, "y" -> 2))
        interpret(environment, False) should matchPattern { case util.Success(0) => }
      }
    }

    "an If expression" - {
      "should return the first expression when the condition is true" in {
        val environment = Environment(Map("x" -> 1, "y" -> 2))
        interpret(environment, ast.If(True, Var("x"), Var("y"))) should matchPattern {
          case util.Success(1) =>
        }
      }

      "should return the second expression when the condition is false" in {
        val environment = Environment(Map("x" -> 1, "y" -> 2))
        interpret(environment, ast.If(False, Var("x"), Var("y"))) should matchPattern {
          case util.Success(2) =>
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
