package cl.ravenhill.scum
package asm.instruction

/** Trait for representing the 'add' (addition) instruction in assembly language.
  *
  * `AddImpl` is a trait designed to encapsulate the 'add' instruction, commonly used in assembly language for
  * arithmetic addition. This instruction adds the value in the source operand ([[source]]) to the value in the
  * destination operand ([[destination]]) and stores the result in the destination operand. This trait provides a
  * structured way to represent the 'add' instruction in Scala-based assembly language representations or interpreters.
  *
  * The trait takes two arguments, `destination` and `source`, representing the destination and source operands for the
  * addition operation, respectively. Both arguments are of type `ass.Arg`, allowing for a flexible representation of
  * assembly language operands.
  *
  * @param destination
  *   The destination operand for the 'add' instruction, of type `ass.Arg`.
  * @param source
  *   The source operand for the 'add' instruction, of type `ass.Arg`.
  */
private[asm] trait Add(destination: asm.Arg, source: asm.Arg) {

  /** Returns a string representation of the 'add' instruction.
    *
    * This method overrides the standard `toString` method to return a string representation of the 'add' instruction in
    * assembly language syntax. The format is "add destination, source", where 'destination' is the destination operand
    * and 'source' is the source operand for the addition. This representation is useful for debugging, logging, or
    * displaying the instruction in a human-readable format.
    *
    * @return
    *   A string representing the 'add' instruction in assembly language syntax.
    */
  override def toString: String = s"add $destination, $source"
}
