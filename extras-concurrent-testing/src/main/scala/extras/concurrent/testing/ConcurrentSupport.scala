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
  def newExecutorService(): ExecutorService =
    Executors.newFixedThreadPool(math.max(2, Runtime.getRuntime.availableProcessors() >> 1))

  def newExecutionContext(executorService: ExecutorService): ExecutionContext =
    newExecutionContextWithLogger(executorService, println(_))

  def newExecutionContextWithLogger(executorService: ExecutorService, logger: String => Unit): ExecutionContext =
    scala
      .concurrent
      .ExecutionContext
      .fromExecutor(
        executorService,
        { th =>
          val stringWriter = new StringWriter()
          val printWriter  = new PrintWriter(stringWriter)
          th.printStackTrace(printWriter)
          logger(s"⚠️ Error in Executor: ${stringWriter.toString}")
        }
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
  def futureToValueAndTerminate[A](fa: Future[A], waitFor: FiniteDuration)(
    implicit executorService: ExecutorService
  ): A =
    try {
      Await.result(fa, waitFor)
    } catch {
      case ex: TimeoutException =>
        @SuppressWarnings(Array("org.wartremover.warts.ToString"))
        val message = ex.toString
        println(s"ex: $message")
        throw ex
    } finally {
      try {
        ExecutorServiceOps.shutdownAndAwaitTerminationWithLogger(executorService, waitFor)(println(_))
      } catch {
        case NonFatal(ex) =>
          @SuppressWarnings(Array("org.wartremover.warts.ToString"))
          val message = ex.toString
          println(s"NonFatal: $message")
      }
    }

}

object ConcurrentSupport extends ConcurrentSupport
