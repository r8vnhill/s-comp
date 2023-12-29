package cl.ravenhill.scomp
package ast.unary

import ast.Expr

/** Represents a "doubled" expression in an expression language.
  *
  * `Doubled` is a case class that extends the `Expr` trait, representing an operation that doubles the value of an
  * enclosed expression. This class encapsulates the concept of multiplying an expression's value by two, a common
  * operation in various expression languages. The `Doubled` operation takes another expression as its operand,
  * demonstrating the capability to nest expressions.
  *
  * @param expr
  *   The expression whose value will be doubled. This is an instance of `Expr`, allowing for complex expression
  *   structures through nested expressions.
  */
case class Doubled(expr: Expr) extends Expr {

  /** Converts the "doubled" expression into its prefix notation.
    *
    * This method overrides the `toPrefix` method from the `Expr` trait and provides a specific implementation for the
    * "doubled" expression. In prefix notation, the "doubled" operator precedes its operand. The method converts the
    * entire "doubled" expression into a string representation in prefix form, which is useful for parsing,
    * interpretation, or displaying the expression in a human-readable format.
    *
    * @return
    *   A `String` representing the "doubled" expression in prefix notation. For example, if the enclosed expression is
    *   a numeric literal `3`, it would be represented as "(doubled 3)" in prefix notation.
    */
  override def toString: String = s"doubled($expr)"
}
