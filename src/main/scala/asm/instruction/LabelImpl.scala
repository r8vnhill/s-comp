package cl.ravenhill.scum
package asm.instruction

/** Trait for representing a label in assembly language.
  *
  * `LabelImpl` is a trait designed to encapsulate a label used in assembly language programming. Labels in assembly
  * language serve as markers or identifiers for certain positions in the code, typically used as targets for jump
  * instructions or to mark the beginning of procedures or sections of code. The trait provides a straightforward and
  * type-safe way to represent these labels within Scala-based assembly language representations or interpreters.
  *
  * The `LabelImpl` trait takes a single parameter, `label`, which is a `String` representing the name of the label.
  * This allows for a clear representation of labels in assembly language code.
  *
  * @param label
  *   The name of the label as a `String`.
  */
trait LabelImpl(label: String) {

  /** Returns a string representation of the label.
    *
    * This method overrides the standard `toString` method to provide a string representation of the label in assembly
    * language syntax. The format is "label:", where 'label' is the name of the label, followed by a colon to denote its
    * presence as a label in the code. This representation is particularly useful for code generation, debugging, and
    * logging, providing a clear indication of labeled sections or targets within assembly language programs.
    *
    * @return
    *   A string representing the label in assembly language syntax.
    */
  override def toString: String = s"$label:"
}
