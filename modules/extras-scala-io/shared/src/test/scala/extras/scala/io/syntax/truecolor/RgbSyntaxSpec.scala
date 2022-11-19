package extras.scala.io.syntax.truecolor

import extras.scala.io.truecolor.{Gens, Rgb, TestTools}
import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2022-06-18
  */
object RgbSyntaxSpec extends Properties {
  override def tests: List[Test] = List(
    property(
      """String value.rgb(Rgb(value)) should return value in colored String value with ASCII escape chars""",
      testRgbRgb,
    ),
    property(
      """String value.rgbed(Rgb(value)) should return value in colored String value with ASCII escape chars ending with scala.io.AnsiColor.RESET""",
      testRgbedRgb,
    ),
    property(
      """String value.rgb(Int) should return value in colored String value with ASCII escape chars""",
      testRgbInt,
    ),
    property(
      """String value.rgb(Int < 0) should return value in colored ASCII escaped String value with 0x000000""",
      testRgbIntLessThan0,
    ),
    property(
      """String value.rgb(Int > 0xffffff) should return value in colored ASCII escaped String value with 0xffffff""",
      testRgbIntGreaterThan0xffffff,
    ),
    property(
      """"".rgb(Int) should return """"",
      testEmptyStringRgbInt,
    ),
    property(
      """String value.rgbed(Int) should return value in colored String value with ASCII escape chars ending with scala.io.AnsiColor.RESET""",
      testRgbedInt,
    ),
    property(
      """String value.rgbed(Int < 0) should return value in colored ASCII escaped String value with 0x000000 ending with scala.io.AnsiColor.RESET""",
      testRgbedIntLessThan0,
    ),
    property(
      """String value.rgbed(Int > 0xffffff) should return value in colored ASCII escaped String value with 0xffffff ending with scala.io.AnsiColor.RESET""",
      testRgbedIntGreaterThan0xffffff,
    ),
    property(
      """"".rgbed(Int) should return """"",
      testEmptyStringRgbedInt,
    ),
  )

  def testRgbRgb: Property = for {
    rgbInput <- Gens.genRgbIntAndInts.log("(rgbInt, (redInt, greenInt, blueInt))")
    (rgbInt, (redInt, greenInt, blueInt)) = rgbInput
    text <- Gen.string(Gen.alphaNum, Range.linear(1, 30)).log("text")
  } yield {
    import extras.scala.io.syntax.truecolor.rgb._
    val rgb      = Rgb.unsafeFromInt(rgbInt)
    val expected = TestTools.toRgbAsciiEsc(redInt, greenInt, blueInt) + text
    val actual   = text.rgb(rgb)
    actual ==== expected
  }

  def testRgbedRgb: Property = for {
    rgbInput <- Gens.genRgbIntAndInts.log("(rgbInt, (redInt, greenInt, blueInt))")
    (rgbInt, (redInt, greenInt, blueInt)) = rgbInput
    text <- Gen.string(Gen.alphaNum, Range.linear(1, 30)).log("text")
  } yield {
    import extras.scala.io.syntax.truecolor.rgb._
    val rgb      = Rgb.unsafeFromInt(rgbInt)
    val expected = TestTools.toRgbAsciiEsc(redInt, greenInt, blueInt) + text + extras.scala.io.Color.reset.toAnsi
    val actual   = text.rgbed(rgb)
    actual ==== expected
  }

  def testRgbInt: Property = for {
    rgbInput <- Gens.genRgbIntAndInts.log("(rgbInt, (redInt, greenInt, blueInt))")
    (rgbInt, (redInt, greenInt, blueInt)) = rgbInput
    text <- Gen.string(Gen.alphaNum, Range.linear(1, 30)).log("text")
  } yield {
    import extras.scala.io.syntax.truecolor.rgb._
    val rgb      = Rgb.unsafeFromInt(rgbInt)
    val expected = TestTools.toRgbAsciiEsc(redInt, greenInt, blueInt) + text
    val actual   = text.rgb(rgbInt)
    actual ==== expected
  }

  def testRgbIntLessThan0: Property = for {
    invalidRgbInt <- Gen.int(Range.linear(Int.MinValue, -1)).log("invalidRgbInt")
    text          <- Gen.string(Gen.alphaNum, Range.linear(1, 30)).log("text")
  } yield {
    import extras.scala.io.syntax.truecolor.rgb._
    val expected = TestTools.toRgbAsciiEsc(0x00, 0x00, 0x00) + text
    val actual   = text.rgb(invalidRgbInt)
    actual ==== expected
  }

  def testRgbIntGreaterThan0xffffff: Property = for {
    invalidRgbInt <- Gen.int(Range.linear(0xffffff + 1, Int.MaxValue)).log("invalidRgbInt")
    text          <- Gen.string(Gen.alphaNum, Range.linear(1, 30)).log("text")
  } yield {
    import extras.scala.io.syntax.truecolor.rgb._
    val expected = TestTools.toRgbAsciiEsc(255, 255, 255) + text
    val actual   = text.rgb(invalidRgbInt)
    actual ==== expected
  }

  def testEmptyStringRgbInt: Property = for {
    intValue <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("intValue")
  } yield {
    import extras.scala.io.syntax.truecolor.rgb._
    val expected = ""
    val actual   = "".rgb(intValue)
    import extras.core.syntax.string._
    (actual ==== expected)
      .log(s"""${actual.encodeToUnicode} should be """"")
  }

  def testRgbedInt: Property = for {
    rgbInput <- Gens.genRgbIntAndInts.log("(rgbInt, (redInt, greenInt, blueInt))")
    (rgbInt, (redInt, greenInt, blueInt)) = rgbInput
    text <- Gen.string(Gen.alphaNum, Range.linear(1, 30)).log("text")
  } yield {
    import extras.scala.io.syntax.truecolor.rgb._
    val expected = TestTools.toRgbAsciiEsc(redInt, greenInt, blueInt) + text + extras.scala.io.Color.reset.toAnsi
    val actual   = text.rgbed(rgbInt)
    actual ==== expected
  }

  def testRgbedIntLessThan0: Property = for {
    invalidRgbInt <- Gen.int(Range.linear(Int.MinValue, -1)).log("invalidRgbInt")
    text          <- Gen.string(Gen.alphaNum, Range.linear(1, 30)).log("text")
  } yield {
    import extras.scala.io.syntax.truecolor.rgb._
    val expected = TestTools.toRgbAsciiEsc(0, 0, 0) + text + extras.scala.io.Color.reset.toAnsi
    val actual   = text.rgbed(invalidRgbInt)
    actual ==== expected
  }

  def testRgbedIntGreaterThan0xffffff: Property = for {
    invalidRgbInt <- Gen.int(Range.linear(0xffffff + 1, Int.MaxValue)).log("invalidRgbInt")
    text          <- Gen.string(Gen.alphaNum, Range.linear(1, 30)).log("text")
  } yield {
    import extras.scala.io.syntax.truecolor.rgb._
    val expected = TestTools.toRgbAsciiEsc(255, 255, 255) + text + extras.scala.io.Color.reset.toAnsi
    val actual   = text.rgbed(invalidRgbInt)
    actual ==== expected
  }

  def testEmptyStringRgbedInt: Property = for {
    intValue <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("intValue")
  } yield {
    import extras.scala.io.syntax.truecolor.rgb._
    val expected = ""
    val actual   = "".rgbed(intValue)
    import extras.core.syntax.string._
    (actual ==== expected)
      .log(s"""${actual.encodeToUnicode} should be """"")
  }

}
