package extras.scala.io.truecolor

import hedgehog.runner._
import hedgehog._

/** @author Kevin Lee
  * @since 2022-06-19
  */
trait CrossVersionRgbSpec {

  protected def corssVersionTests: List[Test] = List(
    property("rgb1 != non-Rgb", testNotEqualsWithDifferentType)
  )

  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  def testNotEqualsWithDifferentType: Property = for {
    rgbInt1 <- Gen.int(Range.linear(0, Rgb.RgbBits)).log("rgbInt1")
    nonRgb  <- Gen.string(Gen.unicode, Range.linear(1, 20)).log("nonRgb")
  } yield {
    val rgb = Rgb.unsafeFromInt(rgbInt1)
    Result.diffNamed("rgb != nonRgb should be true", rgb, nonRgb)(_ != _)
  }

}
