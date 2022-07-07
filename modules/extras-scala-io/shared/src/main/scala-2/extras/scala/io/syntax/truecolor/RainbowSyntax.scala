package extras.scala.io.syntax.truecolor

import extras.scala.io.syntax.truecolor.RainbowSyntax.{RainbowSyntaxOps, RainbowsSyntaxOps}
import extras.scala.io.truecolor.Rainbow

/** @author Kevin Lee
  * @since 2022-06-13
  */
trait RainbowSyntax {
  implicit def rainbowSyntax(s: String): RainbowSyntaxOps = new RainbowSyntaxOps(s)

  implicit def rainbowsSyntax(ss: Seq[String]): RainbowsSyntaxOps = new RainbowsSyntaxOps(ss)
}
object RainbowSyntax {
  class RainbowSyntaxOps(private val s: String) extends AnyVal {
    def rainbowed: String     = Rainbow.rainbow(s)
    def rainbowedHtml: String = Rainbow.rainbowHtml(s)
  }

  class RainbowsSyntaxOps(private val ss: Seq[String]) extends AnyVal {
    def rainbowed: Seq[String] = Rainbow.rainbows(ss)
  }

}
