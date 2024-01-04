
package cl.ravenhill.scum
package asm.instruction

trait MoveImpl(dest: asm.Arg, src: asm.Arg) {
  override def toString: String = s"mov $dest, $src"
}
