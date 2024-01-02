package cl.ravenhill.scum
package ast.binary

import ast.Expr

/** Represents a plus operation in an expression with annotations.
  *
  * The `Plus` case class extends the `Expr[A]` trait and models the arithmetic addition operation between two
  * expressions. It encapsulates two expressions (`left` and `right`) and an annotation of type `A` for each. The
  * annotations can be used for various purposes, such as carrying type metadata, evaluation context, or other relevant
  * information. This class allows for the representation of addition operations in an abstract syntax tree (AST) for a
  * programming language.
  *
  * The `toString` method provides a string representation of the addition operation in a human-readable format, useful
  * for debugging and displaying the AST.
  *
  * @param left
  *   The left-hand side expression of the addition.
  * @param right
  *   The right-hand side expression of the addition.
  * @param metadata
  *   The annotation associated with the addition operation.
  * @tparam A
  *   The type of annotation associated with each expression in the addition operation.
  */
case class Plus[A](left: Expr[A], right: Expr[A])(using override val metadata: Metadata[A]) extends Expr[A] {

  /** Returns a string representation of the plus operation.
    *
    * This method overrides the `toString` method to provide a string format of the addition operation, displaying both
    * the left and right expressions with an addition symbol between them. The format is "(left + right)".
    *
    * @return
    *   A string depicting the addition operation in a readable format.
    */
  override def toString = s"($left + $right)"
}
