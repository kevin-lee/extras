package extras.render

import java.util.UUID
import scala.concurrent.duration.{Duration, FiniteDuration}

/** @author Kevin Lee
  * @since 2022-10-15
  */
trait Render[A] {
  def render(a: A): String
}
object Render {
  def apply[A: Render]: Render[A] = summon[Render[A]]

  @SuppressWarnings(Array("org.wartremover.warts.ToString"))
  def fromToString[A]: Render[A] = _.toString

  inline def render[A](f: A => String): Render[A] = f(_)

  extension [A](renderA: Render[A]) {
    def contramap[B](f: B => A): Render[B] = b => renderA.render(f(b))
  }

  given unitRender: Render[Unit] = fromToString[Unit]

  given booleanRender: Render[Boolean] = fromToString[Boolean]

  given byteRender: Render[Byte]   = fromToString[Byte]
  given shortRender: Render[Short] = fromToString[Short]
  given intRender: Render[Int]     = fromToString[Int]
  given longRender: Render[Long]   = fromToString[Long]

  given floatRender: Render[Float]   = fromToString[Float]
  given doubleRender: Render[Double] = fromToString[Double]

  given charRender: Render[Char] = fromToString[Char]

  given bigIntRender: Render[BigInt]         = fromToString[BigInt]
  given bigDecimalRender: Render[BigDecimal] = fromToString[BigDecimal]

  given stringRender: Render[String] = identity(_)
  given symbolRender: Render[Symbol] = fromToString[Symbol]

  given uuidRender: Render[UUID] = fromToString[UUID]

  given durationRender: Render[Duration]             = fromToString[Duration]
  given finiteDurationRender: Render[FiniteDuration] = fromToString[FiniteDuration]

  type Rendered = Rendered.Type
  object Rendered {
    opaque type Type = String
    inline def apply(rendered: String): Rendered = rendered

    given renderedCanEqual: CanEqual[Rendered, Rendered] = CanEqual.derived

    given toRendered[A](using R: Render[A]): Conversion[A, Rendered] = a => apply(R.render(a))

    extension (rendered: Rendered) {
      inline def toString: String = rendered
    }
  }

}
