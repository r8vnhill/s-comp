package cl.ravenhill.scum
package ast.literal

/** Trait representing an identifier in an abstract syntax tree (AST) with optional metadata.
  *
  * The `Id` trait encapsulates the concept of an identifier, a fundamental component in various programming languages,
  * used in contexts requiring symbolic representation such as variable names, function names, or other symbolic
  * identifiers. This trait holds a string symbol representing the identifier and optional metadata. It provides a
  * custom `toString` method to output this symbol and metadata, the behavior of which can be altered based on the
  * global [[toStringMode]].
  *
  * __Usage:__ Extend this trait in classes that represent elements in an AST identified by a symbolic name or label,
  * along with associated metadata. This trait is particularly useful for creating nodes in an AST that represent
  * variables, functions, or other named entities in a program's source code, with the added capability of holding
  * metadata for each identifier.
  *
  * @param symbol
  *   The string symbol representing the identifier.
  * @param metadata
  *   Optional metadata associated with the identifier.
  */
private[ast] trait Id(symbol: String, metadata: Option[_]) {

  /** Provides a string representation of the identifier, potentially including metadata.
    *
    * This method overrides the standard `toString` method. Depending on the global `toStringMode`, it either returns
    * just the `symbol` or a more detailed representation including both the `symbol` and `metadata`. This behavior
    * facilitates adaptable and readable representations of AST nodes, especially useful for debugging or logging
    * purposes.
    *
    * @return
    *   A string representing the identifier, with detail level depending on `toStringMode`.
    */
  override def toString: String = toStringMode match {
    case ToStringMode.NORMAL => symbol
    case ToStringMode.DEBUG  => s"Id(symbol=$symbol, metadata=$metadata)"
  }
}
