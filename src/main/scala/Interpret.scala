package cl.ravenhill.scum

import scala.util.{Success, Try}

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
    case ast.Num(value)   => Success(value)
    case ast.Var(sym)     => Try(environment(sym))
    case ast.Increment(e) => interpret(e, environment).map(_ + 1)
    case ast.Decrement(e) => interpret(e, environment).map(_ - 1)
    case ast.Doubled(e)   => interpret(e, environment).map(_ * 2)
    case ast.Plus(e1, e2) =>
      interpret(e1, environment).flatMap { v1 =>
        interpret(e2, environment).map { v2 =>
          v1 + v2
        }
      }
    case ast.Minus(e1, e2) =>
      interpret(e1, environment).flatMap { v1 =>
        interpret(e2, environment).map { v2 =>
          v1 - v2
        }
      }
    case ast.Times(e1, e2) =>
      interpret(e1, environment).flatMap { v1 =>
        interpret(e2, environment).map { v2 =>
          v1 * v2
        }
      }
    case ast.Let(sym, expr, body) =>
      interpret(expr, environment).flatMap { v =>
        interpret(body, environment + (sym -> v))
      }
    case ast.If(predicate, thenExpr, elseExpr) =>
      interpret(predicate, environment).flatMap { v =>
        if (v != 0) {
          interpret(thenExpr, environment)
        } else {
          interpret(elseExpr, environment)
        }
      }
  }
}
