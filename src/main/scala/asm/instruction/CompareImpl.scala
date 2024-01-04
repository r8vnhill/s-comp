
package cl.ravenhill.scum
package asm.instruction

trait CompareImpl(dest: asm.Arg, src: asm.Arg) {
  override def toString: String = s"cmp $dest, $src"
}
