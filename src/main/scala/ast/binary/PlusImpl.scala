package cl.ravenhill.scum
package ast.binary

import ast.Expression

trait PlusImpl(left: Expression[_], right: Expression[_]) {
  override def toString = s"($left + $right)"
}
