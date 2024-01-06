
package cl.ravenhill.scum
package asm

import org.scalacheck.Gen
import generators.*

class InstructionTest extends AbstractScumTest with AssemblyGenerators {
  "A Mov instruction" - {
    "should store the source passed to the constructor" in {
      var collected = Map.empty[Arg, Int]
      forAll(generateArg, generateArg) { (src: Arg, dst: Arg) =>
        collected = collected.get(src) match {
          case Some(count) => collected + (src -> (count + 1))
          case None        => collected + (src -> 1)
        }
        Move(dst, src).src should be(src)
      }
      val totalCount = collected.values.sum.toDouble

      val sortedByPercentage = collected
        .map { case (sym, count) => (sym, (count / totalCount) * 100) }
        .toList
        .sortBy(_._2)(Ordering[Double].reverse)

      val prettyPrinted = sortedByPercentage
        .map { case (sym, percentage) => f"$percentage%.2f%% - $sym" }
        .mkString("\n")
      println(prettyPrinted)
    }

    "should store the destination passed to the constructor" in {
      forAll(generateArg, generateArg) { (src: Arg, dst: Arg) =>
        Move(dst, src).dest should be(dst)
      }
    }

    "can be converted to a String" in {
      forAll(generateArg, generateArg) { (dest: Arg, src: Arg) =>
        Move(dest, src).toString should be(s"mov $dest, $src")
      }
    }
  }

  "An Add instruction" - {
    "should store the source passed to the constructor" in {
      forAll(generateArg, generateArg) { (src: Arg, dst: Arg) =>
        Add(dst, src).src should be (src)
      }
    }

    "should store the destination passed to the constructor" in {
      forAll(generateArg, generateArg) { (src: Arg, dst: Arg) =>
        Add(dst, src).dest should be(dst)
      }
    }

    "can be converted to a String" in {
      forAll(generateArg, generateArg) { (dest: Arg, src: Arg) =>
        Add(dest, src).toString should be(s"add $dest, $src")
      }
    }
  }

  "A Sub instruction" - {
    "should store the source passed to the constructor" in {
      forAll(generateArg, generateArg) { (src: Arg, dst: Arg) =>
        Sub(dst, src).src should be (src)
      }
    }

    "should store the destination passed to the constructor" in {
      forAll(generateArg, generateArg) { (src: Arg, dst: Arg) =>
        Sub(dst, src).dest should be(dst)
      }
    }

    "can be converted to a String" in {
      forAll(generateArg, generateArg) { (dest: Arg, src: Arg) =>
        Sub(dest, src).toString should be(s"sub $dest, $src")
      }
    }
  }

  "An Inc instruction" - {
    "should store the destination passed to the constructor" in {
      forAll(generateArg) { (dst: Arg) =>
        Increment(dst).dest should be(dst)
      }
    }

    "can be converted to a String" in {
      forAll(generateArg) { (dest: Arg) =>
        Increment(dest).toString should be(s"inc $dest")
      }
    }
  }

  "A Dec instruction" - {
    "should store the destination passed to the constructor" in {
      forAll(generateArg) { (dst: Arg) =>
        Decrement(dst).dest should be(dst)
      }
    }

    "can be converted to a String" in {
      forAll(generateArg) { (dest: Arg) =>
        Decrement(dest).toString should be(s"dec $dest")
      }
    }
  }
  
  "A Cmp instruction" - {
    "should store the source passed to the constructor" in {
      forAll(generateArg, generateArg) { (src: Arg, dst: Arg) =>
        Compare(dst, src).src should be (src)
      }
    }

    "should store the destination passed to the constructor" in {
      forAll(generateArg, generateArg) { (src: Arg, dst: Arg) =>
        Compare(dst, src).dest should be(dst)
      }
    }

    "can be converted to a String" in {
      forAll(generateArg, generateArg) { (dest: Arg, src: Arg) =>
        Compare(dest, src).toString should be(s"cmp $dest, $src")
      }
    }
  }
  
  "A Je instruction" - {
    "should store the label passed to the constructor" in {
      forAll(generateStringLabel) { label =>
        JumpIfEqual(label).label should be(label)
      }
    }

    "can be converted to a String" in {
      forAll(generateStringLabel) { label =>
        JumpIfEqual(label).toString should be(s"je $label")
      }
    }
  }
  
  "A Jump instruction" - {
    "should store the label passed to the constructor" in {
      forAll(generateStringLabel) { label =>
        Jump(label).label should be(label)
      }
    }

    "can be converted to a String" in {
      forAll(generateStringLabel) { label =>
        Jump(label).toString should be(s"jmp $label")
      }
    }
  }
  
  "A Label instruction" - {
    "should store the label passed to the constructor" in {
      forAll(generateStringLabel) { label =>
        Label(label).label should be(label)
      }
    }

    "can be converted to a String" in {
      forAll(generateStringLabel) { label =>
        Label(label).toString should be(s"$label:")
      }
    }
  }
}
