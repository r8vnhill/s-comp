
package cl.ravenhill.scum
package ast.terminal

import ast.terminal.Num

class NumTest extends AbstractScumTest {
  "A number" - {
    "should have a value property that is set according to the constructor argument" in {
        forAll { (n: Int) =>
          Num(n, 1).n should be (n)
        }
    }
    
    "can be converted to prefix notation" in {
      forAll { (n: Int) =>
        Num(n, 1).toString should be (n.toString)
      }
    }
  }
}
