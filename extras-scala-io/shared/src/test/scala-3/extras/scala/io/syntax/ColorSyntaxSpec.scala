package extras.scala.io.syntax

import extras.scala.io.ColorGens
import hedgehog.*
import hedgehog.runner.*

import scala.io.AnsiColor

/** @author Kevin Lee
  * @since 2021-09-19
  */
object ColorSyntaxSpec extends Properties {
  override def tests: List[Test] = List(
    property("test text.colored(Color)", testColored),
    property("test text.bold", testBold),
    property("test text.underlined", testUnderlined),
    property("test text.red", testRed),
    property("test text.green", testGreen),
    property("test text.blue", testBlue)
  )

  import extras.scala.io.syntax.color.*

  def colorAndReset(text: String, ansiColor: String): String =
    s"${ansiColor}$text${AnsiColor.RESET}"

  def testColored: Property = for {
    colorPair <- ColorGens.genColor.log("color")
    text      <- Gen.string(Gen.alphaNum, Range.linear(3, 5)).log("text")
  } yield {
    val (color, ansiColor) = colorPair
    val actual             = text.colored(color)
    val expected           = colorAndReset(text, ansiColor)
    actual ==== expected
  }

  def testBold: Property = for {
    text <- Gen.string(Gen.alphaNum, Range.linear(3, 5)).log("text")
  } yield {
    val actual   = text.bold
    val expected = colorAndReset(text, AnsiColor.BOLD)
    actual ==== expected
  }

  def testUnderlined: Property = for {
    text <- Gen.string(Gen.alphaNum, Range.linear(3, 5)).log("text")
  } yield {
    val actual   = text.underlined
    val expected = colorAndReset(text, AnsiColor.UNDERLINED)
    actual ==== expected
  }

  def testRed: Property = for {
    text <- Gen.string(Gen.alphaNum, Range.linear(3, 5)).log("text")
  } yield {
    val actual   = text.red
    val expected = colorAndReset(text, AnsiColor.RED)
    actual ==== expected
  }

  def testGreen: Property = for {
    text <- Gen.string(Gen.alphaNum, Range.linear(3, 5)).log("text")
  } yield {
    val actual   = text.green
    val expected = colorAndReset(text, AnsiColor.GREEN)
    actual ==== expected
  }

  def testBlue: Property = for {
    text <- Gen.string(Gen.alphaNum, Range.linear(3, 5)).log("text")
  } yield {
    val actual   = text.blue
    val expected = colorAndReset(text, AnsiColor.BLUE)
    actual ==== expected
  }

}
