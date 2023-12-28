package cl.ravenhill.scomp
package ast.terminal

import ast.terminal.{False, True}

class BoolTest extends AbstractScompTest {
  "A True expression can be converted to prefix notation" in {
    val expr = True
    expr.toString should be("true")
  }

  "A False expression can be converted to prefix notation" in {
    val expr = False
    expr.toString should be("false")
  }
}
