package cl.ravenhill.scum
package ass

import ass.registry.Register

/** Represents an argument in assembly language instructions.
  *
  * This sealed trait `Arg` is used to define the types of arguments that can be passed to assembly language
  * instructions. As a sealed trait, `Arg` ensures that all subtypes are known and contained within the same file,
  * enabling exhaustive checking in pattern matching and enhancing the type safety of the code.
  */
sealed trait Arg

/** Represents a constant value as an argument to an assembly instruction.
  *
  * `Const` is a case class that extends the `Arg` trait, encapsulating an integer value to be used as a constant in
  * assembly language instructions. The immutability of case classes in Scala ensures that once a `Const` instance is
  * created, the value it holds cannot be altered, mirroring the immutable nature of constants in assembly language.
  *
  * @param value
  *   The integer value that this `Const` instance represents.
  */
case class Constant(value: Int) extends Arg {

  /** Returns a string representation of the object.
    *
    * The string representation is obtained by calling `toString` on the `value` property.
    *
    * @return
    *   the string representation of the object
    */
  override def toString: String = value.toString
}

/** Represents a register as an argument to an assembly instruction.
  *
  * `Reg` is a case class that extends the `Arg` trait, used for representing a CPU register in the context of an
  * assembly instruction. It holds a reference to a `Registry` instance, which indicates the specific register being
  * referred to. This class allows for the abstraction of register-based operations in the assembly language
  * representation within Scala.
  *
  * @param reg
  *   The `Registry` instance representing the specific register.
  */
case class RegisterBox(reg: Register) extends Arg {

  /** Returns a string representation of this object.
    *
    * The string representation is obtained by calling `toString` ([[Register.toString]]) on the `reg` property.
    *
    * @return
    *   the string representation of this object
    */
  override def toString: String = reg.toString
}

/** Represents an argument in assembly language that combines a register with an offset.
  *
  * `RegOffset` is a case class that extends the [[Arg]] trait, modeling an argument that consists of a register and an
  * offset. This type of argument is commonly used in assembly language for memory addressing, where an offset is
  * applied to a base address in a register.
  *
  * @param reg
  *   The base register for the argument.
  * @param offset
  *   The offset to be applied to the base register. The actual offset used in the representation is calculated as 8 *
  *   offset.
  */
case class RegisterOffset(reg: Register, offset: Int) extends Arg {

  /** Returns a string representation of the register-offset combination.
    *
    * This method overrides the `toString` method to return a string that represents the argument in a format typical of
    * assembly language memory addressing. The format is "[register + 8 * offset]", where 'register' is the base
    * register and 'offset' is the scaled offset value.
    *
    * @return
    *   A string representing the register-offset argument in assembly language syntax.
    */
  override def toString: String = s"[${reg.toString} + 8 * $offset]"
}
