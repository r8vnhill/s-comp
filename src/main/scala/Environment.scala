
package cl.ravenhill.scomp

import ast.Expr

class Environment(private var env: Map[String, Int] = Map.empty[String, Int]) {
  def apply(name: String): Int = env(name)
}

object Environment {
  def empty() = Environment()
}
