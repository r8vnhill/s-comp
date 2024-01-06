package cl.ravenhill.scum

import scala.annotation.targetName
import scala.util.Try

/** Manages a mapping of variable names to slots (or positions) in a programming environment.
  *
  * This class encapsulates an environment for variables, typically used in the context of a compiler or interpreter. It
  * maintains a mapping of variable names to integer slots, which could represent memory addresses, stack positions, or
  * other relevant indices. The environment is initially empty and can be dynamically expanded.
  *
  * @param bindings
  *   The initial map of variable names to slots. Default is an empty map.
  */
class Environment(private[scum] var bindings: Map[String, Int] = Map.empty[String, Int]) {

  /** Auxiliary constructor creating an environment from a sequence of key-value pairs.
    *
    * This constructor allows for the creation of an `Environment` instance with a pre-defined set of variable names and
    * their corresponding slot numbers. It converts a sequence of key-value pairs into a map that forms the initial
    * environment.
    *
    * @param keyValues
    *   A sequence of key-value pairs where the key is the variable name and the value is its slot.
    */
  def this(keyValues: (String, Int)*) = this(keyValues.toMap)

  /** Retrieves the slot associated with a given variable name.
    *
    * This method attempts to find the slot number for a specified variable name. If the variable is not found in the
    * environment, it returns a failure. The method uses Scala's `Try` to encapsulate the result, providing a safe way
    * to handle the absence of a variable.
    *
    * @param name
    *   The variable name whose slot number is to be retrieved.
    * @return
    *   A `Try[Int]` that is successful with the slot number if the variable exists, or a failure if not found.
    */
  def apply(name: String): Try[Int] = Try(bindings(name))

  /** Retrieves all variable names currently stored in the environment.
    *
    * This method provides access to all variable names defined in the environment. It is useful for inspecting the
    * current state of the environment, such as listing all variables or for debugging purposes.
    *
    * @return
    *   An `Iterable[String]` containing all the variable names in the environment.
    */
  def boundNames: Iterable[String] = bindings.keys

  /** Checks if the environment is empty.
    *
    * This method determines whether the environment contains any variable mappings. An empty environment is one that
    * has no variable names mapped to any slots. This can be particularly useful to check before performing operations
    * that require a non-empty environment or to validate the state of the environment at a given point in time.
    *
    * @return
    *   `true` if the environment is empty (contains no mappings), `false` otherwise.
    */
  def isEmpty: Boolean = bindings.isEmpty

  /** Adds a new variable to the environment with an automatically assigned slot number.
    *
    * This method adds a new variable to the environment. The slot number is assigned based on the current size of the
    * environment, ensuring each variable has a unique slot. It returns a new `Environment` instance with the updated
    * mappings, maintaining immutability.
    *
    * @param name
    *   The name of the variable to be added.
    * @return
    *   A new `Environment` instance containing the added variable.
    */
  @targetName("extendWith")
  def +(name: String): Environment = {
    val slot = 1 + bindings.size
    Environment(bindings + (name -> slot))
  }
}

/** Companion object for the `Environment` class.
  *
  * Provides utility methods related to the `Environment` class, such as creating an empty environment.
  */
object Environment {

  /** Creates and returns an empty `Environment`.
    *
    * This method is useful for initializing an environment with no variable bindings. It's particularly helpful when
    * starting the evaluation of an expression where no variables have been assigned values yet.
    *
    * @return
    *   An instance of `Environment` with an empty variable mapping.
    */
  def empty: Environment = new Environment
}
