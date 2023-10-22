package extras.strings.syntax

import extras.strings.syntax.cases._
import hedgehog._
import hedgehog.runner._

import java.util.Locale

/** @author Kevin Lee
  * @since 2023-10-16
  */
object casesSpec extends Properties {
  override def tests: List[Test] = StringCaseOpsSpec.tests ++ StringSeqCaseOpsSpec.tests

  object StringCaseOpsSpec {
    private val name      = this.getClass.getSimpleName.stripSuffix("$")
    def tests: List[Test] = List(
      // String.toPascalCase
      property(s"$name: test String.toPascalCase with already PascalCase", testToPascalCaseWithAlreadyPascalCase),
      property(s"$name: test String.toPascalCase with PascalCases", testToPascalCaseWithPascalCases),
      property(s"$name: test String.toPascalCase with camelCases", testToPascalCaseWithCamelCases),
      property(s"$name: test String.toPascalCase with all UPPERCASE", testToPascalCaseWithUpperCase),
      property(s"$name: test String.toPascalCase with all lowercase", testToPascalCaseWithLowerCase),
      property(s"$name: test String.toPascalCase with all snake_cases", testToPascalCaseWithSnakeCases),
      property(s"$name: test String.toPascalCase with all lower snake_cases", testToPascalCaseWithLowerSnakeCases),
      property(s"$name: test String.toPascalCase with all UPPER snake_cases", testToPascalCaseWithUpperSnakeCases),
      property(s"$name: test String.toPascalCase with all kebab-cases", testToPascalCaseWithKebabCases),
      property(s"$name: test String.toPascalCase with all lower kebab-cases", testToPascalCaseWithLowerKebabCases),
      property(s"$name: test String.toPascalCase with all UPPER kebab-cases", testToPascalCaseWithUpperKebabCases),
      property(
        s"$name: test String.toPascalCase with all space separated String values",
        testToPascalCaseWithSpaceSeparatedStringValues,
      ),
      property(
        s"$name: test String.toPascalCase with all lower space separated String values",
        testToPascalCaseWithLowerSpaceSeparatedStringValues,
      ),
      property(
        s"$name: test String.toPascalCase with all UPPER space separated String values",
        testToPascalCaseWithUpperSpaceSeparatedStringValues,
      ),

      // String.toOnePascalCase
      property(s"$name: test String.toOnePascalCase with already PascalCase", testToOnePascalCaseWithAlreadyPascalCase),
      property(s"$name: test String.toOnePascalCase with PascalCases", testToOnePascalCaseWithPascalCases),
      property(s"$name: test String.toOnePascalCase with camelCases", testToOnePascalCaseWithCamelCases),
      property(s"$name: test String.toOnePascalCase with all UPPERCASE", testToOnePascalCaseWithUpperCase),
      property(s"$name: test String.toOnePascalCase with all lowercase", testToOnePascalCaseWithLowerCase),

      // String.toCamelCase
      property(
        s"$name: test String.toCamelCase with already CamelCase (lower case)",
        testToCamelCaseWithAlreadyCamelCase,
      ),
      property(s"$name: test String.toCamelCase with PascalCases", testToCamelCaseWithPascalCases),
      property(s"$name: test String.toCamelCase with camelCases", testToCamelCaseWithCamelCases),
      property(s"$name: test String.toCamelCase with all UPPERCASE", testToCamelCaseWithUpperCase),
      property(s"$name: test String.toCamelCase with all lowercase", testToCamelCaseWithLowerCase),
      property(s"$name: test String.toCamelCase with all snake_cases", testToCamelCaseWithSnakeCases),
      property(s"$name: test String.toCamelCase with all lower snake_cases", testToCamelCaseWithLowerSnakeCases),
      property(s"$name: test String.toCamelCase with all UPPER snake_cases", testToCamelCaseWithUpperSnakeCases),
      property(s"$name: test String.toCamelCase with all kebab-cases", testToCamelCaseWithKebabCases),
      property(s"$name: test String.toCamelCase with all lower kebab-cases", testToCamelCaseWithLowerKebabCases),
      property(s"$name: test String.toCamelCase with all UPPER kebab-cases", testToCamelCaseWithUpperKebabCases),
      property(
        s"$name: test String.toCamelCase with all space separated String values",
        testToCamelCaseWithSpaceSeparatedStringValues,
      ),
      property(
        s"$name: test String.toCamelCase with all lower space separated String values",
        testToCamelCaseWithLowerSpaceSeparatedStringValues,
      ),
      property(
        s"$name: test String.toCamelCase with all UPPER space separated String values",
        testToCamelCaseWithUpperSpaceSeparatedStringValues,
      ),

      // String.SplitByCase
      property(s"$name: test String.SplitByCase with PascalCase", testSplitByCaseWithPascalCase),
      property(s"$name: test String.SplitByCase with PascalCases", testSplitByCaseWithPascalCases),
      property(s"$name: test String.SplitByCase with camelCases", testSplitByCaseWithCamelCases),
      property(s"$name: test String.SplitByCase with all UPPERCASE", testSplitByCaseWithUpperCase),
      property(s"$name: test String.SplitByCase with all lowercase", testSplitByCaseWithLowerCase),
    )

