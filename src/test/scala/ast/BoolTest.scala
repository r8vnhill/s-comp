package cl.ravenhill.scomp
package ast

import ast.terminal.{False, True}

class BoolTest extends AbstractScompTest {
  "A True expression can be converted to prefix notation" in {
    val expr = True
    expr.toPrefix should be("true")
  }

  "A False expression can be converted to prefix notation" in {
    val expr = False
    expr.toPrefix should be("false")
  }
}
