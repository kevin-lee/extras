package extras.strings.syntax

import extras.strings.syntax.cases._
import hedgehog._
import hedgehog.runner._

import java.util.Locale

/** @author Kevin Lee
  * @since 2023-10-16
  */
object casesSpec extends Properties {
  override def tests: List[Test] = StringCaseOpsSpec.tests

  object StringCaseOpsSpec {
    private val name      = this.getClass.getSimpleName.stripSuffix("$")
    def tests: List[Test] = List(
      property(s"$name: test String.toPascalCase with already PascalCase", testToPascalCaseWithAlreadyPascalCase),
      property(s"$name: test String.toPascalCase with PascalCases", testToPascalCaseWithPascalCases),
      property(s"$name: test String.toPascalCase with camelCases", testToPascalCaseWithCamelCases),
      property(s"$name: test String.toPascalCase with all UPPERCASE", testToPascalCaseWithUpperCase),
      property(s"$name: test String.toPascalCase with all lowercase", testToPascalCaseWithLowerCase),
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
      s2    <- genPascalCase(1, 10).list(Range.linear(1, 4)).log("s2")
      input <- Gen.constant(s1 + s2.mkString).log("input")
    } yield {
      val expected = s1 + s2.mkString.toLowerCase(Locale.ENGLISH)
      val actual   = input.toPascalCase
      println(
        s"""   input: $input
           |  actual: $actual
           |expected: $expected
           |""".stripMargin
      )
      actual ==== expected
    }

    def testToPascalCaseWithCamelCases: Property = for {
      s1    <- genPascalCase(1, 10).log("s1")
      s2    <- genPascalCase(1, 10).list(Range.linear(1, 4)).log("s2")
      input <- Gen.constant(s1.updated(0, s1.charAt(0).toLower) + s2.mkString).log("input")
    } yield {
      val expected = s1 + s2.mkString.toLowerCase(Locale.ENGLISH)
      val actual   = input.toPascalCase
      println(
        s"""   input: $input
           |  actual: $actual
           |expected: $expected
           |""".stripMargin
      )
      actual ==== expected
    }

    def testToPascalCaseWithUpperCase: Property = for {
      expected <- genPascalCase(1, 10).log("expected")
      input    <- Gen.constant(expected.toUpperCase(Locale.ENGLISH)).log("input")
    } yield {
      val actual = input.toPascalCase
      actual ==== expected
    }

    def testToPascalCaseWithLowerCase: Property = for {
      expected <- genPascalCase(1, 10).log("expected")
      input    <- Gen.constant(expected.toLowerCase(Locale.ENGLISH)).log("input")
    } yield {
      val actual = input.toPascalCase
      actual ==== expected
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
