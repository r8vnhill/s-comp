package cl.ravenhill.scomp

import ast.binary.{Plus, Times}
import ast.terminal.*
import ast.{binary, terminal}

import org.scalacheck.Gen
import org.scalatest.Inside.inside

import scala.util.Success

class ParserTest extends AbstractScompTest {

  "An empty string should result in an empty AST" in {
    parse("") should matchPattern { case util.Success(EOF) => }
  }

  "Parsing an invalid expression should result in an error" in {
    forAll(
      Gen.asciiPrintableStr
        .filterNot(_.isInt)
        .filterNot(_.isBlank)
        .filterNot(_.matches("""[a-zA-Z][a-zA-Z0-9]*"""))
    ) { (expr: String) =>
      parse(expr) should matchPattern { case util.Failure(ParserException(_)) => }
    }
  }

  "A Natural number should be parsed correctly" in {
    forAll { (expected: Int) =>
      inside(parse(expected.toString)) { case Success(Num(actual)) => actual should be(expected) }
    }
  }

  "A Boolean should be parsed correctly" in {
    forAll { (expected: Boolean) =>
      inside(parse(expected.toString)) {
        case Success(True)  => expected should be(true)
        case Success(False) => expected should be(false)
      }
    }
  }

  "A variable should be parsed correctly" in {
    forAll(Gen.alphaNumStr.filter(_.matches("""[a-zA-Z][a-zA-Z0-9]*"""))) { (expected: String) =>
      inside(parse(expected)) { case Success(Var(actual)) => actual should be(expected) }
    }
  }

  "When parsing an addition" - {
    "the left and right operands should be parsed correctly" in {
      forAll { (left: Int, right: Int) =>
        inside(parse(s"+ $left $right")) { case Success(Plus(actualLeft, actualRight)) =>
          actualLeft should matchPattern { case Num(`left`) => }
          actualRight should matchPattern { case Num(`right`) => }
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
        inside(parse(s"* $left $right")) { case Success(Times(actualLeft, actualRight)) =>
          actualLeft should matchPattern { case Num(`left`) => }
          actualRight should matchPattern { case Num(`right`) => }
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
      ("+ * 0 0 2", binary.Plus(binary.Times(Num(0), Num(0)), Num(2))),
      ("* + 1 2 3", binary.Times(binary.Plus(Num(1), Num(2)), Num(3))),
      (
        "+ * 1 2 * 3 4",
        binary.Plus(binary.Times(Num(1), Num(2)), binary.Times(Num(3), Num(4)))
      ),
      ("* + x 1 2", binary.Times(binary.Plus(Var("x"), Num(1)), Num(2)))
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
