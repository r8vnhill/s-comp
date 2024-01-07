package cl.ravenhill.scum
package asm

/** Base trait for representing assembly language instructions.
  *
  * `Instruction` is a sealed trait that serves as a base for a variety of assembly language instructions. Each specific
  * instruction is represented by a subclass, encapsulating the details and specificities of that instruction. This
  * approach allows for a type-safe and structured representation of assembly instructions in Scala-based applications
  * or interpreters.
  */
sealed trait Instruction

/** Represents the 'ret' (return) instruction in assembly language.
  *
  * `Return` is a case object extending the `Instruction` trait and mixing in the `instruction.RetImpl` trait. It
  * represents the 'ret' instruction used to return control from a procedure or function in assembly language.
  */
case object Return extends Instruction with instruction.RetImpl

/** Represents the 'mov' (move) instruction in assembly language.
  *
  * `Move` is a case class that extends the `Instruction` trait and mixes in the `instruction.MoveImpl` trait. It
  * encapsulates the 'mov' instruction, used to transfer data from a source (`src`) to a destination (`dest`) operand.
  *
  * @param dest
  *   The destination operand of the move instruction.
  * @param src
  *   The source operand of the move instruction.
  */
case class Move(dest: Arg, src: Arg) extends Instruction with instruction.MoveImpl(dest, src)

/** Represents the 'add' (addition) instruction in assembly language.
  *
  * `Add` is a case class extending the `Instruction` trait and mixing in the `instruction.AddImpl` trait. It models the
  * 'add' instruction, used to perform arithmetic addition of the source (`src`) operand to the destination (`dest`)
  * operand.
  *
  * @param dest
  *   The destination operand of the addition.
  * @param src
  *   The source operand of the addition.
  */
case class Add(dest: Arg, src: Arg) extends Instruction with instruction.Add(dest, src)

/** Represents the 'sub' (subtract) instruction in assembly language.
  *
  * `Sub` is a case class extending the `Instruction` trait and mixing in the `instruction.SubImpl` trait. It
  * encapsulates the 'sub' instruction, used to subtract the source (`src`) operand from the destination (`dest`)
  * operand.
  *
  * @param dest
  *   The destination operand of the subtraction.
  * @param src
  *   The source operand of the subtraction.
  */
case class Sub(dest: Arg, src: Arg) extends Instruction with instruction.SubImpl(dest, src)

/** Represents the 'inc' (increment) instruction in assembly language.
  *
  * `Increment` is a case class extending the `Instruction` trait and mixing in the `instruction.IncrementImpl` trait.
  * It represents the 'inc' instruction, used to increment the value of the destination (`dest`) operand by one.
  *
  * @param dest
  *   The operand to be incremented.
  */
case class Increment(dest: Arg) extends Instruction with instruction.IncrementImpl(dest)

/** Represents the 'dec' (decrement) instruction in assembly language.
  *
  * `Decrement` is a case class extending the `Instruction` trait and mixing in the `instruction.DecrementImpl` trait.
  * It represents the 'dec' instruction, used to decrement the value of the destination (`dest`) operand by one.
  *
  * @param dest
  *   The operand to be decremented.
  */
case class Decrement(dest: Arg) extends Instruction with instruction.DecrementImpl(dest)

/** Represents the 'cmp' (compare) instruction in assembly language.
  *
  * `Compare` is a case class extending the `Instruction` trait and mixing in the `instruction.CompareImpl` trait. It
  * models the 'cmp' instruction, used to compare two operands, `dest` and `src`, setting flags based on the result.
  *
  * @param dest
  *   The first operand to be compared.
  * @param src
  *   The second operand to be compared.
  */
case class Compare(dest: Arg, src: Arg) extends Instruction with instruction.CompareImpl(dest, src)

/** Represents the 'je' (jump if equal) instruction in assembly language.
  *
  * `JumpIfEqual` is a case class extending the `Instruction` trait and mixing in the `instruction.JumpIfEqualImpl`
  * trait. It encapsulates the 'je' instruction, used to jump to a specified label if the result of the previous
  * comparison was equal.
  *
  * @param label
  *   The target label for the conditional jump.
  */
case class JumpIfEqual(label: String) extends Instruction with instruction.JumpIfEqualImpl(label)

/** Represents the 'jmp' (unconditional jump) instruction in assembly language.
  *
  * `Jump` is a case class extending the `Instruction` trait and mixing in the `instruction.JumpImpl` trait. It
  * represents the 'jmp' instruction, used to perform an unconditional jump to a specified label.
  *
  * @param label
  *   The target label for the jump.
  */
case class Jump(label: String) extends Instruction with instruction.JumpImpl(label)

/** Represents a label in assembly language.
  *
  * `Label` is a case class extending the `Instruction` trait and mixing in the `instruction.LabelImpl` trait. It is
  * used to define a label in assembly language, marking a specific point in the code.
  *
  * @param label
  *   The name of the label.
  */
case class Label(label: String) extends Instruction with instruction.LabelImpl(label)

/** Represents a comment as an instruction in an assembly language or similar programming context.
  *
  * The `Comment` case class extends the `Instruction` trait and mixes in the `instruction.Comment` trait. It is used to
  * represent a comment in a sequence of instructions or code, typically in assembly language or a similar low-level
  * programming context. This class stores the text of the comment and is treated as an instruction, allowing it to be
  * integrated into a sequence of executable instructions or code statements. The inclusion of comments as instructions
  * can be particularly useful for generating human-readable and self-documenting assembly code, aiding in debugging and
  * maintenance.
  *
  * @param text
  *   The text of the comment.
  */
case class Comment(text: String) extends Instruction with instruction.Comment(text)

/** Represents a 'push' instruction in assembly language as a specific case of an instruction.
  *
  * The `Push` case class extends the `Instruction` trait and mixes in the `instruction.Push` trait. It specifically
  * encapsulates the assembly 'push' instruction, which is used to push a given argument onto the stack. This
  * instruction is crucial in assembly language programming for stack operations, such as preparing for a function call
  * or saving register values. The class takes an argument (`arg`) that represents the value to be pushed onto the stack
  * and provides a concise and clear representation of this operation in assembly language syntax.
  *
  * @param arg
  *   The argument to be pushed onto the stack, represented as an `Arg`.
  */
case class Push(arg: Arg) extends Instruction with instruction.Push(arg)

/** Represents a 'pop' instruction in assembly language as a specific instance of an instruction.
  *
  * The `Pop` case class extends the `Instruction` trait and mixes in the `instruction.Pop` trait. It specifically
  * encapsulates the assembly 'pop' instruction, which is used to pop the top value from the stack into a given
  * destination argument. This operation is a fundamental part of stack manipulation in assembly language programming,
  * used for retrieving values from the stack, typically in the context of function returns or register restoration. The
  * class takes a destination argument (`dest`) that specifies where the popped value should be stored, and it provides
  * a clear representation of this operation in assembly language syntax.
  *
  * @param dest
  *   The destination argument where the popped value will be stored, represented as an `Arg`.
  */
case class Pop(override val dest: Arg) extends Instruction with instruction.Pop(dest)
