package cl.ravenhill.scum

import ast.*

import cl.ravenhill.scum.generators.AstGenerators

import scala.util.Success

class InterpretTest extends AbstractScumTest with AstGenerators {
  "Interpreter can interpret example syntax trees" in {
    val testCases = Table(
      ("ast", "expected"),
      // Id literals
      (IdLiteral("x"), Success(1L)),
      (IdLiteral("y"), Success(2L)),
      (IdLiteral("z"), Success(3L)),
      // Increment
      (Increment(IdLiteral("x")), Success(2L)),
      (Increment(Increment(NumericLiteral(1))), Success(3L)),
      (Increment(Doubled(IdLiteral("y"))), Success(5L)),
      // Decrement
      (Decrement(IdLiteral("x")), Success(0L)),
      (Decrement(Decrement(NumericLiteral(1))), Success(-1L)),
      (Decrement(Doubled(IdLiteral("y"))), Success(3L)),
      // Doubled
      (Doubled(IdLiteral("x")), Success(2L)),
      (Doubled(Doubled(NumericLiteral(1))), Success(4L)),
      // Let
      (Let("a", NumericLiteral(1), IdLiteral("a")), Success(1L)),
      (Let("a", NumericLiteral(1), Let("a", NumericLiteral(2), IdLiteral("a"))), Success(2L)),
      // Plus
      (Plus(NumericLiteral(3), NumericLiteral(4)), Success(7L)),
      (Plus(Increment(IdLiteral("x")), Decrement(IdLiteral("y"))), Success(3L)),
      // Minus
      (Minus(NumericLiteral(10), NumericLiteral(4)), Success(6L)),
      (Minus(IdLiteral("y"), IdLiteral("x")), Success(1L)),
      // Times
      (Times(NumericLiteral(2), NumericLiteral(3)), Success(6L)),
      (Times(Decrement(IdLiteral("z")), Increment(IdLiteral("x"))), Success(4L)),
      // If
      (If(NumericLiteral(1), NumericLiteral(10), NumericLiteral(20)), Success(10L)), // Assuming non-zero is true
      (If(NumericLiteral(0), NumericLiteral(10), NumericLiteral(20)), Success(20L)), // Assuming zero is false
      // Complex Let
      (Let("n", NumericLiteral(5), Plus(IdLiteral("n"), NumericLiteral(10))), Success(15L)),
      (Let("a", Increment(IdLiteral("x")), Let("b", Decrement(IdLiteral("y")), Plus(IdLiteral("a"), IdLiteral("b")))), Success(3L))
    )

    forAll(testCases) { (ast, expected) =>
      val result = interpret(ast, Map("x" -> 1L, "y" -> 2L, "z" -> 3L))
      result should be(expected)
    }
  }

  "Numeric literals are interpreted as themselves" in {
    forAll { (long: Long) =>
      interpret(NumericLiteral(long), Map.empty) should be(Success(long))
    }
  }

  "Decrement and increment are symmetric properties" in {
    forAll(generateExpression()) { ast =>
      interpret(ast, Map("ZERO" -> 0)) should be(interpret(Increment(Decrement(ast)), Map("ZERO" -> 0)))
      interpret(ast, Map("ZERO" -> 0)) should be(interpret(Decrement(Increment(ast)), Map("ZERO" -> 0)))
    }
  }
  
  "Addition and subtraction are symmetric properties" in {
    forAll(generateExpression(), generateExpression()) { (ast1, ast2) =>
        interpret(ast1, Map("ZERO" -> 0)) should be(interpret(Minus(Plus(ast1, ast2), ast2), Map("ZERO" -> 0)))
        interpret(ast1, Map("ZERO" -> 0)) should be(interpret(Plus(Minus(ast1, ast2), ast2), Map("ZERO" -> 0)))
    }
  }
}
