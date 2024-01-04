
package cl.ravenhill.scum
package asm.instruction

trait IncrementImpl(destination: asm.Arg) {
  override def toString: String = s"inc $destination"
}
