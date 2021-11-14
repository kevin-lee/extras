package extras.concurrent.testing.syntax

import extras.concurrent.testing.ConcurrentSupport
import extras.concurrent.testing.types.{ErrorLogger, WaitFor}
import hedgehog._

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

/** @author Kevin Lee
  * @since 2021-11-14
  */
object FutureSyntaxSpec {

  implicit val defaultErrorLogger: ErrorLogger[Throwable] =
    ErrorLogger.printlnDefaultErrorLogger

  def testToValue: Property = for {
    n <- Gen.int(Range.linear(1, Int.MaxValue)).log("n")
  } yield {
    val es = ConcurrentSupport.newExecutorService(2)

    implicit val ec: ExecutionContext =
      ConcurrentSupport.newExecutionContext(es, ErrorLogger.printlnExecutionContextErrorLogger)
    ConcurrentSupport.runAndShutdown(es, WaitFor(2.seconds)) {
      implicit val waitFor: WaitFor = WaitFor(1.second)
      import extras.concurrent.testing.syntax.future._

      val fa     = Future(n)
      val actual = fa.toValue
      actual ==== n
    }
  }

}
