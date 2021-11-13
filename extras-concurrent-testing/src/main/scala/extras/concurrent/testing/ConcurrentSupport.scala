package extras.concurrent.testing

import extras.concurrent.ExecutorServiceOps

import java.io.{PrintWriter, StringWriter}
import java.util.concurrent.{ExecutorService, Executors, TimeoutException}
import scala.concurrent.duration._
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
      throw new IllegalArgumentException(s"minThread must be greater than or equal to 1. [minThread: $minThread]")

  def newExecutionContext(executorService: ExecutorService): ExecutionContext =
    newExecutionContextWithLogger(executorService, println(_))

  def newExecutionContextWithErrorMessageLogger(
    executorService: ExecutorService,
    errorLogger: String => Unit
  ): ExecutionContext =
    newExecutionContextWithLogger(
      executorService,
      { th =>
        val stringWriter = new StringWriter()
        val printWriter  = new PrintWriter(stringWriter)
        th.printStackTrace(printWriter)
        errorLogger(s"""⚠️ Error in Executor - Error: ${th.getMessage}
                       |# StackTrace:
                       |${stringWriter.toString}
                       |""".stripMargin)
      }
    )

  def newExecutionContextWithLogger(
    executorService: ExecutorService,
    errorLogger: Throwable => Unit
  ): ExecutionContext =
    ExecutionContext
      .fromExecutor(
        executorService,
        errorLogger
      )

  def runAndShutdown[A](executorService: ExecutorService, waitFor: FiniteDuration)(a: => A): A =
    try a
    finally {
      try {
        ExecutorServiceOps.shutdownAndAwaitTermination(executorService, waitFor)
      } catch {
        case NonFatal(ex) =>
          @SuppressWarnings(Array("org.wartremover.warts.ToString"))
          val message = ex.toString
          println(s"NonFatal: $message")
      }
    }

  @SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.Throw"))
  def futureToValue[A](fa: Future[A], waitFor: FiniteDuration): A =
    try {
      Await.result(fa, waitFor)
    } catch {
      case ex: TimeoutException =>
        @SuppressWarnings(Array("org.wartremover.warts.ToString"))
        val message = ex.toString
        println(s"TimeoutException ⚠️: $message")
        throw ex
    }

  @SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.Throw"))
  def futureToValueAndTerminate[A](executorService: ExecutorService, waitFor: FiniteDuration)(
    fa: => Future[A]
  ): A =
    runAndShutdown(executorService, waitFor) {
      futureToValue(fa, waitFor)
    }

}

object ConcurrentSupport extends ConcurrentSupport
