package extras.scala.io.truecolor

import hedgehog._

/** @author Kevin Lee
  * @since 2022-06-18
  */
object Gens {
  def genValidRgbInt: Gen[Int] =
    Gen.int(Range.linear(0, 0xffffff))

  def genInvalidRgbInt: Gen[Int] =
    Gen
      .frequency1(
        5 -> Gen.int(Range.linear(Rgb.RgbBits + 1, Int.MaxValue)),
        5 -> Gen.int(Range.linear(Int.MinValue, -1))
      )

  def genRgbIntAndInts: Gen[(Int, (Int, Int, Int))] = for {
    rgbInt <- genValidRgbInt
    rInt   <- Gen.constant((rgbInt & Rgb.RedBits) >> 16)
    gInt   <- Gen.constant((rgbInt & Rgb.GreenBits) >> 8)
    bInt   <- Gen.constant(rgbInt & Rgb.BlueBits)
  } yield (rgbInt, (rInt, gInt, bInt))

  def genRgb: Gen[Rgb] =
    Gen
      .int(Range.linear(0, 0xffffff))
      .map(Rgb.unsafeFromInt)

  def toHexString(n: Int): Gen[String] = {
    if (n > 0xffffff)
      Gen.constant(n.toHexString)
    else
      Gen.constant("%06x".format(n))
  }

  def genValidRgbHexString: Gen[String] =
    genValidRgbInt.flatMap(Gens.toHexString)

  def genInvalidRgbHexString: Gen[String] =
    Gen
      .int(Range.linear(Rgb.RgbBits + 1, Int.MaxValue))
      .flatMap(Gens.toHexString)

}
