package cl.ravenhill.scomp
package ass

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
case class Const(value: Int) extends Arg {

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
case class Reg(reg: Register) extends Arg {

  /** Returns a string representation of this object.
    *
    * The string representation is obtained by calling `toString` ([[Reg.toString]]) on the `reg` property.
    *
    * @return
    *   the string representation of this object
    */
  override def toString: String = reg.toString
}
