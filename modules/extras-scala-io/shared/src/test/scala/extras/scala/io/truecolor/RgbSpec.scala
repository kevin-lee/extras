package extras.scala.io.truecolor

import hedgehog._
import hedgehog.runner._

import scala.util.Try

/** @author Kevin Lee
  * @since 2022-06-18
  */
object RgbSpec extends Properties with CrossVersionRgbSpec {
  override def tests: List[Test] = List(
    property("rgb.hasCode == rgb.hasCode", testHashCode),
    property("rgb1.hasCode != rgb2.hasCode", testHashCodeNotEqual),
    property("rgb == rgb", testEquals),
    property("rgb1 != rgb2", testNotEquals),
    property("Rgb.fromInt(valid) should return Right(Rgb)", testFromIntValid),
    property("Rgb.fromInt(invalid) should return Left(error message)", testFromIntInvalid),
    property("Rgb.unsafeFromInt(valid) should return Rgb", testUnsafeFromIntValid),
    property("Rgb.unsafeFromInt(invalid) should throw an exception", testUnsafeFromIntInvalid),
    property("Rgb.fromRgbInts(valid, valid, valid) should return Right(Rgb)", testFromRgbIntsValid),
    property("Rgb.fromRgbInts(one of three invalid) should return Left(error message)", testFromRgbIntsInvalid),
    property("Rgb.UnsafeFromRgbInts(valid, valid, valid) should return Rgb", testUnsafeFromRgbIntsValid),
    property("Rgb.UnsafeFromRgbInts(one of three invalid) should throw an exception", testUnsafeFromRgbIntsInvalid),
    property("Rgb.fromHexString(valid) should return Right(Rgb)", testFromHexStringValid),
    property("Rgb.fromHexString(invalid hex) should return Left(error message)", testFromHexStringInvalidHex),
    property("Rgb.fromHexString(invalid non-hex) should return Left(error message)", testFromHexStringInvalidString),
    property("Rgb.unsafeFromHexString(valid) should return Rgb", testUnsafeFromHexStringValid),
    property("Rgb.unsafeFromHexString(invalid) should return throw an exception", testUnsafeFromHexStringInvalid),
    property("Rgb.unapply(rgb) should match Rgb(r, g, b) case", testUnapply),
    property("Rgb.red, Rgb.green and Rgb.blue should return expected red, green and blue values", testRedGreenBlue),
    property("Rgb.red.toHex should return red in hex", testRedToHex),
    property("Rgb.green.toHex should return green in hex", testGreenToHex),
    property("Rgb.blue.toHex should return blue in hex", testBlueToHex),
    property(
      "Rgb.red(red), Rgb.green(green) and Rgb.blue(blue) should return Rgb with updated red, green and blue values",
      testUpdateRedGreenBlue,
    ),
    property(
      "Rgb.red(n < 0), Rgb.green(n < 0) and Rgb.blue(n < 0) should return Rgb with updated red 0, green 0 and blue 0 values",
      testUpdateRedGreenBlueWithLessThan0,
    ),
    property(
      "Rgb.red(n > 0xffffff), Rgb.green(n > 0xffffff) and Rgb.blue(n > 0xffffff) should return Rgb with updated red 0xffffff, green 0xffffff and blue 0xffffff values",
      testUpdateRedGreenBlueWithGreaterThan0xffffff,
    ),
    property("""Rgb(value).toHex should return value in hex ("ffffff" format)""", testToHex),
    property("""Rgb(value).toHexHtml should return value in hex ("#ffffff" format)""", testToHex),
    property("""Rgb(value).toRgbInts should return value in (red, green, blue): (Int, Int, Int)""", testToRgbInts),
    property("""Rgb(value).toAsciiEsc should return value in colored String with ASCII escape chars""", testToAsciiEsc),
    property(
      """Rgb(value).color(String value) should return value in colored String value with ASCII escape chars""",
      testColor,
    ),
    property(
      """Rgb(value).color("") should return """"",
      testColorEmptyString,
    ),
    property(
      """Rgb(value).colored(String value) should return value in colored String value with ASCII escape chars ending with scala.io.AnsiColor.RESET""",
      testColored,
    ),
    property(
      """Rgb(value).colored("") should return """"",
      testColoredEmptyString,
    ),
  ) ++ corssVersionTests

