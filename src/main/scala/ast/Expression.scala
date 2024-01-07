package cl.ravenhill.scum
package ast

sealed trait Expression[A] {
  val metadata: Option[A]
}

case class NumericLiteral[A](n: Long, override val metadata: Option[A] = None)
    extends Expression[A]
    with literal.Numeric(n)

case class IdLiteral[A](sym: String, override val metadata: Option[A] = None)
    extends Expression[A]
    with literal.Id(sym)

sealed trait UnaryOperation[A](val expr: Expression[A]) extends Expression[A]

case class Decrement[A](override val expr: Expression[A], override val metadata: Option[A] = None)
    extends UnaryOperation[A](expr)
    with unary.DecrementImpl(expr)

case class Increment[A](override val expr: ast.Expression[A], override val metadata: Option[A] = None)
    extends ast.UnaryOperation[A](expr)
    with unary.IncrementImpl(expr)

case class Doubled[A](override val expr: Expression[A], override val metadata: Option[A] = None)
    extends UnaryOperation[A](expr)
    with unary.DoubledImpl(expr)

sealed trait BinaryOperation[A](val left: Expression[A], val right: Expression[A]) extends Expression[A]

case class Plus[A](
    override val left: Expression[A],
    override val right: Expression[A],
    override val metadata: Option[A] = None
) extends BinaryOperation[A](left, right)
    with binary.InfixOperatorImpl("+", left, right)

case class Minus[A](
    override val left: Expression[A],
    override val right: Expression[A],
    override val metadata: Option[A] = None
) extends BinaryOperation[A](left, right)
    with binary.InfixOperatorImpl("-", left, right)

case class Times[A](
    override val left: Expression[A],
    override val right: Expression[A],
    override val metadata: Option[A] = None
) extends BinaryOperation[A](left, right)
    with binary.InfixOperatorImpl("*", left, right)

case class If[A](
    predicate: Expression[A],
    thenBranch: Expression[A],
    elseBranch: Expression[A],
    override val metadata: Option[A] = None
) extends Expression[A]
    with IfImpl(predicate, thenBranch, elseBranch)

case class Let[A](sym: String, expr: Expression[A], body: Expression[A], val metadata: Option[A] = None)
    extends Expression[A]
    with LetImpl(sym, expr, body)
