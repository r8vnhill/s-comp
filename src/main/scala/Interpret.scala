package cl.ravenhill.scum

import scala.util.{Success, Try}

def interpret[A](expression: ast.Expression[A], environment: Map[String, Int]): Try[Int] = {
  expression match {
    case ast.Num(value) => Success(value)
    case ast.Var(sym) => Try(environment(sym))
    case ast.Increment(e) => interpret(e, environment).map(_ + 1)
    case ast.Decrement(e) => interpret(e, environment).map(_ - 1)
  }
}
