package extras.strings.syntax.casesSpec.stringCaseOpsSpec

import extras.strings.syntax.casesSpec.Gens
import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2023-10-29
  */
object ToKebabCaseSpec extends Properties {
  // String.toKebabCase

  import extras.strings.syntax.cases._

  def tests: List[Test] = List(
    property(
      "test String.toKebabCase with already kebab-case",
      testToKebabCaseWithAlreadyKebabCase,
    ),
    property("test String.toKebabCase with PascalCases", testToKebabCaseWithPascalCases),
    property("test String.toKebabCase with camelCases", testToKebabCaseWithCamelCases),
    property("test String.toKebabCase with all UPPERCASE", testToKebabCaseWithUpperCase),
    property("test String.toKebabCase with all lowercase", testToKebabCaseWithLowerCase),
    property("test String.toKebabCase with all Snake_Cases", testToKebabCaseWithSnakeCases),
    property("test String.toKebabCase with all lower_snake_cases", testToKebabCaseWithLowerSnakeCases),
    property("test String.toKebabCase with all UPPER_SNAKE_CASES", testToKebabCaseWithUpperSnakeCases),
    property("test String.toKebabCase with all Kebab-Cases", testToKebabCaseWithKebabCases),
    property("test String.toKebabCase with all lower-kebab-cases", testToKebabCaseWithLowerKebabCases),
    property("test String.toKebabCase with all UPPER-KEBAB-CASES", testToKebabCaseWithUpperKebabCases),
    property(
      "test String.toKebabCase with all space separated String values",
      testToKebabCaseWithSpaceSeparatedStringValues,
    ),
    property(
      "test String.toKebabCase with all lower space separated String values",
      testToKebabCaseWithLowerSpaceSeparatedStringValues,
    ),
    property(
      "test String.toKebabCase with all UPPER SPACE SEPARATED STRING VALUES",
      testToKebabCaseWithUpperSpaceSeparatedStringValues,
    ),
  )

  def testToKebabCaseWithAlreadyKebabCase: Property = for {
    expected <- Gen
                  .string(Gen.lower, Range.linear(1, 10))
                  .list(Range.linear(1, 10))
                  .map(_.mkString("-"))
                  .log("expected")
    input    <- Gen.constant(expected).log("input")
  } yield {
    val actual = input.toKebabCase
    actual ==== expected
  }

  def testToKebabCaseWithPascalCases: Property = for {
    ss    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("ss")
    input <- Gen.constant(ss.mkString).log("input")
  } yield {
    val expected = ss.mkString("-")
    val actual   = input.toKebabCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabCaseWithCamelCases: Property = for {
    s     <- Gen.string(Gen.lower, Range.linear(1, 10)).log("s")
    ss    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("ss")
    input <- Gen.constant(s + ss.mkString).log("input")
  } yield {
    val expected = (s :: ss).mkString("-")
    val actual   = input.toKebabCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabCaseWithUpperCase: Property = for {
    expected <- Gen.string(Gen.upper, Range.linear(1, 10)).log("expected")
    input    <- Gen.constant(expected).log("input")
  } yield {
    val actual = input.toKebabCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabCaseWithLowerCase: Property = for {
    expected <- Gen.string(Gen.lower, Range.linear(1, 10)).log("expected")
    input    <- Gen.constant(expected).log("input")
  } yield {
    val actual = input.toKebabCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabCaseWithSnakeCases: Property = for {
    ss    <- Gens.genPascalCase(1, 10).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("_")).log("input")
  } yield {
    val expected = ss.mkString("-")
    val actual   = input.toKebabCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabCaseWithLowerSnakeCases: Property = for {
    ss    <- Gen.string(Gen.lower, Range.linear(1, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("_")).log("input")
  } yield {
    val expected = ss.mkString("-")
    val actual   = input.toKebabCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabCaseWithUpperSnakeCases: Property = for {
    ss    <- Gen.string(Gen.upper, Range.linear(1, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("_")).log("input")
  } yield {
    val expected = ss.mkString("-")
    val actual   = input.toKebabCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabCaseWithKebabCases: Property = for {
    ss    <- Gens.genPascalCase(1, 10).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("-")).log("input")
  } yield {
    val expected = ss.mkString("-")
    val actual   = input.toKebabCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabCaseWithLowerKebabCases: Property = for {
    ss    <- Gen.string(Gen.lower, Range.linear(1, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("-")).log("input")
  } yield {
    val expected = ss.mkString("-")
    val actual   = input.toKebabCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabCaseWithUpperKebabCases: Property = for {
    ss    <- Gen.string(Gen.upper, Range.linear(1, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("-")).log("input")
  } yield {
    val expected = ss.mkString("-")
    val actual   = input.toKebabCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabCaseWithSpaceSeparatedStringValues: Property = for {
    s     <- Gens.genPascalCase(1, 10).log("ss")
    ss    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("ss")
    input <- Gen.constant((s :: ss).mkString(" ")).log("input")
  } yield {
    val expected = (s :: ss).mkString("-")
    val actual   = input.toKebabCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabCaseWithLowerSpaceSeparatedStringValues: Property = for {
    s     <- Gen.string(Gen.lower, Range.linear(1, 10)).log("s")
    ss    <- Gen.string(Gen.lower, Range.linear(2, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant((s :: ss).mkString(" ")).log("input")
  } yield {
    val expected = (s :: ss).mkString("-")
    val actual   = input.toKebabCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToKebabCaseWithUpperSpaceSeparatedStringValues: Property = for {
    s     <- Gen.string(Gen.upper, Range.linear(1, 10)).log("s")
    ss    <- Gen.string(Gen.upper, Range.linear(2, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant((s :: ss).mkString(" ")).log("input")
  } yield {
    val expected = (s :: ss).mkString("-")
    val actual   = input.toKebabCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }
}
