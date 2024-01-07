package cl.ravenhill.scum
package compiler

import cl.ravenhill.scum.ast.*

private given intToOption: Conversion[Int, Option[Int]] = Some(_)

/** Annotates an expression with additional metadata.
  *
  * @param expression
  *   The expression to be annotated.
  * @return
  *   The annotated expression.
  */
private[compiler] def annotate(expression: ast.Expression[Int]): ast.Expression[Int] = {
  def annotate(expression: ast.Expression[Int], cur: Int): (ast.Expression[Int], Int) = {
    expression match
      case NumericLiteral(n, _) => (NumericLiteral(n, cur), cur + 1)
      case IdLiteral(sym, _)    => (IdLiteral(sym, cur), cur + 1)
      case operation: UnaryOperation[_] =>
        operation match
          case Decrement(sym, _) => (Decrement(sym, cur), cur + 1)
          case Increment(sym, _) => (Increment(sym, cur), cur + 1)
          case Doubled(sym, _)   => (Doubled(sym, cur), cur + 1)
      case operation: BinaryOperation[_] =>
        operation match
          case Plus(left, right, _) =>
            val (leftAnnotated, leftCur)   = annotate(left, cur)
            val (rightAnnotated, rightCur) = annotate(right, leftCur)
            (Plus(leftAnnotated, rightAnnotated, cur), rightCur + 1)
      case If(predicate, thenBranch, elseBranch, _) => (If(predicate, thenBranch, elseBranch, cur), cur + 1)
      case Let(sym, expr, body, _)                  => (Let(sym, expr, body, cur), cur + 1)
  }
  annotate(expression, 0)._1
}
