package extras.scala.io.truecolor

/** @author Kevin Lee
  * @since 2022-07-07
  */
object Data {

  val rainbowAsciiArt: Seq[String] =
    List(
      """ ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄▄        ▄  ▄▄▄▄▄▄▄▄▄▄   ▄▄▄▄▄▄▄▄▄▄▄  ▄         ▄ """,
      """▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░▌      ▐░▌▐░░░░░░░░░░▌ ▐░░░░░░░░░░░▌▐░▌       ▐░▌""",
      """▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀█░▌ ▀▀▀▀█░█▀▀▀▀ ▐░▌░▌     ▐░▌▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀█░▌▐░▌       ▐░▌""",
      """▐░▌       ▐░▌▐░▌       ▐░▌     ▐░▌     ▐░▌▐░▌    ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌       ▐░▌""",
      """▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌     ▐░▌     ▐░▌ ▐░▌   ▐░▌▐░█▄▄▄▄▄▄▄█░▌▐░▌       ▐░▌▐░▌   ▄   ▐░▌""",
      """▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌     ▐░▌     ▐░▌  ▐░▌  ▐░▌▐░░░░░░░░░░▌ ▐░▌       ▐░▌▐░▌  ▐░▌  ▐░▌""",
      """▐░█▀▀▀▀█░█▀▀ ▐░█▀▀▀▀▀▀▀█░▌     ▐░▌     ▐░▌   ▐░▌ ▐░▌▐░█▀▀▀▀▀▀▀█░▌▐░▌       ▐░▌▐░▌ ▐░▌░▌ ▐░▌""",
      """▐░▌     ▐░▌  ▐░▌       ▐░▌     ▐░▌     ▐░▌    ▐░▌▐░▌▐░▌       ▐░▌▐░▌       ▐░▌▐░▌▐░▌ ▐░▌▐░▌""",
      """▐░▌      ▐░▌ ▐░▌       ▐░▌ ▄▄▄▄█░█▄▄▄▄ ▐░▌     ▐░▐░▌▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄█░▌▐░▌░▌   ▐░▐░▌""",
      """▐░▌       ▐░▌▐░▌       ▐░▌▐░░░░░░░░░░░▌▐░▌      ▐░░▌▐░░░░░░░░░░▌ ▐░░░░░░░░░░░▌▐░░▌     ▐░░▌""",
      """ ▀         ▀  ▀         ▀  ▀▀▀▀▀▀▀▀▀▀▀  ▀        ▀▀  ▀▀▀▀▀▀▀▀▀▀   ▀▀▀▀▀▀▀▀▀▀▀  ▀▀       ▀▀ """,
    )

