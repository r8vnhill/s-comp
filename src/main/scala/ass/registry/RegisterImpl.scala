
package cl.ravenhill.scum
package ass.registry

/** Trait representing a CPU register implementation in assembly language.
 *
 * `RegisterImpl` is a trait designed to represent a CPU register in assembly language programming. It provides
 * a common structure and functionality for various registers, encapsulating the concept of an offset for more
 * complex addressing modes.
 *
 * Implementations of this trait can represent specific CPU registers, allowing for a consistent and unified
 * representation of registers in Scala-based assembly language representations.
 */
trait RegisterImpl {
  /** The offset associated with the register.
   *
   * This field represents the offset from the register's base address, used in assembly instructions for addressing
   * purposes. A default value of 0 implies no offset, enabling simpler representation when an offset is not needed.
   * Offsets are used to represent displacements for memory access operations in assembly language.
   *
   * @return The integer offset value associated with the register.
   */
  val offset: Int

  /** Returns a string representation of the register with its offset.
   *
   * This method overrides the standard `toString` method to return a string that represents the register in a format
   * typical of assembly language. If the offset is 0, the representation is simply the register's name. Otherwise, it
   * includes the scaled offset value, formatted as "[register + 8 * offset]", where 'register' is the name of the
   * register and 'offset' is the scaled offset value.
   *
   * @return A string representing the register and its offset in assembly language syntax.
   */
  override def toString: String = {
    val name = this.getClass.getSimpleName.toUpperCase
    offset match {
      case 0 => name
      case _ => s"[$name + 8 * $offset]"
    }
  }
}

/** Companion object for the `RegisterImpl` trait.
 *
 * This object provides additional constants and functionalities related to the `RegisterImpl` trait.
 * It includes the definition of the default offset value for registers, used when no specific offset is
 * provided or needed.
 */
object RegisterImpl {
  /** The default offset value for register instances.
   *
   * This constant defines the default offset value as 0, which indicates no offset is applied. This default
   * is used for creating register instances where the offset is not a relevant factor, simplifying the
   * representation of registers in assembly language instructions.
   *
   * @return The default offset value for registers.
   */
  val defaultOffset: Int = 0
}
