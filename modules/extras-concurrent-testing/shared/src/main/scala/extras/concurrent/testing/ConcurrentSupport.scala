package extras.concurrent.testing

import extras.concurrent.ExecutorServiceOps
import extras.concurrent.testing.types.{ErrorLogger, ExecutionContextErrorLogger, WaitFor}

import java.util.concurrent.{ExecutorService, Executors, TimeoutException}
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.control.NonFatal

/** @author Kevin Lee
  * @since 2020-09-22
  */
trait ConcurrentSupport {
  def newExecutorService(minThread: Int): ExecutorService =
    if (minThread >= 1)
      Executors.newFixedThreadPool(math.max(minThread, Runtime.getRuntime.availableProcessors() >> 1))
    else
      throw new IllegalArgumentException(
        s"minThread must be greater than or equal to 1. [minThread: $minThread]"
      ) // scalafix:ok DisableSyntax.throw

  def newExecutionContext(
    executorService: ExecutorService,
    errorLogger: ExecutionContextErrorLogger
  ): ExecutionContext =
    newExecutionContextWithLogger(executorService, errorLogger)

  def newExecutionContextWithErrorMessageLogger(
    executorService: ExecutorService,
    errorLogger: String => Unit
  ): ExecutionContext =
    newExecutionContextWithLogger(
      executorService,
      ErrorLogger.defaultExecutionContextErrorLogger(errorLogger)
    )

  def newExecutionContextWithLogger(
    executorService: ExecutorService,
    errorLogger: ExecutionContextErrorLogger
  ): ExecutionContext =
    ExecutionContext
      .fromExecutor(
        executorService,
        errorLogger
      )

  def runAndShutdown[A](executorService: ExecutorService, waitFor: WaitFor)(
    a: => A
  )(
    implicit errorLogger: ErrorLogger[Throwable]
  ): A =
    try a
    finally {
      try {
        ExecutorServiceOps.shutdownAndAwaitTermination(executorService, waitFor.waitFor)
      } catch {
        case NonFatal(ex) =>
//          @SuppressWarnings(Array("org.wartremover.warts.ToString"))
//          val message = ex.toString
//          println(s"NonFatal: $message")
          errorLogger(ex)
      }
    }

  @SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.Throw"))
  def futureToValue[A](fa: Future[A], waitFor: WaitFor)(implicit errorLogger: ErrorLogger[TimeoutException]): A =
    try {
      Await.result(fa, waitFor.waitFor)
    } catch {
      case ex: TimeoutException =>
        errorLogger(ex)
        throw ex // scalafix:ok DisableSyntax.throw
    }

  @SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.Throw"))
  def futureToValueAndTerminate[A](executorService: ExecutorService, waitFor: WaitFor)(
    fa: => Future[A]
  )(implicit errorLogger: ErrorLogger[Throwable]): A =
    runAndShutdown(executorService, waitFor) {
      futureToValue(fa, waitFor)
    }

}

object ConcurrentSupport extends ConcurrentSupport
