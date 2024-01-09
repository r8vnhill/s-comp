package cl.ravenhill.scum
package compiler

import ast.*

import java.util.UUID

/** Determines if an expression is an immediate expression in the context of a compiler.
  *
  * This function assesses whether a given expression in an abstract syntax tree (AST) is an 'immediate' expression.
  * Immediate expressions are typically those that can be directly acted upon in the compilation process without
  * requiring further breakdown or simplification. In this implementation, numeric literals (`Num`) and variable
  * references (`Var`) are considered immediate, as they represent simple, direct values or references.
  *
  * @param expression
  *   The AST expression to be checked for immediacy.
  * @return
  *   `true` if the expression is an immediate expression (`Num` or `Var`), otherwise `false`.
  */
private def isImmediate[A](expression: Expression[A]): Boolean = expression match {
  case NumericLiteral(_, _) | IdLiteral(_, _) => true
  case _                                      => false
}

/** Determines if an expression is in Administrative Normal Form (ANF) in an abstract syntax tree (AST).
  *
  * ANF is a form of representing expressions where complex sub-expressions are broken down into simpler ones, typically
  * using variables to hold intermediate values. This function checks if a given expression adheres to the ANF criteria.
  * It supports various types of expressions, including unary operations, binary operations, let-bindings, and
  * conditional expressions. The criteria for ANF depend on the specific type of expression:
  *   - For unary and binary operations, the function checks if their operands are immediate.
  *   - For let-bindings, both the expression being bound and the body of the let must be in ANF.
  *   - For conditional expressions, the predicate must be immediate, and both the 'then' and 'else' branches must be in
  *     ANF.
  *   - Other expressions are considered to be in ANF if they are immediate.
  *
  * @param expression
  *   The AST expression to be checked for ANF.
  * @return
  *   `true` if the expression is in ANF, otherwise `false`.
  * @tparam A
  *   The type of metadata associated with the AST expression.
  */
private[compiler] def isAnf[A](expression: Expression[A]): Boolean = expression match {
  case e: UnaryOperation[A]                     => isImmediate(e)
  case e: BinaryOperation[A]                    => isImmediate(e.left) && isImmediate(e.right)
  case Let(_, expr, body, _)                    => isAnf(expr) && isAnf(body)
  case If(predicate, thenBranch, elseBranch, _) => isImmediate(predicate) && isAnf(thenBranch) && isAnf(elseBranch)
  case _                                        => isImmediate(expression)
}

def toAnf[A](expression: Expression[A]): Expression[A] = {
  def helper(expr: Expression[A]): (Expression[A], List[(String, Expression[A])]) = expr match {
    case NumericLiteral(_, _) | IdLiteral(_, _) => (expr, Nil)
    case e: UnaryOperation[A]                   => (e, Nil)
    case Plus(left, right, annotation) =>
      val (leftAns, leftContext)   = helper(left)
      val (rightAns, rightContext) = helper(right)
      val temp                     = s"plus_${UUID.randomUUID().toString.replace("-", "_")}"
      val newExpr                  = IdLiteral[A](temp)
      val newContext               = leftContext ++ rightContext :+ (temp -> Plus(leftAns, rightAns))
      (newExpr, newContext)
    case Minus(left, right, annotation) =>
        val (leftAns, leftContext)   = helper(left)
        val (rightAns, rightContext) = helper(right)
        val temp                     = s"minus_${annotation.get}"
        val newExpr                  = IdLiteral[A](temp)
        val newContext               = leftContext ++ rightContext :+ (temp -> Minus(leftAns, rightAns))
        (newExpr, newContext)
    case Times(left, right, annotation) =>
        val (leftAns, leftContext)   = helper(left)
        val (rightAns, rightContext) = helper(right)
        val temp                     = s"times_${annotation.get}"
        val newExpr                  = IdLiteral[A](temp)
        val newContext               = leftContext ++ rightContext :+ (temp -> Times(leftAns, rightAns))
        (newExpr, newContext)
    case Let(name, expr, body, _) =>
      val (exprAns, exprContext) = helper(expr)
      val (bodyAns, bodyContext) = helper(body)
      val newExpr                = Let(name, exprAns, bodyAns)
      val newContext             = exprContext ++ bodyContext
      (newExpr, newContext)
  }

  // Wrap the expression with let-bindings based on the context
  def wrapWithLets(expr: Expression[A], context: List[(String, Expression[A])]): Expression[A] =
    context.foldRight(expr) { case ((name, e), acc) => Let(name, e, acc) }

  val (anfExpr, context) = helper(expression)
  wrapWithLets(anfExpr, context)
}
