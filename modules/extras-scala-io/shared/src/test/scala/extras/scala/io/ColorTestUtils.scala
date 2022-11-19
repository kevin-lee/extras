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
          s"${AnsiColor.RESET}${input.show}.toAnsi should be " +
            s"${ColorTestUtils.escapeAnsi(expected)} (${expected}O${AnsiColor.RESET}) " +
            s"but it was ${ColorTestUtils.escapeAnsi(actual)} (${actual}O${AnsiColor.RESET})"
        )
    }
    Result.all(results)
  }

  val colors: List[(Color, String)] = List(
    Color.black      -> AnsiColor.BLACK,
    Color.red        -> AnsiColor.RED,
    Color.green      -> AnsiColor.GREEN,
    Color.yellow     -> AnsiColor.YELLOW,
    Color.blue       -> AnsiColor.BLUE,
    Color.magenta    -> AnsiColor.MAGENTA,
    Color.cyan       -> AnsiColor.CYAN,
    Color.white      -> AnsiColor.WHITE,
    Color.blackBg    -> AnsiColor.BLACK_B,
    Color.redBg      -> AnsiColor.RED_B,
    Color.greenBg    -> AnsiColor.GREEN_B,
    Color.yellowBg   -> AnsiColor.YELLOW_B,
    Color.blueBg     -> AnsiColor.BLUE_B,
    Color.magentaBg  -> AnsiColor.MAGENTA_B,
    Color.cyanBg     -> AnsiColor.CYAN_B,
    Color.whiteBg    -> AnsiColor.WHITE_B,
    Color.reset      -> AnsiColor.RESET,
    Color.bold       -> AnsiColor.BOLD,
    Color.underlined -> AnsiColor.UNDERLINED,
    Color.blink      -> AnsiColor.BLINK,
    Color.reversed   -> AnsiColor.REVERSED,
    Color.invisible  -> AnsiColor.INVISIBLE,
  )

}
