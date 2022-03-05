package extras.concurrent.testing.syntax

import extras.concurrent.testing.ConcurrentSupport
import extras.concurrent.testing.syntax.FutureSyntax.FutureTestOps
import extras.concurrent.testing.types.{ErrorLogger, WaitFor}

import scala.concurrent.{Future, TimeoutException}

/** @author Kevin Lee
  * @since 2021-11-14
  */
object future extends FutureSyntax

trait FutureSyntax {
  implicit def futureTestOps[A](fa: Future[A]): FutureTestOps[A] = new FutureTestOps(fa)
}

object FutureSyntax {
  implicit class FutureTestOps[A](private val fa: Future[A]) extends AnyVal {
    def toValue(implicit waitFor: WaitFor, errrorLogger: ErrorLogger[TimeoutException]): A =
      ConcurrentSupport.futureToValue(fa, waitFor)
  }
}
