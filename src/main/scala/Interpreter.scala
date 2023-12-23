
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

/**
 * Simplifies an arithmetic expression by applying standard arithmetic simplification rules.
 *
 * This function recursively simplifies an expression represented by the `Expr` trait. It applies various
 * simplification rules to reduce the complexity of the expression, focusing particularly on operations involving
 * the identity elements 0 and 1 in addition and multiplication. The function aims to return the most simplified
 * version of the given expression without altering its mathematical value.
 *
 * Simplification rules include:
 *  - Addition with 0: `Plus(Num(0), expr)` or `Plus(expr, Num(0))` simplifies to `expr`.
 *  - Multiplication by 0: `Times(Num(0), expr)` or `Times(expr, Num(0))` simplifies to `Num(0)`.
 *  - Multiplication by 1: `Times(Num(1), expr)` or `Times(expr, Num(1))` simplifies to `expr`.
 *
 * For non-identity expressions in addition and multiplication (`Plus`, `Times`), the function simplifies
 * both operands recursively. If this simplification results in a change, a new `Plus` or `Times` instance
 * with simplified operands is returned. If not, the original expression is returned.
 *
 * @param expr The expression to be simplified, which can be any concrete implementation of the `Expr` trait.
 * @return An `Expr` instance representing the simplified form of the input expression. If the expression cannot
 *         be further simplified, the original expression is returned.
 */
def simplify(expr: Expr): Expr = {
  expr match {
    // Simplify addition when one operand is 0: `0 + right` becomes `right`
    case Plus(Num(0), right) => simplify(right)

    // Simplify addition when one operand is 0: `left + 0` becomes `left`
    case Plus(left, Num(0)) => simplify(left)

    // For non-identity addition, simplify each operand and reconstruct the addition
    // If simplification changes the operands, create a new Plus expression
    case Plus(left, right) =>
      val simplifiedLeft = simplify(left)
      val simplifiedRight = simplify(right)
      if (simplifiedLeft == left && simplifiedRight == right) expr
      else Plus(simplifiedLeft, simplifiedRight)

    // Simplify multiplication when one operand is 0: `0 * expr` or `expr * 0` becomes `0`
    case Times(Num(0), _) | Times(_, Num(0)) => Num(0)

    // Simplify multiplication when one operand is 1: `1 * right` becomes `right`
    case Times(Num(1), right) => simplify(right)

    // Simplify multiplication when one operand is 1: `left * 1` becomes `left`
    case Times(left, Num(1)) => simplify(left)

    // For non-identity multiplication, simplify each operand and reconstruct the multiplication
    // If simplification changes the operands, create a new Times expression
    case Times(left, right) =>
      val simplifiedLeft = simplify(left)
      val simplifiedRight = simplify(right)
      if (simplifiedLeft == left && simplifiedRight == right) expr
      else Times(simplifiedLeft, simplifiedRight)

    // If the expression does not match any simplification rule, return it as is
    case _ => expr
  }
}


