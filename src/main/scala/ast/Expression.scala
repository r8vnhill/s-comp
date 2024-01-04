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

/** Represents a numeric literal expression in an abstract syntax tree (AST).
  *
  * This case class extends the `Expression` trait, encapsulating a numeric literal. It is a part of the polymorphic AST
  * structure where each expression can carry additional metadata of type `A`. The `Num` class specifically represents
  * numeric literals in the AST.
  *
  * @tparam A
  *   The type of the metadata associated with this numeric literal expression.
  * @param n
  *   The integer value of the numeric literal.
  * @param metadata
  *   The metadata associated with this numeric literal expression, provided implicitly.
  */
case class Num[A](n: Int)(using override val metadata: Metadata[A]) extends Expression[A] with terminal.NumImpl(n)

/** Represents a variable expression in an abstract syntax tree (AST).
  *
  * This case class extends the `Expression` trait, encapsulating a variable. It is part of the polymorphic AST
  * structure where each expression can carry additional metadata of type `A`. The `Var` class specifically represents
  * variable expressions in the AST, identified by a symbol.
  *
  * @tparam A
  *   The type of the metadata associated with this variable expression.
  * @param sym
  *   The symbol representing the variable.
  * @param metadata
  *   The metadata associated with this variable expression, provided implicitly.
  */
case class Var[A](sym: String)(using override val metadata: Metadata[A])
    extends Expression[A]
    with terminal.VarImpl(sym)

/** Represents a decrement operation expression (`--expr`) in an abstract syntax tree (AST).
  *
  * `Decrement` is a case class that extends the `ast.Expression` trait, specifically to represent a decrement operation
  * in an AST. It encapsulates an expression to be decremented, along with metadata associated with the operation. The
  * `Decrement` class is a part of the polymorphic AST structure where each expression, including operations like
  * decrement, can carry additional metadata of type `A`.
  *
  * The class also mixes in the `unary.DecrementImpl` trait, which provides the implementation details for the decrement
  * operation, including a custom `toString` method for the operation representation.
  *
  * ## Usage: This class is used to represent a decrement operation in an AST, typically in the context of interpreting,
  * compiling, or manipulating expressions in a programming language or a domain-specific language (DSL).
  *
  * @tparam A
  *   The type of the metadata associated with this decrement operation expression.
  * @param expr
  *   The expression that is to be decremented.
  * @param metadata
  *   The metadata associated with this decrement operation expression, provided implicitly.
  * @example
  *   {{{
  * val expr = Var[Int]("x")
  * val decrementExpr = Decrement(expr)
  * println(decrementExpr) // prints "--(x)"
  *   }}}
  */
case class Decrement[A](expr: ast.Expression[A])(using override val metadata: Metadata[A])
    extends ast.Expression[A]
    with unary.DecrementImpl(expr)

case class Increment[A](expr: ast.Expression[A])(using override val metadata: Metadata[A])
    extends ast.Expression[A]
    with unary.IncrementImpl(expr)
