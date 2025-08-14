package extras.strings.syntax.casesSpec.stringCaseOpsSpec

import extras.strings.syntax.casesSpec.Gens
import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2023-10-16
  */
object ToOnePascalCaseSpec extends Properties {
  // String.toOnePascalCase

  import extras.strings.syntax.cases._

  override def tests: List[Test] = List(
    property("test String.toOnePascalCase with already PascalCase", testToOnePascalCaseWithAlreadyPascalCase),
    property("test String.toOnePascalCase with PascalCases", testToOnePascalCaseWithPascalCases),
    property("test String.toOnePascalCase with camelCases", testToOnePascalCaseWithCamelCases),
    property("test String.toOnePascalCase with all UPPERCASE", testToOnePascalCaseWithUpperCase),
    property("test String.toOnePascalCase with all lowercase", testToOnePascalCaseWithLowerCase),
  )

  def testToOnePascalCaseWithAlreadyPascalCase: Property = for {
    expected <- Gens.genPascalCase(1, 10).log("expected")
    input    <- Gen.constant(expected).log("input")
  } yield {
    val actual = input.toOnePascalCase
    actual ==== expected
  }

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testToOnePascalCaseWithPascalCases: Property = for {
    s1    <- Gens.genPascalCase(1, 10).log("s1")
    s2    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
    input <- Gen.constant(s1 + s2.mkString).log("input")
  } yield {
    val expected = s1 + s2.mkString.toLowerCase()
    val actual   = input.toOnePascalCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testToOnePascalCaseWithCamelCases: Property = for {
    s1    <- Gens.genPascalCase(1, 10).log("s1")
    s2    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
    input <- Gen.constant(s1.updated(0, s1.charAt(0).toLower) + s2.mkString).log("input")
  } yield {
    val expected = s1 + s2.mkString.toLowerCase()
    val actual   = input.toOnePascalCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testToOnePascalCaseWithUpperCase: Property = for {
    expected <- Gens.genPascalCase(1, 10).log("expected")
    input    <- Gen.constant(expected.toUpperCase()).log("input")
  } yield {
    val actual = input.toOnePascalCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testToOnePascalCaseWithLowerCase: Property = for {
    expected <- Gens.genPascalCase(1, 10).log("expected")
    input    <- Gen.constant(expected.toLowerCase()).log("input")
  } yield {
    val actual = input.toOnePascalCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }
}
