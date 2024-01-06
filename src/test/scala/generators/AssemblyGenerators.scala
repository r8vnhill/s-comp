package cl.ravenhill.scum
package generators

import asm.{Arg, Compare, Constant, Move}

import org.scalacheck.Gen

/** Trait providing generators for assembly language constructs, extending [[CommonGenerators]].
  *
  * This trait includes a collection of generator methods used to produce random instances of various assembly language
  * constructs, such as constants, register arguments, and different types of instructions like [[Move]] and
  * [[Compare]]. These generators are particularly useful for property-based testing of assembly code generation and
  * manipulation functionalities.
  */

trait AssemblyGenerators extends CommonGenerators {

  /** Generates a random constant value wrapped in an [[asm.Constant]] object.
    *
    * @param value
    *   A generator for integer values, defaulting to `generateInt()`.
    * @return
    *   A generator for `asm.Constant` objects.
    * @see
    *   [[generateInt]]
    */
  def generateConstant(value: Gen[Int] = generateInt()): Gen[Constant] = for {
    v     <- value
    const <- Gen.const(asm.Constant(v))
  } yield const

  /** Generates a [[Move]] instruction with random source and destination arguments.
    *
    * @param dst
    *   A generator for destination arguments, defaulting to [[generateArg]].
    * @param src
    *   A generator for source arguments, defaulting to [[generateArg]].
    * @return
    *   A generator for [[Move]] instructions.
    */
  def generateMove(dst: Gen[Arg] = generateArg, src: Gen[Arg] = generateArg): Gen[Move] = for {
    dst  <- dst
    src  <- src
    move <- Gen.const(Move(dst, src))
  } yield move

  /** Generates a [[Compare]] instruction with random source and destination arguments.
    *
    * @param dst
    *   A generator for destination arguments, defaulting to [[generateArg]].
    * @param src
    *   A generator for source arguments, defaulting to [[generateArg]].
    * @return
    *   A generator for [[Compare]] instructions.
    */
  def generateCompare(dst: Gen[Arg] = generateArg, src: Gen[Arg] = generateArg): Gen[Compare] = for {
    dst     <- dst
    src     <- src
    compare <- Gen.const(Compare(dst, src))
  } yield compare

  /** Generates a random register from a predefined set of registers.
    *
    * @return
    *   A generator for [[asm.Register]] objects.
    */
  def generateRegister: Gen[asm.Register] = Gen.oneOf(asm.Rax(), asm.Eax(), asm.Rsp())

  /** Generates a random argument, which could be either a [[Constant]] or a [[Register]].
    *
    * @return
    *   A generator for [[asm.Arg]] objects.
    */
  def generateArg: Gen[asm.Arg] = Gen.oneOf(generateConstant(), generateRegister)

  /** Generates a random instruction, which could be either a [[Move]] or [[Compare]] instruction.
    *
    * @return
    *   A generator for [[asm.Instruction]] objects.
    */
  def generateInstruction: Gen[asm.Instruction] = Gen.oneOf(generateMove(), generateCompare())
}
