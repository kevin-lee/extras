package extras.core.syntax

import scala.language.unsafeNulls

/** @author Kevin Lee
  * @since 2025-09-06
  */
trait predefs {
  extension [A](a: A | Null) {
    def ?:(ifNullUseThis: => A): A = if a != null then a.nn else ifNullUseThis // scalafix:ok DisableSyntax.null
  }
}
object predefs extends predefs
