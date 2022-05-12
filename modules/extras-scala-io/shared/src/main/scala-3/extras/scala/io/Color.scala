package extras.scala.io

import scala.io.AnsiColor

enum Color derives CanEqual {
  case Black
  case Red
  case Green
  case Yellow
  case Blue
  case Magenta
  case Cyan
  case White
  case BlackBg
  case RedBg
  case GreenBg
  case YellowBg
  case BlueBg
  case MagentaBg
  case CyanBg
  case WhiteBg
  case Reset
  case Bold
  case Underlined
  case Blink
  case Reversed
  case Invisible
}

object Color {

  def black: Color = Color.Black

  def red: Color = Color.Red

  def green: Color = Color.Green

  def yellow: Color = Color.Yellow

  def blue: Color = Color.Blue

  def magenta: Color = Color.Magenta

  def cyan: Color = Color.Cyan

  def white: Color = Color.White

  def blackBg: Color = Color.BlackBg

  def redBg: Color = Color.RedBg

  def greenBg: Color = Color.GreenBg

  def yellowBg: Color = Color.YellowBg

  def blueBg: Color = Color.BlueBg

  def magentaBg: Color = Color.MagentaBg

  def cyanBg: Color = Color.CyanBg

  def whiteBg: Color = Color.WhiteBg

  def reset: Color = Color.Reset

  def bold: Color = Color.Bold

  def underlined: Color = Color.Underlined

  def blink: Color = Color.Blink

  def reversed: Color = Color.Reversed

  def invisible: Color = Color.Invisible

  extension (color: Color) {
    def render: String = color match {
      case Black =>
        AnsiColor.BLACK

      case Red =>
        AnsiColor.RED

      case Green =>
        AnsiColor.GREEN

      case Yellow =>
        AnsiColor.YELLOW

      case Blue =>
        AnsiColor.BLUE

      case Magenta =>
        AnsiColor.MAGENTA

      case Cyan =>
        AnsiColor.CYAN

      case White =>
        AnsiColor.WHITE

      case BlackBg =>
        AnsiColor.BLACK_B

      case RedBg =>
        AnsiColor.RED_B

      case GreenBg =>
        AnsiColor.GREEN_B

      case YellowBg =>
        AnsiColor.YELLOW_B

      case BlueBg =>
        AnsiColor.BLUE_B

      case MagentaBg =>
        AnsiColor.MAGENTA_B

      case CyanBg =>
        AnsiColor.CYAN_B

      case WhiteBg =>
        AnsiColor.WHITE_B

      case Reset =>
        AnsiColor.RESET

      case Bold =>
        AnsiColor.BOLD

      case Underlined =>
        AnsiColor.UNDERLINED

      case Blink =>
        AnsiColor.BLINK

      case Reversed =>
        AnsiColor.REVERSED

      case Invisible =>
        AnsiColor.INVISIBLE
    }

    def toAnsi: String = Color.render(color)

    def show: String = color.toString
  }

}
