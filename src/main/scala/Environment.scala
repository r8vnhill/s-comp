package cl.ravenhill.scomp

import scala.util.Try

/** Represents an environment for variable bindings in expressions.
  *
  * The `Environment` class encapsulates a mapping of variable names (as strings) to their integer values. This mapping
  * is used primarily for evaluating expressions that contain variables ([[Var]]). It provides a mechanism to look up
  * the value of a variable given its name.
  *
  * @param env Initial mapping of variable names to their integer values. It defaults to an empty map.
  */
class Environment(private var env: Map[String, Int] = Map.empty[String, Int]) {

  /** Retrieves the value associated with a given variable name.
    *
    * This method is used to look up the integer value of a variable based on its name. It is a critical part of
    * evaluating expressions that include variables.
    *
    * @param name The name of the variable whose value is to be retrieved.
    * @return The integer value associated with the given variable name.
    * @throws NoSuchElementException if the variable name is not found in the environment.
    */
  def apply(name: String): Try[Int] = Try(env(name))
}

/** Companion object for the `Environment` class.
  *
  * Provides utility methods related to the `Environment` class, such as creating an empty environment.
  */
object Environment {

  /** Creates and returns an empty `Environment`.
    *
    * This method is useful for initializing an environment with no variable bindings. It's particularly
    * helpful when starting the evaluation of an expression where no variables have been assigned values yet.
    *
    * @return An instance of `Environment` with an empty variable mapping.
    */
  def empty: Environment = new Environment
}
