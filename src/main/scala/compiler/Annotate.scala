package cl.ravenhill.scum
package compiler

/** Annotates an expression with additional metadata.
  *
  * @param expression
  *   The expression to be annotated.
  * @return
  *   The annotated expression.
  */
private[compiler] def annotate(expression: ast.Expression[String]): ast.Expression[String] = {
  expression match {
    case ast.Let(sym, expr, body) => ast.Let(sym, annotate(expr), annotate(body))
    case ast.IdLiteral(sym)             => ast.IdLiteral(sym)
    case ast.NumericLiteral(n)               => ast.NumericLiteral(n)
    case ast.Increment(e)         => ast.Increment(annotate(e))
    case ast.Decrement(e)         => ast.Decrement(annotate(e))
    case ast.Doubled(e)           => ast.Doubled(annotate(e))
    case ast.Plus(e1, e2)         => ast.Plus(annotate(e1), annotate(e2))
    case ast.Minus(e1, e2)        => ast.Minus(annotate(e1), annotate(e2))
    case ast.Times(e1, e2)        => ast.Times(annotate(e1), annotate(e2))
    case ast.If(pred, thenExpr, elseExpr) =>
      ast.If(annotate(pred), annotate(thenExpr), annotate(elseExpr))
  }
}
