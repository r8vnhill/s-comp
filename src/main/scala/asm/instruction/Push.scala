package cl.ravenhill.scum
package asm.instruction

/** Trait representing a 'push' instruction in assembly language.
  *
  * The `Push` trait encapsulates the assembly 'push' instruction, which is used to push a value onto the stack. This
  * instruction is a fundamental part of stack operations in many assembly languages, allowing for the manipulation of
  * the stack during program execution. The trait stores the value to be pushed onto the stack and provides a custom
  * `toString` method for representing the push instruction in a standard assembly language format.
  *
  * @param value
  *   The argument to be pushed onto the stack, represented as an `asm.Arg`.
  */
trait Push(val value: asm.Arg) {

  /** Provides a string representation of the push instruction.
    *
    * This method overrides the standard `toString` method to return the push instruction in the standard assembly
    * language format. The output is the keyword 'push' followed by the value to be pushed onto the stack.
    *
    * @return
    *   A string representation of the push instruction in assembly language format.
    */
  override def toString: String = s"push $value"
}
