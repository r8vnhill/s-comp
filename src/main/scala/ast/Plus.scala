
package cl.ravenhill.scomp
package ast

case class Plus(left: Expr, right: Expr) extends Expr {
  override def toPrefix: String = s"(+ ${left.toPrefix} ${right.toPrefix})"
}
