package cl.ravenhill.scomp
package ast.unary

import ast.Expr

/** Represents an increment expression in a simple expression language.
  *
  * `Increment` is a case class that extends the `Expr` trait, representing an increment operation on an expression.
  * This class encapsulates the concept of incrementing a value, which is a common operation in various programming and
  * expression languages. The increment operation is applied to another expression, encapsulated within this class.
  *
  * @param expr
  *   The expression to be incremented. This is an instance of `Expr`, allowing for nested expressions and complex
  *   expression structures.
  */
case class Increment(expr: Expr) extends Expr {

  /** Returns the string representation of this expression.
    *
    * This method overrides the `toString` method inherited from the `Expr` trait. It returns a string representation of
    * this expression, which is the string representation of the `expr` field, with the `++` operator prepended.
    *
    * @return
    *   The string representation of this expression.
    */
  override def toString: String = s"++($expr)"
}
