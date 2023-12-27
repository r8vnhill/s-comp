package cl.ravenhill.scomp
package ast.binary

import ast.Expr

/** Represents an addition operation in an expression.
  *
  * The `Plus` case class is a concrete implementation of the `Expr` trait. It is used for representing binary addition
  * operations within expressions. The class encapsulates two expressions, `left` and `right`, which are the operands
  * for the addition operation. As a case class, `Plus` benefits from Scala's features like automatic creation of
  * `apply` and `unapply` methods, and default implementations of `equals`, `hashCode`, and `toString`.
  *
  * @param left  The left operand of the addition operation, an instance of `Expr`.
  * @param right The right operand of the addition operation, an instance of `Expr`.
  */
case class Plus(left: Expr, right: Expr) extends Expr {

  /** Converts the addition expression into its prefix notation.
    *
    * The `toPrefix` method for `Plus` constructs a string that represents the addition operation in prefix notation.
    * In prefix notation, the operator precedes the operands. Therefore, this method returns a string of the form
    * "(+ operand1 operand2)", where `operand1` and `operand2` are the prefix representations of the `left` and `right`
    * operands, respectively.
    *
    * @return A `String` representing the addition operation in prefix notation. For example, if `left` is an instance
    *         of `Num(1)` and `right` is an instance of `Num(2)`, the `toPrefix` method will return "(+ 1 2)".
    */
  override def toPrefix: String = s"(+ ${left.toPrefix} ${right.toPrefix})"
}
