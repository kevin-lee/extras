package extras.scala.io.syntax.truecolor

import extras.scala.io.syntax.truecolor.RgbSyntax.RgbSyntaxOps
import extras.scala.io.truecolor.Rgb

/** @author Kevin Lee
  * @since 2022-06-18
  */
trait RgbSyntax {
  implicit def rgbSyntax(s: String): RgbSyntaxOps = new RgbSyntaxOps(s)
}

object RgbSyntax {
  class RgbSyntaxOps(private val s: String) extends AnyVal {
    private def toValidRgbInt(rgb: Int): Int =
      if (rgb < 0)
        0
      else if (rgb > Rgb.RgbBits)
        Rgb.RgbBits
      else
        rgb

    def rgb(rgb: Rgb): String = rgb.color(s)
    @SuppressWarnings(Array("org.wartremover.warts.Overloading"))
    def rgb(rgb: Int): String = Rgb.unsafeFromInt(toValidRgbInt(rgb)).color(s)

    def rgbed(rgb: Rgb): String = rgb.colored(s)
    @SuppressWarnings(Array("org.wartremover.warts.Overloading"))
    def rgbed(rgb: Int): String = Rgb.unsafeFromInt(toValidRgbInt(rgb)).colored(s)
  }
}
