package extras.scala.io.syntax

import extras.scala.io.ColorGens
import hedgehog._
import hedgehog.runner._

import scala.io.AnsiColor

/** @author Kevin Lee
  * @since 2021-09-19
  */
object ColorSyntaxSpec extends Properties {
  override def tests: List[Test] = List(
    property("test text.colored(Color)", testColored),
    property("test txt.black", testBlack),
    property("test txt.red", testRed),
    property("test txt.green", testGreen),
    property("test txt.yellow", testYellow),
    property("test txt.blue", testBlue),
    property("test txt.magenta", testMagenta),
    property("test txt.cyan", testCyan),
    property("test txt.white", testWhite),
    property("test txt.blackBg", testBlackBg),
    property("test txt.redBg", testRedBg),
    property("test txt.greenBg", testGreenBg),
    property("test txt.yellowBg", testYellowBg),
    property("test txt.blueBg", testBlueBg),
    property("test txt.magentaBg", testMagentaBg),
    property("test txt.cyanBg", testCyanBg),
    property("test txt.whiteBg", testWhiteBg),
    property("test txt.reset", testReset),
    property("test txt.bold", testBold),
    property("test txt.underlined", testUnderlined),
    property("test txt.blink", testBlink),
    property("test txt.reversed", testReversed),
    property("test txt.invisible", testInvisible)
  )

  import extras.scala.io.syntax.color._

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

  def testBlack: Property = for {
    text <- Gen.string(Gen.alphaNum, Range.linear(3, 5)).log("text")
  } yield {
    val actual   = text.black
    val expected = colorAndReset(text, AnsiColor.BLACK)
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

  def testYellow: Property = for {
    text <- Gen.string(Gen.alphaNum, Range.linear(3, 5)).log("text")
  } yield {
    val actual   = text.yellow
    val expected = colorAndReset(text, AnsiColor.YELLOW)
    actual ==== expected
  }

  def testBlue: Property = for {
    text <- Gen.string(Gen.alphaNum, Range.linear(3, 5)).log("text")
  } yield {
    val actual   = text.blue
    val expected = colorAndReset(text, AnsiColor.BLUE)
    actual ==== expected
  }

  def testMagenta: Property = for {
    text <- Gen.string(Gen.alphaNum, Range.linear(3, 5)).log("text")
  } yield {
    val actual   = text.magenta
    val expected = colorAndReset(text, AnsiColor.MAGENTA)
    actual ==== expected
  }

  def testCyan: Property = for {
    text <- Gen.string(Gen.alphaNum, Range.linear(3, 5)).log("text")
  } yield {
    val actual   = text.cyan
    val expected = colorAndReset(text, AnsiColor.CYAN)
    actual ==== expected
  }

  def testWhite: Property = for {
    text <- Gen.string(Gen.alphaNum, Range.linear(3, 5)).log("text")
  } yield {
    val actual   = text.white
    val expected = colorAndReset(text, AnsiColor.WHITE)
    actual ==== expected
  }

  def testBlackBg: Property = for {
    text <- Gen.string(Gen.alphaNum, Range.linear(3, 5)).log("text")
  } yield {
    val actual   = text.blackBg
    val expected = colorAndReset(text, AnsiColor.BLACK_B)
    actual ==== expected
  }

  def testRedBg: Property = for {
    text <- Gen.string(Gen.alphaNum, Range.linear(3, 5)).log("text")
  } yield {
    val actual   = text.redBg
    val expected = colorAndReset(text, AnsiColor.RED_B)
    actual ==== expected
  }

  def testGreenBg: Property = for {
    text <- Gen.string(Gen.alphaNum, Range.linear(3, 5)).log("text")
  } yield {
    val actual   = text.greenBg
    val expected = colorAndReset(text, AnsiColor.GREEN_B)
    actual ==== expected
  }

  def testYellowBg: Property = for {
    text <- Gen.string(Gen.alphaNum, Range.linear(3, 5)).log("text")
  } yield {
    val actual   = text.yellowBg
    val expected = colorAndReset(text, AnsiColor.YELLOW_B)
    actual ==== expected
  }

  def testBlueBg: Property = for {
    text <- Gen.string(Gen.alphaNum, Range.linear(3, 5)).log("text")
  } yield {
    val actual   = text.blueBg
    val expected = colorAndReset(text, AnsiColor.BLUE_B)
    actual ==== expected
  }

  def testMagentaBg: Property = for {
    text <- Gen.string(Gen.alphaNum, Range.linear(3, 5)).log("text")
  } yield {
    val actual   = text.magentaBg
    val expected = colorAndReset(text, AnsiColor.MAGENTA_B)
    actual ==== expected
  }

  def testCyanBg: Property = for {
    text <- Gen.string(Gen.alphaNum, Range.linear(3, 5)).log("text")
  } yield {
    val actual   = text.cyanBg
    val expected = colorAndReset(text, AnsiColor.CYAN_B)
    actual ==== expected
  }

  def testWhiteBg: Property = for {
    text <- Gen.string(Gen.alphaNum, Range.linear(3, 5)).log("text")
  } yield {
    val actual   = text.whiteBg
    val expected = colorAndReset(text, AnsiColor.WHITE_B)
    actual ==== expected
  }

  def testReset: Property = for {
    text <- Gen.string(Gen.alphaNum, Range.linear(3, 5)).log("text")
  } yield {
    val actual   = text.reset
    val expected = colorAndReset(text, AnsiColor.RESET)
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

  def testBlink: Property = for {
    text <- Gen.string(Gen.alphaNum, Range.linear(3, 5)).log("text")
  } yield {
    val actual   = text.blink
    val expected = colorAndReset(text, AnsiColor.BLINK)
    actual ==== expected
  }

  def testReversed: Property = for {
    text <- Gen.string(Gen.alphaNum, Range.linear(3, 5)).log("text")
  } yield {
    val actual   = text.reversedColor
    val expected = colorAndReset(text, AnsiColor.REVERSED)
    actual ==== expected
  }

  def testInvisible: Property = for {
    text <- Gen.string(Gen.alphaNum, Range.linear(3, 5)).log("text")
  } yield {
    val actual   = text.invisible
    val expected = colorAndReset(text, AnsiColor.INVISIBLE)
    actual ==== expected
  }

}
