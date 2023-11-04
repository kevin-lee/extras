package extras.strings.syntax.casesSpec.stringSeqCaseOpsSpec

import cats.syntax.all._
import extras.strings.syntax.casesSpec.Gens
import hedgehog._
import hedgehog.runner._

import java.util.Locale

/** @author Kevin Lee
  * @since 2023-10-16
  */
@SuppressWarnings(Array("org.wartremover.warts.SizeIs"))
object MkSnakeCaseStringSpec extends Properties {
  import extras.strings.syntax.cases._

  override def tests: List[Test] = List(
    property("Seq[String].mkSnakeCaseString - already all snake_cases", testMkSnakeCaseStringWithAlreadySnakeCases),
    property("Seq[String].mkSnakeCaseString - all UpperCases", testMkSnakeCaseStringWithAllUpperCases),
    property(
      "Seq[String].mkSnakeCaseString - all PascalCases and combined PascalCases",
      testMkSnakeCaseStringWithAllPascalCasesAndCombinedPascalCases,
    ),
    property("Seq[String].mkSnakeCaseString - all lower cases", testMkSnakeCaseStringWithAllLowerCases),
    property(
      "Seq[String].mkSnakeCaseString - all lower cases and combined camelCases",
      testMkSnakeCaseStringWithAllLowerCasesAndCombinedCamelCases,
    ),
    property("Seq[String].mkSnakeCaseString - all snake_cases", testMkSnakeCaseStringWithAllSnakeCases),
    property("Seq[String].mkSnakeCaseString - all lower_snake_cases", testMkSnakeCaseStringWithAllLowerSnakeCases),
    property("Seq[String].mkSnakeCaseString - all UPPER_SNAKE_CASES", testMkSnakeCaseStringWithAllUpperSnakeCases),
    property(
      "Seq[String].mkSnakeCaseString - all kebab-cases",
      testMkSnakeCaseStringWithAllKebabCases,
    ),
    property(
      "Seq[String].mkSnakeCaseString - all lower-kebab-cases",
      testMkSnakeCaseStringWithAllLowerKebabCases,
    ),
    property(
      "Seq[String].mkSnakeCaseString - all UPPER-KEBAB-CASES",
      testMkSnakeCaseStringWithAllUpperKebabCases,
    ),
    property(
      "Seq[String].mkSnakeCaseString - all space separated values",
      testMkSnakeCaseStringWithAllSpaceSeparatedValues,
    ),
    property(
      "Seq[String].mkSnakeCaseString - all lower space separated values",
      testMkSnakeCaseStringWithAllLowerSpaceSeparatedValues,
    ),
    property(
      "Seq[String].mkSnakeCaseString - all UPPER SPACE SEPARATED VALUES",
      testMkSnakeCaseStringWithAllUpperSpaceSeparatedValues,
    ),
    property(
      "Seq[String].mkSnakeCaseString - PascalCases, camelCases, lower cases, UPPER CASES, snake_cases, kebab-cases and space separated String values",
      testMkSnakeCaseStringWithPascalCasesCamelCasesLowerCasesUpperCasesSnakeCasesKebabCasesAndSpaceSeparatedValues,
    ),
  )

  def testMkSnakeCaseStringWithAlreadySnakeCases: Property =
    for {
      input <-
        Gens
          .genPascalCase(1, 5)
          .list(Range.linear(1, 10))
          .map(_.mkString("_"))
          .list(Range.linear(1, 10))
          .log("input")
          .classify("Length = 1", _.length === 1)
          .classify("Length > 1", _.length > 1)
    } yield {
      val expected = input.mkString("_")
      val actual   = input.mkSnakeCaseString
      actual ==== expected
    }

  def testMkSnakeCaseStringWithAllUpperCases: Property =
    for {
      input    <- Gen
                    .string(Gen.alphaNum, Range.linear(1, 10))
                    .map(_.toUpperCase(Locale.ENGLISH))
                    .list(Range.linear(1, 10))
                    .log("input")
                    .classify("Length = 1", _.length === 1)
                    .classify("Length > 1", _.length > 1)
      expected <- Gen.constant(input.mkString("_")).log("expected")
    } yield {
      val actual = input.mkSnakeCaseString
      val info   =
        s"""
           |>    input: ${input.toString}
           |>   actual: $actual
           |> expected: $expected
           |""".stripMargin

      (actual ==== expected).log(info)
    }

