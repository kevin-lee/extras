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
  def apply[A: Render]: Render[A] = implicitly[Render[A]]

  @SuppressWarnings(Array("org.wartremover.warts.ToString"))
  def fromToString[A]: Render[A] = _.toString

  implicit val unitRender: Render[Unit] = fromToString[Unit]

  implicit val booleanRender: Render[Boolean] = fromToString[Boolean]

  implicit val byteRender: Render[Byte]   = fromToString[Byte]
  implicit val shortRender: Render[Short] = fromToString[Short]
  implicit val intRender: Render[Int]     = fromToString[Int]
  implicit val longRender: Render[Long]   = fromToString[Long]

  implicit val floatRender: Render[Float]   = fromToString[Float]
  implicit val doubleRender: Render[Double] = fromToString[Double]

  implicit val charRender: Render[Char] = fromToString[Char]

  implicit val bigIntRender: Render[BigInt]         = fromToString[BigInt]
  implicit val bigDecimalRender: Render[BigDecimal] = fromToString[BigDecimal]

  implicit val stringRender: Render[String] = identity(_)
  implicit val symbolRender: Render[Symbol] = fromToString[Symbol]

  implicit val uuidRender: Render[UUID] = fromToString[UUID]

  implicit val durationRender: Render[Duration]             = fromToString[Duration]
  implicit val finiteDurationRender: Render[FiniteDuration] = fromToString[FiniteDuration]

  final case class Rendered(override val toString: String) extends AnyVal
  object Rendered {
    implicit def toRendered[A](a: A)(implicit R: Render[A]): Rendered = Rendered(R.render(a))
  }

  final case class RenderInterpolator(stringContext: StringContext) extends AnyVal {
    def render(args: Rendered*): String = stringContext.s(args: _*)
  }
}
