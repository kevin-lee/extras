package extras.scala.io.truecolor

import scala.util.Try

/** @author Kevin Lee
  * @since 2022-06-13
  */
final class Rgb private (val value: Int) {

  override def hashCode(): Int = value

  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  override def equals(that: Any): Boolean = that match {
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

  final case class Red(value: Int) extends AnyVal {
    def toHex: String = "%02x".format(value)
  }
  final case class Green(value: Int) extends AnyVal {
    def toHex: String = "%02x".format(value)
  }
  final case class Blue(value: Int) extends AnyVal {
    def toHex: String = "%02x".format(value)
  }

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
        Some(redIsValid).filterNot(identity).map(_ => "Red").toList,
        Some(greenIsValid).filterNot(identity).map(_ => "Green").toList,
        Some(blueIsValid).filterNot(identity).map(_ => "Blue").toList
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
    ) match {
      case scala.util.Failure(exception) =>
        Left(s"Invalid color hex: $hex, Error: ${exception.getMessage}")
      case scala.util.Success(List(r, g, b)) =>
        fromRgbInts(r, g, b)
      case scala.util.Success(_) =>
        Left(s"Invalid color hex: $hex")
    }

  @SuppressWarnings(Array("org.wartremover.warts.Throw"))
  def unsafeFromHexString(hex: String): Rgb =
    fromHexString(hex).fold(err => sys.error(err), identity)

  def unapply(rgb: Rgb): Option[(Red, Green, Blue)] = Some((rgb.red, rgb.green, rgb.blue))

  @SuppressWarnings(Array("org.wartremover.warts.Overloading"))
  implicit class RgbOps(private val rgb: Rgb) extends AnyVal {
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

    def color(s: String): String = if (s.isEmpty) "" else toAsciiEsc + s

    def colored(s: String): String = if (s.isEmpty) "" else toAsciiEsc + s + extras.scala.io.Color.Reset.toAnsi
  }
}
