package cl.ravenhill.scum
package ass

import org.scalacheck.Gen
import generators.*

class ArgTest extends AbstractScumTest {

  "A Constant" - {
    "should have a value property that is set accordingly to the constructor" in {
      forAll { (value: Int) =>
        Constant(value).value should be(value)
      }
    }
  }

  "A Registry" - {
    "should store the registry passed to the constructor" in {
      forAll(Gen.register) { reg =>
        RegisterBox(reg).reg should be(reg)
      }
    }
  }

  "A Register Offset" - {
    "stores a register" in {
      forAll(Gen.register, Gen.int(-10, 10)) { (register, offset) =>
        RegisterOffset(register, offset)
      }
    }

    "stores an offset" in {
      forAll(Gen.register, Gen.int(-10, 10))
    }
  }
}
