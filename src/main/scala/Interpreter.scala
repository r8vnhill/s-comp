
package cl.ravenhill.scomp

import ast.*

import scala.util.{Failure, Try}

/**
 * Evaluates an expression represented by the `Expr` trait in the given environment.
 *
 * This function takes an environment and an expression and computes the value of the expression based on
 * the environment. The environment (`env`) is expected to provide values for variables (`Var`) contained
 * within the expression. The function uses pattern matching to handle different types of expressions
 * defined in the `Expr` trait.
 *
 * @param env  An `Environment` that maps variable symbols to their integer values. This environment is
 *             used to look up the values of variables.
 * @param expr The expression to be evaluated. This expression is an instance of the `Expr` trait and can
 *             be any of its concrete implementations like `Var`, `Num`, `Plus`, or `Times`.
 * @return The integer result of evaluating the expression.
 * @throws Exception if the expression type is unknown or unsupported.
 */
def interpret(env: Environment, expr: Expr): Try[Int] = {
  expr match {
    case Var(x) => env(x) // Retrieves the value of the variable `x` from the environment.
    case Num(n) => Try(n) // Returns the integer value `n` for numerical constants.
    case Plus(left, right) =>
      interpret(env, left).flatMap(l => 
        interpret(env, right).map(r => l + r)) // Recursively evaluates and adds the left and right operands.
    case Times(left, right) =>
      interpret(env, left).flatMap(l => 
        interpret(env, right).map(r => l * r)) // Recursively evaluates and multiplies the left and right operands.
    case _ => Failure[Int](new Exception(s"Unknown expression: $expr"))
  }
}

