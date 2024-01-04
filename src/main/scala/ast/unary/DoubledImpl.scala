package cl.ravenhill.scum
package ast.unary

import cl.ravenhill.scum.ast.Expression

/** Represents an implementation of a 'doubled' operation in an abstract syntax tree (AST).
  *
  * The `DoubledImpl` trait encapsulates the concept of doubling an expression within an AST. It takes a single
  * expression as its argument and applies a 'doubled' operation to it. This trait is primarily used for enhancing the
  * AST's capabilities by allowing the representation of an expression being doubled. The key feature of this trait is
  * the overridden `toString` method, which provides a custom string representation of the 'doubled' operation, thus
  * aiding in debugging or displaying the AST structure.
  *
  * __Usage:__ Implement this trait in classes or objects that need to represent a 'doubled' operation in the AST. The
  * expression to be doubled should be provided at the time of instantiation.
  *
  * @example
  *   {{{
  * object DoubledExample extends DoubledImpl(Var("x"))
  * println(DoubledExample) // Outputs: doubled(x)
  *   }}}
  *
  * @param expr
  *   The expression that is to be doubled, represented as an `Expression[_]`.
  */
trait DoubledImpl(expr: Expression[_]) {

  /** Provides a string representation of the 'doubled' operation.
    *
    * This method overrides the standard `toString` method to present a specialized string format for the 'doubled'
    * operation. The output format is `doubled(expr)`, where `expr` is the string representation of the underlying
    * expression. This representation is especially useful for visualizing the operation within the AST or during
    * debugging processes.
    *
    * @return
    *   A string representation of the 'doubled' operation on the given expression.
    */
  override def toString: String = s"doubled($expr)"
}
