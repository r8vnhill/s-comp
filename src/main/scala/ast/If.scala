package cl.ravenhill.scomp
package ast

import ast.terminal.Bool

case class If(cond: Bool, thenBranch: Expr, elseBranch: Expr) extends Expr {
  override def toPrefix: String = s"(if ${cond.toPrefix} then ${thenBranch.toPrefix} else ${elseBranch.toPrefix})"
}
