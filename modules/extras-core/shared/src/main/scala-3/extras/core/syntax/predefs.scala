package extras.core.syntax

import scala.language.unsafeNulls

/** @author Kevin Lee
  * @since 2025-09-06
  */
trait predefs {
  extension [A](a: A | Null) {

    /** Null-coalescing (Elvis-style) operator for `A | Null`.
      *
      * Returns `a.nn` when `a` is not `null`; otherwise returns `ifNullUseThis`.
      * The `ifNullUseThis` parameter is call-by-name, so it is evaluated only when `a` is `null`.
      *
      * @example
      * {{{
      * import extras.core.syntax.predefs._
      *
      * val a1: String | Null = "hello"
      * val r1: String = a1 ?: "fallback"         // "hello"
      *
      * val a2: String | Null = null
      * val r2: String = a2 ?: "fallback"         // "fallback"
      *
      * val a3: String | Null = "ok"
      * val r3: String = a3 ?: sys.error("boom")  // "ok" (fallback is not evaluated)
      * }}}
      *
      * @param ifNullUseThis a fallback value to use when `a` is `null` (evaluated lazily).
      * @return `a` (as `A`) if it is not `null`; otherwise `ifNullUseThis`.
      */
    def ?:(ifNullUseThis: => A): A = elvis(ifNullUseThis)

    /** Just an alias to `?:` in order to keep the compatiblity with Scala 2 code as Scala 2's predefs can have only `?:=`
      *
      * @example
      * {{{
      * import extras.core.syntax.predefs._
      *
      * val a1: String | Null = "hello"
      * val r1: String = a1 ?:= "fallback"         // "hello"
      *
      * val a2: String | Null = null
      * val r2: String = a2 ?:= "fallback"         // "fallback"
      *
      * val a3: String | Null = "ok"
      * val r3: String = a3 ?:= sys.error("boom")  // "ok" (fallback is not evaluated)
      * }}}
      *
      * @param ifNullUseThis a fallback value to use when `a` is `null` (evaluated lazily).
      * @return `a` (as `A`) if it is not `null`; otherwise `ifNullUseThis`.
      */
    def ?:=(ifNullUseThis: => A): A = elvis(ifNullUseThis) // scalafix:ok DisableSyntax.null

    private inline def elvis(ifNullUseThis: => A): A =
      if a != null then a.nn else ifNullUseThis // scalafix:ok DisableSyntax.null

  }
}
object predefs extends predefs
