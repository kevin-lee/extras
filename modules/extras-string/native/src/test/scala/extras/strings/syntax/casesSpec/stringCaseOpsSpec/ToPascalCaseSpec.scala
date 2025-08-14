package extras.strings.syntax.casesSpec.stringCaseOpsSpec

import extras.strings.syntax.casesSpec.Gens
import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2023-10-16
  */
object ToPascalCaseSpec extends Properties {
  // String.toPascalCase
  import extras.strings.syntax.cases._

  override def tests: List[Test] = List(
    property("test String.toPascalCase with already PascalCase", testToPascalCaseWithAlreadyPascalCase),
    property("test String.toPascalCase with PascalCases", testToPascalCaseWithPascalCases),
    property("test String.toPascalCase with camelCases", testToPascalCaseWithCamelCases),
    property("test String.toPascalCase with all UPPERCASE", testToPascalCaseWithUpperCase),
    property("test String.toPascalCase with all lowercase", testToPascalCaseWithLowerCase),
    property("test String.toPascalCase with all snake_cases", testToPascalCaseWithSnakeCases),
    property("test String.toPascalCase with all lower_snake_cases", testToPascalCaseWithLowerSnakeCases),
    property("test String.toPascalCase with all UPPER_SNAKE_CASES", testToPascalCaseWithUpperSnakeCases),
    property("test String.toPascalCase with all kebab-cases", testToPascalCaseWithKebabCases),
    property("test String.toPascalCase with all lower-kebab-cases", testToPascalCaseWithLowerKebabCases),
    property("test String.toPascalCase with all UPPER-KEBAB-CASES", testToPascalCaseWithUpperKebabCases),
    property(
      "test String.toPascalCase with all space separated String values",
      testToPascalCaseWithSpaceSeparatedStringValues,
    ),
    property(
      "test String.toPascalCase with all lower space separated String values",
      testToPascalCaseWithLowerSpaceSeparatedStringValues,
    ),
    property(
      "test String.toPascalCase with all UPPER SPACE SEPARATED STRING VALUES",
      testToPascalCaseWithUpperSpaceSeparatedStringValues,
    ),
  )

  def testToPascalCaseWithAlreadyPascalCase: Property = for {
    expected <- Gens.genPascalCase(1, 10).log("expected")
    input    <- Gen.constant(expected).log("input")
  } yield {
    val actual = input.toPascalCase
    actual ==== expected
  }

  def testToPascalCaseWithPascalCases: Property = for {
    s1    <- Gens.genPascalCase(1, 10).log("s1")
    s2    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
    input <- Gen.constant(s1 + s2.mkString).log("input")
  } yield {
    val expected = s1 + s2.mkString
    val actual   = input.toPascalCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToPascalCaseWithCamelCases: Property = for {
    s1    <- Gens.genPascalCase(1, 10).log("s1")
    s2    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
    input <- Gen.constant(s1.updated(0, s1.charAt(0).toLower) + s2.mkString).log("input")
  } yield {
    val expected = s1 + s2.mkString
    val actual   = input.toPascalCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testToPascalCaseWithUpperCase: Property = for {
    expected <- Gens.genPascalCase(1, 10).log("expected")
    input    <- Gen.constant(expected.toUpperCase()).log("input")
  } yield {
    val actual = input.toPascalCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testToPascalCaseWithLowerCase: Property = for {
    expected <- Gens.genPascalCase(1, 10).log("expected")
    input    <- Gen.constant(expected.toLowerCase()).log("input")
  } yield {
    val actual = input.toPascalCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToPascalCaseWithSnakeCases: Property = for {
    s     <- Gens.genPascalCase(1, 10).list(Range.linear(1, 10)).log("s")
    input <- Gen.constant(s.mkString("_")).log("input")
  } yield {
    val expected = s.mkString
    val actual   = input.toPascalCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testToPascalCaseWithLowerSnakeCases: Property = for {
    s     <- Gens.genPascalCase(1, 10).list(Range.linear(1, 10)).log("s")
    input <- Gen.constant(s.map(_.toLowerCase()).mkString("_")).log("input")
  } yield {
    val expected = s.mkString
    val actual   = input.toPascalCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testToPascalCaseWithUpperSnakeCases: Property = for {
    s     <- Gens.genPascalCase(1, 10).list(Range.linear(1, 10)).log("s")
    input <- Gen.constant(s.map(_.toUpperCase()).mkString("_")).log("input")

  } yield {
    val expected = s.mkString
    val actual   = input.toPascalCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToPascalCaseWithKebabCases: Property = for {
    s     <- Gens.genPascalCase(1, 10).list(Range.linear(1, 10)).log("s")
    input <- Gen.constant(s.mkString("-")).log("input")
  } yield {
    val expected = s.mkString
    val actual   = input.toPascalCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testToPascalCaseWithLowerKebabCases: Property = for {
    s     <- Gens.genPascalCase(1, 10).list(Range.linear(1, 10)).log("s")
    input <- Gen.constant(s.map(_.toLowerCase()).mkString("-")).log("input")
  } yield {
    val expected = s.mkString
    val actual   = input.toPascalCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testToPascalCaseWithUpperKebabCases: Property = for {
    s     <- Gens.genPascalCase(1, 10).list(Range.linear(1, 10)).log("s")
    input <- Gen.constant(s.map(_.toUpperCase()).mkString("-")).log("input")
  } yield {
    val expected = s.mkString
    val actual   = input.toPascalCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToPascalCaseWithSpaceSeparatedStringValues: Property = for {
    s     <- Gens.genPascalCase(1, 10).list(Range.linear(1, 10)).log("s")
    input <- Gen.constant(s.mkString(" ")).log("input")
  } yield {
    val expected = s.mkString
    val actual   = input.toPascalCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testToPascalCaseWithLowerSpaceSeparatedStringValues: Property = for {
    s     <- Gens.genPascalCase(1, 10).list(Range.linear(1, 10)).log("s")
    input <- Gen.constant(s.map(_.toLowerCase()).mkString(" ")).log("input")
  } yield {
    val expected = s.mkString
    val actual   = input.toPascalCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testToPascalCaseWithUpperSpaceSeparatedStringValues: Property = for {
    s     <- Gens.genPascalCase(1, 10).list(Range.linear(1, 10)).log("s")
    input <- Gen.constant(s.map(_.toUpperCase()).mkString(" ")).log("input")
  } yield {
    val expected = s.mkString
    val actual   = input.toPascalCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }
}
