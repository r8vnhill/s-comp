package cl.ravenhill.scum
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

/** Represents the EAX register, commonly used in x86 assembly programming.
  *
  * This singleton object extends from the `Register` trait (or class) and is typically used to represent the EAX
  * register in the generated assembly code or intermediate representation within the compiler.
  *
  * The `toString` method is overridden to provide a human-readable name for the register, which is "EAX" in this case.
  */
case object Eax extends Register {

  /** Returns the string representation of the EAX register.
    *
    * @return
    *   A string "EAX", representing the name of the register.
    */
  override def toString: String = "EAX"
}

/** Represents the RSP (Stack Pointer) register in assembly language.
  *
  * `Rsp` is a singleton object that extends from the [[Register]] trait. It models the RSP register, which is
  * typically used in assembly language to point to the top of the stack. This object provides a consistent and
  * type-safe way of referring to the RSP register within a Scala-based assembly language representation.
  */
case object Rsp extends Register {

  /** Returns the string representation of the RSP register.
    *
    * This method overrides the `toString` method to return "RSP", representing the name of the stack pointer register
    * in a human-readable form.
    *
    * @return
    *   A string "RSP", representing the stack pointer register.
    */
  override def toString: String = "RSP"
}
