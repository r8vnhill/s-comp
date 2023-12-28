
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

  "A Sub instruction" - {
    "should store the source passed to the constructor" in {
      forAll(Gen.arg, Gen.arg) { (src: Arg, dst: Arg) =>
        Sub(dst, src).src should be (src)
      }
    }

    "should store the destination passed to the constructor" in {
      forAll(Gen.arg, Gen.arg) { (src: Arg, dst: Arg) =>
        Sub(dst, src).dest should be(dst)
      }
    }
  }

  "An Inc instruction" - {
    "should store the destination passed to the constructor" in {
      forAll(Gen.arg) { (dst: Arg) =>
        Inc(dst).dest should be(dst)
      }
    }
  }

  "A Dec instruction" - {
    "should store the destination passed to the constructor" in {
      forAll(Gen.arg) { (dst: Arg) =>
        Dec(dst).dest should be(dst)
      }
    }
  }
}
