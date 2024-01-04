package cl.ravenhill.scum
package asm.impl

/** Represents a constant value in assembly language.
  *
  * `ConstantImpl` is a trait designed to encapsulate a constant integer value within the context of assembly language
  * programming. This trait can be used to represent constant values that are part of assembly instructions, such as
  * immediate values for arithmetic operations or operands for other types of instructions.
  *
  * The implementation focuses on providing a straightforward and clear representation of the constant value.
  *
  * @param value
  *   The integer value of the constant.
  */
trait ConstantImpl(value: Int) {

  /** Returns a string representation of the constant value.
    *
    * This method overrides the standard `toString` method to return the string representation of the `value` property.
    * It provides a simple and direct way to convert the constant value to a string format, which can be useful for
    * debugging, logging, or displaying the value in a human-readable form.
    *
    * @return
    *   A string representation of the constant value.
    */
  override def toString: String = value.toString
}
