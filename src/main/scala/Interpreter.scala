
package cl.ravenhill.scomp

import ast.*

def interpret(env: Environment, expr: Expr): Int = {
  expr match
    case Var(x) => env(x)
    case Num(n) => n
    case Plus(left, right) => interpret(env, left) + interpret(env, right)
    case Times(left, right) => interpret(env, left) * interpret(env, right)
    case _ => throw new Exception("Unknown expression")
}
