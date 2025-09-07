package extras.core.syntax

import extras.core.syntax.strings.ExtrasStringOps

/** @author Kevin Lee
  * @since 2022-06-25
  */
trait strings {
  implicit def extrasStringOps(s: String): ExtrasStringOps = new ExtrasStringOps(s)
}
object strings extends strings {
  final class ExtrasStringOps(private val s: String) extends AnyVal {
    def encodeToUnicode: String = s.map(c => "\\u%04x".format(c.toInt)).mkString
  }
}
