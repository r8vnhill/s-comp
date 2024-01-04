package cl.ravenhill.scum
package asm.registry

import scala.annotation.targetName


extension (trsp: asm.Rsp.type) {
  @targetName("minus")
  infix def -(offset: Int): asm.Rsp = asm.Rsp(-offset)
}