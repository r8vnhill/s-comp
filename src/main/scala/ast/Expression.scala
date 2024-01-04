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
sealed trait Expression[A] {

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
  * __Usage__ This class is used to represent a decrement operation in an AST, typically in the context of interpreting,
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

/** Represents an increment operation expression (`++expr`) in an abstract syntax tree (AST).
  *
  * `Increment` is a case class that extends the `ast.Expression` trait, specifically to represent an increment
  * operation in an AST. It encapsulates an expression to be incremented, along with metadata associated with the
  * operation. The `Increment` class is a part of the polymorphic AST structure where each expression, including
  * operations like increment, can carry additional metadata of type `A`.
  *
  * The class also mixes in the `unary.IncrementImpl` trait, which provides the implementation details for the increment
  * operation, including a custom `toString` method for the operation representation.
  *
  * __Usage:__ This class is used to represent an increment operation in an AST, typically in the context of
  * interpreting, compiling, or manipulating expressions in a programming language or a domain-specific language (DSL).
  *
  * @tparam A
  *   The type of the metadata associated with this increment operation expression.
  * @param expr
  *   The expression that is to be incremented.
  * @param metadata
  *   The metadata associated with this increment operation expression, provided implicitly.
  * @example
  *   {{{
  * val expr = Var[Int]("x")
  * val incrementExpr = Increment(expr)
  * println(incrementExpr) // prints "++(x)"
  *   }}}
  */
case class Increment[A](expr: ast.Expression[A])(using override val metadata: Metadata[A])
    extends ast.Expression[A]
    with unary.IncrementImpl(expr)

/** Represents a 'doubled' operation expression in an abstract syntax tree (AST).
  *
  * The `Doubled` case class extends the `Expression` trait to specifically represent a 'doubled' operation on an
  * expression within an AST. It encapsulates an expression that is to be doubled, and includes metadata associated with
  * this operation. The class forms a part of the polymorphic AST structure, where each expression, including operations
  * like 'doubled', can carry additional metadata of type `A`.
  *
  * Additionally, this class mixes in the `unary.DoubledImpl` trait, which provides implementation details for the
  * 'doubled' operation, including a custom `toString` method that represents the operation.
  *
  * ## Usage: `Doubled` is utilized to represent a 'doubled' operation in an AST, commonly found in contexts such as
  * interpreting, compiling, or manipulating expressions in programming or domain-specific languages (DSLs).
  *
  * @tparam A
  *   The type of the metadata associated with this 'doubled' operation expression.
  * @param expr
  *   The expression that is to be doubled.
  * @param metadata
  *   The metadata associated with this 'doubled' operation expression, provided implicitly.
  * @example
  *   {{{
  * val expr = Var[Int]("x")
  * val doubledExpr = Doubled(expr)
  * println(doubledExpr) // prints "doubled(x)"
  *   }}}
  */
case class Doubled[A](expr: Expression[A])(using override val metadata: Metadata[A])
    extends Expression[A]
    with unary.DoubledImpl(expr)

/** Represents an 'if' conditional expression in an abstract syntax tree (AST).
  *
  * The `If` case class extends the `Expression` trait, specifically to represent an 'if' conditional expression in an
  * AST. It encapsulates three components: a predicate expression, and two expressions for the 'then' and 'else'
  * branches of the conditional. Additionally, this class includes metadata associated with the 'if' expression. The
  * `If` class is part of the polymorphic AST structure, where each expression can carry additional metadata of type
  * `A`.
  *
  * This class also implements the `IfImpl` trait, which provides the implementation details for the 'if' conditional,
  * including a custom `toString` method that represents the entire conditional expression.
  *
  * ## Usage: `If` is used to represent an 'if' conditional expression in an AST, commonly found in contexts such as
  * interpreting, compiling, or manipulating expressions in programming or domain-specific languages (DSLs).
  *
  * @tparam A
  *   The type of the metadata associated with this 'if' conditional expression.
  * @param predicate
  *   The expression representing the condition to be evaluated in the 'if' statement.
  * @param thenBranch
  *   The expression to be executed if the predicate evaluates to true.
  * @param elseBranch
  *   The expression to be executed if the predicate evaluates to false.
  * @param metadata
  *   The metadata associated with this 'if' conditional expression, provided implicitly.
  * @example
  *   {{{
  * val condition = Var[Int]("condition")
  * val thenExpr = Var[Int]("thenBranch")
  * val elseExpr = Var[Int]("elseBranch")
  * val ifExpr = If(condition, thenExpr, elseExpr)
  * println(ifExpr) // prints "if (condition) then { thenBranch } else { elseBranch }"
  *   }}}
  */
case class If[A](predicate: Expression[A], thenBranch: Expression[A], elseBranch: Expression[A])(using
    override val metadata: Metadata[A]
) extends Expression[A]
    with IfImpl(predicate, thenBranch, elseBranch)

/** Represents a 'let' binding expression in an abstract syntax tree (AST).
  *
  * The `Let` case class extends the `Expression` trait to specifically represent a 'let' binding expression in an AST.
  * This construct is common in functional programming languages and allows for binding an expression (`expr`) to a
  * symbol (`sym`), which is then utilized within another expression (`body`). This class includes metadata associated
  * with the 'let' binding. It forms a part of the polymorphic AST structure, where each expression can carry additional
  * metadata of type `A`.
  *
  * This class also implements the `LetImpl` trait, providing the implementation details for the 'let' binding,
  * including a custom `toString` method that represents the entire binding expression.
  *
  * __Usage:__ `Let` is used to represent a 'let' binding expression in an AST, commonly found in contexts such as
  * interpreting, compiling, or manipulating expressions in programming or domain-specific languages (DSLs).
  *
  * @tparam A
  *   The type of the metadata associated with this 'let' binding expression.
  * @param sym
  *   The symbol to which the expression is bound.
  * @param expr
  *   The expression to be bound to the symbol.
  * @param body
  *   The body expression where the symbol binding is used.
  * @param metadata
  *   The metadata associated with this 'let' binding expression, provided implicitly.
  * @example
  *   {{{
  * val bindingExpr = Var[Int]("x")
  * val bodyExpr = Var[Int]("y")
  * val letExpr = Let("x", bindingExpr, bodyExpr)
  * println(letExpr) // Outputs a string representation of the 'let' binding expression
  *   }}}
  */
case class Let[A](sym: String, expr: Expression[A], body: Expression[A])(using val metadata: Metadata[A])
    extends Expression[A]
    with LetImpl(sym, expr, body)

case class Plus[A](left: Expression[A], right: Expression[A])(using override val metadata: Metadata[A])
    extends Expression[A]
    with binary.PlusImpl(left, right)
