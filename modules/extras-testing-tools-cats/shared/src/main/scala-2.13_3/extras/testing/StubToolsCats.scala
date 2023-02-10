package extras.testing

import cats.MonadThrow
import cats.syntax.all._
import extras.testing.StubTools.MissingStubException
import extras.testing.StubToolsCats.StubToolsCatsPartiallyApplied

/** @author Kevin Lee
  * @since 2023-02-09
  */
trait StubToolsCats {
  def stub[F[*]]: StubToolsCats.StubToolsCatsPartiallyApplied[F] =
    new StubToolsCatsPartiallyApplied[F]
}
object StubToolsCats extends StubToolsCats with StubTools {
  implicit def missingStubException$ : MissingStubException[StubToolsCats] = super.missingStubException[StubToolsCats]
  @SuppressWarnings(Array("org.wartremover.warts.DefaultArguments"))
  final private[StubToolsCats] class StubToolsCatsPartiallyApplied[F[*]](
    private val dummy: Boolean = true
  ) extends AnyVal {
    def apply[A](
      f: => Option[A]
    )(implicit F: MonadThrow[F], whenMissing: => MissingStubException[StubToolsCats]): F[A] =
      F.catchNonFatal(f).flatMap(F.fromOption[A](_, whenMissing))

  }

}
