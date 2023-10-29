package extras.strings.syntax.casesSpec.stringCaseOpsSpec

import extras.strings.syntax.casesSpec.Gens
import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2023-10-16
  */
object ToSnakeCaseSpec extends Properties {
  // String.toSnakeCase

  import extras.strings.syntax.cases._

  def tests: List[Test] = List(
    property(
      "test String.toSnakeCase with already snake_case",
      testToSnakeCaseWithAlreadySnakeCase,
    ),
    property("test String.toSnakeCase with PascalCases", testToSnakeCaseWithPascalCases),
    property("test String.toSnakeCase with camelCases", testToSnakeCaseWithCamelCases),
    property("test String.toSnakeCase with all UPPERCASE", testToSnakeCaseWithUpperCase),
    property("test String.toSnakeCase with all lowercase", testToSnakeCaseWithLowerCase),
    property("test String.toSnakeCase with all Snake_Cases", testToSnakeCaseWithSnakeCases),
    property("test String.toSnakeCase with all lower_snake_cases", testToSnakeCaseWithLowerSnakeCases),
    property("test String.toSnakeCase with all UPPER_SNAKE_CASES", testToSnakeCaseWithUpperSnakeCases),
    property("test String.toSnakeCase with all Kebab-Cases", testToSnakeCaseWithKebabCases),
    property("test String.toSnakeCase with all lower-kebab-cases", testToSnakeCaseWithLowerKebabCases),
    property("test String.toSnakeCase with all UPPER-KEBAB-CASES", testToSnakeCaseWithUpperKebabCases),
    property(
      "test String.toSnakeCase with all space separated String values",
      testToSnakeCaseWithSpaceSeparatedStringValues,
    ),
    property(
      "test String.toSnakeCase with all lower space separated String values",
      testToSnakeCaseWithLowerSpaceSeparatedStringValues,
    ),
    property(
      "test String.toSnakeCase with all UPPER SPACE SEPARATED STRING VALUES",
      testToSnakeCaseWithUpperSpaceSeparatedStringValues,
    ),
  )

  def testToSnakeCaseWithAlreadySnakeCase: Property = for {
    expected <- Gen
                  .string(Gen.lower, Range.linear(1, 10))
                  .list(Range.linear(1, 10))
                  .map(_.mkString("_"))
                  .log("expected")
    input    <- Gen.constant(expected).log("input")
  } yield {
    val actual = input.toSnakeCase
    actual ==== expected
  }

  def testToSnakeCaseWithPascalCases: Property = for {
    ss    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("ss")
    input <- Gen.constant(ss.mkString).log("input")
  } yield {
    val expected = ss.mkString("_")
    val actual   = input.toSnakeCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToSnakeCaseWithCamelCases: Property = for {
    s     <- Gen.string(Gen.lower, Range.linear(1, 10)).log("s")
    ss    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("ss")
    input <- Gen.constant(s + ss.mkString).log("input")
  } yield {
    val expected = (s :: ss).mkString("_")
    val actual   = input.toSnakeCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToSnakeCaseWithUpperCase: Property = for {
    expected <- Gen.string(Gen.upper, Range.linear(1, 10)).log("expected")
    input    <- Gen.constant(expected).log("input")
  } yield {
    val actual = input.toSnakeCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToSnakeCaseWithLowerCase: Property = for {
    expected <- Gen.string(Gen.lower, Range.linear(1, 10)).log("expected")
    input    <- Gen.constant(expected).log("input")
  } yield {
    val actual = input.toSnakeCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToSnakeCaseWithSnakeCases: Property = for {
    ss    <- Gens.genPascalCase(1, 10).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("_")).log("input")
  } yield {
    val expected = ss.mkString("_")
    val actual   = input.toSnakeCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToSnakeCaseWithLowerSnakeCases: Property = for {
    ss    <- Gen.string(Gen.lower, Range.linear(1, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("_")).log("input")
  } yield {
    val expected = ss.mkString("_")
    val actual   = input.toSnakeCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToSnakeCaseWithUpperSnakeCases: Property = for {
    ss    <- Gen.string(Gen.upper, Range.linear(1, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("_")).log("input")
  } yield {
    val expected = ss.mkString("_")
    val actual   = input.toSnakeCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToSnakeCaseWithKebabCases: Property = for {
    ss    <- Gens.genPascalCase(1, 10).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("-")).log("input")
  } yield {
    val expected = ss.mkString("_")
    val actual   = input.toSnakeCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToSnakeCaseWithLowerKebabCases: Property = for {
    ss    <- Gen.string(Gen.lower, Range.linear(1, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("-")).log("input")
  } yield {
    val expected = ss.mkString("_")
    val actual   = input.toSnakeCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToSnakeCaseWithUpperKebabCases: Property = for {
    ss    <- Gen.string(Gen.upper, Range.linear(1, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("-")).log("input")
  } yield {
    val expected = ss.mkString("_")
    val actual   = input.toSnakeCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToSnakeCaseWithSpaceSeparatedStringValues: Property = for {
    s     <- Gens.genPascalCase(1, 10).log("ss")
    ss    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("ss")
    input <- Gen.constant((s :: ss).mkString(" ")).log("input")
  } yield {
    val expected = (s :: ss).mkString("_")
    val actual   = input.toSnakeCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToSnakeCaseWithLowerSpaceSeparatedStringValues: Property = for {
    s     <- Gen.string(Gen.lower, Range.linear(1, 10)).log("s")
    ss    <- Gen.string(Gen.lower, Range.linear(2, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant((s :: ss).mkString(" ")).log("input")
  } yield {
    val expected = (s :: ss).mkString("_")
    val actual   = input.toSnakeCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToSnakeCaseWithUpperSpaceSeparatedStringValues: Property = for {
    s     <- Gen.string(Gen.upper, Range.linear(1, 10)).log("s")
    ss    <- Gen.string(Gen.upper, Range.linear(2, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant((s :: ss).mkString(" ")).log("input")
  } yield {
    val expected = (s :: ss).mkString("_")
    val actual   = input.toSnakeCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }
}
