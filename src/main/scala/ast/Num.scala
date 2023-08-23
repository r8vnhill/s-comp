package cl.ravenhill.scomp
package ast

case class Num(n: Int) extends Expr {
  override def toPrefix: String = n.toString
}
