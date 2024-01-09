package cl.ravenhill.scum

import ast.*

package object compiler {

  private given intToOption: Conversion[Int, Option[Int]] = Some(_)

  /** Annotates an expression with additional metadata.
    *
    * @param expression
    *   The expression to be annotated.
    * @return
    *   The annotated expression.
    */
  def annotate(expression: ast.Expression[Int]): ast.Expression[Int] = {
    annotateNode(expression, 0)._1
  }

  private def annotateNode(node: ast.Expression[Int], cur: Int): (ast.Expression[Int], Int) = {
    node match {
      case lit: Literal[_] => annotateLiteral(lit, cur)
      case op: UnaryOperation[_] => annotateUnaryOperation(op, cur)
      case op: BinaryOperation[_] => annotateBinaryOperation(op, cur)
      case ifExpr: If[_] => annotateIf(ifExpr, cur)
      case letExpr: Let[_] => annotateLet(letExpr, cur)
    }
  }

  private def annotateLiteral(lit: ast.Literal[Int], cur: Int): (ast.Expression[Int], Int) = {
    lit match {
      case NumericLiteral(n, _) => (NumericLiteral(n, cur), cur + 1)
      case IdLiteral(sym, _) => (IdLiteral(sym, cur), cur + 1)
    }
  }

  private def annotateUnaryOperation(op: UnaryOperation[Int], cur: Int): (UnaryOperation[Int], Int) = {
    op match {
      case Decrement(sym, _) => (Decrement(sym, cur), cur + 1)
      case Increment(sym, _) => (Increment(sym, cur), cur + 1)
      case Doubled(sym, _) => (Doubled(sym, cur), cur + 1)
    }
  }

  private def annotateBinaryOperation(op: BinaryOperation[Int], cur: Int): (BinaryOperation[Int], Int) = {
    val (leftAnnotated, leftCur) = annotateNode(op.left, cur)
    val (rightAnnotated, rightCur) = annotateNode(op.right, leftCur)
    op match {
      case Plus(_, _, _) => (Plus(leftAnnotated, rightAnnotated, cur), rightCur + 1)
      case Minus(_, _, _) => (Minus(leftAnnotated, rightAnnotated, cur), rightCur + 1)
      case Times(_, _, _) => (Times(leftAnnotated, rightAnnotated, cur), rightCur + 1)
    }
  }

  private def annotateIf(ifExpr: If[Int], cur: Int): (If[Int], Int) = {
    val (predicateAnnotated, predCur) = annotateNode(ifExpr.predicate, cur)
    val (thenAnnotated, thenCur) = annotateNode(ifExpr.thenBranch, predCur)
    val (elseAnnotated, elseCur) = annotateNode(ifExpr.elseBranch, thenCur)
    (If(predicateAnnotated, thenAnnotated, elseAnnotated, cur), elseCur + 1)
  }

  private def annotateLet(letExpr: Let[Int], cur: Int): (Let[Int], Int) = {
    val (exprAnnotated, exprCur) = annotateNode(letExpr.expr, cur)
    val (bodyAnnotated, bodyCur) = annotateNode(letExpr.body, exprCur)
    (Let(letExpr.sym, exprAnnotated, bodyAnnotated, cur), bodyCur + 1)
  }
}
