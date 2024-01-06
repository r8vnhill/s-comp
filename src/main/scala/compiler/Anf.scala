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
  case Num(_) | Var(_) => true
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
