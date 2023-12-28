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

/**
 * Represents the 'add' instruction in assembly language.
 *
 * `Add` is a case class that extends the `Instruction` class, modeling the 'add' instruction commonly
 * used in assembly language programming. This instruction performs an addition operation, adding the
 * value of the source argument (`src`) to the destination argument (`dest`). Both `dest` and `src`
 * are instances of `Arg`, allowing for flexible representations of the arguments, such as registers
 * or constants.
 *
 * The class encapsulates the semantics of the 'add' instruction within an object-oriented structure,
 * making it easy to construct, manipulate, and interpret 'add' instructions in a Scala-based assembly
 * language representation.
 *
 * @param dest The destination argument where the result of the addition will be stored. It is an instance of `Arg`.
 * @param src  The source argument whose value will be added to the destination. It is also an instance of `Arg`.
 */
case class Add(dest: Arg, src: Arg) extends Instruction {
  override def toString: String = s"add $dest, $src"
}
