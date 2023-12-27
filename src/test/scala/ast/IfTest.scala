package cl.ravenhill.scomp
package ast

import cl.ravenhill.scomp.ast.terminal.{False, Num, True}
import org.scalacheck.Gen

class IfTest extends AbstractScompTest {
  "The condition property should be set according to the constructor argument" in {
    forAll(Gen.choose(0, 1).map(_ == 1)) { condition =>
      val ifNode = If(if condition then True else False, True, False)
      ifNode.cond should be(if condition then True else False)
    }
  }

  "The then branch should be set according to the constructor argument" in {
    forAll(Gen.choose(0, 1).map(_ == 1)) { condition =>
      val ifNode = If(if condition then True else False, True, False)
      ifNode.thenBranch should be(True)
    }
  }

  "The else branch should be set according to the constructor argument" in {
    forAll(Gen.choose(0, 1).map(_ == 1)) { condition =>
      val ifNode = If(if condition then True else False, True, False)
      ifNode.elseBranch should be(False)
    }
  }

  "Can be converted to a prefix string" in {
    forAll(
      Gen
        .choose(0, 1)
        .map(_ == 1)
        .map(
          if _ then True else False
        ),
      Gen.choose(-100, 100).map(Num.apply),
      Gen.choose(-100, 100).map(terminal.Num.apply)
    ) { (condition, thenBranch, elseBranch) =>
      val ifNode = If(condition, thenBranch, elseBranch)
      ifNode.toPrefix should be(s"(if ${condition.toPrefix} then ${thenBranch.toPrefix} else ${elseBranch.toPrefix})")
    }
  }
}
