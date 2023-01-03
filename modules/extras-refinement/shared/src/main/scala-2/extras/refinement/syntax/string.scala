package extras.refinement.syntax

import eu.timepit.refined.types.string.NonEmptyString

/** @author Kevin Lee
  * @since 2022-09-07
  */
trait string {
  import extras.refinement.syntax.string.RefinementNonEmptyStringSyntax

  implicit def refinementNonEmptyStringSyntax(value: NonEmptyString): RefinementNonEmptyStringSyntax =
    new RefinementNonEmptyStringSyntax(value.value)
}
object string extends string {

  final private[string] class RefinementNonEmptyStringSyntax(private val value1: String) extends AnyVal {
    /* WARNING! Do not remove private.
     * This works for only NonEmptyString because of implicit def refinementNonEmptyStringSyntax
     * from the string trait. So removing private makes this extension method work for
     * String ++ NonEmptyString as well which is still valid but unwanted usage.
     */
    def ++(value2: NonEmptyString): NonEmptyString = NonEmptyString.unsafeFrom(value1 + value2.value)
  }

}
