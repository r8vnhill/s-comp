package cl.ravenhill.scum

import cl.ravenhill.scum.ast.{IdLiteral, NumericLiteral}

import scala.util.Success

class InterpretTest extends AbstractScumTest {
  val testCases = Table(
    ("ast", "expected"),
    // Numeric literals
    (NumericLiteral(1), Success(1L)),
    (NumericLiteral(420), Success(420L)),
    (NumericLiteral(1234567890123456789L), Success(1234567890123456789L)),
    (NumericLiteral(-1), Success(-1L)),
    (NumericLiteral(-420), Success(-420L)),
    (NumericLiteral(-1234567890123456789L), Success(-1234567890123456789L)),
    (NumericLiteral(0), Success(0L)),
    (NumericLiteral(Long.MinValue), Success(Long.MinValue)),
    (NumericLiteral(Long.MaxValue), Success(Long.MaxValue)),
    // Identifiers
  )

  "Interpreter can interpret syntax tree" in {
    forAll(testCases) { (ast, expected) =>
      val result = interpret(ast, Map("x" -> 1L, "y" -> 2L, "z" -> 3L))
      result should be(expected)
    }
  }
}
