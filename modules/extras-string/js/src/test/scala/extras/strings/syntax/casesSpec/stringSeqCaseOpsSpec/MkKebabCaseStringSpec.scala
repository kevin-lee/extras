package extras.strings.syntax.casesSpec.stringSeqCaseOpsSpec

import cats.syntax.all._
import extras.strings.syntax.casesSpec.Gens
import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2023-11-04
  */
@SuppressWarnings(Array("org.wartremover.warts.SizeIs"))
object MkKebabCaseStringSpec extends Properties {
  import extras.strings.syntax.cases._

  override def tests: List[Test] = List(
    property("Seq[String].mkKebabCaseString - already all kebab-cases", testMkKebabCaseStringWithAlreadyKebabCases),
    property("Seq[String].mkKebabCaseString - all UpperCases", testMkKebabCaseStringWithAllUpperCases),
    property(
      "Seq[String].mkKebabCaseString - all PascalCases and combined PascalCases",
      testMkKebabCaseStringWithAllPascalCasesAndCombinedPascalCases,
    ),
    property("Seq[String].mkKebabCaseString - all lower cases", testMkKebabCaseStringWithAllLowerCases),
    property(
      "Seq[String].mkKebabCaseString - all lower cases and combined camelCases",
      testMkKebabCaseStringWithAllLowerCasesAndCombinedCamelCases,
    ),
    property("Seq[String].mkKebabCaseString - all snake_cases", testMkKebabCaseStringWithAllSnakeCases),
    property("Seq[String].mkKebabCaseString - all lower_snake_cases", testMkKebabCaseStringWithAllLowerSnakeCases),
    property("Seq[String].mkKebabCaseString - all UPPER_SNAKE_CASES", testMkKebabCaseStringWithAllUpperSnakeCases),
    property(
      "Seq[String].mkKebabCaseString - all kebab-cases",
      testMkKebabCaseStringWithAllKebabCases,
    ),
    property(
      "Seq[String].mkKebabCaseString - all lower-kebab-cases",
      testMkKebabCaseStringWithAllLowerKebabCases,
    ),
    property(
      "Seq[String].mkKebabCaseString - all UPPER-KEBAB-CASES",
      testMkKebabCaseStringWithAllUpperKebabCases,
    ),
    property(
      "Seq[String].mkKebabCaseString - all space separated values",
      testMkKebabCaseStringWithAllSpaceSeparatedValues,
    ),
    property(
      "Seq[String].mkKebabCaseString - all lower space separated values",
      testMkKebabCaseStringWithAllLowerSpaceSeparatedValues,
    ),
    property(
      "Seq[String].mkKebabCaseString - all UPPER SPACE SEPARATED VALUES",
      testMkKebabCaseStringWithAllUpperSpaceSeparatedValues,
    ),
    property(
      "Seq[String].mkKebabCaseString - PascalCases, camelCases, lower cases, UPPER CASES, snake_cases, kebab-cases and space separated String values",
      testMkKebabCaseStringWithPascalCasesCamelCasesLowerCasesUpperCasesSnakeCasesKebabCasesAndSpaceSeparatedValues,
    ),
  )

