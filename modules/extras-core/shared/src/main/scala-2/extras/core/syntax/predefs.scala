package extras.core.syntax

/** @author Kevin Lee
  * @since 2025-09-06
  */
trait predefs {
  implicit def predefOps[A](a: A): predefs.ElvisOps[A] = new predefs.ElvisOps(a)
}
object predefs extends predefs {

  /** Null-coalescing (Elvis-style) operator for Scala 2.
    *
    * Scala 2 doesn't have union types like `A | Null`, so this operates on a plain `A` value that may be `null` at runtime.
    *
    * Unlike Scala 3's extension methods, in Scala 2, extension methods are just regular methods in an implicit value class.
    * As a result, method names ending with `:` are right-associative, so `a ?: b` is evaluated as `b.?:(a)`.
    *
    * Since we need the possibly-null value (`a`) to be the receiver, the operator is provided as `?:=` (not ending with `:`),
    * so `a ?:= b` is evaluated as `a.?:=(b)`.
    *
    * NOTE: In Scala 3, it's `?:`. (e.g. a ?: b)
    *
    * Returns `a` when `a` is not `null`; otherwise returns `ifNullUseThis`.
    * The `ifNullUseThis` parameter is call-by-name, so it is evaluated only when `a` is `null`.
    *
    * @example
    * {{{
    * import extras.core.syntax.predefs._
    *
    * val a1: String = "hello"
    * val r1: String = a1 ?:= "fallback"         // "hello"
    *
    * val a2: String = null
    * val r2: String = a2 ?:= "fallback"         // "fallback"
    *
    * val a3: String = "ok"
    * val r3: String = a3 ?:= sys.error("boom")  // "ok" (fallback is not evaluated)
    * }}}
    *
    * @param ifNullUseThis a fallback value to use when `a` is `null` (evaluated lazily).
    * @return `a` if it is not `null`; otherwise `ifNullUseThis`.
    */
  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  final class ElvisOps[A](private val a: A) extends AnyVal {
    def ?:=(ifNullUseThis: => A): A = if (a != null) a else ifNullUseThis // scalafix:ok DisableSyntax.null
  }

}