  def testHashCode: Property = for {
    rgbInt <- Gen.int(Range.linear(0, Rgb.RgbBits)).log("rgbInt")
  } yield {
    val rgb = Rgb.unsafeFromInt(rgbInt)
    Result.all(
      List(
        rgb.hashCode ==== rgb.hashCode,
        rgb.## ==== rgb.##,
        rgb.hashCode ==== rgbInt,
      )
    )
  }

  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  def testHashCodeNotEqual: Property = for {
    rgbInt1 <- Gen.int(Range.linear(0, Rgb.RgbBits)).log("rgbInt1")
    rgbInt2 <- Gen.constant(rgbInt1 ^ Rgb.RgbBits).log("rgbInt2")
  } yield {
    val rgb1 = Rgb.unsafeFromInt(rgbInt1)
    val rgb2 = Rgb.unsafeFromInt(rgbInt2)
    Result.all(
      List(
        Result.diffNamed("rgb1.hashCode != rgb2.hashCode should be true", rgb1.hashCode, rgb2.hashCode)(_ != _),
        Result.diffNamed("rgb1.## != rgb2.## should be true", rgb1.##, rgb2.##)(_ != _),
        Result.diffNamed("rgb1.hashCode != rgbInt2 should be true", rgb1.hashCode, rgbInt2)(_ != _),
        Result.diffNamed("rgb2.hashCode != rgbInt1 should be true", rgb2.hashCode, rgbInt1)(_ != _),
      )
    )
  }

  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  def testEquals: Property = for {
    rgbInt <- Gen.int(Range.linear(0, Rgb.RgbBits)).log("rgbInt")
  } yield {
    val rgb = Rgb.unsafeFromInt(rgbInt)
    Result.diffNamed("rgb == rgb should be true", rgb, rgb)(_ == _)
  }

  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  def testNotEquals: Property = for {
    rgbInt1 <- Gen.int(Range.linear(0, Rgb.RgbBits)).log("rgbInt1")
    rgbInt2 <- Gen.constant(rgbInt1 ^ Rgb.RgbBits).log("rgbInt2")
  } yield {
    val rgb1 = Rgb.unsafeFromInt(rgbInt1)
    val rgb2 = Rgb.unsafeFromInt(rgbInt2)
    Result.diffNamed("rgb1 != rgb2 should be true", rgb1, rgb2)(_ != _)
  }

  def testFromIntValid: Property = for {
    rgbInt <- Gen.int(Range.linear(0, Rgb.RgbBits)).log("rgbInt")
  } yield {
    val expected = Right(Rgb.unsafeFromInt(rgbInt))
    val actual   = Rgb.fromInt(rgbInt)
    actual ==== expected
  }

  def testFromIntInvalid: Property = for {
    invalidRgbInt <- Gen
                       .frequency1(
                         5 -> Gen.int(Range.linear(Rgb.RgbBits + 1, Int.MaxValue)),
                         5 -> Gen.int(Range.linear(Int.MinValue, -1)),
                       )
                       .log("invalidRgbInt")
  } yield {
    val expected = Left(s"Invalid RGB color. Input: ${invalidRgbInt.toString}")
    val actual   = Rgb.fromInt(invalidRgbInt)
    actual ==== expected
  }

  def testUnsafeFromIntValid: Property = for {
    rgbInt <- Gen.int(Range.linear(0, Rgb.RgbBits)).log("rgbInt")
  } yield {
    val expected = rgbInt
    val actual   = Rgb.unsafeFromInt(rgbInt)
    actual.value ==== expected
  }

  @SuppressWarnings(Array("org.wartremover.warts.ToString"))
  def testUnsafeFromIntInvalid: Property = for {
    invalidRgbInt <- Gen
                       .frequency1(
                         5 -> Gen.int(Range.linear(Rgb.RgbBits + 1, Int.MaxValue)),
                         5 -> Gen.int(Range.linear(Int.MinValue, -1)),
                       )
                       .log("invalidRgbInt")
  } yield {
    val expected                       = s"Invalid RGB color. Input: ${invalidRgbInt.toString}"
    val actual: Either[Throwable, Rgb] = Try {
      Rgb.unsafeFromInt(invalidRgbInt)
    } match {
      case scala.util.Success(value) => Right(value)
      case scala.util.Failure(exception) => Left(exception)
    }

    (actual
      .matchPattern {
        case Left(err: RuntimeException) =>
          err.getMessage ==== expected
      })
      .log(raw"""Expected: RuntimeException("$expected") but got ${actual.toString} instead""")
  }

  def testFromRgbIntsValid: Property = for {
    rgbInt <- Gen.int(Range.linear(0, Rgb.RgbBits)).log("rgbInt")
    rInt   <- Gen.constant((rgbInt & Rgb.RedBits) >> 16).log("rInt")
    gInt   <- Gen.constant((rgbInt & Rgb.GreenBits) >> 8).log("gInt")
    bInt   <- Gen.constant(rgbInt & Rgb.BlueBits).log("bInt")
  } yield {
    val expected = Right(Rgb.unsafeFromInt(rgbInt))
    val actual   = Rgb.fromRgbInts(rInt, gInt, bInt)
    actual ==== expected
  }

  def testFromRgbIntsInvalid: Property = for {
    oneOfRgbInt   <- Gen.int(Range.linear(0, Rgb.BlueBits)).log("oneOfRgbInt")
    invalidRgbInt <- Gen
                       .frequency1(
                         5 -> Gen.int(Range.linear(Rgb.RgbBits + 1, Int.MaxValue)),
                         5 -> Gen.int(Range.linear(Int.MinValue, -1)),
                       )
                       .log("invalidRgbInt")
    choice        <- Gen.element1(1, 2, 4).log("choice")
    (r, g, b)          = ((choice & 4) >> 2, (choice & 2) >> 1, choice & 1)
    (rInt, gInt, bInt) = (
                           (invalidRgbInt * r) + ((r ^ 1) * oneOfRgbInt),
                           (invalidRgbInt * g) + ((g ^ 1) * oneOfRgbInt),
                           (invalidRgbInt * b) + ((b ^ 1) * oneOfRgbInt),
                         )
  } yield {
    val invalidColors = List(r, g, b)
      .zip(List("Red", "Green", "Blue"))
      .collect {
        case (1, color) =>
          color
      }
      .mkString("[", ", ", "]")
    val expected      = Left(
      s"Invalid RGB color. Invalid: $invalidColors, Input: (Red: ${rInt.toString}, Green: ${gInt.toString}, Blue: ${bInt.toString})"
    )
    val actual        = Rgb.fromRgbInts(rInt, gInt, bInt)
    actual ==== expected
  }

  def testUnsafeFromRgbIntsValid: Property = for {
    rgbInt <- Gen.int(Range.linear(0, Rgb.RgbBits)).log("rgbInt")
    rInt   <- Gen.constant((rgbInt & Rgb.RedBits) >> 16).log("rInt")
    gInt   <- Gen.constant((rgbInt & Rgb.GreenBits) >> 8).log("gInt")
    bInt   <- Gen.constant(rgbInt & Rgb.BlueBits).log("bInt")
  } yield {
    val expected                       = Right(Rgb.unsafeFromInt(rgbInt))
    val actual: Either[Throwable, Rgb] = Try(Rgb.unsafeFromRgbInts(rInt, gInt, bInt)) match {
      case scala.util.Success(value) => Right(value)
      case scala.util.Failure(exception) => Left(exception)
    }
    actual ==== expected
  }

  @SuppressWarnings(Array("org.wartremover.warts.ToString"))
  def testUnsafeFromRgbIntsInvalid: Property = for {
    oneOfRgbInt   <- Gen.int(Range.linear(0, Rgb.BlueBits)).log("oneOfRgbInt")
    invalidRgbInt <- Gen
                       .frequency1(
                         5 -> Gen.int(Range.linear(Rgb.RgbBits + 1, Int.MaxValue)),
                         5 -> Gen.int(Range.linear(Int.MinValue, -1)),
                       )
                       .log("invalidRgbInt")
    choice        <- Gen.element1(1, 2, 4).log("choice")
    (r, g, b)          = ((choice & 4) >> 2, (choice & 2) >> 1, choice & 1)
    (rInt, gInt, bInt) = (
                           (invalidRgbInt * r) + ((r ^ 1) * oneOfRgbInt),
                           (invalidRgbInt * g) + ((g ^ 1) * oneOfRgbInt),
                           (invalidRgbInt * b) + ((b ^ 1) * oneOfRgbInt),
                         )
  } yield {
    val invalidColors                  = List(r, g, b)
      .zip(List("Red", "Green", "Blue"))
      .collect {
        case (1, color) =>
          color
      }
      .mkString("[", ", ", "]")
    val expected                       =
      s"Invalid RGB color. Invalid: $invalidColors, Input: (Red: ${rInt.toString}, Green: ${gInt.toString}, Blue: ${bInt.toString})"
    val actual: Either[Throwable, Rgb] = Try(Rgb.unsafeFromRgbInts(rInt, gInt, bInt)) match {
      case scala.util.Success(value) => Right(value)
      case scala.util.Failure(exception) => Left(exception)
    }
    (actual
      .matchPattern {
        case Left(err: RuntimeException) =>
          err.getMessage ==== expected
      })
      .log(raw"""Expected: RuntimeException("$expected") but got ${actual.toString} instead""")
  }

  def testFromHexStringValid: Property = for {
    rgbInt    <- Gens.genValidRgbInt.log("rgbInt")
    hexString <- Gens.toHexString(rgbInt).log("hexString")
  } yield {
    val expected = Right(Rgb.unsafeFromInt(rgbInt))
    val actual   = Rgb.fromHexString(hexString)
    actual ==== expected
  }

  def testFromHexStringInvalidHex: Property = for {
    invalidHexString <- Gens.genInvalidRgbHexString.log("invalidHexString")
  } yield {
    val expected = s"Invalid color hex: $invalidHexString"
    Rgb.fromHexString(invalidHexString) match {
      case Left(actual) =>
        Result.diff(actual, expected)(_.startsWith(_))

      case Right(r) =>
        Result
          .failure
          .log(
            raw"""It should have failed with the error message starting with $expected
                 |  ↪️ but it succeeded and returned ${r.toString} instead
                 |""".stripMargin
          )
    }
  }

  def testFromHexStringInvalidString: Property = for {
    invalidHexString <- Gen
                          .string(Gen.element1('%', '@', '!', '*', '(', ')'), Range.linear(2, 10))
                          .flatMap { head =>
                            Gen.string(Gen.unicode, Range.linear(6, 50)).map(head + _)
                          }
                          .log("invalidHexString")
  } yield {
    val expected = raw"""Invalid color hex: $invalidHexString, Error: For input string: "${invalidHexString.take(2)}""""

    Rgb.fromHexString(invalidHexString) match {
      case Left(actual) =>
        Result.diff(actual, expected)(_.startsWith(_))
      case Right(r) =>
        Result.failure.log(s"Left(error message) was expected but it returned Right(${r.toString}) instead.")
    }
  }

  def testUnsafeFromHexStringValid: Property = for {
    rgbInt    <- Gens.genValidRgbInt.log("rgbInt")
    hexString <- Gens.toHexString(rgbInt).log("hexString")
  } yield {
    val expected = Rgb.unsafeFromInt(rgbInt)
    val actual   = Rgb.unsafeFromHexString(hexString)
    actual ==== expected
  }

  @SuppressWarnings(Array("org.wartremover.warts.ToString"))
  def testUnsafeFromHexStringInvalid: Property = for {
    invalidHexString <- Gens.genInvalidRgbHexString.log("invalidHexString")
  } yield {
    val expected                       = s"Invalid color hex: $invalidHexString"
    val actual: Either[Throwable, Rgb] = Try(Rgb.unsafeFromHexString(invalidHexString)) match {
      case scala.util.Success(value) => Right(value)
      case scala.util.Failure(exception) => Left(exception)
    }
    (actual
      .matchPattern {
        case Left(err: RuntimeException) =>
          err.getMessage ==== expected
      })
      .log(raw"""Expected: RuntimeException("$expected") but got ${actual.toString} instead""")
  }

  def testUnapply: Property = for {
    rgbInput <- Gens.genRgbIntAndInts.log("(rgbInt, (rInt, gInt, bInt))")
    (rgbInt, (expectedRed, expectedGreen, expectedBlue)) = rgbInput
  } yield {
    val rgb = Rgb.unsafeFromInt(rgbInt)
    rgb match {
      case Rgb(red, green, blue) =>
        Result.all(
          List(
            (red.value ==== expectedRed).log("red.value ==== expectedRed"),
            (green.value ==== expectedGreen).log("green.value ==== expectedGreen"),
            (blue.value ==== expectedBlue).log("blue.value ==== expectedBlue"),
          )
        )
    }
  }

  def testRedGreenBlue: Property = for {
    rgbInput <- Gens.genRgbIntAndInts.log("(rgbInt, (rInt, gInt, bInt))")
    (rgbInt, (expectedRed, expectedGreen, expectedBlue)) = rgbInput
  } yield {
    val rgb = Rgb.unsafeFromInt(rgbInt)
    Result.all(
      List(
        (rgb.red.value ==== expectedRed).log("rgb.red.value ==== expectedRed"),
        (rgb.green.value ==== expectedGreen).log("rgb.green.value ==== expectedGreen"),
        (rgb.blue.value ==== expectedBlue).log("rgb.blue.value ==== expectedBlue"),
      )
    )
  }

  def testRedToHex: Property = for {
    rgbInput <- Gens.genRgbIntAndInts.log("(rgbInt, (rInt, gInt, bInt))")
    (rgbInt, (expectedRed, _, _)) = rgbInput
  } yield {
    val rgb      = Rgb.unsafeFromInt(rgbInt)
    val expected = TestTools.toHex(expectedRed, 2)
    val actual   = rgb.red.toHex
    actual ==== expected
  }

  def testGreenToHex: Property = for {
    rgbInput <- Gens.genRgbIntAndInts.log("(rgbInt, (rInt, gInt, bInt))")
    (rgbInt, (_, expectedGreen, _)) = rgbInput
  } yield {
    val rgb      = Rgb.unsafeFromInt(rgbInt)
    val expected = TestTools.toHex(expectedGreen, 2)
    val actual   = rgb.green.toHex
    actual ==== expected
  }

  def testBlueToHex: Property = for {
    rgbInput <- Gens.genRgbIntAndInts.log("(rgbInt, (rInt, gInt, bInt))")
    (rgbInt, (_, _, expectedBlue)) = rgbInput
  } yield {
    val rgb      = Rgb.unsafeFromInt(rgbInt)
    val expected = TestTools.toHex(expectedBlue, 2)
    val actual   = rgb.blue.toHex
    actual ==== expected
  }

  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  def testUpdateRedGreenBlue: Property = for {
    rgbInput <- Gens.genRgbIntAndInts.log("(rgbInt, (rInt, gInt, bInt))")
    (rgbInt, (oldRed, oldGreen, oldBlue)) = rgbInput
    newRgb <- Gen
                .constant(
                  (
                    oldRed ^ 0xff,
                    oldGreen ^ 0xff,
                    oldBlue ^ 0xff,
                  )
                )
                .log("(newRed, newGreen, newBlue)")
    (newRed, newGreen, newBlue) = newRgb
  } yield {
    val rgb    = Rgb.unsafeFromInt(rgbInt)
    val actual = rgb
      .red(newRed)
      .green(newGreen)
      .blue(newBlue)

    Result.all(
      List(
        (actual.red.value ==== newRed).log("actual.red.value ==== newRed"),
        (actual.green.value ==== newGreen).log("actual.green.value ==== newGreen"),
        (actual.blue.value ==== newBlue).log("actual.blue.value ==== newBlue"),
        Result.diffNamed("actual.red.value != oldRed", actual.red.value, oldRed)(_ != _),
        Result.diffNamed("actual.green.value != oldGreen", actual.green.value, oldGreen)(_ != _),
        Result.diffNamed("actual.blue.value != oldBlue", actual.blue.value, oldBlue)(_ != _),
        Result.diffNamed("actual.value == (rgbInt ^ 0xffffff)", actual.value, rgbInt ^ 0xffffff)(_ == _),
        Result.diffNamed("actual.value != rgbInt", actual.value, rgbInt)(_ != _),
      )
    )
  }

  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  def testUpdateRedGreenBlueWithLessThan0: Property = for {
    rgbInput <- Gens.genRgbIntAndInts.log("(rgbInt, (rInt, gInt, bInt))")
    (rgbInt, (oldRed, oldGreen, oldBlue)) = rgbInput
    newRed   <- Gen.int(Range.linear(Int.MinValue, -1)).log("newRed")
    newGreen <- Gen.int(Range.linear(Int.MinValue, -1)).log("newGreen")
    newBlue  <- Gen.int(Range.linear(Int.MinValue, -1)).log("newBlue")
  } yield {
    val rgb    = Rgb.unsafeFromInt(rgbInt)
    val actual = rgb
      .red(newRed)
      .green(newGreen)
      .blue(newBlue)

    Result.all(
      List(
        (actual.red.value ==== 0x00).log("actual.red.value ==== 0x00"),
        (actual.green.value ==== 0x00).log("actual.green.value ==== 0x00"),
        (actual.blue.value ==== 0x00).log("actual.blue.value ==== 0x00"),
        Result.diffNamed("oldRed != newRed", oldRed, newRed)(_ != _),
        Result.diffNamed("oldGreen != newGreen", oldGreen, newGreen)(_ != _),
        Result.diffNamed("oldBlue != newBlue", oldBlue, newBlue)(_ != _),
        Result.diffNamed("actual.red.value != newRed", actual.red.value, newRed)(_ != _),
        Result.diffNamed("actual.green.value != newGreen", actual.green.value, newGreen)(_ != _),
        Result.diffNamed("actual.blue.value != newBlue", actual.blue.value, newBlue)(_ != _),
        Result.diffNamed("actual.value == 0x000000", actual.value, 0x000000)(_ == _),
        Result.diffNamed("actual.value != rgbInt", actual.value, rgbInt)(_ != _),
      )
    )
  }

  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  def testUpdateRedGreenBlueWithGreaterThan0xffffff: Property = for {
    rgbInput <- Gens.genRgbIntAndInts.log("(rgbInt, (rInt, gInt, bInt))")
    (rgbInt, (oldRed, oldGreen, oldBlue)) = rgbInput
    newRed   <- Gen.int(Range.linear(0xffffff + 1, Int.MaxValue)).log("newRed")
    newGreen <- Gen.int(Range.linear(0xffffff + 1, Int.MaxValue)).log("newGreen")
    newBlue  <- Gen.int(Range.linear(0xffffff + 1, Int.MaxValue)).log("newBlue")
  } yield {
    val rgb    = Rgb.unsafeFromInt(rgbInt)
    val actual = rgb
      .red(newRed)
      .green(newGreen)
      .blue(newBlue)

    Result.all(
      List(
        (actual.red.value ==== 0xff).log("actual.red.value ==== 0xff"),
        (actual.green.value ==== 0xff).log("actual.green.value ==== 0xff"),
        (actual.blue.value ==== 0xff).log("actual.blue.value ==== 0xff"),
        Result.diffNamed("oldRed != newRed", oldRed, newRed)(_ != _),
        Result.diffNamed("oldGreen != newGreen", oldGreen, newGreen)(_ != _),
        Result.diffNamed("oldBlue != newBlue", oldBlue, newBlue)(_ != _),
        Result.diffNamed("actual.red.value != newRed", actual.red.value, newRed)(_ != _),
        Result.diffNamed("actual.green.value != newGreen", actual.green.value, newGreen)(_ != _),
        Result.diffNamed("actual.blue.value != newBlue", actual.blue.value, newBlue)(_ != _),
        Result.diffNamed("actual.value == 0xffffff", actual.value, 0xffffff)(_ == _),
        Result.diffNamed("actual.value != rgbInt", actual.value, rgbInt)(_ != _),
      )
    )
  }

  def testToHex: Property = for {
    rgbInt   <- Gens.genValidRgbInt.log("rgbInt")
    expected <- Gens.toHexString(rgbInt).log("expected")
  } yield {
    val actual = Rgb.unsafeFromInt(rgbInt).toHex
    actual ==== expected
  }

  def testToHexHtml: Property = for {
    rgbInt   <- Gens.genValidRgbInt.log("rgbInt")
    expected <- Gens.toHexString(rgbInt).map("#" + _).log("expected")
  } yield {
    val actual = Rgb.unsafeFromInt(rgbInt).toHexHtml
    actual ==== expected
  }

  def testToRgbInts: Property = for {
    rgbInput <-
      Gens.genRgbIntAndInts.log("(rgbInt, (expectedRed, expectedGreen, expectedBlue))")
    (rgbInt, (expectedRed, expectedGreen, expectedBlue)) = rgbInput
  } yield {
    val expected = (expectedRed, expectedGreen, expectedBlue)
    val actual   = Rgb.unsafeFromInt(rgbInt).toRgbInts
    actual ==== expected
  }

  def testToAsciiEsc: Property = for {
    rgbInput <-
      Gens.genRgbIntAndInts.log("(rgbInt, (expectedRed, expectedGreen, expectedBlue))")
    (rgbInt, (expectedRed, expectedGreen, expectedBlue)) = rgbInput
  } yield {
    val expected = s"\u001b[38;2;${expectedRed.toString};${expectedGreen.toString};${expectedBlue.toString}m"
    val actual   = Rgb.unsafeFromInt(rgbInt).toAsciiEsc
    actual ==== expected
  }

  def testColor: Property = for {
    rgbInput <-
      Gens.genRgbIntAndInts.log("(rgbInt, (expectedRed, expectedGreen, expectedBlue))")
    (rgbInt, (expectedRed, expectedGreen, expectedBlue)) = rgbInput
    text <- Gen.string(Gen.alphaNum, Range.linear(1, 30)).log("text")
  } yield {
    val expected = TestTools.toRgbAsciiEsc(expectedRed, expectedGreen, expectedBlue) + text
    val actual   = Rgb.unsafeFromInt(rgbInt).color(text)
    actual ==== expected
  }

  def testColorEmptyString: Property = for {
    rgbInput <-
      Gens.genRgbIntAndInts.log("(rgbInt, (expectedRed, expectedGreen, expectedBlue))")
    (rgbInt, _) = rgbInput
  } yield {
    val expected = ""
    val actual   = Rgb.unsafeFromInt(rgbInt).color("")
    import extras.core.syntax.string._
    (actual ==== expected)
      .log(s"""${actual.encodeToUnicode} should be """"")
  }

  def testColored: Property = for {
    rgbInput <-
      Gens.genRgbIntAndInts.log("(rgbInt, (expectedRed, expectedGreen, expectedBlue))")
    (rgbInt, (expectedRed, expectedGreen, expectedBlue)) = rgbInput
    text <- Gen.string(Gen.alphaNum, Range.linear(1, 30)).log("text")
  } yield {
    val expected =
      TestTools.toRgbAsciiEsc(expectedRed, expectedGreen, expectedBlue) + text + extras
        .scala
        .io
        .Color
        .reset
        .toAnsi
    val actual   = Rgb.unsafeFromInt(rgbInt).colored(text)
    actual ==== expected
  }

  def testColoredEmptyString: Property = for {
    rgbInput <-
      Gens.genRgbIntAndInts.log("(rgbInt, (expectedRed, expectedGreen, expectedBlue))")
    (rgbInt, _) = rgbInput
  } yield {
    val expected = ""
    val actual   = Rgb.unsafeFromInt(rgbInt).colored("")
    import extras.core.syntax.string._
    (actual ==== expected)
      .log(s"""${actual.encodeToUnicode} should be """"")
  }

}
