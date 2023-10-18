package extras.strings.syntax

import java.util.Locale

/** @author Kevin Lee
  * @since 2023-10-16
  */
trait cases {
  implicit def stringCaseOps(s: String): cases.StringCaseOps = new cases.StringCaseOps(s)
}
object cases extends cases {

  final class StringCaseOps(private val s: String) extends AnyVal {

    def toPascalCase: String =
      s.headOption.fold("")(_.toUpper.toString) + s.drop(1).toLowerCase(Locale.ENGLISH)
  }

}
