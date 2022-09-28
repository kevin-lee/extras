package extras.hedgehog.ce3

import CatsEffectRunner.{IoOps, SyncIoOps}
import cats.effect.{IO, Outcome, SyncIO, unsafe}
import cats.syntax.all._
import cats.{Eq, Id, Order, Show, ~>}
import extras.hedgehog.ce3
import extras.tools._
import hedgehog._

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.reflect.ClassTag

/** The code inside CatsEffectRunner was not entirely but mostly copied from
  * https://git.io/JDcCP and was modified for Hedgehog
  *
  * @author Kevin Lee
  * @since 2021-08-05
  */
trait CatsEffectRunner {

  def withIO(test: => Ticker => Result): Result =
    test(Ticker.withNewTestContext())

  def runIO(test: => IO[Result]): Result = {
    implicit val ticker: Ticker = Ticker.withNewTestContext()
    test.completeThen(identity)
  }

  type Ticker = ce3.Ticker
  val Ticker = ce3.Ticker

  implicit lazy val eqThrowable: Eq[Throwable] =
    Eq.fromUniversalEquals[Throwable]

  implicit lazy val showThrowable: Show[Throwable] =
    Show.fromToString[Throwable]

  implicit lazy val eqExecutionContext: Eq[ExecutionContext] =
    Eq.fromUniversalEquals[ExecutionContext]

  implicit def orderIoFiniteDuration(implicit ticker: Ticker): Order[IO[FiniteDuration]] =
    Order.by(ioa => unsafeRun(ioa).fold(None, _ => None, fa => fa))

  implicit def eqIOA[A: Eq](implicit ticker: Ticker): Eq[IO[A]] =
    Eq.by(unsafeRun(_))

  private val someK: Id ~> Option =
    new ~>[Id, Option] { def apply[A](a: A) = a.some }

  @SuppressWarnings(Array("org.wartremover.warts.Throw"))
  def unsafeRun[A](ioa: IO[A])(implicit ticker: Ticker): Outcome[Option, Throwable, A] =
    try {
      @SuppressWarnings(Array("org.wartremover.warts.Var"))
      var results: Outcome[Option, Throwable, A] = Outcome.Succeeded(None) // scalafix:ok DisableSyntax.var

      ioa
        .flatMap(IO.pure(_))
        .handleErrorWith(IO.raiseError(_))
        .unsafeRunAsyncOutcome { oc => results = oc.mapK(someK) }(
          unsafe
            .IORuntime(ticker.ctx, ticker.ctx, scheduler, () => (), unsafe.IORuntimeConfig())
        )

      ticker.ctx.tickAll()

      results
    } catch {
      case t: Throwable =>
        t.printStackTrace()
        throw t // scalafix:ok DisableSyntax.throw
    }

  def unsafeRunSync[A](ioa: SyncIO[A]): Outcome[Id, Throwable, A] =
    try Outcome.succeeded[Id, Throwable, A](ioa.unsafeRunSync())
    catch {
      case t: Throwable => Outcome.errored(t)
    }

  implicit def materializeRuntime(implicit ticker: Ticker): unsafe.IORuntime =
    unsafe.IORuntime(ticker.ctx, ticker.ctx, scheduler, () => (), unsafe.IORuntimeConfig())

  def scheduler(implicit ticker: Ticker): unsafe.Scheduler =
    new unsafe.Scheduler {
      import ticker.ctx

      def sleep(delay: FiniteDuration, action: Runnable): Runnable = {
        val cancel = ctx.schedule(delay, action)
        () => cancel()
      }

      def nowMillis(): Long      = ctx.now().toMillis
      def monotonicNanos(): Long = ctx.now().toNanos
    }

  implicit def ioOps[A](ioa: IO[A]): IoOps[A] = new IoOps(ioa)

  implicit def syncIoOps[A](ioa: SyncIO[A]): SyncIoOps[A] = new SyncIoOps[A](ioa)

}

