package cl.ravenhill.scum
package asm.instruction

/** Trait for representing the 'sub' (subtract) instruction in assembly language.
  *
  * `SubImpl` is a trait designed to encapsulate the 'sub' instruction, which is commonly used in assembly language for
  * arithmetic subtraction. This instruction subtracts the value in the source operand (`src`) from the value in the
  * destination operand (`dest`) and stores the result in the destination operand. The trait provides a structured and
  * type-safe way to represent the 'sub' instruction in Scala-based assembly language representations or interpreters.
  *
  * The `SubImpl` trait takes two parameters, `dest` and `src`, representing the destination and source operands for the
  * subtraction operation, respectively. Both parameters are of type `ass.Arg`, allowing for versatile representation of
  * assembly language operands.
  *
  * @param dest
  *   The destination operand for the 'sub' instruction, of type `ass.Arg`.
  * @param src
  *   The source operand for the 'sub' instruction, of type `ass.Arg`.
  */
trait SubImpl(dest: asm.Arg, src: asm.Arg) {

  /** Returns a string representation of the 'sub' instruction.
    *
    * This method overrides the standard `toString` method to provide a string representation of the 'sub' instruction
    * in assembly language syntax. The format is "sub dest, src", where 'dest' is the destination operand and 'src' is
    * the source operand for the subtraction. This representation assists in debugging, logging, or displaying the
    * instruction in a readable format.
    *
    * @return
    *   A string representing the 'sub' instruction in assembly language syntax.
    */
  override def toString: String = s"sub $dest, $src"
}
