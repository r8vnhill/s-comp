package cl.ravenhill.scomp
package ass

/** Represents an assembly language instruction.
  *
  * This sealed trait `Instruction` is used as a base for various types of instructions in assembly language. As a
  * sealed trait, it ensures that all subtypes (specific instruction types) are known and defined within the same file.
  * This approach facilitates exhaustive checking in pattern matching and enhances the overall type safety and
  * maintainability of the code dealing with assembly instructions.
  */
sealed trait Instruction

/** Represents the 'mov' instruction in assembly language.
  *
  * The `Mov` class models the 'mov' instruction, which is used to move or copy data from the source (`src`) to the
  * destination (`dest`). Both `src` and `dest` are of type `Arg`, allowing for flexible representations of the
  * arguments, such as registers or constants. This class encapsulates the semantics of the 'mov' instruction within an
  * object-oriented structure, making it easy to construct, manipulate, and interpret 'mov' instructions in a
  * Scala-based assembly language representation.
  *
  * @param dest
  *   The destination argument where the data will be moved/copied to. It is an instance of `Arg`.
  * @param src
  *   The source argument from where the data will be moved/copied. It is also an instance of `Arg`.
  */
case class Mov(dest: Arg, src: Arg) extends Instruction {
  override def toString: String = s"mov $dest, $src"
}

/** Represents the 'add' instruction in assembly language.
  *
  * `Add` is a case class that extends the `Instruction` class, modeling the 'add' instruction commonly used in assembly
  * language programming. This instruction performs an addition operation, adding the value of the source argument
  * (`src`) to the destination argument (`dest`). Both `dest` and `src` are instances of `Arg`, allowing for flexible
  * representations of the arguments, such as registers or constants.
  *
  * The class encapsulates the semantics of the 'add' instruction within an object-oriented structure, making it easy to
  * construct, manipulate, and interpret 'add' instructions in a Scala-based assembly language representation.
  *
  * @param dest
  *   The destination argument where the result of the addition will be stored. It is an instance of `Arg`.
  * @param src
  *   The source argument whose value will be added to the destination. It is also an instance of `Arg`.
  */
case class Add(dest: Arg, src: Arg) extends Instruction {
  override def toString: String = s"add $dest, $src"
}

/** Represents the 'sub' instruction in assembly language.
  *
  * `Sub` is a case class that extends the `Instruction` class, modeling the 'sub' instruction commonly used in assembly
  * language programming. This instruction performs a subtraction operation, subtracting the value of the source
  * argument (`src`) from the destination argument (`dest`). Both `dest` and `src` are instances of `Arg`, allowing for
  * flexible representations of the arguments, such as registers or constants.
  *
  * The class encapsulates the semantics of the 'sub' instruction within an object-oriented structure, making it easy to
  * construct, manipulate, and interpret 'sub' instructions in a Scala-based assembly language representation.
  *
  * @param dest
  *   The destination argument where the result of the subtraction will be stored. It is an instance of `Arg`.
  * @param src
  *   The source argument whose value will be subtracted from the destination. It is also an instance of `Arg`.
  */
case class Sub(dest: Arg, src: Arg) extends Instruction {
  override def toString: String = s"sub $dest, $src"
}

/** Represents the 'inc' (increment) instruction in assembly language.
  *
  * `Inc` is a case class that extends the `Instruction` class, modeling the 'inc' instruction commonly used in assembly
  * language programming. This instruction performs an increment operation, increasing the value of the destination
  * argument (`dest`) by one. The `dest` is an instance of `Arg`, allowing for a flexible representation of the
  * argument, which could be a register or a memory location.
  *
  * The class encapsulates the semantics of the 'inc' instruction within an object-oriented structure, making it easy to
  * construct, manipulate, and interpret 'inc' instructions in a Scala-based assembly language representation.
  *
  * @param dest
  *   The destination argument whose value will be incremented. It is an instance of `Arg`.
  */
case class Inc(dest: Arg) extends Instruction {

  /** Provides a string representation of the 'inc' instruction in assembly language syntax.
    *
    * This method overrides the `toString` method to return a string that represents the 'inc' instruction in a format
    * typical of assembly language. The format is "inc destination", where the destination is replaced with the string
    * representation of the destination argument.
    *
    * @return
    *   A string representing the 'inc' instruction, formatted in assembly language syntax.
    */
  override def toString: String = s"inc $dest"
}

/** Represents the 'dec' (decrement) instruction in assembly language.
  *
  * `Dec` is a case class that extends the `Instruction` class, modeling the 'dec' instruction commonly used in assembly
  * language programming. This instruction performs a decrement operation, decreasing the value of the destination
  * argument (`dest`) by one. The `dest` is an instance of `Arg`, allowing for a flexible representation of the
  * argument, which could be a register or a memory location.
  *
  * The class encapsulates the semantics of the 'dec' instruction within an object-oriented structure, making it easy to
  * construct, manipulate, and interpret 'dec' instructions in a Scala-based assembly language representation.
  *
  * @param dest
  *   The destination argument whose value will be decremented. It is an instance of `Arg`.
  */
case class Dec(dest: Arg) extends Instruction {

  /** Provides a string representation of the 'dec' instruction in assembly language syntax.
    *
    * This method overrides the `toString` method to return a string that represents the 'dec' instruction in a format
    * typical of assembly language. The format is "dec destination", where the destination is replaced with the string
    * representation of the destination argument.
    *
    * @return
    *   A string representing the 'dec' instruction, formatted in assembly language syntax.
    */
  override def toString: String = s"dec $dest"
}
