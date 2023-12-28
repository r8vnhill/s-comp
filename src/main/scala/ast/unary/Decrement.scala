
package cl.ravenhill.scomp
package ast.unary

/** Represents a 'decrement' expression in an abstract syntax tree (AST).
 *
 * This case class is part of the AST structure and models the decrement operation (--) on an expression.
 * It encapsulates an expression and provides the functionality to apply a decrement operation to it.
 * This class is useful in languages or contexts where expressions can be directly decremented.
 *
 * @param expr The expression to be decremented.
 */
case class Decrement(expr: ast.Expr) extends ast.Expr {

  /** Provides a string representation of the decrement expression.
   *
   * This method overrides the `toString` method to return a string representation of the decrement operation
   * applied to the expression. The format is "--(expression)", where 'expression' is the expression being
   * decremented.
   *
   * @return A string representing the decrement operation on the expression.
   */
  override def toString: String = s"--($expr)"
}