  val expectedRainbowAsciiArt: String = {
    import Rainbow._
    import extras.scala.io.Color
    // format: off
    List(
      s"""${Red.toAsciiEsc} ▄▄▄▄▄▄▄▄▄▄▄ ${Orange.toAsciiEsc} ▄▄▄▄▄▄▄▄▄▄▄ ${Yellow.toAsciiEsc} ▄▄▄▄▄▄▄▄▄▄▄ ${Green.toAsciiEsc} ▄▄        ▄ ${Blue.toAsciiEsc} ▄▄▄▄▄▄▄▄▄▄  ${Indigo.toAsciiEsc} ▄▄▄▄▄▄▄▄▄▄▄ ${Violet.toAsciiEsc} ▄         ▄ ${Color.reset.toAnsi}""",
      s"""${Red.toAsciiEsc}▐░░░░░░░░░░░▌${Orange.toAsciiEsc}▐░░░░░░░░░░░▌${Yellow.toAsciiEsc}▐░░░░░░░░░░░▌${Green.toAsciiEsc}▐░░▌      ▐░▌${Blue.toAsciiEsc}▐░░░░░░░░░░▌ ${Indigo.toAsciiEsc}▐░░░░░░░░░░░▌${Violet.toAsciiEsc}▐░▌       ▐░▌${Color.reset.toAnsi}""",
      s"""${Red.toAsciiEsc}▐░█▀▀▀▀▀▀▀█░▌${Orange.toAsciiEsc}▐░█▀▀▀▀▀▀▀█░▌${Yellow.toAsciiEsc} ▀▀▀▀█░█▀▀▀▀ ${Green.toAsciiEsc}▐░▌░▌     ▐░▌${Blue.toAsciiEsc}▐░█▀▀▀▀▀▀▀█░▌${Indigo.toAsciiEsc}▐░█▀▀▀▀▀▀▀█░▌${Violet.toAsciiEsc}▐░▌       ▐░▌${Color.reset.toAnsi}""",
      s"""${Red.toAsciiEsc}▐░▌       ▐░▌${Orange.toAsciiEsc}▐░▌       ▐░▌${Yellow.toAsciiEsc}     ▐░▌     ${Green.toAsciiEsc}▐░▌▐░▌    ▐░▌${Blue.toAsciiEsc}▐░▌       ▐░▌${Indigo.toAsciiEsc}▐░▌       ▐░▌${Violet.toAsciiEsc}▐░▌       ▐░▌${Color.reset.toAnsi}""",
      s"""${Red.toAsciiEsc}▐░█▄▄▄▄▄▄▄█░▌${Orange.toAsciiEsc}▐░█▄▄▄▄▄▄▄█░▌${Yellow.toAsciiEsc}     ▐░▌     ${Green.toAsciiEsc}▐░▌ ▐░▌   ▐░▌${Blue.toAsciiEsc}▐░█▄▄▄▄▄▄▄█░▌${Indigo.toAsciiEsc}▐░▌       ▐░▌${Violet.toAsciiEsc}▐░▌   ▄   ▐░▌${Color.reset.toAnsi}""",
      s"""${Red.toAsciiEsc}▐░░░░░░░░░░░▌${Orange.toAsciiEsc}▐░░░░░░░░░░░▌${Yellow.toAsciiEsc}     ▐░▌     ${Green.toAsciiEsc}▐░▌  ▐░▌  ▐░▌${Blue.toAsciiEsc}▐░░░░░░░░░░▌ ${Indigo.toAsciiEsc}▐░▌       ▐░▌${Violet.toAsciiEsc}▐░▌  ▐░▌  ▐░▌${Color.reset.toAnsi}""",
      s"""${Red.toAsciiEsc}▐░█▀▀▀▀█░█▀▀ ${Orange.toAsciiEsc}▐░█▀▀▀▀▀▀▀█░▌${Yellow.toAsciiEsc}     ▐░▌     ${Green.toAsciiEsc}▐░▌   ▐░▌ ▐░▌${Blue.toAsciiEsc}▐░█▀▀▀▀▀▀▀█░▌${Indigo.toAsciiEsc}▐░▌       ▐░▌${Violet.toAsciiEsc}▐░▌ ▐░▌░▌ ▐░▌${Color.reset.toAnsi}""",
      s"""${Red.toAsciiEsc}▐░▌     ▐░▌  ${Orange.toAsciiEsc}▐░▌       ▐░▌${Yellow.toAsciiEsc}     ▐░▌     ${Green.toAsciiEsc}▐░▌    ▐░▌▐░▌${Blue.toAsciiEsc}▐░▌       ▐░▌${Indigo.toAsciiEsc}▐░▌       ▐░▌${Violet.toAsciiEsc}▐░▌▐░▌ ▐░▌▐░▌${Color.reset.toAnsi}""",
      s"""${Red.toAsciiEsc}▐░▌      ▐░▌ ${Orange.toAsciiEsc}▐░▌       ▐░▌${Yellow.toAsciiEsc} ▄▄▄▄█░█▄▄▄▄ ${Green.toAsciiEsc}▐░▌     ▐░▐░▌${Blue.toAsciiEsc}▐░█▄▄▄▄▄▄▄█░▌${Indigo.toAsciiEsc}▐░█▄▄▄▄▄▄▄█░▌${Violet.toAsciiEsc}▐░▌░▌   ▐░▐░▌${Color.reset.toAnsi}""",
      s"""${Red.toAsciiEsc}▐░▌       ▐░▌${Orange.toAsciiEsc}▐░▌       ▐░▌${Yellow.toAsciiEsc}▐░░░░░░░░░░░▌${Green.toAsciiEsc}▐░▌      ▐░░▌${Blue.toAsciiEsc}▐░░░░░░░░░░▌ ${Indigo.toAsciiEsc}▐░░░░░░░░░░░▌${Violet.toAsciiEsc}▐░░▌     ▐░░▌${Color.reset.toAnsi}""",
      s"""${Red.toAsciiEsc} ▀         ▀ ${Orange.toAsciiEsc} ▀         ▀ ${Yellow.toAsciiEsc} ▀▀▀▀▀▀▀▀▀▀▀ ${Green.toAsciiEsc} ▀        ▀▀ ${Blue.toAsciiEsc} ▀▀▀▀▀▀▀▀▀▀  ${Indigo.toAsciiEsc} ▀▀▀▀▀▀▀▀▀▀▀ ${Violet.toAsciiEsc} ▀▀       ▀▀ ${Color.reset.toAnsi}"""
    ).mkString("\n")
    // format: on
  }

}
