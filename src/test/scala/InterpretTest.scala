package cl.ravenhill.scum

import ast.*

import cl.ravenhill.scum.generators.AstGenerators

import scala.util.Success

class InterpretTest extends AbstractScumTest with AstGenerators {
  private val testCases = Table(
    ("ast", "expected"),

    (NumericLiteral(1), Success(1L)),
    (NumericLiteral(420), Success(420L)),
    (NumericLiteral(1234567890123456789L), Success(1234567890123456789L)),
    (NumericLiteral(-1), Success(-1L)),
    (NumericLiteral(-420), Success(-420L)),
    (NumericLiteral(-1234567890123456789L), Success(-1234567890123456789L)),
    (NumericLiteral(0), Success(0L)),
    (NumericLiteral(Long.MinValue), Success(Long.MinValue)),
    (NumericLiteral(Long.MaxValue), Success(Long.MaxValue)),

    (IdLiteral("x"), Success(1L)),
    (IdLiteral("y"), Success(2L)),
    (IdLiteral("z"), Success(3L)),

    (Increment(IdLiteral("x")), Success(2L)),
    (Increment(Increment(NumericLiteral(1))), Success(3L)),

    (Decrement(IdLiteral("x")), Success(0L)),
    (Decrement(Decrement(NumericLiteral(1))), Success(-1L)),
  )

  "Interpreter can interpret syntax tree" in {
    forAll(testCases) { (ast, expected) =>
      val result = interpret(ast, Map("x" -> 1L, "y" -> 2L, "z" -> 3L))
      result should be(expected)
    }
  }

  "Decrement and increment are symmetric properties" in {
    forAll(generateExpression()) { ast =>
     interpret(ast, Map("ZERO" -> 0)) should be(interpret(Increment(Decrement(ast)), Map("ZERO" -> 0)))
     interpret(ast, Map("ZERO" -> 0)) should be(interpret(Decrement(Increment(ast)), Map("ZERO" -> 0)))
    }
  }
}
