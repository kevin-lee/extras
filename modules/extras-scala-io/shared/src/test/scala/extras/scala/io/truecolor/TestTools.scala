package extras.scala.io.truecolor

/** @author Kevin Lee
  * @since 2022-06-18
  */
object TestTools {
  def toRgbAsciiEsc(red: Int, green: Int, blue: Int): String =
    s"\u001b[38;2;${red.toString};${green.toString};${blue.toString}m"

  def toHex(n: Int, maxLength: Int): String = s"%0${maxLength.toString}x".format(n)
}
