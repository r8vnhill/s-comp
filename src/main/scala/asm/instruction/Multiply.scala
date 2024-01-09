package cl.ravenhill.scum
package asm.instruction

import asm.Arg

private[asm] trait Multiply(val source: Arg) {

  override def toString: String = s"mul $source"
}
