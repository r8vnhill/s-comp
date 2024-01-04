package cl.ravenhill.scum
package ast.terminal

/** Represents a variable implementation with a specified symbol.
  *
  * `VarImpl` is a trait that encapsulates a variable implementation, primarily characterized by a symbol (a `String`).
  * The primary purpose of this trait is to provide a consistent way to represent a variable through its symbol. This is
  * particularly useful in scenarios where variables are represented or manipulated as strings, such as in compiler
  * implementations or symbolic computations.
  *
  * ## Usage: This trait can be extended by classes or objects that represent variables. The symbol for the variable is
  * provided at instantiation and is used for the `toString` method, ensuring that the variable is represented by its
  * symbol when converted to a string.
  *
  * @example
  *   {{{
  * object VarX extends VarImpl("x")
  * println(VarX) // Outputs: x
  *   }}}
  *
  * @example
  *   {{{
  * class MyVariable(symbol: String) extends VarImpl(symbol)
  * val myVar = new MyVariable("y")
  * println(myVar) // Outputs: y
  *   }}}
  *
  * @param symbol
  *   The symbol representing the variable.
  */
trait VarImpl(symbol: String) {

  /** Provides a string representation of the instance.
    *
    * This method overrides the default `toString` method to return the `symbol` of the instance. The `symbol` is a
    * string that typically represents the name or identifier of the instance in a meaningful way. By overriding
    * `toString`, instances of the class or trait can be directly printed or logged, displaying the `symbol` for easier
    * identification and debugging.
    *
    * @return
    *   A string representing the `symbol` of the instance.
    */
  override def toString: String = symbol
}
