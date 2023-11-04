package extras.strings.syntax.casesSpec.stringSeqCaseOpsSpec

import cats.syntax.all._
import extras.strings.syntax.casesSpec.Gens
import hedgehog._
import hedgehog.runner._

import java.util.Locale

/** @author Kevin Lee
  * @since 2023-11-05
  */
@SuppressWarnings(Array("org.wartremover.warts.SizeIs"))
object MkKebabLowerCaseStringSpec extends Properties {

  import extras.strings.syntax.cases._

  override def tests: List[Test] = List(
    property(
      "Seq[String].mkKebabLowerCaseString - already all kebab-cases",
      testMkKebabLowerCaseStringWithAlreadyKebabCases,
    ),
    property("Seq[String].mkKebabLowerCaseString - all UpperCases", testMkKebabLowerCaseStringWithAllUpperCases),
    property(
      "Seq[String].mkKebabLowerCaseString - all PascalCases and combined PascalCases",
      testMkKebabLowerCaseStringWithAllPascalCasesAndCombinedPascalCases,
    ),
    property("Seq[String].mkKebabLowerCaseString - all lower cases", testMkKebabLowerCaseStringWithAllLowerCases),
    property(
      "Seq[String].mkKebabLowerCaseString - all lower cases and combined camelCases",
      testMkKebabLowerCaseStringWithAllLowerCasesAndCombinedCamelCases,
    ),
    property("Seq[String].mkKebabLowerCaseString - all snake_cases", testMkKebabLowerCaseStringWithAllSnakeCases),
    property(
      "Seq[String].mkKebabLowerCaseString - all lower_snake_cases",
      testMkKebabLowerCaseStringWithAllLowerSnakeCases,
    ),
    property(
      "Seq[String].mkKebabLowerCaseString - all UPPER_SNAKE_CASES",
      testMkKebabLowerCaseStringWithAllUpperSnakeCases,
    ),
    property(
      "Seq[String].mkKebabLowerCaseString - all kebab-cases",
      testMkKebabLowerCaseStringWithAllKebabCases,
    ),
    property(
      "Seq[String].mkKebabLowerCaseString - all lower-kebab-cases",
      testMkKebabLowerCaseStringWithAllLowerKebabCases,
    ),
    property(
      "Seq[String].mkKebabLowerCaseString - all UPPER-KEBAB-CASES",
      testMkKebabLowerCaseStringWithAllUpperKebabCases,
    ),
    property(
      "Seq[String].mkKebabLowerCaseString - all space separated values",
      testMkKebabLowerCaseStringWithAllSpaceSeparatedValues,
    ),
    property(
      "Seq[String].mkKebabLowerCaseString - all lower space separated values",
      testMkKebabLowerCaseStringWithAllLowerSpaceSeparatedValues,
    ),
    property(
      "Seq[String].mkKebabLowerCaseString - all UPPER SPACE SEPARATED VALUES",
      testMkKebabLowerCaseStringWithAllUpperSpaceSeparatedValues,
    ),
    property(
      "Seq[String].mkKebabLowerCaseString - PascalCases, camelCases, lower cases, UPPER CASES, snake_cases, kebab-cases and space separated String values",
      testMkKebabLowerCaseStringWithPascalCasesCamelCasesLowerCasesUpperCasesSnakeCasesKebabCasesAndSpaceSeparatedValues,
    ),
  )

  def testMkKebabLowerCaseStringWithAlreadyKebabCases: Property =
    for {
      input <-
        Gens
          .genPascalCase(1, 5)
          .list(Range.linear(1, 10))
          .map(_.mkString("-"))
          .list(Range.linear(1, 10))
          .log("input")
          .classify("Length = 1", _.length === 1)
          .classify("Length > 1", _.length > 1)
    } yield {
      val expected = input.map(_.toLowerCase(Locale.ENGLISH)).mkString("-")
      val actual   = input.mkKebabLowerCaseString
      actual ==== expected
    }

