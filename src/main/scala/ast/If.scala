package cl.ravenhill.scomp
package ast

import ast.terminal.Bool

case class If(cond: Bool, thenBranch: Expr, elseBranch: Expr) extends Expr {
  override def toString: String = s"if ($cond) { $thenBranch } else { $elseBranch }"
}
