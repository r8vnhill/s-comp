
package cl.ravenhill.scomp
package ast

import ast.terminal.Num

class NumTest extends AbstractScompTest {
  "A number" - {
    "should have a value property that is set according to the constructor argument" in {
        forAll { (n: Int) =>
          Num(n).n should be (n)
        }
    }
    
    "can be converted to prefix notation" in {
      forAll { (n: Int) =>
        Num(n).toPrefix should be (n.toString)
      }
    }
  }
}
