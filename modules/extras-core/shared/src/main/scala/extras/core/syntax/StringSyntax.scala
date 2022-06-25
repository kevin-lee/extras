package extras.core.syntax

import extras.core.syntax.StringSyntax.ExtrasStringOps

/** @author Kevin Lee
  * @since 2022-06-25
  */
trait StringSyntax {
  implicit def extrasStringOps(s: String): ExtrasStringOps = new ExtrasStringOps(s)
}
object StringSyntax extends StringSyntax {
  final class ExtrasStringOps(private val s: String) extends AnyVal {
    def encodeToUnicode: String = s.map(c => "\\u%04x".format(c.toInt)).mkString
  }
}
