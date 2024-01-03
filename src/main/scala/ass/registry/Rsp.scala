package cl.ravenhill.scum
package ass.registry

import scala.annotation.targetName


extension (trsp: ass.Rsp.type) {
  @targetName("minus")
  infix def -(offset: Int): ass.Rsp = ass.Rsp(-offset)
}