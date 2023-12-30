package cl.ravenhill.scum
package ass

import org.scalacheck.Gen

class ArgTest extends AbstractScumTest {

  "A Constant" - {
    "should have a value property that is set accordingly to the constructor" in {
      forAll { (value: Int) =>
        Const(value).value should be(value)
      }
    }
  }

  "A Registry" - {
    "should store the registry passed to the constructor" in {
      forAll(Gen.oneOf(Seq(Rax))) { reg =>
        Reg(reg).reg should be(reg)
      }
    }
  }
}
