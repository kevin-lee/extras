package extras.scala.io

import scala.io.AnsiColor

object ColorTestUtils {
  def escapeAnsi(s: String): String = "\\u%04x".format(s.head.toInt) + s.tail

  import hedgehog.Result
  def testColorToAnsi: Result = {
    import hedgehog.Syntax
    val results = colors.map {
      case (input, expected) =>
        val actual = input.toAnsi
        (actual ==== expected).log(
          s"${AnsiColor.RESET}${input.toString}.toAnsi should be " +
            s"${ColorTestUtils.escapeAnsi(expected)} (${expected}O${AnsiColor.RESET}) " +
            s"but it was ${ColorTestUtils.escapeAnsi(actual)} (${actual}O${AnsiColor.RESET})"
        )
    }
    Result.all(results)
  }

  val colors = List(
    Color.Black      -> AnsiColor.BLACK,
    Color.Red        -> AnsiColor.RED,
    Color.Green      -> AnsiColor.GREEN,
    Color.Yellow     -> AnsiColor.YELLOW,
    Color.Blue       -> AnsiColor.BLUE,
    Color.Magenta    -> AnsiColor.MAGENTA,
    Color.Cyan       -> AnsiColor.CYAN,
    Color.White      -> AnsiColor.WHITE,
    Color.BlackBg    -> AnsiColor.BLACK_B,
    Color.RedBg      -> AnsiColor.RED_B,
    Color.GreenBg    -> AnsiColor.GREEN_B,
    Color.YellowBg   -> AnsiColor.YELLOW_B,
    Color.BlueBg     -> AnsiColor.BLUE_B,
    Color.MagentaBg  -> AnsiColor.MAGENTA_B,
    Color.CyanBg     -> AnsiColor.CYAN_B,
    Color.WhiteBg    -> AnsiColor.WHITE_B,
    Color.Reset      -> AnsiColor.RESET,
    Color.Bold       -> AnsiColor.BOLD,
    Color.Underlined -> AnsiColor.UNDERLINED,
    Color.Blink      -> AnsiColor.BLINK,
    Color.Reversed   -> AnsiColor.REVERSED,
    Color.Invisible  -> AnsiColor.INVISIBLE
  )

}
