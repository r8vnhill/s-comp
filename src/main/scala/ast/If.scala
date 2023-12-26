
package cl.ravenhill.scomp
package ast

case class If (cond: Bool, trueBranch: Expr, falseBranch: Expr) extends Expr {
  override def toPrefix: String = s"(if ${cond.toPrefix} ${trueBranch.toPrefix} ${falseBranch.toPrefix})"
}
