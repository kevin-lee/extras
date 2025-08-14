package extras.render

import java.util.UUID
import scala.annotation.implicitNotFound
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

  @inline def render[A](f: A => String): Render[A] = f(_)

  implicit final class RenderOps[A](private val renderA: Render[A]) extends AnyVal {
    def contramap[B](f: B => A): Render[B] = b => renderA.render(f(b))

  }

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

  @implicitNotFound(msg =
    "Missing an instance of `CatsContravariant` which means you're trying to use cats.Contravariant, " +
      "but cats library is missing in your project config. " +
      "If you want to have an instance of cats.Contravariant[Render] provided by extras, " +
      """please add `"org.typelevel" %% "cats-core" % CATS_VERSION` to your libraryDependencies in build.sbt"""
  )
  sealed private[Render] trait CatsContravariant[M[_[_]]]
  private[Render] object CatsContravariant {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    @inline implicit final def getCatsContravariant: CatsContravariant[cats.Contravariant] =
      null // scalafix:ok DisableSyntax.null
  }

  @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
  implicit def renderContravariant[F[_[_]]: CatsContravariant]: F[Render] =
    new cats.Contravariant[Render] {
      override def contramap[A, B](fa: Render[A])(f: B => A): Render[B] = b => fa.render(f(b))
    }.asInstanceOf[F[Render]] // scalafix:ok DisableSyntax.asInstanceOf
}
