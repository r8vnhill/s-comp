package cl.ravenhill.scum
package asm

import asm.registry.RegisterImpl
import generators.*

import org.scalacheck.Gen

class ArgTest extends AbstractScumTest {

  "A Constant" - {
    "should have a 'value' property that is set accordingly to the constructor" in {
      forAll { (value: Int) =>
        Constant(value).value should be(value)
      }
    }
  }

  "Return object" - {
    "can be converted to String" in {
      Return.toString should be("ret")
    }
  }

  testRegister("RAX", Rax.apply, Rax())
  testRegister("EAX", Eax.apply, Eax())
  testRegister("RSP", Rsp.apply, Rsp())
  
  /** Tests properties and behavior of a given register class.
    *
    * This method conducts a series of tests on a specific register class, identified by its name and constructor. It
    * verifies that the 'offset' property is correctly set through the constructor and defaults to the value defined in
    * `RegisterImpl.defaultOffset`. It also checks that the register's string representation conforms to expected
    * assembly language syntax, both with and without a non-zero offset.
    *
    * @param name
    *   The name of the register class to be tested, used in assertions and string representation checks.
    * @param constructor
    *   A function that constructs an instance of the register class with a given offset.
    * @param defaultConstructor
    *   A function that constructs an instance of the register class with the default offset.
    */
  private def testRegister(name: String, constructor: Int ⇒ Register, defaultConstructor: ⇒ Register): Unit = {
    s"$name register" - {
      "has property 'offset' that" - {
        "is set in the constructor" in {
          forAll { (offset: Int) ⇒
            constructor(offset).offset should be(offset)
          }
        }

        "defaults to `RegisterImpl.defaultOffset`" in {
          defaultConstructor.offset should be(RegisterImpl.defaultOffset)
        }
      }

      "can be converted to a String when" - {
        "the offset is 0" in {
          constructor(0).toString should be(name)
        }

        "the offset is non-zero" in {
          forAll(Gen.int()) { offset ⇒
            whenever(offset != 0) {
              constructor(offset).toString should (
                fullyMatch regex ("""\[([a-zA-Z]+) \+ 8 \* (-?\d+)]""" withGroups (name, offset.toString))
              )
            }
          }
        }
      }
    }
  }
}