  def testMkSnakeCaseStringWithAllPascalCasesAndCombinedPascalCases: Property =
    for {
      ss       <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).log("ss")
      ss2      <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss2")
      ss3      <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss3")
      input    <- Gen
                    .constant(ss ++ List(ss2.mkString + ss3.mkString))
                    .log("input")
                    .classify("Length <= 2", _.length <= 2)
                    .classify("Length > 2 ", _.length > 2)
      expected <- Gen.constant((ss ++ ss2 ++ ss3).mkString("_")).log("expected")
    } yield {
      val actual = input.mkSnakeCaseString

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

  def testMkSnakeCaseStringWithAllLowerCases: Property =
    for {
      input    <- Gen
                    .string(Gen.alphaNum, Range.linear(1, 10))
                    .map(_.toLowerCase(Locale.ENGLISH))
                    .list(Range.linear(1, 10))
                    .log("input")
                    .classify("Length = 1", _.length === 1)
                    .classify("Length > 1", _.length > 1)
      expected <- Gen.constant(input.mkString("_")).log("expected")
    } yield {
      val actual = input.mkSnakeCaseString
      actual ==== expected
    }

  def testMkSnakeCaseStringWithAllLowerCasesAndCombinedCamelCases: Property =
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
      val expected = (ss1 ++ ss2 ++ ss3).mkString("_")
      val actual   = input.mkSnakeCaseString
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

  def testMkSnakeCaseStringWithAllSnakeCases: Property =
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
      val expected = (ss1 ++ ss2 ++ ss3 ++ ss4).mkString("_")
      val actual   = input.mkSnakeCaseString
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

  def testMkSnakeCaseStringWithAllLowerSnakeCases: Property =
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
      val expected = (ss1 ++ ss2 ++ ss3 ++ ss4).mkString("_")
      val actual   = input.mkSnakeCaseString
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

  def testMkSnakeCaseStringWithAllUpperSnakeCases: Property =
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
      val expected = (ss1 ++ ss2 ++ ss3 ++ ss4).mkString("_")
      val actual   = input.mkSnakeCaseString
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

  def testMkSnakeCaseStringWithAllKebabCases: Property =
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
      val expected = (ss1 ++ ss2 ++ ss3 ++ ss4).mkString("_")
      val actual   = input.mkSnakeCaseString
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

  def testMkSnakeCaseStringWithAllLowerKebabCases: Property =
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
      val expected = (ss1 ++ ss2 ++ ss3 ++ ss4).mkString("_")
      val actual   = input.mkSnakeCaseString
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

  def testMkSnakeCaseStringWithAllUpperKebabCases: Property =
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
      val expected = (ss1 ++ ss2 ++ ss3 ++ ss4).mkString("_")
      val actual   = input.mkSnakeCaseString
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

  def testMkSnakeCaseStringWithAllSpaceSeparatedValues: Property =
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
      val expected = (ss1 ++ ss2 ++ ss3 ++ ss4).mkString("_")
      val actual   = input.mkSnakeCaseString
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

  def testMkSnakeCaseStringWithAllLowerSpaceSeparatedValues: Property =
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
      val expected = (ss1 ++ ss2 ++ ss3 ++ ss4).mkString("_")
      val actual   = input.mkSnakeCaseString
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

  def testMkSnakeCaseStringWithAllUpperSpaceSeparatedValues: Property =
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
      val expected = (ss1 ++ ss2 ++ ss3 ++ ss4).mkString("_")
      val actual   = input.mkSnakeCaseString
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

  def testMkSnakeCaseStringWithPascalCasesCamelCasesLowerCasesUpperCasesSnakeCasesKebabCasesAndSpaceSeparatedValues
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
      ).mkString("_")

      val actual = input.mkSnakeCaseString
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
