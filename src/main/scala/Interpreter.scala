
package cl.ravenhill.scomp

import ast.*

import scala.util.{Failure, Try}

/** Evaluates an expression within a given environment and returns the result wrapped in a `Try`.
  *
  * This function takes an environment and an expression as input. It computes the value of the expression based on
  * the environment, which provides values for variables. The function returns a `Try[Int]` to encapsulate both
  * successful evaluations and exceptions that might occur during evaluation, such as unknown expressions or
  * missing variable values.
  *
  * @param env  The environment used for variable value lookups. It maps variable names to their integer values.
  * @param expr The expression to be evaluated. This expression is an instance of the `Expr` trait and can be any
  *             of its concrete implementations (e.g., `Var`, `Num`, `Plus`, `Times`).
  * @return A `Try[Int]` representing the result of the expression evaluation. It's a `Success[Int]` with the
  *         evaluated value if the evaluation is successful, or a `Failure[Int]` with an appropriate exception
  *         if there are issues during evaluation.
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
    case _ => Failure[Int](UnknownExpressionException(s"$expr"))
  }
}