    def testToPascalCaseWithAlreadyPascalCase: Property = for {
      expected <- genPascalCase(1, 10).log("expected")
      input    <- Gen.constant(expected).log("input")
    } yield {
      val actual = input.toPascalCase
      actual ==== expected
    }

    def testToPascalCaseWithPascalCases: Property = for {
      s1    <- genPascalCase(1, 10).log("s1")
      s2    <- genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
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
      s1    <- genPascalCase(1, 10).log("s1")
      s2    <- genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
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
      expected <- genPascalCase(1, 10).log("expected")
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
      expected <- genPascalCase(1, 10).log("expected")
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
      s     <- genPascalCase(1, 10).list(Range.linear(1, 10)).log("s")
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
      s     <- genPascalCase(1, 10).list(Range.linear(1, 10)).log("s")
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
      s     <- genPascalCase(1, 10).list(Range.linear(1, 10)).log("s")
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
      s     <- genPascalCase(1, 10).list(Range.linear(1, 10)).log("s")
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
      s     <- genPascalCase(1, 10).list(Range.linear(1, 10)).log("s")
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
      s     <- genPascalCase(1, 10).list(Range.linear(1, 10)).log("s")
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
      s     <- genPascalCase(1, 10).list(Range.linear(1, 10)).log("s")
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
      s     <- genPascalCase(1, 10).list(Range.linear(1, 10)).log("s")
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
      s     <- genPascalCase(1, 10).list(Range.linear(1, 10)).log("s")
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
      expected <- genPascalCase(1, 10).log("expected")
      input    <- Gen.constant(expected).log("input")
    } yield {
      val actual = input.toOnePascalCase
      actual ==== expected
    }

