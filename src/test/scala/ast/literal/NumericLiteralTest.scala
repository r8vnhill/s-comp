package cl.ravenhill.scum
package ast.literal

import ast.NumericLiteral

class NumericLiteralTest extends AbstractScumTest {
  "Numeric literal instance" - {
    "has a value property set in the constructor" in {
      forAll { (n: Long) =>
        NumericLiteral(n).n should be(n)
      }
    }

    val table = Table(
      ("n", "expected"),
      (0L, "0"),
      (1L, "1"),
      (-1L, "-1"),
      (Long.MaxValue, Long.MaxValue.toString),
      (Long.MinValue, Long.MinValue.toString)
    )
    "can be converted to String" in {
      forAll(table) { (n, expected) =>
        NumericLiteral(n).toString should be(expected)
      }
    }
  }
}
