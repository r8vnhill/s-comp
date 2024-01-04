
package cl.ravenhill.scum
package ast.terminal

import cl.ravenhill.scum.ast.Num

class NumTest extends AbstractScumTest {
  "A number" - {
    "should have a value property that is set according to the constructor argument" in {
        forAll { (n: Int) =>
          Num(n).n should be (n)
        }
    }
    
    "can be converted to prefix notation" in {
      forAll { (n: Int) =>
        Num(n).toString should be (n.toString)
      }
    }
  }
}
