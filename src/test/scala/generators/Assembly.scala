package cl.ravenhill.scum
package generators

import ass.{Arg, Compare, Constant, Move}

import org.scalacheck.Gen

extension (gen: Gen.type) {

  /** Generates a constant assembly language argument.
    *
    * This method extends the `Gen` object to create a generator for `Const` objects, which represent constant values in
    * assembly language. The method can take a custom generator for the integer value of the constant, or it defaults to
    * using a random integer generator.
    *
    * @param value
    *   A generator for the integer value of the constant. Defaults to a random integer generator.
    * @return
    *   A `Gen[Const]` that produces `Const` instances with potentially randomized values.
    */
  def constant(value: Gen[Int] = gen.int()): Gen[Constant] = for {
    v <- value
    const <- gen.const(ass.Constant(v))
  } yield const

  /** Generates a `Mov` instruction with randomly selected arguments.
    *
    * This method creates a generator for `Mov` instructions, a fundamental assembly operation for moving data between
    * different arguments (like registers or memory locations). The arguments for the `Mov` instruction are randomly
    * generated using provided argument generators. By default, it uses the `gen.arg` generator for both the destination
    * (`dst`) and source (`src`) arguments. The method ensures that the generated `Mov` instruction contains valid and
    * potentially unique argument values, making it suitable for testing scenarios such as property-based testing.
    *
    * @param dst
    *   A generator for the destination argument of the `Mov` instruction. Defaults to `gen.arg`.
    * @param src
    *   A generator for the source argument of the `Mov` instruction. Defaults to `gen.arg`.
    * @return
    *   A `Gen[Mov]` that produces instances of `Mov` instructions with randomly generated arguments.
    */
  def move(dst: Gen[Arg] = gen.arg, src: Gen[Arg] = gen.arg): Gen[Move] = for {
    dst <- dst
    src <- src
    move <- gen.const(Move(dst, src))
  } yield move

  /** Generates a `Cmp` instruction with randomly selected arguments, encapsulated within a `Mov` generator.
    *
    * This method creates a generator for `Cmp` instructions, commonly used in assembly language for comparing two
    * arguments. The arguments for the `Cmp` instruction are randomly generated using provided argument generators. By
    * default, it uses the `gen.arg` generator for both the destination (`dst`) and source (`src`) arguments. This
    * method ensures that the generated `Cmp` instruction contains valid and potentially unique argument values.
    * However, it returns the generated `Cmp` instruction within a `Gen[Mov]` type, which might be a design choice for
    * specific testing or simulation scenarios where a `Mov` type is expected but a `Cmp` operation is intended.
    *
    * @param dst
    *   A generator for the destination argument of the `Cmp` instruction. Defaults to `gen.arg`.
    * @param src
    *   A generator for the source argument of the `Cmp` instruction. Defaults to `gen.arg`.
    * @return
    *   A `Gen[Mov]` that produces instances of `Cmp` instructions with randomly generated arguments.
    */
  def compare(dst: Gen[Arg] = gen.arg, src: Gen[Arg] = gen.arg): Gen[Compare] = for {
    dst <- dst
    src <- src
    compare <- gen.const(Compare(dst, src))
  } yield compare

  /** Generates a random `Register` from a predefined set.
    *
    * This method utilizes ScalaCheck's `Gen.oneOf` to randomly select and generate a `Register` instance from a set of
    * predefined registers. The set includes `Rax`, `Eax`, and `Rsp` from the `ass.registry` package. This method is
    * particularly useful in scenarios where a random but valid register is needed, such as in property-based testing of
    * assembly language code generation and manipulation.
    *
    * The `Register` class represents CPU registers, which are small storage locations within the CPU used to quickly
    * access and store data during computation. Different types of registers (like `Rax`, `Eax`, `Rsp`) are used for
    * various purposes in assembly language programming.
    *
    * @return
    *   A `Gen[Register]` that produces one of the predefined `Register` instances (`Rax`, `Eax`, `Rsp`).
    */
  def register: Gen[ass.Register] = gen.oneOf(ass.Rax(), ass.Eax(), ass.Rsp())

  /** Generates a general assembly language argument.
    *
    * This method extends the `Gen` object to create a generator for `Arg` objects, which can be either constants or
    * register arguments in assembly language. It randomly selects between generating a constant or a register argument.
    *
    * @return
    *   A `Gen[Arg]` that produces either `Const` or `Reg` instances, depending on the random choice.
    */
  def arg: Gen[ass.Arg] = gen.oneOf(gen.constant(), gen.register)

  /** Generates a random assembly language instruction.
    *
    * This method extends the `Gen` object to create a generator for `Instruction` objects, which represent individual
    * instructions in assembly language. Currently, this generator is configured to produce `Mov` instructions, a common
    * type of instruction used for moving or copying data. The `Mov` instruction takes two arguments: a destination and
    * a source, both of which are randomly generated using the `arg` generator method.
    *
    * The randomness in argument generation makes this method suitable for property-based testing where different
    * combinations of arguments for `Mov` instructions can be automatically tested.
    *
    * @return
    *   A `Gen[Instruction]` that produces `Instruction` instances, specifically `Mov` instructions with randomly
    *   generated arguments.
    */
  def instruction: Gen[ass.Instruction] = gen.oneOf(gen.move(), gen.compare())
}
