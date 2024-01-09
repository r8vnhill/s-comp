package cl.ravenhill.scum
package ast.binary

import cl.ravenhill.scum.ast.Expression

/** Represents an implementation of an infix operator in an abstract syntax tree (AST).
  *
  * The `InfixOperatorImpl` trait encapsulates the behavior of a generic infix operation. It takes a symbol (`sym`)
  * representing the infix operator, and two expressions (`left` and `right`) from the AST as its arguments,
  * representing the operands of the operation. Additionally, it accepts `metadata` as an optional parameter for
  * additional information related to the operation. The primary functionality of this trait is to provide a custom
  * string representation for the infix operation, useful for displaying or debugging the AST. The representation format
  * varies based on the `toStringMode`.
  *
  * __Usage:__ This trait can be extended by classes or objects that represent a specific infix operation in an AST. The
  * operator symbol, the expressions to be operated upon (left and right operands), and optional metadata are passed at
  * instantiation.
  *
  * @param sym
  *   The symbol representing the infix operator.
  * @param left
  *   The left-hand side expression of the operation.
  * @param right
  *   The right-hand side expression of the operation.
  * @param metadata
  *   Optional additional information related to the operation.
  */
trait InfixOperatorImpl(val sym: String, val left: Expression[_], val right: Expression[_], val metadata: Option[_]) {

  /** Provides a string representation of the infix operation.
    *
    * This method overrides the standard `toString` method to present a specialized string format for the infix
    * operation. Depending on the `toStringMode`, the output format can be `($left $sym $right)` in NORMAL mode, or
    * `${getClass.getSimpleName}(sym='$sym', left=$left, right=$right, metadata=$metadata)` in DEBUG mode. This
    * representation is particularly useful for visualizing the operation within the AST or during debugging processes.
    *
    * @return
    *   A string representation of the infix operation.
    */
  override def toString: String = toStringMode match {
    case ToStringMode.NORMAL => s"($left $sym $right)"
    case ToStringMode.DEBUG  => s"${getClass.getSimpleName}(sym='$sym', left=$left, right=$right, metadata=$metadata)"
  }
}