private[ce3] object CatsEffectRunner extends CatsEffectRunner {

  final class IoOps[A](private val ioa: IO[A]) extends AnyVal {

    def tickTo(expected: Outcome[Option, Throwable, A])(
      implicit ticker: Ticker,
      eq: Eq[A],
      sh: Show[A]
    ): Boolean = {
      val oc = unsafeRun(ioa)
      oc eqv expected
    }

    def tickToResult(expected: Outcome[Option, Throwable, A])(
      implicit ticker: Ticker,
      eq: Eq[A],
      sh: Show[A]
    ): Result = {
      val oc = unsafeRun(ioa)
      Result.assert(oc eqv expected).log(s"${oc.show} !== ${expected.show}")
    }

    def completeAs(expected: A)(implicit ticker: Ticker, eq: Eq[A], sh: Show[A]): Result =
      tickToResult(Outcome.Succeeded(expected.some))

    def completeAndEqualTo(expected: A)(implicit ticker: Ticker, eq: Eq[A], sh: Show[A]): Boolean =
      tickTo(Outcome.Succeeded(Some(expected)))

    def completeThen(assertion: A => Result)(implicit ticker: Ticker): Result =
      unsafeRun(ioa) match {
        case Outcome.Succeeded(Some(actual)) =>
          assertion(actual)

        case Outcome.Succeeded(None) =>
          Result.failure.log("No result has been returned")

        case Outcome.Errored(err) =>
          Result.failure.log(s"Unexpected error: \n${err.stackTraceString}")

        case Outcome.Canceled() =>
          Result.failure.log("Cancelled")
      }

    def expectError[E <: Throwable: ClassTag](expected: E)(implicit ticker: Ticker, eq: Eq[E], sh: Show[E]): Result = {
      val expectedThrowableMessage = s"${expected.getClass.getName} was expected"

      unsafeRun(ioa) match {
        case Outcome.Errored(e: E) =>
          Result
            .diffNamed("actual should be equal to expected", e, expected)(_ eqv _)
            .log(
              expectedThrowableMessage + s" and it was thrown but not equal to the expected. [actual: ${e.show}, expected: ${expected.show}] \n${e.stackTraceString}"
            )

        case Outcome.Errored(e) =>
          Result
            .failure
            .log(expectedThrowableMessage + s" but ${e.getClass.getName} was thrown instead.\n${e.stackTraceString}")

        case Outcome.Canceled() =>
          Result.failure.log("Cancelled")

        case Outcome.Succeeded(Some(value)) =>
          Result
            .failure
            .log(
              expectedThrowableMessage + s" but no Throwable was thrown and some value has been returned. result: ${String
                  .valueOf(value)}."
            )

        case Outcome.Succeeded(None) =>
          Result.failure.log(expectedThrowableMessage + " but no Throwable was thrown nor did it return anything.")
      }
    }

    def errorThen(assertion: Throwable => Result)(implicit ticker: Ticker): Result =
      unsafeRun(ioa) match {
        case Outcome.Errored(e) =>
          assertion(e)

        case Outcome.Canceled() =>
          Result.failure.log("Cancelled")

        case Outcome.Succeeded(Some(value)) =>
          Result
            .failure
            .log(
              s"Expected some error, but no Throwable was thrown and some value has been returned. result: ${String.valueOf(value)}."
            )

        case Outcome.Succeeded(None) =>
          Result.failure.log("Expected some error, but no Throwable was thrown nor did it return anything.")

      }

  }

  final class SyncIoOps[A](private val ioa: SyncIO[A]) extends AnyVal {
    def completeAsSync(expected: A)(implicit eq: Eq[A], sh: Show[A]): Result = {
      val a = ioa.unsafeRunSync()
      Result.assert(a eqv expected).log(s"${a.show} !== ${expected.show}")
    }

    def completeAsEqualToSync(expected: A)(implicit eq: Eq[A]): Boolean = {
      val a = ioa.unsafeRunSync()
      a eqv expected
    }
  }
}
