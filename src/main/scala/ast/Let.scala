package cl.ravenhill.scomp
package ast

/** Represents a 'let' expression in a functional language construct.
  *
  * This case class models the 'let' binding, which introduces a new symbol (variable) and binds it to the result of an
  * expression, then uses this symbol in the body of another expression. It is a common feature in functional
  * programming languages.
  *
  * @param sym
  *   The symbol (variable name) to be introduced.
  * @param expr
  *   The expression whose result will be bound to the symbol.
  * @param body
  *   The expression where the symbol will be used.
  */
case class Let(sym: String, expr: Expr, body: Expr) extends Expr {

  /** Converts the 'let' expression to its prefix notation representation.
    *
    * This method overrides the `toPrefix` method to return a string that represents the 'let' expression in prefix
    * notation, commonly used in functional programming. The format is ".let { symbol -> body }", where 'symbol' is the
    * bound variable and 'body' is the expression where the symbol is used.
    *
    * @return
    *   A string representing the 'let' expression in prefix notation.
    */
  override def toString: String = s"($expr).let { $sym -> $body }"
}
