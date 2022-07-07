package extras.scala.io.syntax.truecolor

import extras.scala.io.truecolor.Rainbow

/** @author Kevin Lee
  * @since 2022-06-13
  */
trait RainbowSyntax {
  extension (s: String) {
    def rainbowed: String     = Rainbow.rainbow(s)
    def rainbowedHtml: String = Rainbow.rainbowHtml(s)
  }

  extension (ss: Seq[String]) {
    def rainbowed: Seq[String] = Rainbow.rainbows(ss)
  }
}
