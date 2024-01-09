package cl.ravenhill.scum

import scala.util.{Failure, Success, Try}

/** Interprets an AST (Abstract Syntax Tree) expression and evaluates it to an integer value.
  *
  * This function recursively interprets an expression represented in an AST, based on the type of the expression. It
  * handles various expression types like numeric literals, variable references, arithmetic operations (increment,
  * decrement, addition, subtraction, multiplication), let-bindings, and conditional expressions. The interpretation
  * process involves evaluating the expression according to the rules defined for each expression type. The environment
  * parameter is a map that provides the bindings of variables to their integer values, necessary for interpreting
  * variable references.
  *
  * __Usage:__ This function is typically used in an interpreter for a programming language or DSL (Domain-Specific
  * Language) that operates on expressions represented as an AST.
  *
  * @param expression
  *   The AST expression to be interpreted.
  * @param environment
  *   A map representing the current variable bindings in the scope of the expression.
  * @return
  *   A `Try[Int]` representing the result of interpreting the expression. It contains the evaluated integer value or an
  *   error if the interpretation fails.
  * @tparam A
  *   The type of metadata associated with the AST expression.
  *
  * @example
  *   {{{
  * val expr = ast.Plus(ast.Num(1), ast.Num(2))
  * val result = interpret(expr, Map.empty)
  * println(result) // Outputs: Success(3)
  *   }}}
  */
def interpret[A](expression: ast.Expression[A], environment: Map[String, Long]): Try[Long] = {
  expression match {
    case ast.NumericLiteral(value, _) => Success(value)
    case ast.IdLiteral(sym, _) =>
      environment.get(sym).map(Success(_)).getOrElse(Failure(new NoSuchElementException(s"Undefined variable: $sym")))
    case ast.Increment(e, _)           => interpret(e, environment).map(_ + 1)
    case ast.Decrement(e, _)           => interpret(e, environment).map(_ - 1)
    case ast.Doubled(e, _)             => interpret(e, environment).map(_ * 2)
    case binOp: ast.BinaryOperation[A] => interpretBinaryOperation(binOp, environment)
    case ast.Let(sym, expr, body, _) =>
      for {
        v      <- interpret(expr, environment)
        result <- interpret(body, environment + (sym -> v))
      } yield result
    case ast.If(predicate, thenExpr, elseExpr, _) =>
      interpret(predicate, environment).flatMap { v =>
        interpret(if (v != 0) thenExpr else elseExpr, environment)
      }
  }
}

/** Interprets a binary operation expression in an abstract syntax tree (AST).
  *
  * @tparam A
  *   The type parameter associated with the expression (not used in the current implementation).
  * @param binOp
  *   The binary operation expression to interpret.
  * @param environment
  *   The environment containing variable bindings used during interpretation.
  * @return
  *   A `Try[Long]` representing the result of the binary operation or an error if the interpretation fails.
  */
private def interpretBinaryOperation[A](binOp: ast.BinaryOperation[A], environment: Map[String, Long]): Try[Long] =
  binOp match {
    case ast.Plus(e1, e2, _) =>
      for {
        v1 <- interpret(e1, environment)
        v2 <- interpret(e2, environment)
      } yield v1 + v2
    case ast.Minus(e1, e2, _) =>
      for {
        v1 <- interpret(e1, environment)
        v2 <- interpret(e2, environment)
      } yield v1 - v2
    case ast.Times(e1, e2, _) =>
      for {
        v1 <- interpret(e1, environment)
        v2 <- interpret(e2, environment)
      } yield v1 * v2
  }
