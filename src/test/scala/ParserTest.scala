package cl.ravenhill.scomp

import ast.EOF

import org.scalacheck.Gen
import org.scalatest.Inside.inside

import scala.util.Success

class ParserTest extends AbstractScompTest {

  "An empty string should result in an empty AST" in {
    parse("") should matchPattern { case util.Success(EOF) =>
    }
  }

  "Parsing an invalid expression should result in an error" in {
    forAll(Gen.alphaStr.filterNot(_.isInt).filterNot(_.isBlank)) { (expr: String) =>
      parse(expr) should matchPattern { case util.Failure(ParserException(_)) => }
    }
  }

  "A Natural number should be parsed correctly" in {
    forAll { (expected: Int) =>
      inside(parse(expected.toString)) { case Success(ast.Num(actual)) =>
        actual should be(expected)
      }
    }
  }

  "When parsing an addition" - {
    "the left and right operands should be parsed correctly" in {
      forAll { (left: Int, right: Int) =>
        inside(parse(s"+ $left $right")) { case Success(ast.Plus(actualLeft, actualRight)) =>
          actualLeft should matchPattern { case ast.Num(`left`) => }
          actualRight should matchPattern { case ast.Num(`right`) => }
        }
      }
    }

    "should result in an error when the left operand is missing" in {
      forAll { (right: Int) =>
        parse(s"+ $right") should matchPattern { case util.Failure(ParserException(_)) => }
      }
    }
  }

  "When parsing a multiplication" - {
    "the left and right operands should be parsed correctly" in {
      forAll { (left: Int, right: Int) =>
        inside(parse(s"* $left $right")) { case Success(ast.Times(actualLeft, actualRight)) =>
          actualLeft should matchPattern { case ast.Num(`left`) => }
          actualRight should matchPattern { case ast.Num(`right`) => }
        }
      }
    }

    "should result in an error when the left operand is missing" in {
      forAll { (right: Int) =>
        parse(s"* $right") should matchPattern { case util.Failure(ParserException(_)) => }
      }
    }
  }

  "When parsing nested expressions" - {
    val expressions = Table(
      ("expression", "parsed"),
      ("+ * 0 0 2", ast.Plus(ast.Times(ast.Num(0), ast.Num(0)), ast.Num(2))),
      ("* + 1 2 3", ast.Times(ast.Plus(ast.Num(1), ast.Num(2)), ast.Num(3))),
      ("+ * 1 2 * 3 4", ast.Plus(ast.Times(ast.Num(1), ast.Num(2)), ast.Times(ast.Num(3), ast.Num(4)))),
    )
    "the left and right operands should be parsed correctly" in {
      forAll(expressions) { (expression: String, expected: ast.Expr) =>
        inside(parse(expression)) { case Success(actual) =>
          actual should matchPattern { case `expected` => }
        }
      }
    }
  }
}
