
package cl.ravenhill.scum
package ass

import org.scalacheck.Gen
import generators.*

class InstructionTest extends AbstractScumTest {
  "A Mov instruction" - {
    "should store the source passed to the constructor" in {
      forAll(Gen.arg, Gen.arg) { (src: Arg, dst: Arg) =>
        Move(dst, src).src should be(src)
      }
    }

    "should store the destination passed to the constructor" in {
      forAll(Gen.arg, Gen.arg) { (src: Arg, dst: Arg) =>
        Move(dst, src).dest should be(dst)
      }
    }

    "can be converted to a String" in {
      forAll(Gen.arg, Gen.arg) { (dest: Arg, src: Arg) =>
        Move(dest, src).toString should be(s"mov $dest, $src")
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
        Increment(dst).dest should be(dst)
      }
    }

    "can be converted to a String" in {
      forAll(Gen.arg) { (dest: Arg) =>
        Increment(dest).toString should be(s"inc $dest")
      }
    }
  }

  "A Dec instruction" - {
    "should store the destination passed to the constructor" in {
      forAll(Gen.arg) { (dst: Arg) =>
        Decrement(dst).dest should be(dst)
      }
    }

    "can be converted to a String" in {
      forAll(Gen.arg) { (dest: Arg) =>
        Decrement(dest).toString should be(s"dec $dest")
      }
    }
  }
  
  "A Cmp instruction" - {
    "should store the source passed to the constructor" in {
      forAll(Gen.arg, Gen.arg) { (src: Arg, dst: Arg) =>
        Compare(dst, src).src should be (src)
      }
    }

    "should store the destination passed to the constructor" in {
      forAll(Gen.arg, Gen.arg) { (src: Arg, dst: Arg) =>
        Compare(dst, src).dest should be(dst)
      }
    }

    "can be converted to a String" in {
      forAll(Gen.arg, Gen.arg) { (dest: Arg, src: Arg) =>
        Compare(dest, src).toString should be(s"cmp $dest, $src")
      }
    }
  }
  
  "A Je instruction" - {
    "should store the label passed to the constructor" in {
      forAll(Gen.stringLabel) { label =>
        JumpIfEqual(label).label should be(label)
      }
    }

    "can be converted to a String" in {
      forAll(Gen.stringLabel) { label =>
        JumpIfEqual(label).toString should be(s"je $label")
      }
    }
  }
  
  "A Jump instruction" - {
    "should store the label passed to the constructor" in {
      forAll(Gen.stringLabel) { label =>
        Jump(label).label should be(label)
      }
    }

    "can be converted to a String" in {
      forAll(Gen.stringLabel) { label =>
        Jump(label).toString should be(s"jmp $label")
      }
    }
  }
  
  "A Label instruction" - {
    "should store the label passed to the constructor" in {
      forAll(Gen.stringLabel) { label =>
        Label(label).label should be(label)
      }
    }

    "can be converted to a String" in {
      forAll(Gen.stringLabel) { label =>
        Label(label).toString should be(s"$label:")
      }
    }
  }
}
