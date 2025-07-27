package extras.render

import java.util.UUID
import scala.annotation.{implicitNotFound, nowarn}
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
      inline def value: String = rendered
    }
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
    inline given getCatsContravariant: CatsContravariant[cats.Contravariant] = null // scalafix:ok DisableSyntax.null
  }

  @nowarn(
    "msg=evidence parameter evidence\\$.+ of type (.+\\.)+CatsContravariant\\[F\\] in method renderContravariant is never used"
  )
  @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
  given RenderContravariant[F[_[_]]: CatsContravariant]: F[Render] =
    new cats.Contravariant[Render] {
      override def contramap[A, B](fa: Render[A])(f: B => A): Render[B] = b => fa.render(f(b))
    }.asInstanceOf[F[Render]] // scalafix:ok DisableSyntax.asInstanceOf

}
