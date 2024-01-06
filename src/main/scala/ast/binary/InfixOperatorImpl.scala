package cl.ravenhill.scum
package ast.binary

import cl.ravenhill.scum.ast.Expression

/** Represents an implementation of an infix operator in an abstract syntax tree (AST).
  *
  * The `InfixOperatorImp` trait encapsulates the behavior of a generic infix operation. It takes a symbol (`sym`)
  * representing the infix operator, and two expressions (`left` and `right`) from the AST as its arguments,
  * representing the operands of the operation. The primary functionality of this trait is to provide a custom string
  * representation for the infix operation, which is useful for displaying or debugging the AST.
  *
  * __Usage:__ This trait can be extended by classes or objects that represent a specific infix operation in an AST. The
  * operator symbol and the expressions to be operated upon (left and right operands) are passed at instantiation.
  *
  * @example
  *   {{{
  * object AddExample extends InfixOperatorImp("+", Var("x"), Num(2))
  * println(AddExample) // Outputs: (x + 2)
  *   }}}
  *
  * @param sym
  *   The symbol representing the infix operator.
  * @param left
  *   The left-hand side expression of the operation.
  * @param right
  *   The right-hand side expression of the operation.
  */
trait InfixOperatorImpl(sym: String, left: Expression[_], right: Expression[_]) {

  /** Provides a string representation of the infix operation.
    *
    * This method overrides the standard `toString` method to present a specialized string format for the infix
    * operation. The output format is `($left $sym $right)`, where `left` and `right` are the string representations of
    * the corresponding expressions in the operation, and `$sym` is the symbol of the operator. This representation is
    * particularly useful for visualizing the operation within the AST or during debugging processes.
    *
    * @return
    *   A string representation of the infix operation.
    */
  override def toString: String = s"($left $sym $right)"
}
