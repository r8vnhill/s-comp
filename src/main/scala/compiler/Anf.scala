package cl.ravenhill.scum
package compiler

import cl.ravenhill.scum.ast.{Expression, Num, Var}

import java.util.function.UnaryOperator

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
private def isImmediate(expression: Expression[_]): Boolean = expression match {
  case Num(_) | Var(_) => true
  case _               => false
}
