
package cl.ravenhill.scomp

class ParserTest extends AbstractScompTest {
  "Parsing an expression" - {
    "should correctly parse a number" in {
      parse("1") should be (ast.Num(1))
    }

    "should correctly parse a variable" in {
      parse("x") should be (ast.Var("x"))
    }

    "should correctly parse an addition expression" in {
      parse("+ 1 2") should be (ast.Plus(ast.Num(1), ast.Num(2)))
    }

    "should correctly parse a multiplication expression" in {
      parse("* 1 2") should be (ast.Times(ast.Num(1), ast.Num(2)))
    }

    "should throw an IllegalArgumentException when parsing an invalid addition" in {
      a [IllegalArgumentException] should be thrownBy parse("+ 1")
    }

    "should throw an IllegalArgumentException when parsing an invalid multiplication" in {
      a [IllegalArgumentException] should be thrownBy parse("* 1")
    }

    "should correctly parse nested expressions" in {
      parse("+ 1 * 2 3") should be (ast.Plus(ast.Num(1), ast.Times(ast.Num(2), ast.Num(3))))
    }
  }
}
