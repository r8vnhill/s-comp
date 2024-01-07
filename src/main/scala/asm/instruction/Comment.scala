package cl.ravenhill.scum
package asm.instruction

/** Trait representing a comment in an assembly language or a similar context.
  *
  * This trait encapsulates the concept of a comment, a commonly used feature in programming and scripting languages for
  * adding descriptive text to the source code. It is particularly useful in contexts like assembly language
  * programming, where comments are used to describe the purpose or behavior of a sequence of instructions. The
  * `Comment` trait stores the text of the comment and provides a custom `toString` method for formatting the comment in
  * a standard way.
  *
  * __Usage:__ Extend this trait in classes that represent elements in a code structure where comments are applicable,
  * such as assembly instructions or script commands. This trait is useful for generating human-readable comments in
  * code generation tasks or for debugging purposes.
  *
  * @param comment
  *   The text of the comment.
  */
private[asm] trait Comment(val comment: String) {

  /** Provides a string representation of the comment.
    *
    * This method overrides the standard `toString` method to return the comment text formatted with a leading semicolon
    * and a space, which is a common convention for comments in many programming and scripting languages.
    *
    * @return
    *   A string representation of the comment in the standard comment format.
    */
  override def toString: String = s"; $comment"
}
