package cl.ravenhill.scum
package ast.literal

import cl.ravenhill.scum.ToStringMode.{DEBUG, NORMAL}

/** Trait for representing numeric literals in an abstract syntax tree (AST) with optional metadata.
  *
  * The `Numeric` trait is designed to encapsulate a numeric literal, commonly represented as a long integer value. It
  * is a part of the `ast.terminal` package, indicating its role in representing terminal (leaf) nodes in an AST,
  * specifically for numeric values. The inclusion of metadata extends its utility by allowing the attachment of
  * additional information relevant to each numeric literal, such as type annotations, source code locations, or other
  * contextual data.
  *
  * In addition to the numeric value `n`, this trait provides a custom `toString` method that can output different
  * representations based on the global `toStringMode`. This feature makes it adaptable for various purposes, including
  * debugging, logging, or displaying the value in different formats.
  *
  * @param n
  *   The long integer value of the numeric literal.
  * @param metadata
  *   Optional metadata associated with the numeric literal.
  */
private[ast] trait Numeric(val n: Long, val metadata: Option[_]) {

  /** Returns a string representation of the numeric literal, potentially including metadata.
    *
    * This method overrides the standard `toString` method. Depending on the global `toStringMode`, it either returns
    * just the numeric value `n` or a more detailed representation including both `n` and `metadata`. This behavior
    * allows for flexible and informative representations of numeric literals, which is particularly useful for
    * debugging or detailed logging.
    *
    * @return
    *   A string representation of the numeric literal, with detail level depending on `toStringMode`.
    */
  override def toString: String = toStringMode match {
    case ToStringMode.NORMAL => n.toString
    case ToStringMode.DEBUG  => s"Numeric(n=$n, metadata=$metadata)"
  }
}
