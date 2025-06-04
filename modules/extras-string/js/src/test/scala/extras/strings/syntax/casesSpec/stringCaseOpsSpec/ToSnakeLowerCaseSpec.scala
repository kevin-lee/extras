package extras.strings.syntax.casesSpec.stringCaseOpsSpec

import extras.strings.syntax.casesSpec.Gens
import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2023-10-26
  */
object ToSnakeLowerCaseSpec extends Properties {
  // String.toSnakeLowerCase

  import extras.strings.syntax.cases._

  def tests: List[Test] = List(
    property(
      "test String.toSnakeLowerCase with already snake_case",
      testToSnakeLowerCaseWithAlreadySnakeCase,
    ),
    property("test String.toSnakeLowerCase with PascalCases", testToSnakeLowerCaseWithPascalCases),
    property("test String.toSnakeLowerCase with camelCases", testToSnakeLowerCaseWithCamelCases),
    property("test String.toSnakeLowerCase with all UPPERCASE", testToSnakeLowerCaseWithUpperCase),
    property("test String.toSnakeLowerCase with all lowercase", testToSnakeLowerCaseWithLowerCase),
    property("test String.toSnakeLowerCase with all Snake_Cases", testToSnakeLowerCaseWithSnakeCases),
    property("test String.toSnakeLowerCase with all lower_snake_cases", testToSnakeLowerCaseWithLowerSnakeCases),
    property("test String.toSnakeLowerCase with all UPPER_SNAKE_CASES", testToSnakeLowerCaseWithUpperSnakeCases),
    property("test String.toSnakeLowerCase with all Kebab-Cases", testToSnakeLowerCaseWithKebabCases),
    property("test String.toSnakeLowerCase with all lower-kebab-cases", testToSnakeLowerCaseWithLowerKebabCases),
    property("test String.toSnakeLowerCase with all UPPER-KEBAB-CASES", testToSnakeLowerCaseWithUpperKebabCases),
    property(
      "test String.toSnakeLowerCase with all space separated String values",
      testToSnakeLowerCaseWithSpaceSeparatedStringValues,
    ),
    property(
      "test String.toSnakeLowerCase with all lower space separated String values",
      testToSnakeLowerCaseWithLowerSpaceSeparatedStringValues,
    ),
    property(
      "test String.toSnakeLowerCase with all UPPER SPACE SEPARATED STRING VALUES",
      testToSnakeLowerCaseWithUpperSpaceSeparatedStringValues,
    ),
  )

  def testToSnakeLowerCaseWithAlreadySnakeCase: Property = for {
    input    <- Gen
                  .string(Gen.lower, Range.linear(1, 10))
                  .list(Range.linear(1, 10))
                  .map(_.mkString("_"))
                  .log("input")
    expected <- Gen.constant(input.map(_.toLower)).log("expected")
  } yield {
    val actual = input.toSnakeLowerCase
    actual ==== expected
  }

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testToSnakeLowerCaseWithPascalCases: Property = for {
    ss    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("ss")
    input <- Gen.constant(ss.mkString).log("input")
  } yield {
    val expected = ss.map(_.toLowerCase()).mkString("_")
    val actual   = input.toSnakeLowerCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testToSnakeLowerCaseWithCamelCases: Property = for {
    s     <- Gen.string(Gen.lower, Range.linear(1, 10)).log("s")
    ss    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("ss")
    input <- Gen.constant(s + ss.mkString).log("input")
  } yield {
    val expected = (s :: ss).map(_.toLowerCase()).mkString("_")
    val actual   = input.toSnakeLowerCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testToSnakeLowerCaseWithUpperCase: Property = for {
    expected <- Gen.string(Gen.lower, Range.linear(1, 10)).log("expected")
    input    <- Gen.constant(expected.toUpperCase()).log("input")
  } yield {
    val actual = input.toSnakeLowerCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToSnakeLowerCaseWithLowerCase: Property = for {
    input    <- Gen.string(Gen.lower, Range.linear(1, 10)).log("input")
    expected <- Gen.constant(input).log("expected")
  } yield {
    val actual = input.toSnakeLowerCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testToSnakeLowerCaseWithSnakeCases: Property = for {
    ss    <- Gens.genPascalCase(1, 10).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("_")).log("input")
  } yield {
    val expected = ss.map(_.toLowerCase()).mkString("_")
    val actual   = input.toSnakeLowerCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToSnakeLowerCaseWithLowerSnakeCases: Property = for {
    ss    <- Gen.string(Gen.lower, Range.linear(1, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("_")).log("input")
  } yield {
    val expected = ss.mkString("_")
    val actual   = input.toSnakeLowerCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testToSnakeLowerCaseWithUpperSnakeCases: Property = for {
    ss    <- Gen.string(Gen.upper, Range.linear(1, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("_")).log("input")
  } yield {
    val expected = ss.map(_.toLowerCase()).mkString("_")
    val actual   = input.toSnakeLowerCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testToSnakeLowerCaseWithKebabCases: Property = for {
    ss    <- Gens.genPascalCase(1, 10).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("-")).log("input")
  } yield {
    val expected = ss.map(_.toLowerCase()).mkString("_")
    val actual   = input.toSnakeLowerCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToSnakeLowerCaseWithLowerKebabCases: Property = for {
    ss    <- Gen.string(Gen.lower, Range.linear(1, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("-")).log("input")
  } yield {
    val expected = ss.mkString("_")
    val actual   = input.toSnakeLowerCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testToSnakeLowerCaseWithUpperKebabCases: Property = for {
    ss    <- Gen.string(Gen.upper, Range.linear(1, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant(ss.mkString("-")).log("input")
  } yield {
    val expected = ss.map(_.toLowerCase()).mkString("_")
    val actual   = input.toSnakeLowerCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testToSnakeLowerCaseWithSpaceSeparatedStringValues: Property = for {
    s     <- Gens.genPascalCase(1, 10).log("ss")
    ss    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("ss")
    input <- Gen.constant((s :: ss).mkString(" ")).log("input")
  } yield {
    val expected = (s :: ss).map(_.toLowerCase()).mkString("_")
    val actual   = input.toSnakeLowerCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToSnakeLowerCaseWithLowerSpaceSeparatedStringValues: Property = for {
    s     <- Gen.string(Gen.lower, Range.linear(1, 10)).log("s")
    ss    <- Gen.string(Gen.lower, Range.linear(2, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant((s :: ss).mkString(" ")).log("input")
  } yield {
    val expected = (s :: ss).mkString("_")
    val actual   = input.toSnakeLowerCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testToSnakeLowerCaseWithUpperSpaceSeparatedStringValues: Property = for {
    s     <- Gen.string(Gen.upper, Range.linear(1, 10)).log("s")
    ss    <- Gen.string(Gen.upper, Range.linear(2, 10)).list(Range.linear(1, 5)).log("ss")
    input <- Gen.constant((s :: ss).mkString(" ")).log("input")
  } yield {
    val expected = (s :: ss).map(_.toLowerCase()).mkString("_")
    val actual   = input.toSnakeLowerCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }
}
