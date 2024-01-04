
package cl.ravenhill.scum
package asm.instruction

trait DecrementImpl(destination: asm.Arg) {
  override def toString: String = s"dec $destination"
}
