package extras.strings.syntax.casesSpec

import hedgehog._
import hedgehog.runner._

import java.util.Locale

/** @author Kevin Lee
  * @since 2023-10-16
  */

object StringSeqCaseOpsSpec extends Properties {
  import extras.strings.syntax.cases._

  override def tests: List[Test] = List(
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
    property("Seq[String].mkPascalCaseString - all lower_snake_cases", testMkPascalCaseStringWithAllLowerSnakeCases),
    property("Seq[String].mkPascalCaseString - all UPPER_SNAKE_CASES", testMkPascalCaseStringWithAllUpperSnakeCases),
    property(
      "Seq[String].mkPascalCaseString - all kebab-cases",
      testMkPascalCaseStringWithAllKebabCases,
    ),
    property(
      "Seq[String].mkPascalCaseString - all lower-kebab-cases",
      testMkPascalCaseStringWithAllLowerKebabCases,
    ),
    property(
      "Seq[String].mkPascalCaseString - all UPPER-KEBAB-CASES",
      testMkPascalCaseStringWithAllUpperKebabCases,
    ),
    property(
      "Seq[String].mkPascalCaseString - all space separated values",
      testMkPascalCaseStringWithAllSpaceSeparatedValues,
    ),
    property(
      "Seq[String].mkPascalCaseString - all lower space separated values",
      testMkPascalCaseStringWithAllLowerSpaceSeparatedValues,
    ),
    property(
      "Seq[String].mkPascalCaseString - all UPPER SPACE SEPARATED VALUES",
      testMkPascalCaseStringWithAllUpperSpaceSeparatedValues,
    ),
    property(
      "Seq[String].mkPascalCaseString - PascalCases, camelCases, lower cases, UPPER CASES, snake_cases, kebab-cases and space separated String values",
      testMkPascalCaseStringWithPascalCasesCamelCasesLowerCasesUpperCasesSnakeCasesKebabCasesAndSpaceSeparatedValues,
    ),
  )

  def testMkPascalCaseString: Property =
    for {
      input <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).log("input")
    } yield {
      val expected = input.mkString
      val actual   = input.mkPascalCaseString
      actual ==== expected
    }

  def testMkPascalCaseStringWithAllUpperCases: Property =
    for {
      allPascalCases <- Gens.genPascalCase(1, 10).list(Range.linear(1, 10)).log("allPascalCases")
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
      ss    <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).log("ss")
      ss2   <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss2")
      ss3   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss3")
      input <- Gen.constant(ss ++ List(ss2.mkString + ss3.mkString)).log("input")
    } yield {
      val expected = ss.mkString + ss2.mkString + ss3.mkString
      val actual   = input.mkPascalCaseString
      actual ==== expected
    }

  def testMkPascalCaseStringWithAllLowerCases: Property =
    for {
      allPascalCases <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).log("allPascalCases")
      input          <- Gen.constant(allPascalCases.map(_.toLowerCase(Locale.ENGLISH))).log("input")
    } yield {
      val expected = allPascalCases.mkString
      val actual   = input.mkPascalCaseString
      actual ==== expected
    }

  def testMkPascalCaseStringWithAllLowerCasesAndCombinedCamelCases: Property =
    for {
      ss    <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).log("ss")
      ss2   <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss2")
      ss3   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss3")
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
      ss1   <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).log("ss1")
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
      val actual   = input.mkPascalCaseString
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

  def testMkPascalCaseStringWithAllLowerSnakeCases: Property =
    for {
      ss1   <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).log("ss1")
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
      val actual   = input.mkPascalCaseString
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

  def testMkPascalCaseStringWithAllUpperSnakeCases: Property =
    for {
      ss1   <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).log("ss1")
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
      val actual   = input.mkPascalCaseString
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

  def testMkPascalCaseStringWithAllKebabCases: Property =
    for {
      ss1   <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).log("ss1")
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
      val actual   = input.mkPascalCaseString
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

  def testMkPascalCaseStringWithAllLowerKebabCases: Property =
    for {
      ss1   <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).log("ss1")
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
      val actual   = input.mkPascalCaseString
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

  def testMkPascalCaseStringWithAllUpperKebabCases: Property =
    for {
      ss1   <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).log("ss1")
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
      val actual   = input.mkPascalCaseString
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

  def testMkPascalCaseStringWithAllSpaceSeparatedValues: Property =
    for {
      ss1   <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).log("ss1")
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
      val actual   = input.mkPascalCaseString
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

  def testMkPascalCaseStringWithAllLowerSpaceSeparatedValues: Property =
    for {
      ss1   <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).log("ss1")
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
      val actual   = input.mkPascalCaseString
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

  def testMkPascalCaseStringWithAllUpperSpaceSeparatedValues: Property =
    for {
      ss1   <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).log("ss1")
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
      val actual   = input.mkPascalCaseString
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

  def testMkPascalCaseStringWithPascalCasesCamelCasesLowerCasesUpperCasesSnakeCasesKebabCasesAndSpaceSeparatedValues
    : Property =
    for {
      s     <- Gens.genPascalCase(1, 5).log("s")
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
