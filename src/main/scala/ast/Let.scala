package cl.ravenhill.scum
package ast

/** Represents a 'let' expression with an annotation in a functional language construct.
  *
  * This case class models the 'let' binding, a common feature in functional programming languages. It introduces a new
  * symbol (variable) and binds it to the result of an expression (`expr`), then uses this symbol in the body (`body`)
  * of another expression. The class is now enhanced with an annotation of type `A`, allowing for additional information
  * to be associated with the 'let' expression, such as type metadata or evaluation context.
  *
  * @param sym
  *   The symbol (variable name) to be introduced.
  * @param expr
  *   The expression whose result will be bound to the symbol.
  * @param body
  *   The expression where the symbol will be used.
  * @param annotation
  *   The annotation associated with this 'let' expression, of type `A`.
  * @tparam A
  *   The type of annotation associated with this instance of the 'let' expression.
  */
case class Let[A](sym: String, expr: Expr[A], body: Expr[A], annotation: A) extends Expr[A] {

  /** Returns a string representation of the 'let' expression in prefix notation.
    *
    * This method overrides the `toString` method to provide a string that represents the 'let' expression in a format
    * typical of functional programming. The format is "($expr).let { $sym -> $body }", where 'symbol' is the bound
    * variable and 'body' is the expression where the symbol is used.
    *
    * @return
    *   A string representing the 'let' expression in a readable and familiar format.
    */
  override def toString: String = s"($expr).let { $sym -> $body }"
}
