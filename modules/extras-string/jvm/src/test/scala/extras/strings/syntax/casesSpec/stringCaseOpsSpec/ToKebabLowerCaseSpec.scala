package extras.strings.syntax.casesSpec.stringCaseOpsSpec

import extras.strings.syntax.casesSpec.Gens
import hedgehog._
import hedgehog.runner._

import java.util.Locale

/** @author Kevin Lee
  * @since 2023-10-26
  */
object ToKebabLowerCaseSpec extends Properties {
  // String.toKebabLowerCase

  import extras.strings.syntax.cases._

  def tests: List[Test] = List(
    property(
      "test String.toKebabLowerCase with already kebab-cases",
      testToKebabLowerCaseWithAlreadyKebabCase,
    ),
    property("test String.toKebabLowerCase with PascalCases", testToKebabLowerCaseWithPascalCases),
    property("test String.toKebabLowerCase with camelCases", testToKebabLowerCaseWithCamelCases),
    property("test String.toKebabLowerCase with all UPPERCASE", testToKebabLowerCaseWithUpperCase),
    property("test String.toKebabLowerCase with all lowercase", testToKebabLowerCaseWithLowerCase),
    property("test String.toKebabLowerCase with all Snake_Cases", testToKebabLowerCaseWithSnakeCases),
    property("test String.toKebabLowerCase with all lower_snake_cases", testToKebabLowerCaseWithLowerSnakeCases),
    property("test String.toKebabLowerCase with all UPPER_SNAKE_CASES", testToKebabLowerCaseWithUpperSnakeCases),
    property("test String.toKebabLowerCase with all Kebab-Cases", testToKebabLowerCaseWithKebabCases),
    property("test String.toKebabLowerCase with all lower-kebab-cases", testToKebabLowerCaseWithLowerKebabCases),
    property("test String.toKebabLowerCase with all UPPER-KEBAB-CASES", testToKebabLowerCaseWithUpperKebabCases),
    property(
      "test String.toKebabLowerCase with all space separated String values",
      testToKebabLowerCaseWithSpaceSeparatedStringValues,
    ),
    property(
      "test String.toKebabLowerCase with all lower space separated String values",
      testToKebabLowerCaseWithLowerSpaceSeparatedStringValues,
    ),
    property(
      "test String.toKebabLowerCase with all UPPER SPACE SEPARATED STRING VALUES",
      testToKebabLowerCaseWithUpperSpaceSeparatedStringValues,
    ),
  )

  def testToKebabLowerCaseWithAlreadyKebabCase: Property = for {
    input    <- Gen
                  .string(Gen.lower, Range.linear(1, 10))
                  .list(Range.linear(1, 10))
                  .map(_.mkString("-"))
                  .log("input")
    expected <- Gen.constant(input.map(_.toLower)).log("expected")
  } yield {
    val actual = input.toKebabLowerCase
    actual ==== expected
  }

  def testToKebabLowerCaseWithPascalCases: Property = for {
    ss    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("ss")
    input <- Gen.constant(ss.mkString).log("input")
  } yield {
    val expected = ss.map(_.toLowerCase(Locale.ENGLISH)).mkString("-")
    val actual   = input.toKebabLowerCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabLowerCaseWithCamelCases: Property = for {
    s     <- Gen.string(Gen.lower, Range.linear(1, 10)).log("s")
    ss    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("ss")
    input <- Gen.constant(s + ss.mkString).log("input")
  } yield {
    val expected = (s :: ss).map(_.toLowerCase(Locale.ENGLISH)).mkString("-")
    val actual   = input.toKebabLowerCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabLowerCaseWithUpperCase: Property = for {
    expected <- Gen.string(Gen.lower, Range.linear(1, 10)).log("expected")
    input    <- Gen.constant(expected.toUpperCase(Locale.ENGLISH)).log("input")
  } yield {
    val actual = input.toKebabLowerCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabLowerCaseWithLowerCase: Property = for {
    input    <- Gen.string(Gen.lower, Range.linear(1, 10)).log("input")
    expected <- Gen.constant(input).log("expected")
  } yield {
    val actual = input.toKebabLowerCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabLowerCaseWithSnakeCases: Property = for {
    ss    <- Gens.genPascalCase(1, 10).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("_")).log("input")
  } yield {
    val expected = ss.map(_.toLowerCase(Locale.ENGLISH)).mkString("-")
    val actual   = input.toKebabLowerCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabLowerCaseWithLowerSnakeCases: Property = for {
    ss    <- Gen.string(Gen.lower, Range.linear(1, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("_")).log("input")
  } yield {
    val expected = ss.mkString("-")
    val actual   = input.toKebabLowerCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabLowerCaseWithUpperSnakeCases: Property = for {
    ss    <- Gen.string(Gen.upper, Range.linear(1, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("_")).log("input")
  } yield {
    val expected = ss.map(_.toLowerCase(Locale.ENGLISH)).mkString("-")
    val actual   = input.toKebabLowerCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabLowerCaseWithKebabCases: Property = for {
    ss    <- Gens.genPascalCase(1, 10).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("-")).log("input")
  } yield {
    val expected = ss.map(_.toLowerCase(Locale.ENGLISH)).mkString("-")
    val actual   = input.toKebabLowerCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabLowerCaseWithLowerKebabCases: Property = for {
    ss    <- Gen.string(Gen.lower, Range.linear(1, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("-")).log("input")
  } yield {
    val expected = ss.mkString("-")
    val actual   = input.toKebabLowerCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabLowerCaseWithUpperKebabCases: Property = for {
    ss    <- Gen.string(Gen.upper, Range.linear(1, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("-")).log("input")
  } yield {
    val expected = ss.map(_.toLowerCase(Locale.ENGLISH)).mkString("-")
    val actual   = input.toKebabLowerCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabLowerCaseWithSpaceSeparatedStringValues: Property = for {
    s     <- Gens.genPascalCase(1, 10).log("ss")
    ss    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("ss")
    input <- Gen.constant((s :: ss).mkString(" ")).log("input")
  } yield {
    val expected = (s :: ss).map(_.toLowerCase(Locale.ENGLISH)).mkString("-")
    val actual   = input.toKebabLowerCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabLowerCaseWithLowerSpaceSeparatedStringValues: Property = for {
    s     <- Gen.string(Gen.lower, Range.linear(1, 10)).log("s")
    ss    <- Gen.string(Gen.lower, Range.linear(2, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant((s :: ss).mkString(" ")).log("input")
  } yield {
    val expected = (s :: ss).mkString("-")
    val actual   = input.toKebabLowerCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabLowerCaseWithUpperSpaceSeparatedStringValues: Property = for {
    s     <- Gen.string(Gen.upper, Range.linear(1, 10)).log("s")
    ss    <- Gen.string(Gen.upper, Range.linear(2, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant((s :: ss).mkString(" ")).log("input")
  } yield {
    val expected = (s :: ss).map(_.toLowerCase(Locale.ENGLISH)).mkString("-")
    val actual   = input.toKebabLowerCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }
}
