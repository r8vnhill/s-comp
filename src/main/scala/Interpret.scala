package cl.ravenhill.scum

import scala.util.{Success, Try}


def interpret[A](expression: ast.Expression[A]): Try[Int] = expression match {
  case ast.Num(value) => Success(value)
}