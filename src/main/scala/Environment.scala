package cl.ravenhill.scum

import scala.annotation.targetName
import scala.util.Try

/** Manages a mapping of variable names to slots (or positions) in a programming environment.
  *
  * This class encapsulates an environment for variables, typically used in the context of a compiler or interpreter. It
  * maintains a mapping of variable names to integer slots, which could represent memory addresses, stack positions, or
  * other relevant indices. The environment is initially empty and can be dynamically expanded.
  *
  * @param env
  *   The initial map of variable names to slots. Default is an empty map.
  */
class Environment(private var env: Map[String, Int] = Map.empty[String, Int]) {

  /** Retrieves the slot associated with a given variable name.
    *
    * This method attempts to find the slot number for a specified variable name. If the variable is not found in the
    * environment, it returns a failure.
    *
    * @param name
    *   The variable name whose slot number is to be retrieved.
    * @return
    *   A `Try[Int]` that is successful with the slot number if the variable exists, or a failure if not found.
    */
  def apply(name: String): Try[Int] = Try(env(name))

  /** Adds a new variable to the environment with an automatically assigned slot number.
    *
    * This method adds a new variable to the environment. The slot number is assigned based on the current size of the
    * environment, ensuring each variable has a unique slot. It returns a new `Environment` instance with the updated
    * mappings.
    *
    * @param name
    *   The name of the variable to be added.
    * @return
    *   A new `Environment` instance containing the added variable.
    */
  @targetName("add")
  def +(name: String): Environment = {
    val slot = 1 + env.size
    Environment(env + (name -> slot))
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
