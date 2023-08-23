
package cl.ravenhill.scomp
package ast

case class Var(sym: String) extends Expr {
  override def toPrefix: String = sym
}
