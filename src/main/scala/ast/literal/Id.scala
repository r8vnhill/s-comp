package cl.ravenhill.scum
package ast.literal

/** Trait representing an identifier literal in an abstract syntax tree (AST).
  *
  * This trait is used to encapsulate the concept of an identifier literal, which is a fundamental component in various
  * programming languages, especially in contexts where symbolic representation is required, such as variable names,
  * function names, or other symbolic identifiers. The `IdLiteral` trait holds a string symbol representing the
  * identifier and provides a custom `toString` method to output this symbol.
  *
  * __Usage:__ Extend this trait in classes that represent elements in an AST which are identified by a symbolic name or
  * label. This trait is particularly useful for creating nodes in an AST that represent variables, functions, or other
  * named entities in a program's source code.
  *
  * @param symbol
  *   The string symbol representing the identifier.
  */
private[ast] trait Id(symbol: String) {

  /** Provides a string representation of the identifier.
    *
    * This method overrides the standard `toString` method to return the `symbol` that represents the identifier. It
    * facilitates the easy and readable representation of AST nodes that are identified by names or labels.
    *
    * @return
    *   A string representing the identifier.
    */
  override def toString: String = symbol
}
