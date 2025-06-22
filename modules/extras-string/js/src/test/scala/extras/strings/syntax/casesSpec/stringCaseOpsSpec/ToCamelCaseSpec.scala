package extras.strings.syntax.casesSpec.stringCaseOpsSpec

import extras.strings.syntax.casesSpec.Gens

import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2023-10-16
  */
object ToCamelCaseSpec extends Properties {
  // String.toCamelCase

  import extras.strings.syntax.cases._

  override def tests: List[Test] = List(
    property(
      "test String.toCamelCase with already CamelCase (lower case)",
      testToCamelCaseWithAlreadyCamelCase,
    ),
    property("test String.toCamelCase with PascalCases", testToCamelCaseWithPascalCases),
    property("test String.toCamelCase with camelCases", testToCamelCaseWithCamelCases),
    property("test String.toCamelCase with all UPPERCASE", testToCamelCaseWithUpperCase),
    property("test String.toCamelCase with all lowercase", testToCamelCaseWithLowerCase),
    property("test String.toCamelCase with all snake_cases", testToCamelCaseWithSnakeCases),
    property("test String.toCamelCase with all lower_snake_cases", testToCamelCaseWithLowerSnakeCases),
    property("test String.toCamelCase with all UPPER_SNAKE_CASES", testToCamelCaseWithUpperSnakeCases),
    property("test String.toCamelCase with all kebab-cases", testToCamelCaseWithKebabCases),
    property("test String.toCamelCase with all lower-kebab-cases", testToCamelCaseWithLowerKebabCases),
    property("test String.toCamelCase with all UPPER-KEBAB-CASES", testToCamelCaseWithUpperKebabCases),
    property(
      "test String.toCamelCase with all space separated String values",
      testToCamelCaseWithSpaceSeparatedStringValues,
    ),
    property(
      "test String.toCamelCase with all lower space separated String values",
      testToCamelCaseWithLowerSpaceSeparatedStringValues,
    ),
    property(
      "test String.toCamelCase with all UPPER SPACE SEPARATED STRING VALUES",
      testToCamelCaseWithUpperSpaceSeparatedStringValues,
    ),
  )

  def testToCamelCaseWithAlreadyCamelCase: Property = for {
    expected <- Gen.string(Gen.lower, Range.linear(1, 10)).log("expected")
    input    <- Gen.constant(expected).log("input")
  } yield {
    val actual = input.toCamelCase
    actual ==== expected
  }

  def testToCamelCaseWithPascalCases: Property = for {
    s1    <- Gen.string(Gen.lower, Range.linear(1, 10)).log("s1")
    s2    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
    input <- Gen.constant(s1.updated(0, s1.charAt(0).toUpper) + s2.mkString).log("input")
  } yield {
    val expected = s1 + s2.mkString
    val actual   = input.toCamelCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToCamelCaseWithCamelCases: Property = for {
    s1    <- Gen.string(Gen.lower, Range.linear(1, 10)).log("s1")
    s2    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
    input <- Gen.constant(s1 + s2.mkString).log("input")
  } yield {
    val expected = s1 + s2.mkString
    val actual   = input.toCamelCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testToCamelCaseWithUpperCase: Property = for {
    expected <- Gen.string(Gen.lower, Range.linear(1, 10)).log("expected")
    input    <- Gen.constant(expected.toUpperCase()).log("input")
  } yield {
    val actual = input.toCamelCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testToCamelCaseWithLowerCase: Property = for {
    expected <- Gen.string(Gen.lower, Range.linear(1, 10)).log("expected")
    input    <- Gen.constant(expected.toLowerCase()).log("input")
  } yield {
    val actual = input.toCamelCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToCamelCaseWithSnakeCases: Property = for {
    s1    <- Gen.string(Gen.lower, Range.linear(1, 10)).log("s1")
    s2    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
    input <- Gen.constant(s1 + "_" + s2.mkString("_")).log("input")
  } yield {
    val expected = s1 + s2.mkString
    val actual   = input.toCamelCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testToCamelCaseWithLowerSnakeCases: Property = for {
    s1    <- Gen.string(Gen.lower, Range.linear(1, 10)).log("s1")
    s2    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
    input <- Gen.constant(s1 + "_" + s2.map(_.toLowerCase()).mkString("_")).log("input")
  } yield {
    val expected = s1 + s2.mkString
    val actual   = input.toCamelCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testToCamelCaseWithUpperSnakeCases: Property = for {
    s1    <- Gen.string(Gen.lower, Range.linear(1, 10)).log("s1")
    s2    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
    input <- Gen
               .constant(
                 s1.toUpperCase() + "_" + s2.map(_.toUpperCase()).mkString("_")
               )
               .log("input")
  } yield {
    val expected = s1 + s2.mkString
    val actual   = input.toCamelCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToCamelCaseWithKebabCases: Property = for {
    s1    <- Gen.string(Gen.lower, Range.linear(1, 10)).log("s1")
    s2    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
    input <- Gen.constant(s1 + "-" + s2.mkString("-")).log("input")
  } yield {
    val expected = s1 + s2.mkString
    val actual   = input.toCamelCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testToCamelCaseWithLowerKebabCases: Property = for {
    s1    <- Gen.string(Gen.lower, Range.linear(1, 10)).log("s1")
    s2    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
    input <- Gen.constant(s1 + "-" + s2.map(_.toLowerCase()).mkString("-")).log("input")
  } yield {
    val expected = s1 + s2.mkString
    val actual   = input.toCamelCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testToCamelCaseWithUpperKebabCases: Property = for {
    s1    <- Gen.string(Gen.lower, Range.linear(1, 10)).log("s1")
    s2    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
    input <- Gen
               .constant(
                 s1.toUpperCase() + "-" + s2.map(_.toUpperCase()).mkString("-")
               )
               .log("input")
  } yield {
    val expected = s1 + s2.mkString
    val actual   = input.toCamelCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToCamelCaseWithSpaceSeparatedStringValues: Property = for {
    s1    <- Gen.string(Gen.lower, Range.linear(1, 10)).log("s1")
    s2    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
    input <- Gen.constant(s1 + " " + s2.mkString(" ")).log("input")
  } yield {
    val expected = s1 + s2.mkString
    val actual   = input.toCamelCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testToCamelCaseWithLowerSpaceSeparatedStringValues: Property = for {
    s1    <- Gen.string(Gen.lower, Range.linear(1, 10)).log("s1")
    s2    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
    input <- Gen.constant(s1 + " " + s2.map(_.toLowerCase()).mkString(" ")).log("input")
  } yield {
    val expected = s1 + s2.mkString
    val actual   = input.toCamelCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testToCamelCaseWithUpperSpaceSeparatedStringValues: Property = for {
    s1    <- Gen.string(Gen.lower, Range.linear(1, 10)).log("s1")
    s2    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
    input <- Gen
               .constant(s1.toUpperCase() + " " + s2.map(_.toUpperCase()).mkString(" "))
               .log("input")
  } yield {
    val expected = s1 + s2.mkString
    val actual   = input.toCamelCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }
}
