package cl.ravenhill.scum
package ast

/** Represents the base trait for an expression in an abstract syntax tree (AST).
  *
  * This trait is the foundation for a polymorphic abstract syntax tree structure, where each expression in the tree can
  * carry an additional type parameter `A`. This parameter can be used to annotate expressions with extra information,
  * such as type information, metadata, or evaluation context.
  *
  * Implementations of this trait would represent specific kinds of expressions, like numeric literals, identifiers,
  * let-bindings, or primitive operations.
  *
  * @tparam A
  *   The type of the annotation associated with each expression. This allows for attaching additional information to
  *   expressions in a flexible manner.
  */
trait Expr[A]
