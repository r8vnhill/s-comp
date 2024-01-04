package cl.ravenhill.scum
package asm.instruction

/** Trait for representing the 'ret' (return) instruction in assembly language.
  *
  * `RetImpl` is a trait that provides a standard representation for the 'ret' instruction, commonly used in assembly
  * language to indicate the end of a function or a subroutine. This instruction is typically used to return control to
  * the calling function or to the operating system.
  *
  * Implementing this trait allows for a consistent and clear representation of the 'ret' instruction in Scala-based
  * assembly language representations or interpreters.
  */
trait RetImpl {

  /** Returns a string representation of the 'ret' instruction.
    *
    * This method overrides the standard `toString` method to return "ret", which is the conventional representation of
    * the return instruction in assembly language. This simple and direct representation aids in debugging, logging, or
    * displaying the instruction in a human-readable format.
    *
    * @return
    *   A string "ret", representing the return instruction.
    */
  override def toString: String = "ret"
}
