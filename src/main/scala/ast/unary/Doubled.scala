package cl.ravenhill.scum
package ast.unary

import ast.Expr

/** Represents a doubling operation in an expression with an additional annotation.
  *
  * The `Doubled` case class is part of the abstract syntax tree (AST) and extends the `Expr[A]` trait. It models the
  * operation of doubling the value of an expression. This class is used to represent the unary operation of doubling in
  * an AST, encapsulating the target expression to be doubled and an annotation of type `A`. The annotation can serve
  * various purposes, such as carrying type metadata, evaluation context, or other relevant information. As a case
  * class, `Doubled` benefits from Scala's standard features for case classes, including `apply`, `unapply`, `equals`,
  * `hashCode`, and `toString`.
  *
  * @param expr
  *   The expression whose value is to be doubled.
  * @param metadata
  *   The annotation associated with this doubling operation, of type `A`.
  * @tparam A
  *   The type of annotation associated with this instance of the doubling operation.
  */
case class Doubled[A](expr: Expr[A])(using override val metadata: Metadata[A]) extends Expr[A] {

  /** Returns the string representation of the doubling operation.
    *
    * This method overrides the `toString` method to provide a string format of the doubling operation applied to the
    * expression. The format is "doubled(expression)", offering a straightforward and readable depiction of the
    * operation.
    *
    * @return
    *   A string representing the doubling operation in a clear format.
    */
  override def toString: String = s"doubled($expr)"
}
