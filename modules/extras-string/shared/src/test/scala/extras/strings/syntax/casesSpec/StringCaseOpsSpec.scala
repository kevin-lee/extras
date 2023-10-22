package extras.strings.syntax.casesSpec

import hedgehog._
import hedgehog.runner._

import java.util.Locale

/** @author Kevin Lee
  * @since 2023-10-16
  */

object StringCaseOpsSpec extends Properties {
  import extras.strings.syntax.cases._

  override def tests: List[Test] = List(
    // String.toPascalCase
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

    // String.toOnePascalCase
    property("test String.toOnePascalCase with already PascalCase", testToOnePascalCaseWithAlreadyPascalCase),
    property("test String.toOnePascalCase with PascalCases", testToOnePascalCaseWithPascalCases),
    property("test String.toOnePascalCase with camelCases", testToOnePascalCaseWithCamelCases),
    property("test String.toOnePascalCase with all UPPERCASE", testToOnePascalCaseWithUpperCase),
    property("test String.toOnePascalCase with all lowercase", testToOnePascalCaseWithLowerCase),

    // String.toCamelCase
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

    // String.SplitByCase
    property("test String.SplitByCase with PascalCase", testSplitByCaseWithPascalCase),
    property("test String.SplitByCase with PascalCases", testSplitByCaseWithPascalCases),
    property("test String.SplitByCase with camelCases", testSplitByCaseWithCamelCases),
    property("test String.SplitByCase with all UPPERCASE", testSplitByCaseWithUpperCase),
    property("test String.SplitByCase with all lowercase", testSplitByCaseWithLowerCase),
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

  def testToPascalCaseWithUpperCase: Property = for {
    expected <- Gens.genPascalCase(1, 10).log("expected")
    input    <- Gen.constant(expected.toUpperCase(Locale.ENGLISH)).log("input")
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

  def testToPascalCaseWithLowerCase: Property = for {
    expected <- Gens.genPascalCase(1, 10).log("expected")
    input    <- Gen.constant(expected.toLowerCase(Locale.ENGLISH)).log("input")
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

  def testToPascalCaseWithLowerSnakeCases: Property = for {
    s     <- Gens.genPascalCase(1, 10).list(Range.linear(1, 10)).log("s")
    input <- Gen.constant(s.map(_.toLowerCase(Locale.ENGLISH)).mkString("_")).log("input")
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

  def testToPascalCaseWithUpperSnakeCases: Property = for {
    s     <- Gens.genPascalCase(1, 10).list(Range.linear(1, 10)).log("s")
    input <- Gen.constant(s.map(_.toUpperCase(Locale.ENGLISH)).mkString("_")).log("input")

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

  def testToPascalCaseWithLowerKebabCases: Property = for {
    s     <- Gens.genPascalCase(1, 10).list(Range.linear(1, 10)).log("s")
    input <- Gen.constant(s.map(_.toLowerCase(Locale.ENGLISH)).mkString("-")).log("input")
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

  def testToPascalCaseWithUpperKebabCases: Property = for {
    s     <- Gens.genPascalCase(1, 10).list(Range.linear(1, 10)).log("s")
    input <- Gen.constant(s.map(_.toUpperCase(Locale.ENGLISH)).mkString("-")).log("input")
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

  def testToPascalCaseWithLowerSpaceSeparatedStringValues: Property = for {
    s     <- Gens.genPascalCase(1, 10).list(Range.linear(1, 10)).log("s")
    input <- Gen.constant(s.map(_.toLowerCase(Locale.ENGLISH)).mkString(" ")).log("input")
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

  def testToPascalCaseWithUpperSpaceSeparatedStringValues: Property = for {
    s     <- Gens.genPascalCase(1, 10).list(Range.linear(1, 10)).log("s")
    input <- Gen.constant(s.map(_.toUpperCase(Locale.ENGLISH)).mkString(" ")).log("input")
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

  ///

  def testToOnePascalCaseWithAlreadyPascalCase: Property = for {
    expected <- Gens.genPascalCase(1, 10).log("expected")
    input    <- Gen.constant(expected).log("input")
  } yield {
    val actual = input.toOnePascalCase
    actual ==== expected
  }

  def testToOnePascalCaseWithPascalCases: Property = for {
    s1    <- Gens.genPascalCase(1, 10).log("s1")
    s2    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
    input <- Gen.constant(s1 + s2.mkString).log("input")
  } yield {
    val expected = s1 + s2.mkString.toLowerCase(Locale.ENGLISH)
    val actual   = input.toOnePascalCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToOnePascalCaseWithCamelCases: Property = for {
    s1    <- Gens.genPascalCase(1, 10).log("s1")
    s2    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
    input <- Gen.constant(s1.updated(0, s1.charAt(0).toLower) + s2.mkString).log("input")
  } yield {
    val expected = s1 + s2.mkString.toLowerCase(Locale.ENGLISH)
    val actual   = input.toOnePascalCase

    val info =
      s"""
         |>    input: $input
         |>   actual: $actual
         |> expected: $expected
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testToOnePascalCaseWithUpperCase: Property = for {
    expected <- Gens.genPascalCase(1, 10).log("expected")
    input    <- Gen.constant(expected.toUpperCase(Locale.ENGLISH)).log("input")
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

  def testToOnePascalCaseWithLowerCase: Property = for {
    expected <- Gens.genPascalCase(1, 10).log("expected")
    input    <- Gen.constant(expected.toLowerCase(Locale.ENGLISH)).log("input")
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

  ///
  def testSplitByCaseWithPascalCase: Property = for {
    s     <- Gens.genPascalCase(1, 10).log("s")
    input <- Gen.constant(s).log("input")
  } yield {
    val expected = Vector(s)
    val actual   = input.splitByCase

    val info =
      s"""
         |>    input: $input
         |>   actual: ${actual.toString}
         |> expected: ${expected.toString}
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testSplitByCaseWithPascalCases: Property = for {
    s1    <- Gens.genPascalCase(1, 10).log("s1")
    s2    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
    input <- Gen.constant(s1 + s2.mkString).log("input")
  } yield {
    val expected = (s1 :: s2).toVector
    val actual   = input.splitByCase

    val info =
      s"""
         |>    input: $input
         |>   actual: ${actual.toString}
         |> expected: ${expected.toString}
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testSplitByCaseWithCamelCases: Property = for {
    s1    <- Gens.genPascalCase(1, 10).map(s => s.updated(0, s.charAt(0).toLower)).log("s1")
    s2    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
    input <- Gen.constant(s1 + s2.mkString).log("input")
  } yield {
    val expected = (s1 :: s2).toVector
    val actual   = input.splitByCase

    val info =
      s"""   input: $input
         |  actual: ${actual.toString}
         |expected: ${expected.toString}
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testSplitByCaseWithUpperCase: Property = for {
    s     <- Gens.genPascalCase(1, 10).log("s")
    input <- Gen.constant(s.toUpperCase(Locale.ENGLISH)).log("input")
  } yield {
    val expected = Vector(input)
    val actual   = input.splitByCase

    val info =
      s"""
         |>    input: $input
         |>   actual: ${actual.toString}
         |> expected: ${expected.toString}
         |""".stripMargin

    (actual ==== expected).log(info)
  }

  def testSplitByCaseWithLowerCase: Property = for {
    s     <- Gens.genPascalCase(1, 10).log("s")
    input <- Gen.constant(s.toLowerCase(Locale.ENGLISH)).log("input")
  } yield {
    val expected = Vector(input)
    val actual   = input.splitByCase
    val info     =
      s"""
         |>    input: $input
         |>   actual: ${actual.toString}
         |> expected: ${expected.toString}
         |""".stripMargin

    (actual ==== expected).log(info)
  }

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

  def testToCamelCaseWithUpperCase: Property = for {
    expected <- Gen.string(Gen.lower, Range.linear(1, 10)).log("expected")
    input    <- Gen.constant(expected.toUpperCase(Locale.ENGLISH)).log("input")
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

  def testToCamelCaseWithLowerCase: Property = for {
    expected <- Gen.string(Gen.lower, Range.linear(1, 10)).log("expected")
    input    <- Gen.constant(expected.toLowerCase(Locale.ENGLISH)).log("input")
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

  def testToCamelCaseWithLowerSnakeCases: Property = for {
    s1    <- Gen.string(Gen.lower, Range.linear(1, 10)).log("s1")
    s2    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
    input <- Gen.constant(s1 + "_" + s2.map(_.toLowerCase(Locale.ENGLISH)).mkString("_")).log("input")
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

  def testToCamelCaseWithUpperSnakeCases: Property = for {
    s1    <- Gen.string(Gen.lower, Range.linear(1, 10)).log("s1")
    s2    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
    input <- Gen
               .constant(
                 s1.toUpperCase(Locale.ENGLISH) + "_" + s2.map(_.toUpperCase(Locale.ENGLISH)).mkString("_")
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

  def testToCamelCaseWithLowerKebabCases: Property = for {
    s1    <- Gen.string(Gen.lower, Range.linear(1, 10)).log("s1")
    s2    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
    input <- Gen.constant(s1 + "-" + s2.map(_.toLowerCase(Locale.ENGLISH)).mkString("-")).log("input")
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

  def testToCamelCaseWithUpperKebabCases: Property = for {
    s1    <- Gen.string(Gen.lower, Range.linear(1, 10)).log("s1")
    s2    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
    input <- Gen
               .constant(
                 s1.toUpperCase(Locale.ENGLISH) + "-" + s2.map(_.toUpperCase(Locale.ENGLISH)).mkString("-")
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

  def testToCamelCaseWithLowerSpaceSeparatedStringValues: Property = for {
    s1    <- Gen.string(Gen.lower, Range.linear(1, 10)).log("s1")
    s2    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
    input <- Gen.constant(s1 + " " + s2.map(_.toLowerCase(Locale.ENGLISH)).mkString(" ")).log("input")
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

  def testToCamelCaseWithUpperSpaceSeparatedStringValues: Property = for {
    s1    <- Gen.string(Gen.lower, Range.linear(1, 10)).log("s1")
    s2    <- Gens.genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
    input <- Gen
               .constant(s1.toUpperCase(Locale.ENGLISH) + " " + s2.map(_.toUpperCase(Locale.ENGLISH)).mkString(" "))
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

  ///

}
