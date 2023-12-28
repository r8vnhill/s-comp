
package cl.ravenhill.scomp
package ass

import org.scalacheck.Gen

class InstructionTest extends AbstractScompTest {
  "A Mov instruction" - {
    "should store the source passed to the constructor" in {
      forAll(Gen.arg, Gen.arg) { (src: Arg, dst: Arg) =>
        Mov(dst, src).src should be(src)
      }
    }

    "should store the destination passed to the constructor" in {
      forAll(Gen.arg, Gen.arg) { (src: Arg, dst: Arg) =>
        Mov(dst, src).dest should be(dst)
      }
    }
  }

  "An Add instruction" - {
    "should store the source passed to the constructor" in {
      forAll(Gen.arg, Gen.arg) { (src: Arg, dst: Arg) =>
        Add(dst, src).src should be (src)
      }
    }

    "should store the destination passed to the constructor" in {
      forAll(Gen.arg, Gen.arg) { (src: Arg, dst: Arg) =>
        Add(dst, src).dest should be(dst)
      }
    }
  }
}
