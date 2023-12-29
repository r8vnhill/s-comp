
package cl.ravenhill.scomp
package ass

import org.scalacheck.Gen
import generators.arg
import generators.mov
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

    "can be converted to a String" in {
      forAll(Gen.arg, Gen.arg) { (dest: Arg, src: Arg) =>
        Mov(dest, src).toString should be(s"mov $dest, $src")
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

    "can be converted to a String" in {
      forAll(Gen.arg, Gen.arg) { (dest: Arg, src: Arg) =>
        Add(dest, src).toString should be(s"add $dest, $src")
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

    "can be converted to a String" in {
      forAll(Gen.arg, Gen.arg) { (dest: Arg, src: Arg) =>
        Sub(dest, src).toString should be(s"sub $dest, $src")
      }
    }
  }

  "An Inc instruction" - {
    "should store the destination passed to the constructor" in {
      forAll(Gen.arg) { (dst: Arg) =>
        Inc(dst).dest should be(dst)
      }
    }

    "can be converted to a String" in {
      forAll(Gen.arg) { (dest: Arg) =>
        Inc(dest).toString should be(s"inc $dest")
      }
    }
  }

  "A Dec instruction" - {
    "should store the destination passed to the constructor" in {
      forAll(Gen.arg) { (dst: Arg) =>
        Dec(dst).dest should be(dst)
      }
    }

    "can be converted to a String" in {
      forAll(Gen.arg) { (dest: Arg) =>
        Dec(dest).toString should be(s"dec $dest")
      }
    }
  }
}
