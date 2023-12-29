package cl.ravenhill.scomp

import ass.{Const, Rax}
import ast.terminal.Num

import org.scalacheck.Gen
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

abstract class AbstractScompTest extends AnyFreeSpec with should.Matchers with ScalaCheckPropertyChecks 