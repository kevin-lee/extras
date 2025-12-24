package extras.core.syntax

import hedgehog._
import hedgehog.runner._

import extras.core.syntax.predefs._

/** @author Kevin Lee
  * @since 2025-09-06
  */
object predefsSpec extends Properties {
  def tests: List[Test] = List(
    property("a ?:= alternative where a is non-null value should return a", testElvisWithNonNull),
    property(
      "a ?:= alternative where a is non-null value should never evaluate the alternative value",
      testElvisWithNonNullNeverEvaluatesAlternativeValue,
    ),
    example("a ?:= alternative where a is null should return alternative", testElvisWithNull),
    example("a ?:= null-alternative where a is null should return null", testElvisWithNullAndNullAlternative),
    example(
      "a ?:= alternative where a is null should evaluate the alternative value",
      testElvisWithNullShouldEvaluateTheAlternativeValue,
    ),
  )

  def testElvisWithNonNull: Property = for {
    s <- Gen.string(Gen.alpha, Range.linear(1, 5)).log("s")
  } yield {
    val input: String    = s
    val input2: String   = s
    val expected: String = s

    val actual  = input ?:= "DEFAULT"
    val actual2 = input2 ?:= "DEFAULT"

    Result.all(
      List(
        (actual ==== expected).log("actual is not equal to expected"),
        (actual2 ==== expected).log("actual2 is not equal to expected"),
      )
    )
  }

  def testElvisWithNonNullNeverEvaluatesAlternativeValue: Property = for {
    s <- Gen.string(Gen.alpha, Range.linear(1, 5)).log("s")
  } yield {
    val input: String  = s
    val input2: String = s

    val alternativeValue: String = "DEFAULT"

    @SuppressWarnings(Array("org.wartremover.warts.Var"))
    var evaluatedAlternative: Option[String]  = None // scalafix:ok DisableSyntax.var
    @SuppressWarnings(Array("org.wartremover.warts.Var"))
    var evaluatedAlternative2: Option[String] = None // scalafix:ok DisableSyntax.var

    val expected = s

    val resultBeforeElvis  = evaluatedAlternative ==== None
    val result2BeforeElvis = evaluatedAlternative2 ==== None

    val actual = input ?:= {
      evaluatedAlternative = Some(alternativeValue)
      alternativeValue
    }

    val actual2 = input2 ?:= {
      evaluatedAlternative2 = Some(alternativeValue)
      alternativeValue
    }

    val resultAfterElvis  = evaluatedAlternative ==== None
    val result2AfterElvis = evaluatedAlternative2 ==== None

    Result.all(
      List(
        resultBeforeElvis.log("Failed: Result before elvis"),
        (actual ==== expected).log("actual is not equal to expected"),
        resultAfterElvis.log("Failed: Result after elvis"),
        ///
        result2BeforeElvis.log("Failed: Result 2 before elvis"),
        (actual2 ==== expected).log("actual is not equal to expected"),
        result2AfterElvis.log("Failed: Result 2 after elvis"),
      )
    )
  }

  def testElvisWithNull: Result = {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    val s: String  = null // scalafix:ok DisableSyntax.null
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    val s2: String = null // scalafix:ok DisableSyntax.null

    val expected: String = "DEFAULT"
    val actual: String   = s ?:= "DEFAULT"
    val actual2: String  = s2 ?:= "DEFAULT"

    Result.all(
      List(
        (actual ==== expected).log("actual is not equal to expected"),
        (actual2 ==== expected).log("actual2 is not equal to expected"),
      )
    )
  }

  def testElvisWithNullAndNullAlternative: Result = {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    val s: String  = null // scalafix:ok DisableSyntax.null
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    val s2: String = null // scalafix:ok DisableSyntax.null

    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    val expected: String = null // scalafix:ok DisableSyntax.null
    val actual: String   = s ?:= expected
    val actual2: String  = s2 ?:= expected

    Result.all(
      List(
        (actual ==== expected).log("actual is not equal to expected"),
        (actual2 ==== expected).log("actual2 is not equal to expected"),
      )
    )
  }

  def testElvisWithNullShouldEvaluateTheAlternativeValue: Result = {
    val alternativeValue: String = "DEFAULT"

    @SuppressWarnings(Array("org.wartremover.warts.Var"))
    var evaluatedAlternative: Option[String]  = None // scalafix:ok DisableSyntax.var
    @SuppressWarnings(Array("org.wartremover.warts.Var"))
    var evaluatedAlternative2: Option[String] = None // scalafix:ok DisableSyntax.var

    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    val s: String  = null // scalafix:ok DisableSyntax.null
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    val s2: String = null // scalafix:ok DisableSyntax.null

    val expected = alternativeValue

    val resultBeforeElvis  = evaluatedAlternative ==== None
    val result2BeforeElvis = evaluatedAlternative2 ==== None

    val actual = s ?:= {
      evaluatedAlternative = Some(alternativeValue)
      alternativeValue
    }

    val actual2 = s2 ?:= {
      evaluatedAlternative2 = Some(alternativeValue)
      alternativeValue
    }

    val resultAfterElvis  = evaluatedAlternative ==== Some(expected)
    val result2AfterElvis = evaluatedAlternative2 ==== Some(expected)

    Result.all(
      List(
        resultBeforeElvis.log("Failed: Result before elvis"),
        (actual ==== expected).log("actual is not equal to expected"),
        resultAfterElvis.log("Failed: Result after elvis"),
        ///
        result2BeforeElvis.log("Failed: Result 2 before elvis"),
        (actual2 ==== expected).log("actual2 is not equal to expected"),
        result2AfterElvis.log("Failed: Result 2 after elvis"),
      )
    )
  }

}
