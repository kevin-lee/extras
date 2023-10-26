package extras.strings.syntax.casesSpec.stringCaseOpsSpec

import extras.strings.syntax.casesSpec.Gens
import hedgehog._
import hedgehog.runner._

import java.util.Locale

/** @author Kevin Lee
  * @since 2023-10-25
  */
object ToSnakeUpperCaseSpec extends Properties {
  // String.toSnakeUpperCase

  import extras.strings.syntax.cases._

  def tests: List[Test] = List(
    property(
      "test String.toCamelCase with already snake_case",
      testToSnakeUpperCaseWithAlreadySnakeCase,
    ),
    property("test String.toSnakeUpperCase with PascalCases", testToSnakeUpperCaseWithPascalCases),
    property("test String.toSnakeUpperCase with camelCases", testToSnakeUpperCaseWithCamelCases),
    property("test String.toSnakeUpperCase with all UPPERCASE", testToSnakeUpperCaseWithUpperCase),
    property("test String.toSnakeUpperCase with all lowercase", testToSnakeUpperCaseWithLowerCase),
    property("test String.toSnakeUpperCase with all Snake_Cases", testToSnakeUpperCaseWithSnakeCases),
    property("test String.toSnakeUpperCase with all lower_snake_cases", testToSnakeUpperCaseWithLowerSnakeCases),
    property("test String.toSnakeUpperCase with all UPPER_SNAKE_CASES", testToSnakeUpperCaseWithUpperSnakeCases),
    property("test String.toSnakeUpperCase with all Kebab-Cases", testToSnakeUpperCaseWithKebabCases),
    property("test String.toSnakeUpperCase with all lower-kebab-cases", testToSnakeUpperCaseWithLowerKebabCases),
    property("test String.toSnakeUpperCase with all UPPER-KEBAB-CASES", testToSnakeUpperCaseWithUpperKebabCases),
    property(
      "test String.toSnakeUpperCase with all space separated String values",
      testToSnakeUpperCaseWithSpaceSeparatedStringValues,
    ),
    property(
      "test String.toSnakeUpperCase with all lower space separated String values",
      testToSnakeUpperCaseWithLowerSpaceSeparatedStringValues,
    ),
    property(
      "test String.toSnakeUpperCase with all UPPER SPACE SEPARATED STRING VALUES",
      testToSnakeUpperCaseWithUpperSpaceSeparatedStringValues,
    ),
  )

  def testToSnakeUpperCaseWithAlreadySnakeCase: Property = for {
    input    <- Gen
                  .string(Gen.lower, Range.linear(1, 10))
                  .list(Range.linear(1, 10))
                  .map(_.mkString("_"))
                  .log("input")
    expected <- Gen.constant(input.map(_.toUpper)).log("expected")
  } yield {
    val actual = input.toSnakeUpperCase
    actual ==== expected
  }

  def testToSnakeUpperCaseWithPascalCases: Property = for {
    ss    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("ss")
    input <- Gen.constant(ss.mkString).log("input")
  } yield {
    val expected = ss.map(_.toUpperCase(Locale.ENGLISH)).mkString("_")
    val actual   = input.toSnakeUpperCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToSnakeUpperCaseWithCamelCases: Property = for {
    s     <- Gen.string(Gen.lower, Range.linear(1, 10)).log("s")
    ss    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("ss")
    input <- Gen.constant(s + ss.mkString).log("input")
  } yield {
    val expected = (s :: ss).map(_.toUpperCase(Locale.ENGLISH)).mkString("_")
    val actual   = input.toSnakeUpperCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToSnakeUpperCaseWithUpperCase: Property = for {
    expected <- Gen.string(Gen.upper, Range.linear(1, 10)).log("expected")
    input    <- Gen.constant(expected).log("input")
  } yield {
    val actual = input.toSnakeUpperCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToSnakeUpperCaseWithLowerCase: Property = for {
    input    <- Gen.string(Gen.lower, Range.linear(1, 10)).log("input")
    expected <- Gen.constant(input.map(_.toUpper)).log("expected")
  } yield {
    val actual = input.toSnakeUpperCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToSnakeUpperCaseWithSnakeCases: Property = for {
    ss    <- Gens.genPascalCase(1, 10).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("_")).log("input")
  } yield {
    val expected = ss.map(_.toUpperCase(Locale.ENGLISH)).mkString("_")
    val actual   = input.toSnakeUpperCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToSnakeUpperCaseWithLowerSnakeCases: Property = for {
    ss    <- Gen.string(Gen.lower, Range.linear(1, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("_")).log("input")
  } yield {
    val expected = ss.map(_.toUpperCase(Locale.ENGLISH)).mkString("_")
    val actual   = input.toSnakeUpperCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToSnakeUpperCaseWithUpperSnakeCases: Property = for {
    ss    <- Gen.string(Gen.upper, Range.linear(1, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("_")).log("input")
  } yield {
    val expected = ss.mkString("_")
    val actual   = input.toSnakeUpperCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToSnakeUpperCaseWithKebabCases: Property = for {
    ss    <- Gens.genPascalCase(1, 10).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("-")).log("input")
  } yield {
    val expected = ss.map(_.toUpperCase(Locale.ENGLISH)).mkString("_")
    val actual   = input.toSnakeUpperCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToSnakeUpperCaseWithLowerKebabCases: Property = for {
    ss    <- Gen.string(Gen.lower, Range.linear(1, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("-")).log("input")
  } yield {
    val expected = ss.map(_.toUpperCase(Locale.ENGLISH)).mkString("_")
    val actual   = input.toSnakeUpperCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToSnakeUpperCaseWithUpperKebabCases: Property = for {
    ss    <- Gen.string(Gen.upper, Range.linear(1, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("-")).log("input")
  } yield {
    val expected = ss.map(_.toUpperCase(Locale.ENGLISH)).mkString("_")
    val actual   = input.toSnakeUpperCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToSnakeUpperCaseWithSpaceSeparatedStringValues: Property = for {
    s     <- Gens.genPascalCase(1, 10).log("ss")
    ss    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("ss")
    input <- Gen.constant((s :: ss).mkString(" ")).log("input")
  } yield {
    val expected = (s :: ss).map(_.toUpperCase(Locale.ENGLISH)).mkString("_")
    val actual   = input.toSnakeUpperCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToSnakeUpperCaseWithLowerSpaceSeparatedStringValues: Property = for {
    s     <- Gen.string(Gen.lower, Range.linear(1, 10)).log("s")
    ss    <- Gen.string(Gen.lower, Range.linear(2, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant((s :: ss).mkString(" ")).log("input")
  } yield {
    val expected = (s :: ss).map(_.toUpperCase(Locale.ENGLISH)).mkString("_")
    val actual   = input.toSnakeUpperCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToSnakeUpperCaseWithUpperSpaceSeparatedStringValues: Property = for {
    s     <- Gen.string(Gen.upper, Range.linear(1, 10)).log("s")
    ss    <- Gen.string(Gen.upper, Range.linear(2, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant((s :: ss).mkString(" ")).log("input")
  } yield {
    val expected = (s :: ss).map(_.toUpperCase(Locale.ENGLISH)).mkString("_")
    val actual   = input.toSnakeUpperCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }
}
