package cl.ravenhill.scum
package asm.instruction

/** Trait for representing the 'je' (jump if equal) instruction in assembly language.
  *
  * `JumpIfEqualImpl` is a trait designed to encapsulate the 'je' instruction, which stands for 'jump if equal'. This
  * instruction is used in assembly language to perform a jump to a specified label if the previous comparison resulted
  * in equality (typically indicated by the zero flag being set). The trait provides a structured way to represent this
  * conditional jump instruction within Scala-based assembly language representations or interpreters.
  *
  * The trait takes a single parameter, `label`, representing the target label to jump to if the condition is met. The
  * parameter is of type `ass.Arg`, allowing for flexible representation of labels or addresses in assembly language.
  *
  * @param label
  *   The target label for the 'je' instruction.
  */
trait JumpIfEqualImpl(label: String) {

  /** Returns a string representation of the 'je' (jump if equal) instruction.
    *
    * This method overrides the standard `toString` method to provide a string representation of the 'je' instruction in
    * assembly language syntax. The format is "je label", where 'label' is the target label for the conditional jump.
    * This representation is helpful for debugging, logging, or displaying the instruction in a human-readable format.
    *
    * @return
    *   A string representing the 'je' instruction in assembly language syntax.
    */
  override def toString: String = s"je $label"
}
