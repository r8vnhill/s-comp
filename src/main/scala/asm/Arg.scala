package cl.ravenhill.scum
package asm

import asm.registry.RegisterImpl

/** Represents an argument in assembly language instructions.
  *
  * `Arg` is a sealed trait used as a base for different types of arguments that can be passed to assembly language
  * instructions. This trait allows for a polymorphic representation of arguments, enabling a unified approach to
  * handling different types of operands in assembly language constructs.
  */
sealed trait Arg

/** Represents a constant value as an argument in assembly language instructions.
  *
  * `Constant` is a case class that extends the `Arg` trait. It represents constant integer values that can be used as
  * arguments in assembly language instructions. The value is encapsulated in a concrete representation, making it
  * suitable for use in constructing assembly instructions within Scala-based representations.
  *
  * @param value
  *   The integer value of the constant.
  */
case class Constant(value: Long) extends Arg with impl.ConstantImpl(value)

/** Base trait for representing CPU registers in assembly language.
  *
  * `Register` is a sealed trait that extends the `Arg` trait and mixes in the `impl.RegisterImpl` trait. It acts as a
  * base for different types of CPU registers, providing common functionality and representation for registers in
  * assembly language instructions.
  */
sealed trait Register extends Arg with RegisterImpl

/** Represents the RAX register in x86-64 assembly programming.
  *
  * `Rax` is a case class extending the `Register` trait. It specifically represents the RAX register, commonly used in
  * x86-64 assembly language. The class allows for a type-safe and clear representation of the RAX register, along with
  * an optional integer offset for more complex addressing.
  *
  * @param offset
  *   The integer offset associated with the RAX register, defaulting to `RegisterImpl.defaultOffset`.
  */
case class Rax(override val offset: Long = RegisterImpl.defaultOffset) extends Register

/** Represents the EAX register in x86 assembly programming.
  *
  * `Eax` is a case class extending the `Register` trait. It represents the EAX register, widely used in x86 assembly
  * language. This class provides a convenient way to refer to the EAX register in Scala-based assembly language
  * representations, along with an optional offset for addressing.
  *
  * @param offset
  *   The integer offset associated with the EAX register, defaulting to `RegisterImpl.defaultOffset`.
  */
case class Eax(override val offset: Long = RegisterImpl.defaultOffset) extends Register

/** Represents the RSP (Stack Pointer) register in assembly language.
  *
  * `Rsp` is a case class that extends the `Register` trait. It models the RSP register, used in assembly language for
  * stack management. This class offers a type-safe representation of the RSP register, along with an optional offset
  * for advanced addressing modes.
  *
  * @param offset
  *   An optional integer offset for the RSP register, defaulting to `RegisterImpl.defaultOffset`.
  */
case class Rsp(override val offset: Long = RegisterImpl.defaultOffset) extends Register
