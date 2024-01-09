package cl.ravenhill.scum
package ast.unary

import cl.ravenhill.scum.ast.Expression

/** Represents an implementation of a 'doubled' operation in an abstract syntax tree (AST) with optional metadata.
  *
  * The `Doubled` trait encapsulates the concept of doubling an expression within an AST. It takes a single expression
  * as its argument, along with optional metadata, and applies a 'doubled' operation to it. This trait enhances the
  * AST's capabilities by allowing the representation of an expression being doubled. The key feature of this trait is
  * the overridden `toString` method, which provides a custom string representation of the 'doubled' operation. This
  * representation varies based on the global [[toStringMode]] and is particularly useful for visualizing the operation
  * within the AST or aiding in debugging processes.
  *
  * __Usage:__ Implement this trait in classes or objects that represent a 'doubled' operation in the AST. The
  * expression to be doubled and its associated metadata should be provided at the time of instantiation. The metadata
  * can include additional information like type annotations, source code locations, or other relevant data.
  *
  * @param expr
  *   The expression that is to be doubled, represented as an `Expression[_]`.
  * @param metadata
  *   Optional metadata associated with the 'doubled' operation.
  */
private[ast] trait Doubled(val expr: Expression[_], val metadata: Option[_]) {

  /** Provides a string representation of the 'doubled' operation, potentially including metadata.
    *
    * This method overrides the standard `toString` method to present a specialized string format for the 'doubled'
    * operation. Depending on the `toStringMode`, it either returns a concise representation in the format
    * `doubled(expr)` or a more detailed representation including both `expr` and `metadata`. This adaptability is
    * especially useful for different use cases, such as visualizing the operation within the AST or providing detailed
    * information during debugging.
    *
    * @return
    *   A string representation of the 'doubled' operation, with detail level varying based on `toStringMode`.
    */
  override def toString: String = toStringMode match {
    case ToStringMode.NORMAL => s"doubled($expr)"
    case ToStringMode.DEBUG  => s"Doubled(expr = $expr, metadata = $metadata)"
  }
}
