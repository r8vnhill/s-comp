package cl.ravenhill.scum
package ast.ternary

import cl.ravenhill.scum.ast.Expression

/** Represents an implementation of a 'let' expression in an abstract syntax tree (AST).
  *
  * The `Let` trait encapsulates the behavior of a 'let' expression, a common construct in functional programming
  * languages. It allows for binding a value (represented by `expr`) to a symbol (`sym`), and then using this binding in
  * the `body` expression. This trait enhances the AST's capabilities by enabling the representation of 'let' bindings.
  * A key feature of this trait is the overridden `toString` method, which provides a custom string representation of
  * the 'let' binding, aiding in debugging or displaying the AST structure.
  *
  * __Usage:__ Implement this trait in classes or objects that need to represent a 'let' binding expression in the AST.
  * The symbol, the expression to be bound, and the body expression, along with optional metadata, should be provided at
  * the time of instantiation.
  *
  * @example
  *   {{{
  * object LetExample extends Let("x", Num(42), Var("x"))
  * println(LetExample) // Outputs: (42).let { x -> x }
  *   }}}
  * @param sym
  *   The symbol to which the expression is bound.
  * @param expr
  *   The expression to be bound to the symbol.
  * @param body
  *   The body expression where the symbol binding is used.
  * @param metadata
  *   Optional additional information related to the 'let' expression.
  */
trait Let(val sym: String, val expr: Expression[_], val body: Expression[_], val metadata: Option[_]) {

  /** Provides a string representation of the 'let' binding expression.
    *
    * This method overrides the standard `toString` method to present a specialized string format for the 'let' binding.
    * Depending on the `toStringMode`, the output format is `($expr).let { $sym -> $body }` in NORMAL mode, or
    * `Let(sym='$sym', expr=$expr, body=$body, metadata=$metadata)` in DEBUG mode. This representation is particularly
    * useful for visualizing the structure within the AST or during debugging processes.
    *
    * @return
    *   A string representation of the 'let' binding expression.
    */
  override def toString: String = toStringMode match {
    case ToStringMode.NORMAL => s"($expr).let { $sym -> $body }"
    case ToStringMode.DEBUG  => s"Let(sym='$sym', expr=$expr, body=$body, metadata=$metadata)"
  }
}
