package extras.scala.io.syntax

import extras.scala.io.Color
import extras.scala.io.syntax.ColorSyntax.ColorOps

/** @author Kevin Lee
  * @since 2021-09-19
  */
trait ColorSyntax {
  implicit def colorOps(text: String): ColorOps = new ColorOps(text)
}

object ColorSyntax {

  final class ColorOps(private val text: String) extends AnyVal {

    def colored(color: Color): String =
      s"${color.toAnsi}$text${Color.reset.toAnsi}"

    def bold: String       = colored(Color.bold)
    def underlined: String = colored(Color.underlined)

    def red: String   = colored(Color.red)
    def green: String = colored(Color.green)
    def blue: String  = colored(Color.blue)
  }

}
