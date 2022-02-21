package extras.scala.io

import scala.io.AnsiColor

sealed trait Color

object Color {
  case object Black extends Color
  case object Red extends Color
  case object Green extends Color
  case object Yellow extends Color
  case object Blue extends Color
  case object Magenta extends Color
  case object Cyan extends Color
  case object White extends Color
  case object BlackBg extends Color
  case object RedBg extends Color
  case object GreenBg extends Color
  case object YellowBg extends Color
  case object BlueBg extends Color
  case object MagentaBg extends Color
  case object CyanBg extends Color
  case object WhiteBg extends Color
  case object Reset extends Color
  case object Bold extends Color
  case object Underlined extends Color
  case object Blink extends Color
  case object Reversed extends Color
  case object Invisible extends Color

  def black: Color = Black

  def red: Color = Red

  def green: Color = Green

  def yellow: Color = Yellow

  def blue: Color = Blue

  def magenta: Color = Magenta

  def cyan: Color = Cyan

  def white: Color = White

  def blackBg: Color = BlackBg

  def redBg: Color = RedBg

  def greenBg: Color = GreenBg

  def yellowBg: Color = YellowBg

  def blueBg: Color = BlueBg

  def magentaBg: Color = MagentaBg

  def cyanBg: Color = CyanBg

  def whiteBg: Color = WhiteBg

  def reset: Color = Reset

  def bold: Color = Bold

  def underlined: Color = Underlined

  def blink: Color = Blink

  def reversed: Color = Reversed

  def invisible: Color = Invisible

  def render(color: Color): String = color match {
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

  implicit final class ColorOps(private val color: Color) extends AnyVal {
    def toAnsi: String = Color.render(color)
  }

}