  def testMkKebabLowerCaseStringWithAllUpperCases: Property =
    for {
      input    <- Gen
                    .string(Gen.alphaNum, Range.linear(1, 10))
                    .map(_.toUpperCase(Locale.ENGLISH))
                    .list(Range.linear(1, 10))
                    .log("input")
                    .classify("Length = 1", _.length === 1)
                    .classify("Length > 1", _.length > 1)
      expected <- Gen.constant(input.map(_.toLowerCase(Locale.ENGLISH)).mkString("-")).log("expected")
    } yield {
      val actual = input.mkKebabLowerCaseString
      val info   =
        s"""
           |>    input: ${input.toString}
           |>   actual: $actual
           |> expected: $expected
           |""".stripMargin

      (actual ==== expected).log(info)
    }

  def testMkKebabLowerCaseStringWithAllPascalCasesAndCombinedPascalCases: Property =
    for {
      ss       <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).log("ss")
      ss2      <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss2")
      ss3      <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss3")
      input    <- Gen
                    .constant(ss ++ List(ss2.mkString + ss3.mkString))
                    .log("input")
                    .classify("Length <= 2", _.length <= 2)
                    .classify("Length > 2 ", _.length > 2)
      expected <- Gen.constant((ss ++ ss2 ++ ss3).map(_.toLowerCase(Locale.ENGLISH)).mkString("-")).log("expected")
    } yield {
      val actual = input.mkKebabLowerCaseString

      val info =
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

  def testMkKebabLowerCaseStringWithAllLowerCases: Property =
    for {
      input    <- Gen
                    .string(Gen.alphaNum, Range.linear(1, 10))
                    .map(_.toLowerCase(Locale.ENGLISH))
                    .list(Range.linear(1, 10))
                    .log("input")
                    .classify("Length = 1", _.length === 1)
                    .classify("Length > 1", _.length > 1)
      expected <- Gen.constant(input.map(_.toLowerCase(Locale.ENGLISH)).mkString("-")).log("expected")
    } yield {
      val actual = input.mkKebabLowerCaseString
      actual ==== expected
    }

  def testMkKebabLowerCaseStringWithAllLowerCasesAndCombinedCamelCases: Property =
    for {
      ss1   <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).log("ss1")
      ss2   <- Gens
                 .genPascalCase(2, 5)
                 .list(Range.linear(2, 3))
                 .map(ss =>
                   ss.headOption.map(s2 => s2.headOption.fold("")(_.toLower.toString) ++ s2.drop(1)).toList ++
                     ss.drop(1)
                 )
                 .log("ss2")
      ss3   <- Gens
                 .genPascalCase(2, 5)
                 .list(Range.linear(3, 5))
                 .map(ss =>
                   ss
                     .headOption
                     .map(s3 => s3.headOption.fold("")(_.toLower.toString) ++ s3.drop(1))
                     .toList ++ ss.drop(1)
                 )
                 .log("ss3")
      input <- Gen
                 .constant(ss1 ++ List(ss2.mkString, ss3.mkString))
                 .log("input")
                 .classify("Length <= 6", _.length <= 6)
                 .classify("Length > 6 ", _.length > 6)
    } yield {
      val expected = (ss1 ++ ss2 ++ ss3).map(_.toLowerCase(Locale.ENGLISH)).mkString("-")
      val actual   = input.mkKebabLowerCaseString
      val info     =
        s"""
           |>       ss: ${ss1.toString}
           |>      ss2: ${ss2.toString}
           |>      ss3: ${ss3.toString}
           |>    input: ${input.toString}
           |>    split: ${input.map(_.splitByCase).toString}
           |>   actual: $actual
           |> expected: $expected
           |""".stripMargin

      (actual ==== expected).log(info)
    }

  def testMkKebabLowerCaseStringWithAllSnakeCases: Property =
    for {
      ss1   <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).log("ss1")
      ss2   <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss2")
      ss3   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss3")
      ss4   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss4")
      input <-
        Gen
          .constant(
            List(
              ss1.mkString("_"),
              ss2.mkString("_"),
            ) ++
              List(
                ss3.mkString("_"),
                ss4.mkString("_"),
              )
          )
          .log("input")
          .classify(
            "list.count(_.length >= 10) = (list.length / 2)",
            list => list.count(_.length >= 10) === (list.length >> 1),
          )
          .classify(
            "list.count(_.length >= 10) > (list.length / 2)",
            list => list.count(_.length >= 10) > (list.length >> 1),
          )
          .classify(
            "list.count(_.length >= 10) < (list.length / 2)",
            list => list.count(_.length >= 10) < (list.length >> 1),
          )
    } yield {
      val expected = (ss1 ++ ss2 ++ ss3 ++ ss4).map(_.toLowerCase(Locale.ENGLISH)).mkString("-")
      val actual   = input.mkKebabLowerCaseString
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

  def testMkKebabLowerCaseStringWithAllLowerSnakeCases: Property =
    for {
      ss1   <- Gens
                 .genPascalCase(1, 5)
                 .list(Range.linear(1, 10))
                 .map(_.map(_.toLowerCase(Locale.ENGLISH)))
                 .log("ss1")
      ss2   <- Gens
                 .genPascalCase(2, 5)
                 .list(Range.linear(2, 3))
                 .map(_.map(_.toLowerCase(Locale.ENGLISH)))
                 .log("ss2")
      ss3   <- Gens
                 .genPascalCase(2, 5)
                 .list(Range.linear(3, 5))
                 .map(_.map(_.toLowerCase(Locale.ENGLISH)))
                 .log("ss3")
      ss4   <- Gens
                 .genPascalCase(2, 5)
                 .list(Range.linear(3, 5))
                 .map(_.map(_.toLowerCase(Locale.ENGLISH)))
                 .log("ss4")
      input <- Gen
                 .constant(
                   List(
                     ss1.mkString("_"),
                     ss2.mkString("_"),
                   ) ++
                     List(
                       ss3.mkString("_"),
                       ss4.mkString("_"),
                     )
                 )
                 .log("input")
                 .classify(
                   "list.count(_.length >= 10) = (list.length / 2)",
                   list => list.count(_.length >= 10) === (list.length >> 1),
                 )
                 .classify(
                   "list.count(_.length >= 10) > (list.length / 2)",
                   list => list.count(_.length >= 10) > (list.length >> 1),
                 )
                 .classify(
                   "list.count(_.length >= 10) < (list.length / 2)",
                   list => list.count(_.length >= 10) < (list.length >> 1),
                 )
    } yield {
      val expected = (ss1 ++ ss2 ++ ss3 ++ ss4).map(_.toLowerCase(Locale.ENGLISH)).mkString("-")
      val actual   = input.mkKebabLowerCaseString
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

  def testMkKebabLowerCaseStringWithAllUpperSnakeCases: Property =
    for {
      ss1   <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).map(_.map(_.toUpperCase(Locale.ENGLISH))).log("ss1")
      ss2   <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).map(_.map(_.toUpperCase(Locale.ENGLISH))).log("ss2")
      ss3   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).map(_.map(_.toUpperCase(Locale.ENGLISH))).log("ss3")
      ss4   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).map(_.map(_.toUpperCase(Locale.ENGLISH))).log("ss4")
      input <- Gen
                 .constant(
                   List(
                     ss1.mkString("_"),
                     ss2.mkString("_"),
                   ) ++
                     List(
                       ss3.mkString("_"),
                       ss4.mkString("_"),
                     )
                 )
                 .log("input")
                 .classify(
                   "list.count(_.length >= 10) = (list.length / 2)",
                   list => list.count(_.length >= 10) === (list.length >> 1),
                 )
                 .classify(
                   "list.count(_.length >= 10) > (list.length / 2)",
                   list => list.count(_.length >= 10) > (list.length >> 1),
                 )
                 .classify(
                   "list.count(_.length >= 10) < (list.length / 2)",
                   list => list.count(_.length >= 10) < (list.length >> 1),
                 )
    } yield {
      val expected = (ss1 ++ ss2 ++ ss3 ++ ss4).map(_.toLowerCase(Locale.ENGLISH)).mkString("-")
      val actual   = input.mkKebabLowerCaseString
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

  def testMkKebabLowerCaseStringWithAllKebabCases: Property =
    for {
      ss1   <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).log("ss1")
      ss2   <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss2")
      ss3   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss3")
      ss4   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss4")
      input <- Gen
                 .constant(
                   List(
                     ss1.mkString("-"),
                     ss2.mkString("-"),
                   ) ++
                     List(
                       ss3.mkString("-"),
                       ss4.mkString("-"),
                     )
                 )
                 .log("input")
                 .classify(
                   "list.count(_.length >= 10) = (list.length / 2)",
                   list => list.count(_.length >= 10) === (list.length >> 1),
                 )
                 .classify(
                   "list.count(_.length >= 10) > (list.length / 2)",
                   list => list.count(_.length >= 10) > (list.length >> 1),
                 )
                 .classify(
                   "list.count(_.length >= 10) < (list.length / 2)",
                   list => list.count(_.length >= 10) < (list.length >> 1),
                 )
    } yield {
      val expected = (ss1 ++ ss2 ++ ss3 ++ ss4).map(_.toLowerCase(Locale.ENGLISH)).mkString("-")
      val actual   = input.mkKebabLowerCaseString
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

  def testMkKebabLowerCaseStringWithAllLowerKebabCases: Property =
    for {
      ss1   <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).map(_.map(_.toLowerCase(Locale.ENGLISH))).log("ss1")
      ss2   <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).map(_.map(_.toLowerCase(Locale.ENGLISH))).log("ss2")
      ss3   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).map(_.map(_.toLowerCase(Locale.ENGLISH))).log("ss3")
      ss4   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).map(_.map(_.toLowerCase(Locale.ENGLISH))).log("ss4")
      input <- Gen
                 .constant(
                   List(
                     ss1.mkString("-"),
                     ss2.mkString("-"),
                   ) ++
                     List(
                       ss3.mkString("-"),
                       ss4.mkString("-"),
                     )
                 )
                 .log("input")
                 .classify(
                   "list.count(_.length >= 10) = (list.length / 2)",
                   list => list.count(_.length >= 10) === (list.length >> 1),
                 )
                 .classify(
                   "list.count(_.length >= 10) > (list.length / 2)",
                   list => list.count(_.length >= 10) > (list.length >> 1),
                 )
                 .classify(
                   "list.count(_.length >= 10) < (list.length / 2)",
                   list => list.count(_.length >= 10) < (list.length >> 1),
                 )
    } yield {
      val expected = (ss1 ++ ss2 ++ ss3 ++ ss4).map(_.toLowerCase(Locale.ENGLISH)).mkString("-")
      val actual   = input.mkKebabLowerCaseString
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

  def testMkKebabLowerCaseStringWithAllUpperKebabCases: Property =
    for {
      ss1   <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).map(_.map(_.toUpperCase(Locale.ENGLISH))).log("ss1")
      ss2   <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).map(_.map(_.toUpperCase(Locale.ENGLISH))).log("ss2")
      ss3   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).map(_.map(_.toUpperCase(Locale.ENGLISH))).log("ss3")
      ss4   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).map(_.map(_.toUpperCase(Locale.ENGLISH))).log("ss4")
      input <- Gen
                 .constant(
                   List(
                     ss1.mkString("-"),
                     ss2.mkString("-"),
                   ) ++
                     List(
                       ss3.mkString("-"),
                       ss4.mkString("-"),
                     )
                 )
                 .log("input")
                 .classify(
                   "list.count(_.length >= 10) = (list.length / 2)",
                   list => list.count(_.length >= 10) === (list.length >> 1),
                 )
                 .classify(
                   "list.count(_.length >= 10) > (list.length / 2)",
                   list => list.count(_.length >= 10) > (list.length >> 1),
                 )
                 .classify(
                   "list.count(_.length >= 10) < (list.length / 2)",
                   list => list.count(_.length >= 10) < (list.length >> 1),
                 )
    } yield {
      val expected = (ss1 ++ ss2 ++ ss3 ++ ss4).map(_.toLowerCase(Locale.ENGLISH)).mkString("-")
      val actual   = input.mkKebabLowerCaseString
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

  def testMkKebabLowerCaseStringWithAllSpaceSeparatedValues: Property =
    for {
      ss1   <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).log("ss1")
      ss2   <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss2")
      ss3   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss3")
      ss4   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss4")
      input <- Gen
                 .constant(
                   List(
                     ss1.mkString(" "),
                     ss2.mkString(" "),
                   ) ++
                     List(
                       ss3.mkString(" "),
                       ss4.mkString(" "),
                     )
                 )
                 .log("input")
                 .classify(
                   "list.count(_.length >= 10) = (list.length / 2)",
                   list => list.count(_.length >= 10) === (list.length >> 1),
                 )
                 .classify(
                   "list.count(_.length >= 10) > (list.length / 2)",
                   list => list.count(_.length >= 10) > (list.length >> 1),
                 )
                 .classify(
                   "list.count(_.length >= 10) < (list.length / 2)",
                   list => list.count(_.length >= 10) < (list.length >> 1),
                 )
    } yield {
      val expected = (ss1 ++ ss2 ++ ss3 ++ ss4).map(_.toLowerCase(Locale.ENGLISH)).mkString("-")
      val actual   = input.mkKebabLowerCaseString
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

  def testMkKebabLowerCaseStringWithAllLowerSpaceSeparatedValues: Property =
    for {
      ss1   <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).map(_.map(_.toLowerCase(Locale.ENGLISH))).log("ss1")
      ss2   <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).map(_.map(_.toLowerCase(Locale.ENGLISH))).log("ss2")
      ss3   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).map(_.map(_.toLowerCase(Locale.ENGLISH))).log("ss3")
      ss4   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).map(_.map(_.toLowerCase(Locale.ENGLISH))).log("ss4")
      input <- Gen
                 .constant(
                   List(
                     ss1.mkString(" "),
                     ss2.mkString(" "),
                   ) ++
                     List(
                       ss3.mkString(" "),
                       ss4.mkString(" "),
                     )
                 )
                 .log("input")
                 .classify(
                   "list.count(_.length >= 10) = (list.length / 2)",
                   list => list.count(_.length >= 10) === (list.length >> 1),
                 )
                 .classify(
                   "list.count(_.length >= 10) > (list.length / 2)",
                   list => list.count(_.length >= 10) > (list.length >> 1),
                 )
                 .classify(
                   "list.count(_.length >= 10) < (list.length / 2)",
                   list => list.count(_.length >= 10) < (list.length >> 1),
                 )
    } yield {
      val expected = (ss1 ++ ss2 ++ ss3 ++ ss4).map(_.toLowerCase(Locale.ENGLISH)).mkString("-")
      val actual   = input.mkKebabLowerCaseString
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

  def testMkKebabLowerCaseStringWithAllUpperSpaceSeparatedValues: Property =
    for {
      ss1   <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).map(_.map(_.toUpperCase(Locale.ENGLISH))).log("ss1")
      ss2   <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).map(_.map(_.toUpperCase(Locale.ENGLISH))).log("ss2")
      ss3   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).map(_.map(_.toUpperCase(Locale.ENGLISH))).log("ss3")
      ss4   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).map(_.map(_.toUpperCase(Locale.ENGLISH))).log("ss4")
      input <- Gen
                 .constant(
                   List(
                     ss1.mkString(" "),
                     ss2.mkString(" "),
                   ) ++
                     List(
                       ss3.mkString(" "),
                       ss4.mkString(" "),
                     )
                 )
                 .log("input")
                 .classify(
                   "list.count(_.length >= 10) = (list.length / 2)",
                   list => list.count(_.length >= 10) === (list.length >> 1),
                 )
                 .classify(
                   "list.count(_.length >= 10) > (list.length / 2)",
                   list => list.count(_.length >= 10) > (list.length >> 1),
                 )
                 .classify(
                   "list.count(_.length >= 10) < (list.length / 2)",
                   list => list.count(_.length >= 10) < (list.length >> 1),
                 )
    } yield {
      val expected = (ss1 ++ ss2 ++ ss3 ++ ss4).map(_.toLowerCase(Locale.ENGLISH)).mkString("-")
      val actual   = input.mkKebabLowerCaseString
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

  def testMkKebabLowerCaseStringWithPascalCasesCamelCasesLowerCasesUpperCasesSnakeCasesKebabCasesAndSpaceSeparatedValues
    : Property =
    for {
      sLowerCase     <- Gen.string(Gen.lower, Range.linear(1, 50)).log("sLowerCase")
      ss1PascalCases <- Gens.genPascalCase(2, 5).list(Range.linear(1, 7)).log("ss1PascalCases")
      ss2CamelCases  <- Gens
                          .genPascalCase(2, 5)
                          .list(Range.linear(2, 3))
                          .map(_.map(s => s.updated(0, s.charAt(0).toLower)))
                          .log("ss2CamelCases")
      ss3LowerCases  <- Gens
                          .genPascalCase(2, 5)
                          .list(Range.linear(3, 5))
                          .map(_.map(_.toLowerCase(Locale.ENGLISH)))
                          .log("ss3LowerCases")
      ss4UpperCases  <- Gens
                          .genPascalCase(2, 5)
                          .list(Range.linear(2, 3))
                          .map(_.map(_.toUpperCase(Locale.ENGLISH)))
                          .log("ss4UpperCases")

      ss5 <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss5")
      ss6 <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss6")

      ss7LowerCases   <- Gens
                           .genPascalCase(2, 5)
                           .list(Range.linear(3, 5))
                           .map(_.map(_.toLowerCase(Locale.ENGLISH)))
                           .log("ss7LowerCases")
      ss8LowerCases   <- Gens
                           .genPascalCase(2, 5)
                           .list(Range.linear(2, 3))
                           .map(_.map(_.toLowerCase(Locale.ENGLISH)))
                           .log("ss8LowerCases")
      ss9UpperCases   <- Gens
                           .genPascalCase(2, 5)
                           .list(Range.linear(3, 5))
                           .map(_.map(_.toUpperCase(Locale.ENGLISH)))
                           .log("ss9UpperCases")
      ss10UpperCases  <- Gens
                           .genPascalCase(2, 5)
                           .list(Range.linear(3, 5))
                           .map(_.map(_.toUpperCase(Locale.ENGLISH)))
                           .log("ss10UpperCases")
      ss11PascalCases <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss11PascalCases")
      ss12PascalCases <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss12PascalCases")
      ss13LowerCases  <- Gens
                           .genPascalCase(2, 5)
                           .list(Range.linear(3, 5))
                           .map(_.map(_.toLowerCase(Locale.ENGLISH)))
                           .log("ss13LowerCases")
      ss14LowerCases  <- Gens
                           .genPascalCase(2, 5)
                           .list(Range.linear(3, 5))
                           .map(_.map(_.toLowerCase(Locale.ENGLISH)))
                           .log("ss14LowerCases")
      ss15UpperCases  <- Gens
                           .genPascalCase(2, 5)
                           .list(Range.linear(3, 5))
                           .map(_.map(_.toUpperCase(Locale.ENGLISH)))
                           .log("ss15UpperCases")
      ss16UpperCases  <- Gens
                           .genPascalCase(2, 5)
                           .list(Range.linear(3, 5))
                           .map(_.map(_.toUpperCase(Locale.ENGLISH)))
                           .log("ss16UpperCases")
      ss17PascalCases <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss17PascalCases")
      ss18PascalCases <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss18PascalCases")
      ss19LowerCases  <- Gens
                           .genPascalCase(2, 5)
                           .list(Range.linear(3, 5))
                           .map(_.map(_.toLowerCase(Locale.ENGLISH)))
                           .log("ss19LowerCases")
      ss20LowerCases  <- Gens
                           .genPascalCase(2, 5)
                           .list(Range.linear(3, 5))
                           .map(_.map(_.toLowerCase(Locale.ENGLISH)))
                           .log("ss20LowerCases")
      ss21UpperCases  <- Gens
                           .genPascalCase(2, 5)
                           .list(Range.linear(3, 5))
                           .map(_.map(_.toUpperCase(Locale.ENGLISH)))
                           .log("ss21UpperCases")
      ss22UpperCases  <- Gens
                           .genPascalCase(2, 5)
                           .list(Range.linear(3, 5))
                           .map(_.map(_.toUpperCase(Locale.ENGLISH)))
                           .log("ss22UpperCases")
      input           <- Gen
                           .constant(
                             (sLowerCase :: ss1PascalCases)
                               ++ ss2CamelCases
                               ++ ss3LowerCases
                               ++ ss4UpperCases
                               ++ List(ss5.mkString("_"), ss6.mkString("_")) // Snake_Case
                               ++ List( // lower_snake_case
                                 ss7LowerCases.mkString("_"),
                                 ss8LowerCases.mkString("_"),
                               )
                               ++ List( // UPPER_SNAKE_CASE
                                 ss9UpperCases.mkString("_"),
                                 ss10UpperCases.mkString("_"),
                               )
                               ++ List(ss11PascalCases.mkString("-"), ss12PascalCases.mkString("-")) // Kebab-Case
                               ++ List( // lower-kebab-case
                                 ss13LowerCases.mkString("-"),
                                 ss14LowerCases.mkString("-"),
                               )
                               ++ List( // UPPER-KEBAB-CASE
                                 ss15UpperCases.mkString("-"),
                                 ss16UpperCases.mkString("-"),
                               )
                               ++ List(ss17PascalCases.mkString(" "), ss18PascalCases.mkString(" ")) // Space Separated String
                               ++ List( // lower space separated string
                                 ss19LowerCases.mkString(" "),
                                 ss20LowerCases.mkString(" "),
                               )
                               ++ List( // UPPER SPACE SEPARATED STRING
                                 ss21UpperCases.mkString(" "),
                                 ss22UpperCases.mkString(" "),
                               )
                           )
                           .log("input")
                           .classify(
                             "list.count(_.length >= 10) = (list.length / 2)",
                             list => list.count(_.length >= 10) === (list.length >> 1),
                           )
                           .classify(
                             "list.count(_.length >= 10) > (list.length / 2)",
                             list => list.count(_.length >= 10) > (list.length >> 1),
                           )
                           .classify(
                             "list.count(_.length >= 10) < (list.length / 2)",
                             list => list.count(_.length >= 10) < (list.length >> 1),
                           )
    } yield {
      val expected = (
        sLowerCase :: (
          ss1PascalCases ++ ss2CamelCases ++ ss3LowerCases ++ ss4UpperCases ++ ss5 ++
            ss6 ++ ss7LowerCases ++ ss8LowerCases ++ ss9UpperCases ++ ss10UpperCases ++
            ss11PascalCases ++ ss12PascalCases ++ ss13LowerCases ++ ss14LowerCases ++ ss15UpperCases ++
            ss16UpperCases ++ ss17PascalCases ++ ss18PascalCases ++ ss19LowerCases ++ ss20LowerCases ++
            ss21UpperCases ++ ss22UpperCases
        )
      ).map(_.toLowerCase(Locale.ENGLISH))
        .mkString("-")

      val actual = input.mkKebabLowerCaseString
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
