package extras.scala.io.syntax

import extras.scala.io.Color

/** @author Kevin Lee
  * @since 2021-09-19
  */
trait ColorSyntax {

  extension (text: String) {

    def colored(color: Color): String =
      s"${color.toAnsi}$text${Color.Reset.toAnsi}"

    def bold: String       = colored(Color.Bold)
    def underlined: String = colored(Color.Underlined)

    def red: String   = colored(Color.Red)
    def green: String = colored(Color.Green)
    def blue: String  = colored(Color.Blue)
  }

}
