package extras.scala.io.syntax

import extras.scala.io.Color

/** @author Kevin Lee
  * @since 2021-09-19
  */
trait ColorSyntax {

  extension (text: String) {

    def colored(color: Color): String =
      s"${color.toAnsi}$text${Color.Reset.toAnsi}"

    def black: String         = colored(Color.black)
    def red: String           = colored(Color.red)
    def green: String         = colored(Color.green)
    def yellow: String        = colored(Color.yellow)
    def blue: String          = colored(Color.blue)
    def magenta: String       = colored(Color.magenta)
    def cyan: String          = colored(Color.cyan)
    def white: String         = colored(Color.white)
    def blackBg: String       = colored(Color.blackBg)
    def redBg: String         = colored(Color.redBg)
    def greenBg: String       = colored(Color.greenBg)
    def yellowBg: String      = colored(Color.yellowBg)
    def blueBg: String        = colored(Color.blueBg)
    def magentaBg: String     = colored(Color.magentaBg)
    def cyanBg: String        = colored(Color.cyanBg)
    def whiteBg: String       = colored(Color.whiteBg)
    def reset: String         = colored(Color.reset)
    def bold: String          = colored(Color.bold)
    def underlined: String    = colored(Color.underlined)
    def blink: String         = colored(Color.blink)
    def reversedColor: String = colored(Color.reversed)
    def invisible: String     = colored(Color.invisible)
  }

}
