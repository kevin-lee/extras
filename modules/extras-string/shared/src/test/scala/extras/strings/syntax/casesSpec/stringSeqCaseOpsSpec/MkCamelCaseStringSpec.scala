package extras.strings.syntax.casesSpec.stringSeqCaseOpsSpec

import extras.strings.syntax.casesSpec.Gens
import hedgehog._
import hedgehog.runner._

import java.util.Locale

/** @author Kevin Lee
  * @since 2023-10-16
  */
object MkCamelCaseStringSpec extends Properties {
  import extras.strings.syntax.cases._

  override def tests: List[Test] = List(
    property("Seq[String].mkCamelCaseString - already all camelCases", testMkCamelCaseString),
    property("Seq[String].mkCamelCaseString - all UpperCases", testMkCamelCaseStringWithAllUpperCases),
    property(
      "Seq[String].mkCamelCaseString - all PascalCases and combined PascalCases",
      testMkCamelCaseStringWithAllPascalCasesAndCombinedPascalCases,
    ),
    property("Seq[String].mkCamelCaseString - all lower cases", testMkCamelCaseStringWithAllLowerCases),
    property(
      "Seq[String].mkCamelCaseString - all lower cases and combined camelCases",
      testMkCamelCaseStringWithAllCamelCasesAndCombinedCamelCases,
    ),
    property("Seq[String].mkCamelCaseString - all snake_cases", testMkCamelCaseStringWithAllSnakeCases),
    property("Seq[String].mkCamelCaseString - all lower_snake_cases", testMkCamelCaseStringWithAllLowerSnakeCases),
    property("Seq[String].mkCamelCaseString - all UPPER_SNAKE_CASES", testMkCamelCaseStringWithAllUpperSnakeCases),
    property(
      "Seq[String].mkCamelCaseString - all kebab-cases",
      testMkCamelCaseStringWithAllKebabCases,
    ),
    property(
      "Seq[String].mkCamelCaseString - all lower-kebab-cases",
      testMkCamelCaseStringWithAllLowerKebabCases,
    ),
    property(
      "Seq[String].mkCamelCaseString - all UPPER-KEBAB-CASES",
      testMkCamelCaseStringWithAllUpperKebabCases,
    ),
    property(
      "Seq[String].mkCamelCaseString - all space separated values",
      testMkCamelCaseStringWithAllSpaceSeparatedValues,
    ),
    property(
      "Seq[String].mkCamelCaseString - all lower space separated values",
      testMkCamelCaseStringWithAllLowerSpaceSeparatedValues,
    ),
    property(
      "Seq[String].mkCamelCaseString - all UPPER SPACE SEPARATED VALUES",
      testMkCamelCaseStringWithAllUpperSpaceSeparatedValues,
    ),
    property(
      "Seq[String].mkCamelCaseString - PascalCases, camelCases, lower cases, UPPER CASES, snake_cases, kebab-cases and space separated String values",
      testMkCamelCaseStringWithPascalCasesCamelCasesLowerCasesUpperCasesSnakeCasesKebabCasesAndSpaceSeparatedValues,
    ),
  )

