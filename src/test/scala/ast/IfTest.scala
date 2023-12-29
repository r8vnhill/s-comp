package cl.ravenhill.scomp
package ast
import cl.ravenhill.scomp.ast.terminal.{False, Num, True}
import org.scalacheck.Gen
import generators.expr

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

  "Can be converted to a string" in {
    forAll(
      Gen
        .choose(0, 1)
        .map(_ == 1)
        .map(
          if _ then True else False
        ),
      Gen.expr(),
      Gen.expr()
    ) { (condition, thenBranch, elseBranch) =>
      val ifNode = If(condition, thenBranch, elseBranch)
      ifNode.toString should be(s"if ($condition) { $thenBranch } else { $elseBranch }")
    }
  }
}
