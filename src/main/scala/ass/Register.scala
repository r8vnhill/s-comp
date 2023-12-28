package cl.ravenhill.scomp
package ass

/** Represents a CPU register in assembly language programming.
  *
  * This sealed trait is used as a base for representing different types of registers in an assembly language context.
  * Being a sealed trait, all of its implementations (subtypes) are known and restricted to the current file. This
  * ensures that all possible registers are exhaustively handled in pattern matching, enhancing type safety and reducing
  * the likelihood of runtime errors.
  */
sealed trait Register

/** Represents the RAX register in x86-64 assembly programming.
  *
  * `Rax` is a case object extending the `Reg` trait. In the context of x86-64 assembly language, RAX is a
  * general-purpose register commonly used to store the return value of a function. As a case object, `Rax` provides a
  * convenient and type-safe way to refer to the RAX register in Scala representations of assembly language programs.
  */
case object Rax extends Register {
  override def toString: String = "RAX"
}
