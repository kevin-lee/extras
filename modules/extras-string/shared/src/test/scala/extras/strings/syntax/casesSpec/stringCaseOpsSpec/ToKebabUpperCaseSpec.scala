package extras.strings.syntax.casesSpec.stringCaseOpsSpec

import extras.strings.syntax.casesSpec.Gens
import hedgehog._
import hedgehog.runner._

import java.util.Locale

/** @author Kevin Lee
  * @since 2023-10-25
  */
object ToKebabUpperCaseSpec extends Properties {
  // String.toKebabUpperCase

  import extras.strings.syntax.cases._

  def tests: List[Test] = List(
    property(
      "test String.toKebabUpperCase with already kebab-cases",
      testToKebabUpperCaseWithAlreadyKebabCase,
    ),
    property("test String.toKebabUpperCase with PascalCases", testToKebabUpperCaseWithPascalCases),
    property("test String.toKebabUpperCase with camelCases", testToKebabUpperCaseWithCamelCases),
    property("test String.toKebabUpperCase with all UPPERCASE", testToKebabUpperCaseWithUpperCase),
    property("test String.toKebabUpperCase with all lowercase", testToKebabUpperCaseWithLowerCase),
    property("test String.toKebabUpperCase with all Snake_Cases", testToKebabUpperCaseWithSnakeCases),
    property("test String.toKebabUpperCase with all lower_snake_cases", testToKebabUpperCaseWithLowerSnakeCases),
    property("test String.toKebabUpperCase with all UPPER_SNAKE_CASES", testToKebabUpperCaseWithUpperSnakeCases),
    property("test String.toKebabUpperCase with all Kebab-Cases", testToKebabUpperCaseWithKebabCases),
    property("test String.toKebabUpperCase with all lower-kebab-cases", testToKebabUpperCaseWithLowerKebabCases),
    property("test String.toKebabUpperCase with all UPPER-KEBAB-CASES", testToKebabUpperCaseWithUpperKebabCases),
    property(
      "test String.toKebabUpperCase with all space separated String values",
      testToKebabUpperCaseWithSpaceSeparatedStringValues,
    ),
    property(
      "test String.toKebabUpperCase with all lower space separated String values",
      testToKebabUpperCaseWithLowerSpaceSeparatedStringValues,
    ),
    property(
      "test String.toKebabUpperCase with all UPPER SPACE SEPARATED STRING VALUES",
      testToKebabUpperCaseWithUpperSpaceSeparatedStringValues,
    ),
  )

  def testToKebabUpperCaseWithAlreadyKebabCase: Property = for {
    input    <- Gen
                  .string(Gen.lower, Range.linear(1, 10))
                  .list(Range.linear(1, 10))
                  .map(_.mkString("-"))
                  .log("input")
    expected <- Gen.constant(input.map(_.toUpper)).log("expected")
  } yield {
    val actual = input.toKebabUpperCase
    actual ==== expected
  }

  def testToKebabUpperCaseWithPascalCases: Property = for {
    ss    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("ss")
    input <- Gen.constant(ss.mkString).log("input")
  } yield {
    val expected = ss.map(_.toUpperCase(Locale.ENGLISH)).mkString("-")
    val actual   = input.toKebabUpperCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabUpperCaseWithCamelCases: Property = for {
    s     <- Gen.string(Gen.lower, Range.linear(1, 10)).log("s")
    ss    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("ss")
    input <- Gen.constant(s + ss.mkString).log("input")
  } yield {
    val expected = (s :: ss).map(_.toUpperCase(Locale.ENGLISH)).mkString("-")
    val actual   = input.toKebabUpperCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabUpperCaseWithUpperCase: Property = for {
    expected <- Gen.string(Gen.upper, Range.linear(1, 10)).log("expected")
    input    <- Gen.constant(expected).log("input")
  } yield {
    val actual = input.toKebabUpperCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabUpperCaseWithLowerCase: Property = for {
    input    <- Gen.string(Gen.lower, Range.linear(1, 10)).log("input")
    expected <- Gen.constant(input.map(_.toUpper)).log("expected")
  } yield {
    val actual = input.toKebabUpperCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabUpperCaseWithSnakeCases: Property = for {
    ss    <- Gens.genPascalCase(1, 10).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("_")).log("input")
  } yield {
    val expected = ss.map(_.toUpperCase(Locale.ENGLISH)).mkString("-")
    val actual   = input.toKebabUpperCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabUpperCaseWithLowerSnakeCases: Property = for {
    ss    <- Gen.string(Gen.lower, Range.linear(1, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("_")).log("input")
  } yield {
    val expected = ss.map(_.toUpperCase(Locale.ENGLISH)).mkString("-")
    val actual   = input.toKebabUpperCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabUpperCaseWithUpperSnakeCases: Property = for {
    ss    <- Gen.string(Gen.upper, Range.linear(1, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("_")).log("input")
  } yield {
    val expected = ss.mkString("-")
    val actual   = input.toKebabUpperCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabUpperCaseWithKebabCases: Property = for {
    ss    <- Gens.genPascalCase(1, 10).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("-")).log("input")
  } yield {
    val expected = ss.map(_.toUpperCase(Locale.ENGLISH)).mkString("-")
    val actual   = input.toKebabUpperCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabUpperCaseWithLowerKebabCases: Property = for {
    ss    <- Gen.string(Gen.lower, Range.linear(1, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("-")).log("input")
  } yield {
    val expected = ss.map(_.toUpperCase(Locale.ENGLISH)).mkString("-")
    val actual   = input.toKebabUpperCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabUpperCaseWithUpperKebabCases: Property = for {
    ss    <- Gen.string(Gen.upper, Range.linear(1, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("-")).log("input")
  } yield {
    val expected = ss.map(_.toUpperCase(Locale.ENGLISH)).mkString("-")
    val actual   = input.toKebabUpperCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabUpperCaseWithSpaceSeparatedStringValues: Property = for {
    s     <- Gens.genPascalCase(1, 10).log("ss")
    ss    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("ss")
    input <- Gen.constant((s :: ss).mkString(" ")).log("input")
  } yield {
    val expected = (s :: ss).map(_.toUpperCase(Locale.ENGLISH)).mkString("-")
    val actual   = input.toKebabUpperCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabUpperCaseWithLowerSpaceSeparatedStringValues: Property = for {
    s     <- Gen.string(Gen.lower, Range.linear(1, 10)).log("s")
    ss    <- Gen.string(Gen.lower, Range.linear(2, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant((s :: ss).mkString(" ")).log("input")
  } yield {
    val expected = (s :: ss).map(_.toUpperCase(Locale.ENGLISH)).mkString("-")
    val actual   = input.toKebabUpperCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabUpperCaseWithUpperSpaceSeparatedStringValues: Property = for {
    s     <- Gen.string(Gen.upper, Range.linear(1, 10)).log("s")
    ss    <- Gen.string(Gen.upper, Range.linear(2, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant((s :: ss).mkString(" ")).log("input")
  } yield {
    val expected = (s :: ss).map(_.toUpperCase(Locale.ENGLISH)).mkString("-")
    val actual   = input.toKebabUpperCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }
}
