package extras.scala.io.syntax.truecolor

import extras.scala.io.truecolor.Rainbow
import hedgehog._
import hedgehog.runner._

import extras.scala.io.truecolor.Data.{expectedRainbowAsciiArt, rainbowAsciiArt}

/** @author Kevin Lee
  * @since 2022-06-19
  */
object RainbowSyntaxSpec extends Properties {
  override def tests: List[Prop] = List(
    example("test List[String].map(_.rainbowed) should return rainbowed List[String]", testRainbowAsciiArt),
    example("test Seq[String].rainbowed should return rainbowed Seq[String]", testRainbowAsciiArtWithSeq),
    property("test String.rainbowed should return rainbowed String", testRainbow),
    property("test String.rainbowed should return rainbowed String2", testRainbow2),
    example("""test "".rainbowed should return rainbowed """"", testEmptyStringRainbowed),
    property("test String.rainbowedHtml should return rainbowed String in HTML", testRainbowHtml),
    example("""test "".rainbowedHtml should return """"", testEmptyStringRainbowedHtml),
  )

  def testRainbowAsciiArt: Result = {
    import extras.scala.io.syntax.truecolor.rainbow._

    val expected = expectedRainbowAsciiArt
    val actual   = rainbowAsciiArt.map(_.rainbowed).mkString("\n")
    println(actual)

    actual ==== expected
  }

  def testRainbowAsciiArtWithSeq: Result = {
    import extras.scala.io.syntax.truecolor.rainbow._

    val expected = expectedRainbowAsciiArt
    val actual   = rainbowAsciiArt.rainbowed.mkString("\n")
    println(actual)

    actual ==== expected
  }

  def testRainbow: Property = for {
    s1       <- Gen.string(Gen.alphaNum, Range.singleton(3)).log("s1")
    s2       <- Gen.string(Gen.alphaNum, Range.singleton(3)).log("s2")
    s3       <- Gen.string(Gen.alphaNum, Range.singleton(3)).log("s3")
    s4       <- Gen.string(Gen.alphaNum, Range.singleton(3)).log("s4")
    s5       <- Gen.string(Gen.alphaNum, Range.singleton(3)).log("s5")
    s6       <- Gen.string(Gen.alphaNum, Range.singleton(3)).log("s6")
    s7       <- Gen.string(Gen.alphaNum, Range.singleton(3)).log("s7")
    input    <- Gen.constant(s"$s1$s2$s3$s4$s5$s6$s7").log("input")
    expected <-
      Gen
        .constant(
          s"${Rainbow.Red.toAsciiEsc}$s1${Rainbow.Orange.toAsciiEsc}$s2${Rainbow.Yellow.toAsciiEsc}$s3" +
            s"${Rainbow.Green.toAsciiEsc}$s4${Rainbow.Blue.toAsciiEsc}$s5${Rainbow.Indigo.toAsciiEsc}$s6${Rainbow.Violet.toAsciiEsc}$s7" +
            s"${extras.scala.io.Color.reset.toAnsi}"
        )
        .log("expected")
  } yield {
    import extras.scala.io.syntax.truecolor.rainbow._
    val actual = input.rainbowed
    actual ==== expected
  }

  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  def testRainbow2: Property = for {
    s <- Gen.string(Gen.alphaNum, Range.linear(1, 50)).log("s")
  } yield {
    import extras.scala.io.syntax.truecolor.rainbow._
    val actual = s.rainbowed

    val assertList      = List(
      Result.assert(actual.contains(Rainbow.Red.toAsciiEsc)).log("Should contains Red"),
      Result.assert(actual.contains(Rainbow.Orange.toAsciiEsc)).log("Should contains Orange"),
      Result.assert(actual.contains(Rainbow.Yellow.toAsciiEsc)).log("Should contains Yellow"),
      Result.assert(actual.contains(Rainbow.Green.toAsciiEsc)).log("Should contains Green"),
      Result.assert(actual.contains(Rainbow.Blue.toAsciiEsc)).log("Should contains Blue"),
      Result.assert(actual.contains(Rainbow.Indigo.toAsciiEsc)).log("Should contains Indigo"),
      Result.assert(actual.contains(Rainbow.Violet.toAsciiEsc)).log("Should contains Violet"),
    )
    val length          = s.length
    val assertListToUse = if (length < 7) assertList.take(length) else assertList

    val withoutRainbow = actual
      .replace(Rainbow.Red.toAsciiEsc, "")
      .replace(Rainbow.Orange.toAsciiEsc, "")
      .replace(Rainbow.Yellow.toAsciiEsc, "")
      .replace(Rainbow.Green.toAsciiEsc, "")
      .replace(Rainbow.Blue.toAsciiEsc, "")
      .replace(Rainbow.Indigo.toAsciiEsc, "")
      .replace(Rainbow.Violet.toAsciiEsc, "")
      .replace(extras.scala.io.Color.Reset.toAnsi, "")

    Result.all(
      List(
        Result
          .diffNamed("actual must be not the same as the input", actual, s)(_ != _)
          .log(s"actual: $actual / input: $s"),
        (withoutRainbow ==== s)
          .log(s"result - rainbow colors should be the same as input: withoutRainbow: $withoutRainbow, input: $s"),
      ) ++ assertListToUse
    )

  }

  def testEmptyStringRainbowed: Result = {
    import extras.scala.io.syntax.truecolor.rainbow._
    val expected = ""
    val actual   = "".rainbowed
    import extras.core.syntax.string._
    (actual ==== expected)
      .log(s"""${actual.encodeToUnicode} should be """"")
  }

  def testRainbowHtml: Property = for {
    s1       <- Gen.string(Gen.alphaNum, Range.singleton(3)).log("s1")
    s2       <- Gen.string(Gen.alphaNum, Range.singleton(3)).log("s2")
    s3       <- Gen.string(Gen.alphaNum, Range.singleton(3)).log("s3")
    s4       <- Gen.string(Gen.alphaNum, Range.singleton(3)).log("s4")
    s5       <- Gen.string(Gen.alphaNum, Range.singleton(3)).log("s5")
    s6       <- Gen.string(Gen.alphaNum, Range.singleton(3)).log("s6")
    s7       <- Gen.string(Gen.alphaNum, Range.singleton(3)).log("s7")
    input    <- Gen.constant(s"$s1$s2$s3$s4$s5$s6$s7").log("input")
    expected <-
      Gen
        .constant(
          List
            .fill(7)("""<span style="color: %s;">%s</span>""")
            .mkString
            .format(
              List(
                Rainbow.Red,
                Rainbow.Orange,
                Rainbow.Yellow,
                Rainbow.Green,
                Rainbow.Blue,
                Rainbow.Indigo,
                Rainbow.Violet,
              )
                .map(_.toHexHtml)
                .zip(
                  List(
                    s1,
                    s2,
                    s3,
                    s4,
                    s5,
                    s6,
                    s7,
                  )
                )
                .flatMap { case (color, text) => List(color, text) }: _*
            )
        )
        .log("expected")
  } yield {
    import extras.scala.io.syntax.truecolor.rainbow._
    val actual = input.rainbowedHtml
    actual ==== expected
  }

  def testEmptyStringRainbowedHtml: Result = {
    import extras.scala.io.syntax.truecolor.rainbow._
    val expected = ""
    val actual   = "".rainbowedHtml
    import extras.core.syntax.string._
    (actual ==== expected)
      .log(s"""${actual.encodeToUnicode} should be """"")
  }

}
