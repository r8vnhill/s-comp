package cl.ravenhill.scum
package ast

import generators.*

import org.scalacheck.Gen

class LetTest extends AbstractScumTest with AstGenerators {
  "A Let expression" - {
    "should store the symbol passed to the constructor" in {
      var collected = Map.empty[Expression[String], Int]
      forAll(generateStringLabel, generateExpression(), generateExpression()) { (sym, e1, e2) =>
        collected = collected.get(e1) match {
          case Some(count) => collected + (e1 -> (count + 1))
          case None        => collected + (e1 -> 1)
        }
        val let = Let(sym, e1, e2)
        let.sym should be(sym)
      }
      val totalCount = collected.values.sum.toDouble

      val sortedByPercentage = collected
        .map { case (sym, count) => (sym, (count / totalCount) * 100) }
        .toList
        .sortBy(_._2)(Ordering[Double].reverse)

      val prettyPrinted = sortedByPercentage
        .map { case (sym, percentage) => f"$percentage%.2f%% - $sym" }
        .mkString("\n")
      println(prettyPrinted)
    }

    "should store the first expression passed to the constructor" in {
      forAll(generateStringLabel, generateExpression(), generateExpression()) { (sym, e1, e2) =>
        val let = Let(sym, e1, e2)
        let.expr should be(e1)
      }
    }

    "should store the second expression passed to the constructor" in {
      forAll(generateStringLabel, generateExpression(), generateExpression()) { (sym, e1, e2) =>
        val let = Let(sym, e1, e2)
        let.body should be(e2)
      }
    }

    "can be converted to a String" in {
      forAll(generateStringLabel, generateExpression(), generateExpression()) { (sym, e1, e2) =>
        val let = Let(sym, e1, e2)
        let.toString should be(s"($e1).let { $sym -> $e2 }")
      }
    }
  }
}
