package cl.ravenhill.scum
package ast

import cl.ravenhill.scum.ast.ternary.{If, Let}

/** Base trait for an expression in an abstract syntax tree (AST).
  *
  * This trait forms the foundation of a polymorphic AST structure, where each expression can optionally carry
  * additional metadata of type `A`. The metadata can be used for various purposes such as attaching source code
  * locations, type information, or other relevant data.
  *
  * @tparam A
  *   Type of the optional metadata associated with the expression.
  */
sealed trait Expression[A] {
  val metadata: Option[A]
}

/** Base trait for literal expressions in an AST.
  *
  * @tparam A
  *   Type of the optional metadata associated with the literal.
  */
sealed trait Literal[A] extends Expression[A]

/** Represents a numeric literal expression in an AST.
  *
  * @tparam A
  *   Type of the optional metadata associated with the numeric literal.
  * @param n
  *   The numeric value of the literal.
  * @param metadata
  *   The optional metadata associated with this numeric literal.
  */
case class NumericLiteral[A](override val n: Long, override val metadata: Option[A] = None)
    extends Literal[A]
    with literal.Numeric(n, metadata)

/** Represents an identifier literal expression in an AST.
  *
  * @tparam A
  *   Type of the optional metadata associated with the identifier literal.
  * @param sym
  *   The string symbol representing the identifier.
  * @param metadata
  *   The optional metadata associated with this identifier literal.
  */
case class IdLiteral[A](sym: String, override val metadata: Option[A] = None)
    extends Literal[A]
    with literal.Id(sym, metadata)

/** Base trait for unary operation expressions in an AST.
  *
  * @tparam A
  *   Type of the optional metadata associated with the unary operation.
  * @param expr
  *   The expression the unary operation is applied to.
  */
sealed trait UnaryOperation[A](val expr: Expression[A]) extends Expression[A]

/** Represents a 'decrement' unary operation in an abstract syntax tree (AST).
  *
  * @tparam A
  *   Type of the optional metadata associated with the unary operation.
  * @param expr
  *   The expression the decrement operation is applied to.
  * @param metadata
  *   The optional metadata associated with this decrement operation.
  */
case class Decrement[A](override val expr: Expression[A], override val metadata: Option[A] = None)
    extends UnaryOperation[A](expr)
    with unary.Decrement[A](expr, metadata)

/** Represents an 'increment' unary operation in an AST.
  *
  * @tparam A
  *   Type of the optional metadata associated with the unary operation.
  * @param expr
  *   The expression the increment operation is applied to.
  * @param metadata
  *   The optional metadata associated with this increment operation.
  */
case class Increment[A](override val expr: Expression[A], override val metadata: Option[A] = None)
    extends UnaryOperation[A](expr)
    with unary.Increment(expr, metadata)

/** Represents a 'doubled' unary operation in an AST.
  *
  * @tparam A
  *   Type of the optional metadata associated with the unary operation.
  * @param expr
  *   The expression the doubled operation is applied to.
  * @param metadata
  *   The optional metadata associated with this doubled operation.
  */
case class Doubled[A](override val expr: Expression[A], override val metadata: Option[A] = None)
    extends UnaryOperation[A](expr)
    with unary.Doubled(expr, metadata)

/** Base trait for binary operation expressions in an AST.
  *
  * @tparam A
  *   Type of the optional metadata associated with the binary operation.
  * @param left
  *   The left operand of the binary operation.
  * @param right
  *   The right operand of the binary operation.
  */
sealed trait BinaryOperation[A](val left: Expression[A], val right: Expression[A]) extends Expression[A]

/** Represents an 'addition' binary operation in an AST.
  *
  * @tparam A
  *   Type of the optional metadata associated with the binary operation.
  * @param left
  *   The left operand of the addition.
  * @param right
  *   The right operand of the addition.
  * @param metadata
  *   The optional metadata associated with this addition operation.
  */
case class Plus[A](
    override val left: Expression[A],
    override val right: Expression[A],
    override val metadata: Option[A] = None
) extends BinaryOperation[A](left, right)
    with binary.InfixOperatorImpl("+", left, right, metadata)

/** Represents a 'subtraction' binary operation in an AST.
  *
  * @tparam A
  *   Type of the optional metadata associated with the binary operation.
  * @param left
  *   The left operand of the subtraction.
  * @param right
  *   The right operand of the subtraction.
  * @param metadata
  *   The optional metadata associated with this subtraction operation.
  */
case class Minus[A](
    override val left: Expression[A],
    override val right: Expression[A],
    override val metadata: Option[A] = None
) extends BinaryOperation[A](left, right)
    with binary.InfixOperatorImpl("-", left, right, metadata)

/** Represents a 'multiplication' binary operation in an AST.
  *
  * @tparam A
  *   Type of the optional metadata associated with the binary operation.
  * @param left
  *   The left operand of the multiplication.
  * @param right
  *   The right operand of the multiplication.
  * @param metadata
  *   The optional metadata associated with this multiplication operation.
  */
case class Times[A](
    override val left: Expression[A],
    override val right: Expression[A],
    override val metadata: Option[A] = None
) extends BinaryOperation[A](left, right)
    with binary.InfixOperatorImpl("*", left, right, metadata)

/** Represents a conditional 'if' expression in an AST.
  *
  * @tparam A
  *   Type of the optional metadata associated with the conditional expression.
  * @param predicate
  *   The condition to be evaluated.
  * @param thenBranch
  *   The expression to be executed if the condition is true.
  * @param elseBranch
  *   The expression to be executed if the condition is false.
  * @param metadata
  *   The optional metadata associated with this conditional expression.
  */
case class If[A](
    override val predicate: Expression[A],
    override val thenBranch: Expression[A],
    override val elseBranch: Expression[A],
    override val metadata: Option[A] = None
) extends Expression[A]
    with ternary.If(predicate, thenBranch, elseBranch, metadata)

/** Represents a 'let' expression in an AST.
  *
  * @tparam A
  *   Type of the optional metadata associated with the 'let' expression.
  * @param sym
  *   The symbol (variable name) to bind the expression to.
  * @param expr
  *   The expression whose value is to be bound.
  * @param body
  *   The expression in which the bound variable will be used.
  * @param metadata
  *   The optional metadata associated with this 'let' expression.
  */
case class Let[A](
    override val sym: String,
    override val expr: Expression[A],
    override val body: Expression[A],
    override val metadata: Option[A] = None
) extends Expression[A]
    with ternary.Let(sym, expr, body, metadata)
