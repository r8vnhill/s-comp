package cl.ravenhill.scomp

import ast.Var

class InterpreterTest extends AbstractScompTest {
  "An interpreter" - {
    "should be able to interpret a variable when" - {
      "it is defined" in {
        val environment = Environment(Map("x" -> 1))
        interpret(environment, Var("x")) should matchPattern {
          case util.Success(1) =>
        }
      }

      "it is not defined" in {
        val environment = Environment(Map("x" -> 1))
        interpret(environment, Var("y")) should matchPattern {
          case util.Failure(_) =>
        }
      }
    }

    "should be able to interpret a number" in {
      interpret(Environment.empty, ast.Num(1)) should matchPattern {
        case util.Success(1) =>
      }
    }

    "should be able to interpret a sum" in {
      val environment = Environment(Map("x" -> 1, "y" -> 2))
      interpret(environment, ast.Plus(Var("x"), Var("y"))) should matchPattern {
        case util.Success(3) =>
      }
    }

    "should be able to interpret a product" in {
      val environment = Environment(Map("x" -> 1, "y" -> 2))
      interpret(environment, ast.Times(Var("x"), Var("y"))) should matchPattern {
        case util.Success(2) =>
      }
    }
  }

  "A simplifier" - {
    "should simplify expressions with zero" - {
      "in addition on the left" in {
        simplify(ast.Plus(ast.Num(0), ast.Var("x"))) should be(ast.Var("x"))
      }

      "in addition on the right" in {
        simplify(ast.Plus(ast.Var("x"), ast.Num(0))) should be(ast.Var("x"))
      }

      "in multiplication on the left" in {
        simplify(ast.Times(ast.Num(0), ast.Var("x"))) should be(ast.Num(0))
      }

      "in multiplication on the right" in {
        simplify(ast.Times(ast.Var("x"), ast.Num(0))) should be(ast.Num(0))
      }
    }

    "should simplify expressions with one in multiplication" - {
      "on the left" in {
        simplify(ast.Times(ast.Num(1), ast.Var("x"))) should be(ast.Var("x"))
      }

      "on the right" in {
        simplify(ast.Times(ast.Var("x"), ast.Num(1))) should be(ast.Var("x"))
      }
    }

    "should not change already simplified expressions" in {
      val expr = ast.Var("x")
      simplify(expr) should be(expr)
    }

    "should simplify nested expressions" in {
      val expr = ast.Plus(ast.Num(0), ast.Plus(ast.Var("x"), ast.Num(0)))
      simplify(expr) should be(ast.Var("x"))
    }
  }
}
