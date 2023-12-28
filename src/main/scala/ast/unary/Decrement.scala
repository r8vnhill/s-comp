
package cl.ravenhill.scomp
package ast.unary

case class Decrement(expr: ast.Expr) extends ast.Expr {
  override def toPrefix: String = s"(dec ${expr.toPrefix})"
}
