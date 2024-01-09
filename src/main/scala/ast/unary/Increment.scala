package cl.ravenhill.scum
package ast.unary

import ast.Expression

import cl.ravenhill.scum.ToStringMode.{DEBUG, NORMAL}

/** Represents an increment operation (`++`) in an abstract syntax tree (AST) with optional metadata.
  *
  * The `Increment` trait encapsulates the behavior of an increment operation. It takes an expression from the AST as
  * its argument, indicating the expression to be incremented, along with optional metadata. This trait provides a
  * custom string representation for the increment operation, which varies depending on the global `toStringMode`. This
  * functionality is useful for displaying or debugging the AST, allowing a clear and adaptable representation of the
  * increment operation.
  *
  * __Usage:__ Extend this trait in classes or objects that represent an increment operation in an AST. The expression
  * to be incremented and its associated metadata are passed at instantiation. The metadata can include additional
  * information like type annotations, source code locations, or other relevant data.
  *
  * @param expr
  *   The expression to be incremented, represented as an `Expression[_]`.
  * @param metadata
  *   Optional metadata associated with the increment operation.
  */
private[ast] trait Increment(val expr: Expression[_], val metadata: Option[_]) {

  /** Provides a string representation of the increment operation, potentially including metadata.
    *
    * This method overrides the default `toString` method. Depending on the `toStringMode`, it either returns a concise
    * representation in the format `++(expr)` or a more detailed representation including both the `expr` and
    * `metadata`. This adaptability is helpful for different use cases, such as visualizing the increment operation in
    * the AST or providing detailed information during debugging.
    *
    * @return
    *   A string representing the increment operation, with detail level varying based on `toStringMode`.
    */
  override def toString: String = toStringMode match {
    case ToStringMode.NORMAL => s"++($expr)"
    case ToStringMode.DEBUG  => s"Increment(expr=$expr, metadata=$metadata)"
  }
}
