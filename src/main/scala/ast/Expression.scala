package cl.ravenhill.scum
package ast

/** Represents the base trait for an expression in an abstract syntax tree (AST).
  *
  * This trait is the foundation for a polymorphic abstract syntax tree structure, where each expression in the tree can
  * carry an additional type parameter `A`. This parameter can be used to attach metadata to each expression, which can
  * be used for various purposes, like attaching source code locations to expressions, or attaching type information to
  * expressions.
  *
  * Implementations of this trait would represent specific kinds of expressions, like numeric literals, identifiers,
  * let-bindings, or primitive operations.
  *
  * @tparam A
  *   The type of the metadata associated with each expression. This allows for attaching additional information to
  *   expressions in a flexible manner.
  */
trait Expression[A] {

  /** The metadata associated with this expression. */
  val metadata: Metadata[A]
}

case class Num[A](n: Int)(using override val metadata: Metadata[A]) extends Expression[A] with terminal.NumImpl(n)

case class Var[A](sym: String)(using override val metadata: Metadata[A])
    extends Expression[A]
    with terminal.VarImpl(sym)
