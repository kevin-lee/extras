package extras.scala.io.truecolor

import scala.util.Try

/** @author Kevin Lee
  * @since 2022-06-13
  */
final class Rgb private (val value: Int) derives CanEqual {

  import compiletime.asMatchable

  override def hashCode(): Int = value

  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  override def equals(that: Any): Boolean = that.asMatchable match {
    case thatRgb: Rgb =>
      this.value == thatRgb.value
    case _ =>
      false
  }

  override def toString: String =
    s"Rgb(${this.red.value.toString}, ${this.green.value.toString}, ${this.blue.value.toString})"
}
object Rgb {
  val RgbBits: Int   = 0xffffff
  val RedBits: Int   = 0xff << 16
  val GreenBits: Int = 0xff << 8
  val BlueBits: Int  = 0xff

  type Red = Red.Red
  object Red {
    opaque type Red = Int
    def apply(red: Int): Red = red

    given redCanEqual: CanEqual[Red, Red] = CanEqual.derived

    extension (red: Red) {
      def value: Int    = red
      def toHex: String = "%02x".format(value)
    }
  }

  type Green = Green.Green
  object Green {
    opaque type Green = Int
    def apply(green: Int): Green = green

    given greenCanEqual: CanEqual[Green, Green] = CanEqual.derived

    extension (green: Green) {
      def value: Int    = green
      def toHex: String = "%02x".format(value)
    }
  }

  type Blue = Blue.Blue
  object Blue {
    opaque type Blue = Int
    def apply(blue: Int): Blue = blue

    given blueCanEqual: CanEqual[Blue, Blue] = CanEqual.derived

    extension (blue: Blue) {
      def value: Int    = blue
      def toHex: String = "%02x".format(value)
    }
  }

  inline def apply(rgb: Int): Rgb =
    inline if rgb >= 0x000000 && rgb <= 0xffffff
    then unsafeFromInt(rgb)
    else
      compiletime.error(
        "Invalid RGB color. Valid Range(" +
          compiletime.codeOf(0x000000) + " - " + compiletime.codeOf(0xffffff) +
          "), Input: " + compiletime.codeOf(rgb)
      )

  def fromInt(rgb: Int): Either[String, Rgb] =
    if (rgb >= 0 && rgb <= RgbBits)
      Right(new Rgb(rgb))
    else
      Left(s"Invalid RGB color. Input: ${rgb.toString}")

  def unsafeFromInt(rgb: Int): Rgb =
    fromInt(rgb).fold(err => sys.error(err), identity)

  def fromRgbInts(red: Int, green: Int, blue: Int): Either[String, Rgb] = {
    val redIsValid   = red >= 0 && red <= 255
    val greenIsValid = green >= 0 && green <= 255
    val blueIsValid  = blue >= 0 && blue <= 255
    if (redIsValid && greenIsValid && blueIsValid) {
      Right(new Rgb((red << 16) + (green << 8) + blue))
    } else {
      val invalidList = List(
        Some(redIsValid).filterNot(identity).map(_ => "Red"),
        Some(greenIsValid).filterNot(identity).map(_ => "Green"),
        Some(blueIsValid).filterNot(identity).map(_ => "Blue")
      ).flatten
      Left(
        s"Invalid RGB color. Invalid: [${invalidList.mkString(", ")}]," +
          s" Input: (Red: ${red.toString}, Green: ${green.toString}, Blue: ${blue.toString})"
      )
    }
  }

  @SuppressWarnings(Array("org.wartremover.warts.Throw"))
  def unsafeFromRgbInts(red: Int, green: Int, blue: Int): Rgb =
    fromRgbInts(red, green, blue).fold(err => sys.error(err), identity)

  def fromHexString(hex: String): Either[String, Rgb] =
    Try(
      hex
        .stripPrefix("#")
        .sliding(2, 2)
        .toList
        .map(Integer.parseInt(_, 16))
    )
      .toEither
      .left
      .map { err =>
        s"Invalid color hex: $hex, Error: ${err.getMessage}"
      }
      .flatMap {
        case List(r, g, b) =>
          fromRgbInts(r, g, b)
        case _ =>
          Left(s"Invalid color hex: $hex")
      }

  @SuppressWarnings(Array("org.wartremover.warts.Throw"))
  def unsafeFromHexString(hex: String): Rgb =
    fromHexString(hex).fold(err => sys.error(err), identity)

  def unapply(rgb: Rgb): Option[(Red, Green, Blue)] = Some((rgb.red, rgb.green, rgb.blue))

  extension (rgb: Rgb) {
    def red: Rgb.Red     = Rgb.Red((rgb.value & Rgb.RedBits) >> 16)
    def green: Rgb.Green = Rgb.Green((rgb.value & Rgb.GreenBits) >> 8)
    def blue: Rgb.Blue   = Rgb.Blue(rgb.value & Rgb.BlueBits)

    private def toValidHex(n: Int): Int =
      if (n < 0)
        0
      else if (n > 255)
        255
      else
        n

    def red(value: Int): Rgb   = {
      val onlyValidBits = (toValidHex(value) << 16) & RedBits
      val gb            = rgb.value & (GreenBits + BlueBits)
      Rgb.unsafeFromInt(onlyValidBits + gb)
    }
    def green(value: Int): Rgb = {
      val onlyValidBits = (toValidHex(value) << 8) & GreenBits
      val rb            = rgb.value & (RedBits + BlueBits)
      Rgb.unsafeFromInt(onlyValidBits + rb)
    }
    def blue(value: Int): Rgb  = {
      val onlyValidBits = toValidHex(value) & BlueBits
      val rg            = rgb.value & (RedBits + GreenBits)
      Rgb.unsafeFromInt(onlyValidBits + rg)
    }

    def toHex: String              = "%06x".format(rgb.value)
    def toHexHtml: String          = s"#$toHex"
    def toRgbInts: (Int, Int, Int) = (rgb.red.value, rgb.green.value, rgb.blue.value)
    def toAsciiEsc: String         =
      s"\u001b[38;2;${rgb.red.value.toString};${rgb.green.value.toString};${rgb.blue.value.toString}m"

    def color(s: String): String = toAsciiEsc + s

    def colored(s: String): String = toAsciiEsc + s + extras.scala.io.Color.Reset.toAnsi
  }
}
