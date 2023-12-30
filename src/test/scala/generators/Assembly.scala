
package cl.ravenhill.scum
package generators

import ass.{Arg, Const, Mov}

import org.scalacheck.Gen

extension (gen: Gen.type) {

  /** Generates a constant assembly language argument.
   *
   * This method extends the `Gen` object to create a generator for `Const` objects, which represent constant values
   * in assembly language. The method can take a custom generator for the integer value of the constant, or it
   * defaults to using a random integer generator.
   *
   * @param value
   * A generator for the integer value of the constant. Defaults to a random integer generator.
   * @return
   * A `Gen[Const]` that produces `Const` instances with potentially randomized values.
   */
  def constant(value: Gen[Int] = gen.int()): Gen[Const] = gen.const(ass.Const(value.sample.get))
  
  def mov(dst: Gen[Arg] = gen.arg, src: Gen[Arg] = gen.arg): Gen[Mov] = gen.const(Mov(dst.sample.get, src.sample.get))


  /** Generates a registry argument for assembly language.
   *
   * This method extends the `Gen` object to create a generator for `Reg` objects, specifically for generating
   * register arguments in assembly language. Currently, it is limited to generating the `Rax` register.
   *
   * @return
   * A `Gen[Reg]` that produces `Reg` instances, currently limited to `Rax`.
   */
  def registry: Gen[ass.Reg] = gen.oneOf(Seq(ass.Reg(ass.Rax)))

  /** Generates a general assembly language argument.
   *
   * This method extends the `Gen` object to create a generator for `Arg` objects, which can be either constants or
   * register arguments in assembly language. It randomly selects between generating a constant or a register
   * argument.
   *
   * @return
   * A `Gen[Arg]` that produces either `Const` or `Reg` instances, depending on the random choice.
   */
  def arg: Gen[ass.Arg] = gen.oneOf(gen.constant(), gen.registry)

  /** Generates a random assembly language instruction.
   *
   * This method extends the `Gen` object to create a generator for `Instruction` objects, which represent individual
   * instructions in assembly language. Currently, this generator is configured to produce `Mov` instructions, a
   * common type of instruction used for moving or copying data. The `Mov` instruction takes two arguments: a
   * destination and a source, both of which are randomly generated using the `arg` generator method.
   *
   * The randomness in argument generation makes this method suitable for property-based testing where different
   * combinations of arguments for `Mov` instructions can be automatically tested.
   *
   * @return
   * A `Gen[Instruction]` that produces `Instruction` instances, specifically `Mov` instructions with randomly
   * generated arguments.
   */
  def instruction: Gen[ass.Instruction] = gen.oneOf(Seq(ass.Mov(gen.arg.sample.get, gen.arg.sample.get)))
}