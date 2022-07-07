package extras.scala.io.truecolor

import Data.{expectedRainbowAsciiArt, rainbowAsciiArt}
import hedgehog._
import hedgehog.runner._

import scala.util.Try

/** @author Kevin Lee
  * @since 2022-06-13
  */
object RainbowSpec extends Properties {
  override def tests: List[Test] = List(
    example("test Rainbow.rainbow(Big RAINBOW ASCII art) should return rainbowed String", testRainbowAsciiArt),
    example("test Rainbow.rainbows(Seq[String]) should return rainbowed Seq[String]", testRainbowAsciiArtWithSeq),
    property("test Rainbow.rainbow(String) should return rainbowed String", testRainbow),
    property("test Rainbow.rainbow(String) should return rainbowed String2", testRainbow2),
    example("""test Rainbow.rainbow("") should return """"", testRainbowEmptyString),
    property("test Rainbow.rainbowHtml(String) should return rainbowed String in HTML", testRainbowHtml),
    example("""test Rainbow.rainbowHtml("") should return """"", testRainbowHtmlEmptyString),
    property("test Rainbow.Index.value should return corresponding Int value", testRainbowIndexValue),
    property("test Rainbow.unsafeFromInt(valid Int) should return Rainbow.Index", testRainbowUnsafeFromIntValid),
    property("test Rainbow.unsafeFromInt(invalid Int) should throw exception", testRainbowUnsafeFromIntInvalid)
  )

  def testRainbowAsciiArt: Result = {
    val actual = rainbowAsciiArt.map(Rainbow.rainbow).mkString("\n")
    println(actual)
    actual ==== expectedRainbowAsciiArt
  }

  def testRainbowAsciiArtWithSeq: Result = {
    val actual = Rainbow.rainbows(rainbowAsciiArt).mkString("\n")
    println(actual)
    actual ==== expectedRainbowAsciiArt
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
    val actual = Rainbow.rainbow(input)
    actual ==== expected
  }

  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  def testRainbow2: Property = for {
    s <- Gen.string(Gen.alphaNum, Range.linear(1, 50)).log("s")
  } yield {
    val actual = Rainbow.rainbow(s)

    val assertList      = List(
      Result.assert(actual.contains(Rainbow.Red.toAsciiEsc)).log("Should contains Red"),
      Result.assert(actual.contains(Rainbow.Orange.toAsciiEsc)).log("Should contains Orange"),
      Result.assert(actual.contains(Rainbow.Yellow.toAsciiEsc)).log("Should contains Yellow"),
      Result.assert(actual.contains(Rainbow.Green.toAsciiEsc)).log("Should contains Green"),
      Result.assert(actual.contains(Rainbow.Blue.toAsciiEsc)).log("Should contains Blue"),
      Result.assert(actual.contains(Rainbow.Indigo.toAsciiEsc)).log("Should contains Indigo"),
      Result.assert(actual.contains(Rainbow.Violet.toAsciiEsc)).log("Should contains Violet")
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
          .log(s"result - rainbow colors should be the same as input: withoutRainbow: $withoutRainbow, input: $s")
      ) ++ assertListToUse
    )

  }

  def testRainbowEmptyString: Result = {
    val expected = ""
    val actual   = Rainbow.rainbow("")
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
            .fill(7)(raw"""<span style="color: %s;">%s</span>""")
            .mkString
            .format(
              List(
                Rainbow.Red,
                Rainbow.Orange,
                Rainbow.Yellow,
                Rainbow.Green,
                Rainbow.Blue,
                Rainbow.Indigo,
                Rainbow.Violet
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
                    s7
                  )
                )
                .flatMap { case (color, text) => List(color, text) }: _*
            )
        )
        .log("expected")
  } yield {
    val actual = Rainbow.rainbowHtml(input)
    actual ==== expected
  }

  def testRainbowHtmlEmptyString: Result = {
    val expected = ""
    val actual   = Rainbow.rainbowHtml("")
    import extras.core.syntax.string._
    (actual ==== expected)
      .log(s"""${actual.encodeToUnicode} should be """"")
  }

  def testRainbowIndexValue: Property = for {
    indexAndExpected <- Gen
                          .element1(
                            (Rainbow.Index.zero, 0),
                            (Rainbow.Index.one, 1),
                            (Rainbow.Index.two, 2),
                            (Rainbow.Index.three, 3),
                            (Rainbow.Index.four, 4),
                            (Rainbow.Index.five, 5),
                            (Rainbow.Index.six, 6)
                          )
                          .log("(index, expected)")
    (index, expected) = indexAndExpected
  } yield {
    val actual = index.value
    actual ==== expected
  }

  def testRainbowUnsafeFromIntValid: Property = for {
    indexIntAndExpected <- Gen
                             .element1(
                               (0, Rainbow.Index.zero),
                               (1, Rainbow.Index.one),
                               (2, Rainbow.Index.two),
                               (3, Rainbow.Index.three),
                               (4, Rainbow.Index.four),
                               (5, Rainbow.Index.five),
                               (6, Rainbow.Index.six)
                             )
                             .log("(indexInt, expected)")
    (indexInt, expected) = indexIntAndExpected
  } yield {
    val actual = Rainbow.Index.unsafeFromInt(indexInt)
    actual ==== expected
  }

  @SuppressWarnings(Array("org.wartremover.warts.ToString"))
  def testRainbowUnsafeFromIntInvalid: Property = for {
    invalidIndexInt <- Gen
                         .choice1(Gen.int(Range.linear(Int.MinValue, -1)), Gen.int(Range.linear(7, Int.MaxValue)))
                         .log("(indexInt, expected)")
  } yield {
    val expected = s"ColorIndex must be Int between 0 and 6 (min: 0 / max: 6). Input: ${invalidIndexInt.toString}"
    val result   = Try(Rainbow.Index.unsafeFromInt(invalidIndexInt))
    (result
      .matchPattern {
        case scala.util.Failure(err: RuntimeException) =>
          val actual = err.getMessage
          actual ==== expected
      })
      .log(
        raw"""Expected: RuntimeException("$expected") is thrown but get something else instead. ${result.toString}"""
      )
  }

}
