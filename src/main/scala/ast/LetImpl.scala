package cl.ravenhill.scum
package ast

/** Represents an implementation of a 'let' expression in an abstract syntax tree (AST).
  *
  * The `LetImpl` trait encapsulates the behavior of a 'let' expression, which is a common construct in functional
  * programming languages. It allows for binding a value (represented by `expr`) to a symbol (`sym`), and then using
  * this binding in the `body` expression. This trait is primarily used for enhancing the AST's capabilities by allowing
  * the representation of 'let' bindings. The key feature of this trait is the overridden `toString` method, which
  * provides a custom string representation of the 'let' binding, aiding in debugging or displaying the AST structure.
  *
  * __Usage:__ Implement this trait in classes or objects that need to represent a 'let' binding expression in the AST.
  * The symbol, expression to be bound, and the body expression should be provided at the time of instantiation.
  *
  * @example
  *   {{{
  * object LetExample extends LetImpl("x", Num(42), Var("x"))
  * println(LetExample) // Outputs: (42).let { x -> x }
  *   }}}
  *
  * @param sym
  *   The symbol to which the expression is bound.
  * @param expr
  *   The expression to be bound to the symbol.
  * @param body
  *   The body expression where the symbol binding is used.
  */
trait LetImpl(sym: String, expr: Expression[_], body: Expression[_]) {

  /** Provides a string representation of the 'let' binding expression.
    *
    * This method overrides the standard `toString` method to present a specialized string format for the 'let' binding.
    * The output format is `($expr).let { $sym -> $body }`, where `$expr` is the string representation of the expression
    * being bound, `$sym` is the symbol to which the expression is bound, and `$body` is the body expression where the
    * binding is utilized. This representation is particularly useful for visualizing the structure within the AST or
    * during debugging processes.
    *
    * @return
    *   A string representation of the 'let' binding expression.
    */
  override def toString: String = s"($expr).let { $sym -> $body }"
}
