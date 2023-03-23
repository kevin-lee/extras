package extras.testing

import cats.Monad
import cats.syntax.all._
import effectie.core._
import extras.testing.StubTools.MissingStubException
import extras.testing.StubToolsFx.{stubToolsFxPartiallyApplied, StubToolsFxPartiallyApplied}

/** @author Kevin Lee
  * @since 2023-02-09
  */
trait StubToolsFx {
  @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf"))
  final def stub[F[*]]: StubToolsFx.StubToolsFxPartiallyApplied[F] =
    stubToolsFxPartiallyApplied.asInstanceOf[StubToolsFxPartiallyApplied[F]] // scalafix:ok DisableSyntax.asInstanceOf
}
object StubToolsFx extends StubToolsFx with StubTools {
  implicit def missingStubException$ : MissingStubException[StubToolsFx] = super.missingStubException[StubToolsFx]
  @SuppressWarnings(Array("org.wartremover.warts.DefaultArguments"))
  final private[StubToolsFx] class StubToolsFxPartiallyApplied[F[*]](
    private val dummy: Boolean = true
  ) extends AnyVal {
    def apply[A](
      f: => Option[A]
    )(implicit F: FxCtor[F], M: Monad[F], whenMissing: => MissingStubException[StubToolsFx]): F[A] =
      F.pureOrError(f).flatMap(F.fromOption[A](_)(whenMissing))

  }

  private[StubToolsFx] val stubToolsFxPartiallyApplied = new StubToolsFxPartiallyApplied

}
