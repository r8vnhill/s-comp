package cl.ravenhill.scum
package ass

import generators.*

import org.scalacheck.Gen

class ArgTest extends AbstractScumTest {

  "A Constant" - {
    "should have a value property that is set accordingly to the constructor" in {
      forAll { (value: Int) =>
        Constant(value).value should be(value)
      }
    }
  }
}
