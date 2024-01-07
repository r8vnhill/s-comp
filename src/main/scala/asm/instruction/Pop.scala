package cl.ravenhill.scum
package asm.instruction

/** Trait representing a 'pop' instruction in assembly language.
  *
  * The `Pop` trait encapsulates the assembly 'pop' instruction, which is used to pop a value from the stack into a
  * specified destination. This instruction plays a key role in stack operations within assembly language programming,
  * facilitating the retrieval of values from the stack. The trait stores the destination argument where the popped
  * value will be stored and provides a custom `toString` method for representing the pop instruction in a standard
  * assembly language format.
  *
  * @param dest
  *   The destination argument where the popped value will be stored, represented as an `asm.Arg`.
  */
private[asm] trait Pop(val dest: asm.Arg) {

  /** Provides a string representation of the pop instruction.
    *
    * This method overrides the standard `toString` method to return the pop instruction in the standard assembly
    * language format. The output is the keyword 'pop' followed by the destination argument where the value from the
    * stack will be stored.
    *
    * @return
    *   A string representation of the pop instruction in assembly language format.
    */
  override def toString: String = s"pop $dest"
}