    def testToOnePascalCaseWithPascalCases: Property = for {
      s1    <- genPascalCase(1, 10).log("s1")
      s2    <- genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
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
      s1    <- genPascalCase(1, 10).log("s1")
      s2    <- genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
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
      expected <- genPascalCase(1, 10).log("expected")
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
      expected <- genPascalCase(1, 10).log("expected")
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
      s     <- genPascalCase(1, 10).log("s")
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
      s1    <- genPascalCase(1, 10).log("s1")
      s2    <- genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
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
      s1    <- genPascalCase(1, 10).map(s => s.updated(0, s.charAt(0).toLower)).log("s1")
      s2    <- genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
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
      s     <- genPascalCase(1, 10).log("s")
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
      s     <- genPascalCase(1, 10).log("s")
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
      s2    <- genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
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
      s2    <- genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
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
      s2    <- genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
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
      s2    <- genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
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
      s2    <- genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
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
      s2    <- genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
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
      s2    <- genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
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
      s2    <- genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
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
      s2    <- genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
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
      s2    <- genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
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
      s2    <- genPascalCase(2, 10).list(Range.linear(1, 4)).log("s2")
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

  object StringSeqCaseOpsSpec {
    def tests: List[Test] = List(
      property("Seq[String].mkPascalCaseString - already all PascalCases", testMkPascalCaseString),
      property("Seq[String].mkPascalCaseString - all UpperCases", testMkPascalCaseStringWithAllUpperCases),
      property(
        "Seq[String].mkPascalCaseString - all PascalCases and combined PascalCases",
        testMkPascalCaseStringWithAllPascalCasesAndCombinedPascalCases,
      ),
      property("Seq[String].mkPascalCaseString - all lower cases", testMkPascalCaseStringWithAllLowerCases),
      property(
        "Seq[String].mkPascalCaseString - all lower cases and combined camelCases",
        testMkPascalCaseStringWithAllLowerCasesAndCombinedCamelCases,
      ),
      property("Seq[String].mkPascalCaseString - all snake_cases", testMkPascalCaseStringWithAllSnakeCases),
      property(
        "Seq[String].mkPascalCaseString - all kebab-cases",
        testMkPascalCaseStringWithAllKebabCases,
      ),
      property(
        "Seq[String].mkPascalCaseString - all space separated values",
        testMkPascalCaseStringWithAllSpaceSeparatedValues,
      ),
      property(
        "Seq[String].mkPascalCaseString - PascalCases, camelCases, lower cases, UPPER CASES, snake_cases, kebab-cases and space separated String values",
        testMkPascalCaseStringWithPascalCasesCamelCasesLowerCasesUpperCasesSnakeCasesKebabCasesAndSpaceSeparatedValues,
      ),
    )

    def testMkPascalCaseString: Property =
      for {
        input <- genPascalCase(1, 5).list(Range.linear(1, 10)).log("input")
      } yield {
        val expected = input.mkString
        val actual   = input.mkPascalCaseString
        actual ==== expected
      }

    def testMkPascalCaseStringWithAllUpperCases: Property =
      for {
        allPascalCases <- genPascalCase(1, 10).list(Range.linear(1, 10)).log("allPascalCases")
        input          <- Gen.constant(allPascalCases.map(_.toUpperCase(Locale.ENGLISH))).log("input")
      } yield {
        val expected = allPascalCases.mkString
        val actual   = input.mkPascalCaseString
        val info     =
          s"""
             |>          input: ${input.toString}
             |> allPascalCases: ${allPascalCases.toString}
             |>         actual: $actual
             |>       expected: $expected
             |""".stripMargin

        (actual ==== expected).log(info)
      }

    def testMkPascalCaseStringWithAllPascalCasesAndCombinedPascalCases: Property =
      for {
        ss    <- genPascalCase(1, 5).list(Range.linear(1, 10)).log("ss")
        ss2   <- genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss2")
        ss3   <- genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss3")
        input <- Gen.constant(ss ++ List(ss2.mkString + ss3.mkString)).log("input")
      } yield {
        val expected = ss.mkString + ss2.mkString + ss3.mkString
        val actual   = input.mkPascalCaseString
        actual ==== expected
      }

    def testMkPascalCaseStringWithAllLowerCases: Property =
      for {
        allPascalCases <- genPascalCase(1, 5).list(Range.linear(1, 10)).log("allPascalCases")
        input          <- Gen.constant(allPascalCases.map(_.toLowerCase(Locale.ENGLISH))).log("input")
      } yield {
        val expected = allPascalCases.mkString
        val actual   = input.mkPascalCaseString
        actual ==== expected
      }

    def testMkPascalCaseStringWithAllLowerCasesAndCombinedCamelCases: Property =
      for {
        ss    <- genPascalCase(1, 5).list(Range.linear(1, 10)).log("ss")
        ss2   <- genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss2")
        ss3   <- genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss3")
        input <- Gen
                   .constant(
                     ss ++ List(
                       (
                         ss2.headOption.map(s2 => s2.headOption.fold("")(_.toLower.toString) ++ s2.drop(1)).toList ++
                           ss2.drop(1)
                       ).mkString,
                       (
                         ss3.headOption.map(s3 => s3.headOption.fold("")(_.toLower.toString) ++ s3.drop(1)).toList ++
                           ss3.drop(1)
                       ).mkString,
                     )
                   )
                   .log("input")
      } yield {
        val expected = ss.mkString + ss2.mkString + ss3.mkString
        val actual   = input.mkPascalCaseString
        val info     =
          s"""
             |>       ss: ${ss.toString}
             |>      ss2: ${ss2.toString}
             |>      ss3: ${ss3.toString}
             |>    input: ${input.toString}
             |>    split: ${input.map(_.splitByCase).toString}
             |>   actual: $actual
             |> expected: $expected
             |""".stripMargin

        (actual ==== expected).log(info)
      }

    def testMkPascalCaseStringWithAllSnakeCases: Property =
      for {
        ss    <- genPascalCase(1, 5).list(Range.linear(1, 10)).log("ss")
        ss2   <- genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss2")
        ss3   <- genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss3")
        input <- Gen
                   .constant(
                     ss.map(_.toLowerCase(Locale.ENGLISH)) ++ List(
                       (
                         ss2.map(_.toLowerCase(Locale.ENGLISH)).mkString("_")
                       ).mkString,
                       (
                         ss3.mkString("_")
                       ).mkString,
                     )
                   )
                   .log("input")
      } yield {
        val expected = ss.mkString + ss2.mkString + ss3.mkString
        val actual   = input.mkPascalCaseString
        val info     =
          s"""
             |>       ss: ${ss.toString}
             |>      ss2: ${ss2.toString}
             |>      ss3: ${ss3.toString}
             |>    input: ${input.toString}
             |>    split: ${input.map(_.splitByCase).toString}
             |>   actual: $actual
             |> expected: $expected
             |>""".stripMargin

        (actual ==== expected).log(info)
      }

    def testMkPascalCaseStringWithAllKebabCases: Property =
      for {
        ss    <- genPascalCase(1, 5).list(Range.linear(1, 10)).log("ss")
        ss2   <- genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss2")
        ss3   <- genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss3")
        input <- Gen
                   .constant(
                     ss.map(_.toLowerCase(Locale.ENGLISH)) ++ List(
                       (
                         ss2.map(_.toLowerCase(Locale.ENGLISH)).mkString("-")
                       ).mkString,
                       (
                         ss3.mkString("-")
                       ).mkString,
                     )
                   )
                   .log("input")
      } yield {
        val expected = ss.mkString + ss2.mkString + ss3.mkString
        val actual   = input.mkPascalCaseString
        val info     =
          s"""
             |>       ss: ${ss.toString}
             |>      ss2: ${ss2.toString}
             |>      ss3: ${ss3.toString}
             |>    input: ${input.toString}
             |>    split: ${input.map(_.splitByCase).toString}
             |>   actual: $actual
             |> expected: $expected
             |>""".stripMargin

        (actual ==== expected).log(info)
      }

    def testMkPascalCaseStringWithAllSpaceSeparatedValues: Property =
      for {
        ss    <- genPascalCase(1, 5).list(Range.linear(1, 10)).log("ss")
        ss2   <- genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss2")
        ss3   <- genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss3")
        input <- Gen
                   .constant(
                     ss ++ List(
                       (
                         ss2.mkString(" ")
                       ).mkString,
                       (
                         ss3.mkString(" ")
                       ).mkString,
                     )
                   )
                   .log("input")
      } yield {
        val expected = ss.mkString + ss2.mkString + ss3.mkString
        val actual   = input.mkPascalCaseString
        val info     =
          s"""
             |>       ss: ${ss.toString}
             |>      ss2: ${ss2.toString}
             |>      ss3: ${ss3.toString}
             |>    input: ${input.toString}
             |>    split: ${input.map(_.splitByCase).toString}
             |>   actual: $actual
             |> expected: $expected
             |>""".stripMargin

        (actual ==== expected).log(info)
      }

    def testMkPascalCaseStringWithPascalCasesCamelCasesLowerCasesUpperCasesSnakeCasesKebabCasesAndSpaceSeparatedValues
      : Property =
      for {
        s     <- genPascalCase(1, 5).log("s")
        ss1   <- genPascalCase(2, 5).list(Range.linear(1, 7)).log("ss1")
        ss2   <- genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss2")
        ss3   <- genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss3")
        ss4   <- genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss4")
        ss5   <- genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss5")
        ss6   <- genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss6")
        ss7   <- genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss7")
        ss8   <- genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss8")
        ss9   <- genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss9")
        ss10  <- genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss10")
        ss11  <- genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss11")
        ss12  <- genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss12")
        ss13  <- genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss13")
        ss14  <- genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss14")
        ss15  <- genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss15")
        ss16  <- genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss16")
        ss17  <- genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss17")
        ss18  <- genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss18")
        ss19  <- genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss19")
        ss20  <- genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss20")
        ss21  <- genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss21")
        ss22  <- genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss22")
        input <- Gen
                   .constant(
                     s.updated(0, s.charAt(0).toLower) ::
                       ss1 // PascalCase
                       ++ ss2.map(s => s.updated(0, s.charAt(0).toLower)) // camelCase
                       ++ ss3.map(_.toLowerCase(Locale.ENGLISH)) // lower case
                       ++ ss4.map(_.toUpperCase(Locale.ENGLISH)) // UPPER CASE
                       ++ List(ss5.mkString("_"), ss6.mkString("_")) // Snake_Case
                       ++ List( // lower_snake_case
                         ss7.map(_.toLowerCase(Locale.ENGLISH)).mkString("_"),
                         ss8.map(_.toLowerCase(Locale.ENGLISH)).mkString("_"),
                       )
                       ++ List( // UPPER_SNAKE_CASE
                         ss9.map(_.toUpperCase(Locale.ENGLISH)).mkString("_"),
                         ss10.map(_.toUpperCase(Locale.ENGLISH)).mkString("_"),
                       )
                       ++ List(ss11.mkString("-"), ss12.mkString("-")) // Kebab-Case
                       ++ List( // lower-kebab-case
                         ss13.map(_.toLowerCase(Locale.ENGLISH)).mkString("-"),
                         ss14.map(_.toLowerCase(Locale.ENGLISH)).mkString("-"),
                       )
                       ++ List( // UPPER-KEBAB-CASE
                         ss15.map(_.toUpperCase(Locale.ENGLISH)).mkString("-"),
                         ss16.map(_.toUpperCase(Locale.ENGLISH)).mkString("-"),
                       )
                       ++ List(ss17.mkString(" "), ss18.mkString(" ")) // Space Separated String
                       ++ List( // lower space separated string
                         ss19.map(_.toLowerCase(Locale.ENGLISH)).mkString(" "),
                         ss20.map(_.toLowerCase(Locale.ENGLISH)).mkString(" "),
                       )
                       ++ List( // UPPER SPACE SEPARATED STRING
                         ss21.map(_.toUpperCase(Locale.ENGLISH)).mkString(" "),
                         ss22.map(_.toUpperCase(Locale.ENGLISH)).mkString(" "),
                       )
                   )
                   .log("input")
      } yield {
        val expected = s +
          ss1.mkString + ss2.mkString + ss3.mkString ++ ss4.mkString ++ ss5.mkString ++
          ss6.mkString + ss7.mkString + ss8.mkString ++ ss9.mkString ++ ss10.mkString ++
          ss11.mkString + ss12.mkString + ss13.mkString ++ ss14.mkString ++ ss15.mkString ++
          ss16.mkString + ss17.mkString + ss18.mkString ++ ss19.mkString ++ ss20.mkString ++
          ss21.mkString + ss22.mkString

        val actual = input.mkPascalCaseString
        val info   =
          s"""
             |>    input: ${input.toString}
             |>    split: ${input.map(_.splitByCase).toString}
             |>   actual: $actual
             |> expected: $expected
             |>""".stripMargin

        (actual ==== expected).log(info)
      }

  }

  def genPascalCase(min: Int, max: Int): Gen[String] =
    for {
      head <- Gen.upper
      tail <- Gen.string(Gen.lower, Range.linear(min - 1, max - 1))
    } yield head +: tail

  def genCamelCaseList(range: Range[Int]): Gen[List[String]] =
    Gen.string(Gen.lower, Range.linear(1, 5)).list(range)

}
