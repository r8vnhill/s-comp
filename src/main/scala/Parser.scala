package cl.ravenhill.scomp

import ast.Expr

def parse(stringExpr: String): Expr = {

  def binaryOpParse(opChar: Char, construct: (Expr, Expr) => Expr): Option[Expr] = {
    if (stringExpr.startsWith(s"$opChar ")) {
      val expr = stringExpr.substring(2).trim
      val parts = expr.split(" ", 2)
      if (parts.length == 2) {
        val left = parse(parts(0))
        val right = parse(parts(1))
        Some(construct(left, right))
      } else None
    } else None
  }

  stringExpr match {
    case s if s.startsWith("+ ") =>
      binaryOpParse('+', ast.Plus.apply)
        .getOrElse(throw new IllegalArgumentException("Invalid format for + expression"))
    case s if s.startsWith("* ") =>
      binaryOpParse('*', ast.Times.apply)
        .getOrElse(throw new IllegalArgumentException("Invalid format for * expression"))
    case _ => stringExpr.toIntOption match {
      case Some(value) => ast.Num(value)
      case None => ast.Var(stringExpr)
    }
  }
}

