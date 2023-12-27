package cl.ravenhill.scomp

import ast.{binary, terminal}

import cl.ravenhill.scomp.ast.binary.{Plus, Times}
import cl.ravenhill.scomp.ast.terminal.{False, Num, True, Var}

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
        interpret(environment, ast.If(terminal.True, Var("x"), terminal.Var("y"))) should matchPattern {
          case util.Success(1) =>
        }
      }

      "should return the second expression when the condition is false" in {
        val environment = Environment(Map("x" -> 1, "y" -> 2))
        interpret(environment, ast.If(terminal.False, terminal.Var("x"), terminal.Var("y"))) should matchPattern {
          case util.Success(2) =>
        }
      }
    }
  }

  "A simplifier" - {
    "should simplify expressions with zero" - {
      "in addition on the left" in {
        simplify(binary.Plus(terminal.Num(0), terminal.Var("x"))) should be(terminal.Var("x"))
      }

      "in addition on the right" in {
        simplify(binary.Plus(terminal.Var("x"), terminal.Num(0))) should be(terminal.Var("x"))
      }

      "in multiplication on the left" in {
        simplify(binary.Times(terminal.Num(0), terminal.Var("x"))) should be(terminal.Num(0))
      }

      "in multiplication on the right" in {
        simplify(binary.Times(terminal.Var("x"), terminal.Num(0))) should be(terminal.Num(0))
      }
    }

    "should simplify expressions with one in multiplication" - {
      "on the left" in {
        simplify(binary.Times(terminal.Num(1), terminal.Var("x"))) should be(terminal.Var("x"))
      }

      "on the right" in {
        simplify(binary.Times(terminal.Var("x"), terminal.Num(1))) should be(terminal.Var("x"))
      }
    }

    "should not change already simplified expressions" in {
      val expr = terminal.Var("x")
      simplify(expr) should be(expr)
    }

    "should simplify nested expressions" in {
      val expr = binary.Plus(terminal.Num(0), binary.Plus(terminal.Var("x"), terminal.Num(0)))
      simplify(expr) should be(terminal.Var("x"))
    }
  }
}
