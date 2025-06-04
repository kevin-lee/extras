package extras.strings.syntax.casesSpec.stringCaseOpsSpec

import extras.strings.syntax.casesSpec.Gens
import hedgehog._
import hedgehog.runner._

import java.util.Locale

/** @author Kevin Lee
  * @since 2023-10-16
  */
object SplitByCaseSpec extends Properties {
  // String.SplitByCase

  import extras.strings.syntax.cases._

  override def tests: List[Test] = List(
    example("test String.SplitByCase with an empty String", testSplitByCaseWithEmptyString),
    property("test String.SplitByCase with PascalCase", testSplitByCaseWithPascalCase),
    property("test String.SplitByCase with PascalCases", testSplitByCaseWithPascalCases),
    property("test String.SplitByCase with camelCases", testSplitByCaseWithCamelCases),
    property("test String.SplitByCase with all UPPERCASE", testSplitByCaseWithUpperCase),
    property("test String.SplitByCase with all lowercase", testSplitByCaseWithLowerCase),
  )

  def testSplitByCaseWithEmptyString: Result = {
    val input    = ""
    val expected = Vector.empty[String]
    val actual   = input.splitByCase

    val info =
      s"""
         |>    input: $input
         |>   actual: ${actual.toString}
         |> expected: ${expected.toString}
         |""".stripMargin

    (actual ==== expected).log(info)
  }

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
}
