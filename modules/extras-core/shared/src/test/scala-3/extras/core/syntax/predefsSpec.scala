package extras.core.syntax

import hedgehog.*
import hedgehog.runner.*

import extras.core.syntax.predefs.*

import scala.language.unsafeNulls

/** @author Kevin Lee
  * @since 2025-09-06
  */
object predefsSpec extends Properties {
  def tests: List[Test] = List(
    property("a ?: alternative where a is non-null value should return a", testElvisWithNonNull),
    property(
      "a ?: alternative where a is non-null value should never evaluate the alternative value",
      testElvisWithNonNullNeverEvaluatesAlternativeValue,
    ),
    example("a ?: alternative where a is null should return alternative", testElvisWithNull),
    example(
      "a ?: alternative where a is null should evaluate the alternative value",
      testElvisWithNullShouldEvaluateTheAlternativeValue,
    ),
  )

  def testElvisWithNonNull: Property = for {
    s <- Gen.string(Gen.alpha, Range.linear(1, 5)).log("s")
  } yield {
    val input: String | Null = s
    val expected: String     = s

    val actual = input ?: "DEFAULT"

    actual ==== expected
  }

  def testElvisWithNonNullNeverEvaluatesAlternativeValue: Property = for {
    s <- Gen.string(Gen.alpha, Range.linear(1, 5)).log("s")
  } yield {
    val input: String | Null = s

    val alternativeValue: String = "DEFAULT"

    var evaluatedAlternative: Option[String] = None // scalafix:ok DisableSyntax.var

    val expected = s

    val resultBeforeElvis = evaluatedAlternative ==== None
    val actual            = input ?: {
      evaluatedAlternative = Some(alternativeValue)
      alternativeValue
    }
    val resultAfterElvis  = evaluatedAlternative ==== None

    Result.all(
      List(
        resultBeforeElvis.log("Failed: Result before elvis"),
        actual ==== expected,
        resultAfterElvis.log("Failed: Result after elvis"),
      )
    )
  }

  def testElvisWithNull: Result = {
    val s: String | Null = null // scalafix:ok DisableSyntax.null

    val expected: String = "DEFAULT"
    val actual: String   = s ?: "DEFAULT"

    actual ==== expected
  }

  def testElvisWithNullShouldEvaluateTheAlternativeValue: Result = {
    val alternativeValue: String             = "DEFAULT"
    var evaluatedAlternative: Option[String] = None // scalafix:ok DisableSyntax.var

    val s: String | Null = null // scalafix:ok DisableSyntax.null

    val expected = alternativeValue

    val resultBeforeElvis = evaluatedAlternative ==== None

    val actual = s ?: {
      evaluatedAlternative = Some(alternativeValue)
      alternativeValue
    }

    val resultAfterElvis = evaluatedAlternative ==== Some(expected)

    Result.all(
      List(
        resultBeforeElvis.log("Failed: Result before elvis"),
        actual ==== expected,
        resultAfterElvis.log("Failed: Result after elvis"),
      )
    )
  }

}
