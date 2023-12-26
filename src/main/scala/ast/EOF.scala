package cl.ravenhill.scomp
package ast

/** Represents an End-Of-File (EOF) marker in the context of expression parsing.
  *
  * The `EOF` case object extends the `Expr` trait, providing a specialized representation for the end of an input
  * stream or data. It is particularly useful in parsing operations where a clear and explicit indication of the end of
  * input is required. This object can be used to signal termination conditions in various scenarios, such as iterating
  * through a sequence of expressions or reaching the end of a file or input stream during parsing.
  *
  * As a case object, `EOF` also inherits the properties of being a singleton and immutable, making it an efficient and
  * reliable choice for representing EOF markers across the system.
  */
case object EOF extends Expr {

  /** Returns the prefix representation of this object.
    *
    * The prefix representation of `EOF` is the string `"EOF"`.
    *
    * @return
    *   the prefix representation of this object.
    */
  override def toPrefix: String = "EOF"
}
