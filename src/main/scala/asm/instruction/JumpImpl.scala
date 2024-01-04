package cl.ravenhill.scum
package asm.instruction

/** Trait for representing the 'jmp' (unconditional jump) instruction in assembly language.
  *
  * `JumpImpl` is a trait designed to encapsulate the 'jmp' instruction, which is used in assembly language to perform
  * an unconditional jump to a specified label. This instruction causes the program execution to continue from the
  * location denoted by the label, effectively altering the flow of control. The trait provides a structured and
  * type-safe way to represent this jump instruction in Scala-based assembly language representations or interpreters.
  *
  * The `JumpImpl` trait takes a single parameter, `label`, which is a `String` representing the target label for the
  * jump. This allows for a clear and straightforward representation of jump instructions targeting specific points in
  * the code.
  *
  * @param label
  *   The target label as a `String` for the 'jmp' instruction.
  */
trait JumpImpl(label: String) {

  /** Returns a string representation of the 'jmp' (unconditional jump) instruction.
    *
    * This method overrides the standard `toString` method to provide a string representation of the 'jmp' instruction
    * in assembly language syntax. The format is "jmp label", where 'label' is the target label for the jump. This
    * representation is helpful for debugging, logging, or displaying the instruction in a human-readable format, and it
    * aids in understanding the control flow of assembly language programs.
    *
    * @return
    *   A string representing the 'jmp' instruction in assembly language syntax.
    */
  override def toString: String = s"jmp $label"
}