  def testMkKebabCaseStringWithAlreadyKebabCases: Property =
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
      val expected = input.mkString("-")
      val actual   = input.mkKebabCaseString
      actual ==== expected
    }

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testMkKebabCaseStringWithAllUpperCases: Property =
    for {
      input    <- Gen
                    .string(Gen.alphaNum, Range.linear(1, 10))
                    .map(_.toUpperCase())
                    .list(Range.linear(1, 10))
                    .log("input")
                    .classify("Length = 1", _.length === 1)
                    .classify("Length > 1", _.length > 1)
      expected <- Gen.constant(input.mkString("-")).log("expected")
    } yield {
      val actual = input.mkKebabCaseString
      val info   =
        s"""
           |>    input: ${input.toString}
           |>   actual: $actual
           |> expected: $expected
           |""".stripMargin

      (actual ==== expected).log(info)
    }

  def testMkKebabCaseStringWithAllPascalCasesAndCombinedPascalCases: Property =
    for {
      ss       <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).log("ss")
      ss2      <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss2")
      ss3      <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss3")
      input    <- Gen
                    .constant(ss ++ List(ss2.mkString + ss3.mkString))
                    .log("input")
                    .classify("Length <= 2", _.length <= 2)
                    .classify("Length > 2 ", _.length > 2)
      expected <- Gen.constant((ss ++ ss2 ++ ss3).mkString("-")).log("expected")
    } yield {
      val actual = input.mkKebabCaseString

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

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testMkKebabCaseStringWithAllLowerCases: Property =
    for {
      input    <- Gen
                    .string(Gen.alphaNum, Range.linear(1, 10))
                    .map(_.toLowerCase())
                    .list(Range.linear(1, 10))
                    .log("input")
                    .classify("Length = 1", _.length === 1)
                    .classify("Length > 1", _.length > 1)
      expected <- Gen.constant(input.mkString("-")).log("expected")
    } yield {
      val actual = input.mkKebabCaseString
      actual ==== expected
    }

  def testMkKebabCaseStringWithAllLowerCasesAndCombinedCamelCases: Property =
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
      val expected = (ss1 ++ ss2 ++ ss3).mkString("-")
      val actual   = input.mkKebabCaseString
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

  def testMkKebabCaseStringWithAllSnakeCases: Property =
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
      val expected = (ss1 ++ ss2 ++ ss3 ++ ss4).mkString("-")
      val actual   = input.mkKebabCaseString
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

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testMkKebabCaseStringWithAllLowerSnakeCases: Property =
    for {
      ss1   <- Gens
                 .genPascalCase(1, 5)
                 .list(Range.linear(1, 10))
                 .map(_.map(_.toLowerCase()))
                 .log("ss1")
      ss2   <- Gens
                 .genPascalCase(2, 5)
                 .list(Range.linear(2, 3))
                 .map(_.map(_.toLowerCase()))
                 .log("ss2")
      ss3   <- Gens
                 .genPascalCase(2, 5)
                 .list(Range.linear(3, 5))
                 .map(_.map(_.toLowerCase()))
                 .log("ss3")
      ss4   <- Gens
                 .genPascalCase(2, 5)
                 .list(Range.linear(3, 5))
                 .map(_.map(_.toLowerCase()))
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
      val expected = (ss1 ++ ss2 ++ ss3 ++ ss4).mkString("-")
      val actual   = input.mkKebabCaseString
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

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testMkKebabCaseStringWithAllUpperSnakeCases: Property =
    for {
      ss1   <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).map(_.map(_.toUpperCase())).log("ss1")
      ss2   <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).map(_.map(_.toUpperCase())).log("ss2")
      ss3   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).map(_.map(_.toUpperCase())).log("ss3")
      ss4   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).map(_.map(_.toUpperCase())).log("ss4")
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
      val expected = (ss1 ++ ss2 ++ ss3 ++ ss4).mkString("-")
      val actual   = input.mkKebabCaseString
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

  def testMkKebabCaseStringWithAllKebabCases: Property =
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
      val expected = (ss1 ++ ss2 ++ ss3 ++ ss4).mkString("-")
      val actual   = input.mkKebabCaseString
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

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testMkKebabCaseStringWithAllLowerKebabCases: Property =
    for {
      ss1   <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).map(_.map(_.toLowerCase())).log("ss1")
      ss2   <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).map(_.map(_.toLowerCase())).log("ss2")
      ss3   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).map(_.map(_.toLowerCase())).log("ss3")
      ss4   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).map(_.map(_.toLowerCase())).log("ss4")
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
      val expected = (ss1 ++ ss2 ++ ss3 ++ ss4).mkString("-")
      val actual   = input.mkKebabCaseString
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

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testMkKebabCaseStringWithAllUpperKebabCases: Property =
    for {
      ss1   <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).map(_.map(_.toUpperCase())).log("ss1")
      ss2   <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).map(_.map(_.toUpperCase())).log("ss2")
      ss3   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).map(_.map(_.toUpperCase())).log("ss3")
      ss4   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).map(_.map(_.toUpperCase())).log("ss4")
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
      val expected = (ss1 ++ ss2 ++ ss3 ++ ss4).mkString("-")
      val actual   = input.mkKebabCaseString
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

  def testMkKebabCaseStringWithAllSpaceSeparatedValues: Property =
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
      val expected = (ss1 ++ ss2 ++ ss3 ++ ss4).mkString("-")
      val actual   = input.mkKebabCaseString
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

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testMkKebabCaseStringWithAllLowerSpaceSeparatedValues: Property =
    for {
      ss1   <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).map(_.map(_.toLowerCase())).log("ss1")
      ss2   <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).map(_.map(_.toLowerCase())).log("ss2")
      ss3   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).map(_.map(_.toLowerCase())).log("ss3")
      ss4   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).map(_.map(_.toLowerCase())).log("ss4")
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
      val expected = (ss1 ++ ss2 ++ ss3 ++ ss4).mkString("-")
      val actual   = input.mkKebabCaseString
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

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testMkKebabCaseStringWithAllUpperSpaceSeparatedValues: Property =
    for {
      ss1   <- Gens.genPascalCase(1, 5).list(Range.linear(1, 10)).map(_.map(_.toUpperCase())).log("ss1")
      ss2   <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).map(_.map(_.toUpperCase())).log("ss2")
      ss3   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).map(_.map(_.toUpperCase())).log("ss3")
      ss4   <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).map(_.map(_.toUpperCase())).log("ss4")
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
      val expected = (ss1 ++ ss2 ++ ss3 ++ ss4).mkString("-")
      val actual   = input.mkKebabCaseString
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

  @SuppressWarnings(Array("org.wartremover.warts.PlatformDefault"))
  def testMkKebabCaseStringWithPascalCasesCamelCasesLowerCasesUpperCasesSnakeCasesKebabCasesAndSpaceSeparatedValues
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
                          .map(_.map(_.toLowerCase()))
                          .log("ss3LowerCases")
      ss4UpperCases  <- Gens
                          .genPascalCase(2, 5)
                          .list(Range.linear(2, 3))
                          .map(_.map(_.toUpperCase()))
                          .log("ss4UpperCases")

      ss5 <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss5")
      ss6 <- Gens.genPascalCase(2, 5).list(Range.linear(2, 3)).log("ss6")

      ss7LowerCases   <- Gens
                           .genPascalCase(2, 5)
                           .list(Range.linear(3, 5))
                           .map(_.map(_.toLowerCase()))
                           .log("ss7LowerCases")
      ss8LowerCases   <- Gens
                           .genPascalCase(2, 5)
                           .list(Range.linear(2, 3))
                           .map(_.map(_.toLowerCase()))
                           .log("ss8LowerCases")
      ss9UpperCases   <- Gens
                           .genPascalCase(2, 5)
                           .list(Range.linear(3, 5))
                           .map(_.map(_.toUpperCase()))
                           .log("ss9UpperCases")
      ss10UpperCases  <- Gens
                           .genPascalCase(2, 5)
                           .list(Range.linear(3, 5))
                           .map(_.map(_.toUpperCase()))
                           .log("ss10UpperCases")
      ss11PascalCases <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss11PascalCases")
      ss12PascalCases <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss12PascalCases")
      ss13LowerCases  <- Gens
                           .genPascalCase(2, 5)
                           .list(Range.linear(3, 5))
                           .map(_.map(_.toLowerCase()))
                           .log("ss13LowerCases")
      ss14LowerCases  <- Gens
                           .genPascalCase(2, 5)
                           .list(Range.linear(3, 5))
                           .map(_.map(_.toLowerCase()))
                           .log("ss14LowerCases")
      ss15UpperCases  <- Gens
                           .genPascalCase(2, 5)
                           .list(Range.linear(3, 5))
                           .map(_.map(_.toUpperCase()))
                           .log("ss15UpperCases")
      ss16UpperCases  <- Gens
                           .genPascalCase(2, 5)
                           .list(Range.linear(3, 5))
                           .map(_.map(_.toUpperCase()))
                           .log("ss16UpperCases")
      ss17PascalCases <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss17PascalCases")
      ss18PascalCases <- Gens.genPascalCase(2, 5).list(Range.linear(3, 5)).log("ss18PascalCases")
      ss19LowerCases  <- Gens
                           .genPascalCase(2, 5)
                           .list(Range.linear(3, 5))
                           .map(_.map(_.toLowerCase()))
                           .log("ss19LowerCases")
      ss20LowerCases  <- Gens
                           .genPascalCase(2, 5)
                           .list(Range.linear(3, 5))
                           .map(_.map(_.toLowerCase()))
                           .log("ss20LowerCases")
      ss21UpperCases  <- Gens
                           .genPascalCase(2, 5)
                           .list(Range.linear(3, 5))
                           .map(_.map(_.toUpperCase()))
                           .log("ss21UpperCases")
      ss22UpperCases  <- Gens
                           .genPascalCase(2, 5)
                           .list(Range.linear(3, 5))
                           .map(_.map(_.toUpperCase()))
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
      ).mkString("-")

      val actual = input.mkKebabCaseString
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
