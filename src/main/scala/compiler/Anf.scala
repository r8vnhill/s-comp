package cl.ravenhill.scum
package compiler

import ast.*

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
  case NumericLiteral(_) | IdLiteral(_) => true
  case _               => false
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
  case e: UnaryOperation[A]                  => isImmediate(e)
  case e: BinaryOperation[A]                 => isImmediate(e.left) && isImmediate(e.right)
  case Let(_, expr, body)                    => isAnf(expr) && isAnf(body)
  case If(predicate, thenBranch, elseBranch) => isImmediate(predicate) && isAnf(thenBranch) && isAnf(elseBranch)
  case _                                     => isImmediate(expression)
}

/** Transforms an expression into Administrative Normal Form (ANF).
  *
  * This function recursively converts a given expression into ANF, a form where complex expressions are simplified
  * using let-bindings. ANF is beneficial for compiler design, simplifying the code generation and optimization phases.
  * The function handles different types of expressions, such as numeric literals (`Num`), variable references (`Var`),
  * unary operations (`UnaryOperation`), binary operations (`BinaryOperation`), let-bindings (`Let`), and conditional
  * expressions (`If`).
  *
  * __Usage:__ Utilize this function as part of the compilation process to transform expressions into a form that's
  * easier to evaluate or compile into lower-level code.
  *
  * @param expression
  *   The expression to be transformed into ANF.
  * @tparam A
  *   The type of metadata associated with the AST expressions.
  * @return
  *   The expression transformed into ANF.
  * @example
  *   {{{
  * val originalExpr = Plus(Num(1), Num(2))
  * val anfExpr = toAnf(originalExpr)
  * println(anfExpr) // Outputs the expression in ANF
  *   }}}
  *
  * The transformation rules are as follows:
  *   - `Num` and `Var` expressions are already in ANF and are returned as is.
  *   - `UnaryOperation` expressions are transformed by creating a temporary variable to hold the result of the
  *     operation.
  *   - `BinaryOperation` expressions are transformed by converting each operand into ANF and introducing a temporary
  *     variable for the operation's result.
  *   - `Let` expressions are processed by transforming the expression being bound into ANF and then applying ANF
  *     transformation to the body.
  *   - `If` expressions are handled by transforming the 'then' and 'else' branches into ANF, while keeping the
  *     condition unchanged. If additional context (bindings) is created in the branches, it is wrapped in a new
  *     let-binding.
  */
private[compiler] def toAnf[A](expression: Expression[A])(using annotation: Metadata[A]): Expression[A] =
  expression match {
    case NumericLiteral(_) | IdLiteral(_) => expression
    case e: UnaryOperation[A] =>
      val (_, operandContext) = extractAnfContext(e.expr)
      val temp                = s"temp_${e.metadata}"
      val newContext          = operandContext + (temp -> e)
      createContextExpression(newContext, IdLiteral[A](temp))
    case e: BinaryOperation[A] =>
      val (_, leftContext)  = extractAnfContext(e.left)
      val (_, rightContext) = extractAnfContext(e.right)
      val temp              = s"temp_${e.metadata}"
      val newContext        = leftContext ++ rightContext + (temp -> e)
      createContextExpression(newContext, IdLiteral[A](temp))
    case Let(sym, expr, body) =>
      val (anfExpr, context) = extractAnfContext(expr)
      Let(sym, anfExpr, toAnf(createContextExpression(context, body)))
    case If(predicate, thenBranch, elseBranch) =>
      val (thenAnf, thenContext) = extractAnfContext(toAnf(thenBranch))
      val (elseAnf, elseContext) = extractAnfContext(toAnf(elseBranch))
      val temp                   = s"temp_${annotation}"
      val newContext             = thenContext ++ elseContext
      val newIf =
        If(predicate, createContextExpression(thenContext, thenAnf), createContextExpression(elseContext, elseAnf))
      if (newContext.isEmpty) newIf
      else Let(temp, newIf, IdLiteral[A](temp))
  }

/** Extracts the Administrative Normal Form (ANF) context from an expression.
  *
  * This function analyses an expression and, if it is a 'Let' expression, extracts its context. The context in ANF is
  * defined as the bindings that a 'Let' expression introduces. For a 'Let' expression, it returns the body of the 'Let'
  * and a map representing the binding (variable name to expression). For other kinds of expressions, it returns the
  * expression itself and an empty map, indicating that there are no bindings (or context) to extract.
  *
  * @param expression
  *   The AST expression from which the ANF context is to be extracted.
  * @tparam A
  *   The type of metadata associated with the AST expressions.
  * @return
  *   A pair consisting of the main expression and a map representing the extracted ANF context. For 'Let' expressions,
  *   the map contains the variable binding; for other expressions, the map is empty.
  */
private def extractAnfContext[A](expression: Expression[A]): (Expression[A], Map[String, Expression[A]]) =
  expression match {
    case Let(sym, expr, body) => (body, Map(sym -> expr))
    case _                    => (expression, Map.empty)
  }

/** Creates a context expression by wrapping an expression with a series of let-bindings based on a given context.
  *
  * This function takes a context represented as a map of variable names to expressions and an expression. It constructs
  * a new expression where each variable in the context is bound to its corresponding expression using let-bindings. The
  * let-bindings are applied in a right-to-left order, effectively nesting each binding inside the next. This approach
  * is used to construct expressions that are in Administrative Normal Form (ANF), where complex expressions are broken
  * down into simpler ones, using let-bindings to hold intermediate values.
  *
  * @param context
  *   A map representing the context as a series of variable bindings, where each variable name is associated with an
  *   expression.
  * @param expression
  *   The expression to be wrapped with the context.
  * @tparam A
  *   The type of metadata associated with the AST expressions.
  * @return
  *   An expression that represents the original `expression` wrapped in let-bindings as dictated by the `context`.
  */
private def createContextExpression[A](context: Map[String, Expression[A]], expression: Expression[A])(using
    metadata: Metadata[A]
): Expression[A] = context.foldRight(expression) { case ((sym, expr), acc) => Let(sym, expr, acc) }