  def testMkCamelCaseString: Property =
    for {
      s     <- Gen.string(Gen.lower, Range.linear(1, 5)).log("s")
      ss1   <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).log("ss1")
      ss2   <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss2")
      ss3   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss3")
      input <- Gen.constant(s :: ss1 ++ List(ss2.mkString + ss3.mkString)).log("input")
    } yield {
      val expected = input.mkString
      val actual   = input.mkCamelCaseString

      val info =
        s"""
           |>              s: $s
           |>            ss1: ${ss1.toString}
           |>          input: ${input.toString}
           |>         actual: $actual
           |>       expected: $expected
           |""".stripMargin

      (actual ==== expected).log(info)
    }

  def testMkCamelCaseStringWithAllUpperCases: Property =
    for {
      s   <- Gen.string(Gen.lower, Range.linear(1, 5)).log("s")
      ss1 <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).log("ss1")

      input <- Gen.constant(s.toUpperCase(Locale.ENGLISH) :: ss1.map(_.toUpperCase(Locale.ENGLISH))).log("input")
    } yield {
      val expected = s.mkString + ss1.mkString
      val actual   = input.mkCamelCaseString

      val info =
        s"""
           |>              s: $s
           |>            ss1: ${ss1.toString}
           |>          input: ${input.toString}
           |>         actual: $actual
           |>       expected: $expected
           |""".stripMargin

      (actual ==== expected).log(info)
    }

  def testMkCamelCaseStringWithAllPascalCasesAndCombinedPascalCases: Property =
    for {
      s   <- Gen.string(Gen.lower, Range.linear(1, 5)).log("s")
      ss1 <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).log("ss1")

      ss2   <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss2")
      ss3   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss3")
      input <- Gen.constant(s.updated(0, s.charAt(0).toUpper) :: ss1 ++ List(ss2.mkString + ss3.mkString)).log("input")
    } yield {
      val expected = s + ss1.mkString + ss2.mkString + ss3.mkString
      val actual   = input.mkCamelCaseString
      actual ==== expected
    }

  def testMkCamelCaseStringWithAllLowerCases: Property =
    for {
      s     <- Gen.string(Gen.lower, Range.linear(1, 5)).log("s")
      ss    <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).log("ss")
      input <- Gen.constant(s.toLowerCase(Locale.ENGLISH) :: ss.map(_.toLowerCase(Locale.ENGLISH))).log("input")
    } yield {
      val expected = s + ss.mkString
      val actual   = input.mkCamelCaseString
      actual ==== expected
    }

  def testMkCamelCaseStringWithAllCamelCasesAndCombinedCamelCases: Property =
    for {
      ss1   <- Gens.genFirstLowerCasesThenAllPascalCases(5)(1, 5).log("ss1")
      ss2   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss3")
      ss3   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss3")
      ss4   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss4")
      input <- Gen
                 .constant(
                   ss1 ++
                     (ss2.headOption.fold("")(s => s.updated(0, s.charAt(0).toLower)) :: ss2.drop(1)) ++
                     List(
                       ss3.mkString,
                       ss4.mkString,
                     )
                 )
                 .log("input")
    } yield {
      val expected = ss1.mkString + ss2.mkString + ss3.mkString + ss4.mkString
      val actual   = input.mkCamelCaseString
      val info     =
        s"""
           |>      ss1: ${ss1.toString}
           |>      ss2: ${ss2.toString}
           |>      ss3: ${ss3.toString}
           |>      ss4: ${ss4.toString}
           |>    input: ${input.toString}
           |>    split: ${input.map(_.splitByCase).toString}
           |>   actual: $actual
           |> expected: $expected
           |""".stripMargin

      (actual ==== expected).log(info)
    }

  def testMkCamelCaseStringWithAllSnakeCases: Property =
    for {
      ss1   <- Gens.genFirstLowerCasesThenAllPascalCases(5)(1, 5).log("ss1")
      ss2   <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss2")
      ss3   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss3")
      ss4   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss4")
      input <- Gen
                 .constant(
                   List(
                     ss1.mkString("_").mkString,
                     ss2.mkString("_").mkString,
                   ) ++
                     List(
                       ss3.mkString("_").mkString,
                       ss4.mkString("_").mkString,
                     )
                 )
                 .log("input")
    } yield {
      val expected = ss1.mkString + ss2.mkString + ss3.mkString + ss4.mkString
      val actual   = input.mkCamelCaseString
      val info     =
        s"""
           |>      ss1: ${ss1.toString}
           |>      ss2: ${ss2.toString}
           |>      ss3: ${ss3.toString}
           |>      ss4: ${ss4.toString}
           |>    input: ${input.toString}
           |>    split: ${input.map(_.splitByCase).toString}
           |>   actual: $actual
           |> expected: $expected
           |>""".stripMargin

      (actual ==== expected).log(info)
    }

  def testMkCamelCaseStringWithAllLowerSnakeCases: Property =
    for {
      ss1   <- Gens.genFirstLowerCasesThenAllPascalCases(5)(1, 5).log("ss1")
      ss2   <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss2")
      ss3   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss3")
      ss4   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss4")
      input <- Gen
                 .constant(
                   List(
                     ss1.map(_.toLowerCase(Locale.ENGLISH)).mkString("_").mkString,
                     ss2.map(_.toLowerCase(Locale.ENGLISH)).mkString("_").mkString,
                   ) ++
                     List(
                       ss3.map(_.toLowerCase(Locale.ENGLISH)).mkString("_").mkString,
                       ss4.map(_.toLowerCase(Locale.ENGLISH)).mkString("_").mkString,
                     )
                 )
                 .log("input")
    } yield {
      val expected = ss1.mkString + ss2.mkString + ss3.mkString + ss4.mkString
      val actual   = input.mkCamelCaseString
      val info     =
        s"""
           |>      ss1: ${ss1.toString}
           |>      ss2: ${ss2.toString}
           |>      ss3: ${ss3.toString}
           |>      ss4: ${ss4.toString}
           |>    input: ${input.toString}
           |>    split: ${input.map(_.splitByCase).toString}
           |>   actual: $actual
           |> expected: $expected
           |>""".stripMargin

      (actual ==== expected).log(info)
    }

  def testMkCamelCaseStringWithAllUpperSnakeCases: Property =
    for {
      ss1   <- Gens.genFirstLowerCasesThenAllPascalCases(5)(1, 5).log("ss1")
      ss2   <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss2")
      ss3   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss3")
      ss4   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss4")
      input <- Gen
                 .constant(
                   List(
                     ss1.map(_.toUpperCase(Locale.ENGLISH)).mkString("_").mkString,
                     ss2.map(_.toUpperCase(Locale.ENGLISH)).mkString("_").mkString,
                   ) ++
                     List(
                       ss3.map(_.toUpperCase(Locale.ENGLISH)).mkString("_").mkString,
                       ss4.map(_.toUpperCase(Locale.ENGLISH)).mkString("_").mkString,
                     )
                 )
                 .log("input")
    } yield {
      val expected = ss1.mkString + ss2.mkString + ss3.mkString + ss4.mkString
      val actual   = input.mkCamelCaseString
      val info     =
        s"""
           |>      ss1: ${ss1.toString}
           |>      ss2: ${ss2.toString}
           |>      ss3: ${ss3.toString}
           |>      ss4: ${ss4.toString}
           |>    input: ${input.toString}
           |>    split: ${input.map(_.splitByCase).toString}
           |>   actual: $actual
           |> expected: $expected
           |>""".stripMargin

      (actual ==== expected).log(info)
    }

  def testMkCamelCaseStringWithAllKebabCases: Property =
    for {
      ss1   <- Gens.genFirstLowerCasesThenAllPascalCases(5)(1, 5).log("ss1")
      ss2   <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss2")
      ss3   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss3")
      ss4   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss4")
      input <- Gen
                 .constant(
                   List(
                     ss1.mkString("-").mkString,
                     ss2.mkString("-").mkString,
                   ) ++
                     List(
                       ss3.mkString("-").mkString,
                       ss4.mkString("-").mkString,
                     )
                 )
                 .log("input")
    } yield {
      val expected = ss1.mkString + ss2.mkString + ss3.mkString + ss4.mkString
      val actual   = input.mkCamelCaseString
      val info     =
        s"""
           |>      ss1: ${ss1.toString}
           |>      ss2: ${ss2.toString}
           |>      ss3: ${ss3.toString}
           |>      ss4: ${ss3.toString}
           |>    input: ${input.toString}
           |>    split: ${input.map(_.splitByCase).toString}
           |>   actual: $actual
           |> expected: $expected
           |>""".stripMargin

      (actual ==== expected).log(info)
    }

  def testMkCamelCaseStringWithAllLowerKebabCases: Property =
    for {
      ss1   <- Gens.genFirstLowerCasesThenAllPascalCases(5)(1, 5).log("ss1")
      ss2   <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss2")
      ss3   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss3")
      ss4   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss4")
      input <- Gen
                 .constant(
                   List(
                     ss1.map(_.toLowerCase(Locale.ENGLISH)).mkString("-").mkString,
                     ss2.map(_.toLowerCase(Locale.ENGLISH)).mkString("-").mkString,
                   ) ++
                     List(
                       ss3.map(_.toLowerCase(Locale.ENGLISH)).mkString("-").mkString,
                       ss4.map(_.toLowerCase(Locale.ENGLISH)).mkString("-").mkString,
                     )
                 )
                 .log("input")
    } yield {
      val expected = ss1.mkString + ss2.mkString + ss3.mkString + ss4.mkString
      val actual   = input.mkCamelCaseString
      val info     =
        s"""
           |>      ss1: ${ss1.toString}
           |>      ss2: ${ss2.toString}
           |>      ss3: ${ss3.toString}
           |>      ss4: ${ss3.toString}
           |>    input: ${input.toString}
           |>    split: ${input.map(_.splitByCase).toString}
           |>   actual: $actual
           |> expected: $expected
           |>""".stripMargin

      (actual ==== expected).log(info)
    }

  def testMkCamelCaseStringWithAllUpperKebabCases: Property =
    for {
      ss1   <- Gens.genFirstLowerCasesThenAllPascalCases(5)(1, 5).log("ss1")
      ss2   <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss2")
      ss3   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss3")
      ss4   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss4")
      input <- Gen
                 .constant(
                   List(
                     ss1.map(_.toUpperCase(Locale.ENGLISH)).mkString("-").mkString,
                     ss2.map(_.toUpperCase(Locale.ENGLISH)).mkString("-").mkString,
                   ) ++
                     List(
                       ss3.map(_.toUpperCase(Locale.ENGLISH)).mkString("-").mkString,
                       ss4.map(_.toUpperCase(Locale.ENGLISH)).mkString("-").mkString,
                     )
                 )
                 .log("input")
    } yield {
      val expected = ss1.mkString + ss2.mkString + ss3.mkString + ss4.mkString
      val actual   = input.mkCamelCaseString
      val info     =
        s"""
           |>      ss1: ${ss1.toString}
           |>      ss2: ${ss2.toString}
           |>      ss3: ${ss3.toString}
           |>      ss4: ${ss3.toString}
           |>    input: ${input.toString}
           |>    split: ${input.map(_.splitByCase).toString}
           |>   actual: $actual
           |> expected: $expected
           |>""".stripMargin

      (actual ==== expected).log(info)
    }

  def testMkCamelCaseStringWithAllSpaceSeparatedValues: Property =
    for {
      ss1   <- Gens.genFirstLowerCasesThenAllPascalCases(5)(1, 5).log("ss1")
      ss2   <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss2")
      ss3   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss3")
      ss4   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss4")
      input <- Gen
                 .constant(
                   List(
                     ss1.mkString(" ").mkString,
                     ss2.mkString(" ").mkString,
                   ) ++
                     List(
                       ss3.mkString(" ").mkString,
                       ss4.mkString(" ").mkString,
                     )
                 )
                 .log("input")
    } yield {
      val expected = ss1.mkString + ss2.mkString + ss3.mkString + ss4.mkString
      val actual   = input.mkCamelCaseString
      val info     =
        s"""
           |>      ss1: ${ss1.toString}
           |>      ss2: ${ss2.toString}
           |>      ss3: ${ss3.toString}
           |>      ss4: ${ss3.toString}
           |>    input: ${input.toString}
           |>    split: ${input.map(_.splitByCase).toString}
           |>   actual: $actual
           |> expected: $expected
           |>""".stripMargin

      (actual ==== expected).log(info)
    }

  def testMkCamelCaseStringWithAllLowerSpaceSeparatedValues: Property =
    for {
      ss1   <- Gens.genFirstLowerCasesThenAllPascalCases(5)(1, 5).log("ss1")
      ss2   <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss2")
      ss3   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss3")
      ss4   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss4")
      input <- Gen
                 .constant(
                   List(
                     ss1.map(_.toLowerCase(Locale.ENGLISH)).mkString(" ").mkString,
                     ss2.map(_.toLowerCase(Locale.ENGLISH)).mkString(" ").mkString,
                   ) ++
                     List(
                       ss3.map(_.toLowerCase(Locale.ENGLISH)).mkString(" ").mkString,
                       ss4.map(_.toLowerCase(Locale.ENGLISH)).mkString(" ").mkString,
                     )
                 )
                 .log("input")
    } yield {
      val expected = ss1.mkString + ss2.mkString + ss3.mkString + ss4.mkString
      val actual   = input.mkCamelCaseString
      val info     =
        s"""
           |>      ss1: ${ss1.toString}
           |>      ss2: ${ss2.toString}
           |>      ss3: ${ss3.toString}
           |>      ss4: ${ss3.toString}
           |>    input: ${input.toString}
           |>    split: ${input.map(_.splitByCase).toString}
           |>   actual: $actual
           |> expected: $expected
           |>""".stripMargin

      (actual ==== expected).log(info)
    }

  def testMkCamelCaseStringWithAllUpperSpaceSeparatedValues: Property =
    for {
      ss1   <- Gens.genFirstLowerCasesThenAllPascalCases(5)(1, 5).log("ss1")
      ss2   <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss2")
      ss3   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss3")
      ss4   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss4")
      input <- Gen
                 .constant(
                   List(
                     ss1.map(_.toUpperCase(Locale.ENGLISH)).mkString(" ").mkString,
                     ss2.map(_.toUpperCase(Locale.ENGLISH)).mkString(" ").mkString,
                   ) ++
                     List(
                       ss3.map(_.toUpperCase(Locale.ENGLISH)).mkString(" ").mkString,
                       ss4.map(_.toUpperCase(Locale.ENGLISH)).mkString(" ").mkString,
                     )
                 )
                 .log("input")
    } yield {
      val expected = ss1.mkString + ss2.mkString + ss3.mkString + ss4.mkString
      val actual   = input.mkCamelCaseString
      val info     =
        s"""
           |>      ss1: ${ss1.toString}
           |>      ss2: ${ss2.toString}
           |>      ss3: ${ss3.toString}
           |>      ss4: ${ss3.toString}
           |>    input: ${input.toString}
           |>    split: ${input.map(_.splitByCase).toString}
           |>   actual: $actual
           |> expected: $expected
           |>""".stripMargin

      (actual ==== expected).log(info)
    }

  def testMkCamelCaseStringWithPascalCasesCamelCasesLowerCasesUpperCasesSnakeCasesKebabCasesAndSpaceSeparatedValues
    : Property =
    for {
      s     <- Gens.genFirstLowerCasesThenAllPascalCases(5)(1, 5).map(_.mkString).log("s")
      ss1   <- Gens.genPascalCase(2, 5).list(Range.linear(1, 7)).log("ss1")
      ss2   <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss2")
      ss3   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss3")
      ss4   <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss4")
      ss5   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss5")
      ss6   <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss6")
      ss7   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss7")
      ss8   <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss8")
      ss9   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss9")
      ss10  <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss10")
      ss11  <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss11")
      ss12  <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss12")
      ss13  <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss13")
      ss14  <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss14")
      ss15  <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss15")
      ss16  <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss16")
      ss17  <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss17")
      ss18  <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss18")
      ss19  <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss19")
      ss20  <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss20")
      ss21  <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss21")
      ss22  <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss22")
      input <- Gen
                 .constant(
                   s ::
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

      val actual = input.mkCamelCaseString
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
