package cl.ravenhill.scum
package ass

/** Represents an assembly language instruction.
  *
  * This sealed trait `Instruction` is used as a base for various types of instructions in assembly language. As a
  * sealed trait, it ensures that all subtypes (specific instruction types) are known and defined within the same file.
  * This approach facilitates exhaustive checking in pattern matching and enhances the overall type safety and
  * maintainability of the code dealing with assembly instructions.
  */
sealed trait Instruction

/** Represents the 'ret' (return) instruction in assembly language.
  *
  * `Ret` is a case object that extends the `Instruction` sealed trait, specifically representing the return instruction
  * in an assembly language. The 'ret' instruction is typically used to return from a subroutine or function. Being a
  * case object, `Ret` benefits from Scala's features for case classes and objects, such as a default implementation of
  * methods like `toString`. This object is part of the comprehensive set of instruction types defined for assembly
  * language representation.
  *
  * The `toString` method override provides a string representation of the 'ret' instruction, facilitating its use in
  * assembly code generation and debugging processes.
  */
case object Ret extends Instruction {

  /** Returns a string representation of the 'ret' instruction.
    *
    * This method overrides the `toString` method to return "ret", which is the standard representation of the return
    * instruction in assembly language.
    *
    * @return
    *   A string "ret", representing the return instruction.
    */
  override def toString: String = "ret"
}

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
case class Move(dest: Arg, src: Arg) extends Instruction {
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
case class Increment(dest: Arg) extends Instruction {

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
case class Decrement(dest: Arg) extends Instruction {

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

/** Represents a 'cmp' (compare) instruction in assembly language.
  *
  * The `Cmp` case class extends the `Instruction` trait and models the comparison operation in assembly language. This
  * operation compares two arguments and sets the flags in the status register based on the result. It is a fundamental
  * instruction for conditional execution in assembly programming. The class encapsulates both the destination and
  * source arguments for the comparison.
  *
  * The `toString` method override provides a string representation of the 'cmp' instruction, formatted in a way typical
  * of assembly language instructions, which enhances readability and is useful for assembly code generation and
  * debugging.
  *
  * @param dest
  *   The destination argument for the comparison.
  * @param src
  *   The source argument to compare against the destination.
  */
case class Compare(dest: Arg, src: Arg) extends Instruction {

  /** Returns a string representation of the 'cmp' instruction.
    *
    * This method overrides the `toString` method to return a string format of the 'cmp' instruction, displaying the
    * destination and source arguments. The format is "cmp destination, source".
    *
    * @return
    *   A string depicting the 'cmp' instruction in assembly language syntax.
    */
  override def toString: String = s"cmp $dest, $src"
}

/** Represents the 'je' (jump if equal) instruction in assembly language.
  *
  * The `Je` case class extends the `Instruction` trait and models the jump instruction used in assembly language when a
  * specific condition is met — specifically, it represents the 'jump if equal' condition. This instruction causes the
  * program to jump to a different part of the code, identified by the provided label, if the zero flag (ZF) is set,
  * which typically occurs when a comparison results in equality.
  *
  * The `toString` method override provides a string representation of the 'je' instruction, following the standard
  * assembly language syntax. This enhances the readability of the generated code and is useful for debugging and code
  * generation.
  *
  * @param label
  *   The label to jump to if the zero flag is set.
  */
case class JumpIfEqual(label: String) extends Instruction {

  /** Returns a string representation of the 'je' (jump if equal) instruction.
    *
    * This method overrides the `toString` method to produce a format typical for the 'je' instruction in assembly
    * language. The format is "je label", where 'label' is the destination label for the jump.
    *
    * @return
    *   A string representing the 'je' instruction, including the target label.
    */
  override def toString: String = s"je $label"
}

/** Represents the 'jmp' (jump) instruction in assembly language.
  *
  * The `Jump` case class extends the `Instruction` trait and models the jump instruction ('jmp') in assembly language.
  * This instruction is used to unconditionally transfer the program control to a different part of the code, which is
  * identified by the provided label. The jump instruction is a fundamental control flow operation in assembly
  * programming and is essential for implementing loops, conditionals, and other control structures.
  *
  * @param label
  *   The label to which control should jump.
  */
case class Jump(label: String) extends Instruction {

  /** Returns a string representation of the 'jmp' (jump) instruction.
    *
    * This method overrides the `toString` method to return the 'jmp' instruction in a standard assembly language
    * format. The format is "jmp label", where 'label' is the destination label for the jump.
    *
    * @return
    *   A string representing the 'jmp' instruction, including the target label.
    */
  override def toString: String = s"jmp $label"
}

/** Represents a label in assembly language.
  *
  * The `Label` case class extends the `Instruction` trait and models a label in assembly language. Labels are used as
  * markers or identifiers in the code, typically serving as targets for jump instructions like `jmp`, `je`, `jne`, etc.
  * They are essential for controlling the flow of the program, allowing the code to jump to different sections based on
  * certain conditions or during loops.
  *
  * The `toString` method override provides a string representation of the label, formatted in a standard way for
  * assembly language. This representation includes the label name followed by a colon, which is the typical syntax for
  * defining labels in assembly code.
  *
  * @param label
  *   The name of the label.
  */
case class Label(label: String) extends Instruction {

  /** Returns a string representation of the label.
    *
    * This method overrides the `toString` method to return the label in a format typical for labels in assembly
    * language. The format is "label:", where 'label' is the name of the label.
    *
    * @return
    *   A string representing the label in assembly language syntax.
    */
  override def toString: String = s"$label:"
}
