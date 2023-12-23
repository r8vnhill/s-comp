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
}
