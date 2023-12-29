package cl.ravenhill.scomp
package ast.unary

import ast.Expr

/** Represents an increment operation in an expression with an additional annotation.
  *
  * The `Increment` case class is part of the abstract syntax tree (AST) and extends the `Expr[A]` trait. It models the
  * operation of incrementing the value of an expression. This class is utilized to represent the unary increment
  * operation in an AST, encompassing the target expression to be incremented and an annotation of type `A`. The
  * annotation can be used for various purposes such as carrying type metadata, evaluation context, or other relevant
  * information. As a case class, `Increment` inherits Scala's standard features for case classes, like `apply`,
  * `unapply`, `equals`, `hashCode`, and `toString`.
  *
  * @param expr
  *   The expression that is subject to the increment operation.
  * @param annotation
  *   The annotation associated with this increment operation, of type `A`.
  * @tparam A
  *   The type of annotation associated with this instance of the increment operation.
  */
case class Increment[A](expr: Expr[A], annotation: A) extends Expr[A] {

  /** Returns the string representation of the increment operation.
    *
    * This method overrides the `toString` method to provide a string format of the increment operation applied to the
    * expression. The format is "++(expression)", which is a clear and concise representation of the operation.
    *
    * @return
    *   A string representing the increment operation in a readable format.
    */
  override def toString: String = s"++($expr)"
}
