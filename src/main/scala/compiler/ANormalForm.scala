package cl.ravenhill.scum
package compiler

import ast.*

private[compiler] def transformExpressionToAnf[A](
    expr: Expression[A],
    context: List[(String, Expression[A])],
    counter: Int
): (Expression[A], List[(String, Expression[A])], Int) = expr match {
  case immediate @ (NumericLiteral(_, _) | IdLiteral(_, _)) => (immediate, context, counter)
  case e: UnaryOperation[A]                                 => transformUnaryOperationToAnf(e, context, counter)
  case e: BinaryOperation[A]                                => transformBinaryOperationToAnf(e, context, counter)
  case Let(sym, expr, body, _) => transformLetBindingToAnf(context, counter, sym, expr, body)
}

/** Transforms a unary operation into A-Normal Form (ANF).
  *
  * This method processes a unary operation and transforms it into ANF, a form where complex expressions are broken down
  * into simpler ones. The transformation involves ensuring that the operand of the unary operation is an immediate
  * expression. If the operand is not immediate, it is first transformed into ANF, and a temporary variable is
  * introduced to hold the result of this operand.
  *
  * The unary operation is then reapplied to this temporary variable, resulting in a structure where the operation's
  * operand is a simple expression, conforming to ANF rules.
  *
  * @param op
  *   The unary operation to be transformed.
  * @param context
  *   The current transformation context, represented as a list of variable bindings in the form of '(variable name,
  *   expression)'. This context accumulates the necessary 'Let' bindings during the transformation process.
  * @param counter
  *   An integer counter used to generate unique variable names for new temporary variables.
  * @return
  *   A tuple containing the transformed expression in ANF, the updated context, and the incremented counter. The
  *   transformed expression will be a unary operation applied to a temporary variable if the original operand was not
  *   immediate.
  */
private def transformUnaryOperationToAnf[A](
    op: UnaryOperation[A],
    context: List[(String, Expression[A])],
    counter: Int
): (Expression[A], List[(String, Expression[A])], Int) = {
  // The operand is transformed into ANF first. If it's not immediate, a temporary variable is used.
  val (newExpr, newContext, newCounter) = transformExpressionToAnf(op.expr, context, counter)
  // Generate a unique name for the temporary variable.
  val tempVar = s"${op.getClass.getSimpleName.toLowerCase}$$$newCounter"
  // Update the context with the new variable and its corresponding expression.
  val updatedContext = newContext :+ (tempVar -> newExpr)

  // Apply the unary operation to the temporary variable and update the context and counter accordingly.
  op match {
    case Decrement(_, _) => (Decrement(IdLiteral[A](tempVar)), updatedContext, newCounter + 1)
    case Increment(_, _) => (Increment(IdLiteral[A](tempVar)), updatedContext, newCounter + 1)
    case Doubled(_, _)   => (Doubled(IdLiteral[A](tempVar)), updatedContext, newCounter + 1)
  }
}

/** Transforms a binary operation into A-Normal Form (ANF).
  *
  * This method processes a binary operation (like Plus, Minus, Times) and transforms it into ANF. In ANF, complex
  * operations are simplified such that each operation directly acts on simple expressions or variables. This
  * transformation ensures that both operands of the binary operation are immediate expressions or simple variables. If
  * either operand is not immediate, it is first transformed into ANF, and temporary variables are introduced to hold
  * the results of these operands.
  *
  * The binary operation is then reapplied to these temporary variables. This results in a structure where both operands
  * of the operation are simple expressions, adhering to the rules of ANF.
  *
  * @param op
  *   The binary operation to be transformed.
  * @param context
  *   The current transformation context, represented as a list of variable bindings in the form of '(variable name,
  *   expression)'. This context accumulates the necessary 'Let' bindings during the transformation process.
  * @param counter
  *   An integer counter used to generate unique variable names for new temporary variables.
  * @return
  *   A tuple containing the transformed expression in ANF, the updated context, and the incremented counter. The
  *   transformed expression will be a binary operation applied to temporary variables if the original operands were not
  *   immediate.
  */
private def transformBinaryOperationToAnf[A](
    op: BinaryOperation[A],
    context: List[(String, Expression[A])],
    counter: Int
): (Expression[A], List[(String, Expression[A])], Int) = {
  val (leftExpr, leftContext, leftCounter)    = transformExpressionToAnf(op.left, context, counter)
  val (rightExpr, rightContext, rightCounter) = transformExpressionToAnf(op.right, leftContext, leftCounter)
  val tempVar1                                = s"${leftExpr.getClass.getSimpleName.toLowerCase}$$$leftCounter"
  val tempVar2                                = s"${rightExpr.getClass.getSimpleName.toLowerCase}$$$rightCounter"
  op match
    case Plus(_, _, _)  => (Plus(IdLiteral[A](tempVar1), IdLiteral[A](tempVar2)), rightContext, rightCounter + 1)
    case Minus(_, _, _) => (Minus(IdLiteral[A](tempVar1), IdLiteral[A](tempVar2)), rightContext, rightCounter + 1)
    case Times(_, _, _) => (Times(IdLiteral[A](tempVar1), IdLiteral[A](tempVar2)), rightContext, rightCounter + 1)
}

/** Transforms a Let binding into A-Normal Form (ANF).
  *
  * This method processes a Let binding and ensures that both the expression being bound and the body of the Let binding
  * are in ANF. It handles the transformation of the expression associated with a Let binding into ANF, updates the
  * context with this transformed expression, and then processes the body of the Let binding in this updated context.
  *
  * @param context
  *   The current transformation context, represented as a list of variable bindings in the form of '(variable name,
  *   expression)'. This context is used to track the bindings and transformations made to the expressions during the
  *   ANF conversion process.
  * @param counter
  *   An integer counter used to generate unique variable names for new temporary variables. This counter is incremented
  *   as new variables are introduced during the transformation.
  * @param sym
  *   The symbol (variable name) associated with the Let binding.
  * @param expr
  *   The expression being bound to the symbol in the Let binding. This expression is transformed into ANF.
  * @param body
  *   The body of the Let binding, where the bound expression is used. This body is also transformed into ANF in the
  *   context of the new binding.
  * @return
  *   A tuple containing the transformed body expression in ANF, the final updated context after processing both the
  *   expression and the body, and the final counter value.
  */
private def transformLetBindingToAnf[A](
    context: List[(String, Expression[A])],
    counter: Int,
    sym: String,
    expr: Expression[A],
    body: Expression[A]
) = {
  val (newExpr, newContext, newCounter)     = transformExpressionToAnf(expr, context, counter)
  val updatedContext                        = newContext :+ (sym -> newExpr)
  val (newBody, finalContext, finalCounter) = transformExpressionToAnf(body, updatedContext, newCounter)
  (newBody, finalContext, finalCounter)
}
