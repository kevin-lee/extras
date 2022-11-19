package extras.concurrent.testing

import extras.concurrent.ExecutorServiceOps
import extras.concurrent.testing.types.{ErrorLogger, WaitFor}
import hedgehog._
import hedgehog.runner._
import hedgehog.sbt.MessageOnlyException

import java.util.concurrent.ThreadPoolExecutor
import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._

/** @author Kevin Lee
  * @since 2021-11-13
  */
object ConcurrentSupportSpec extends Properties {
  override def tests: List[Test] = List(
    property(
      "test ConcurrentSupport.newExecutorService(n) should create ThreadPoolExecutor with maximumPoolSize >= n",
      testConcurrentSupportNewExecutorService,
    ),
    property(
      "test ConcurrentSupport.runAndShutdown",
      testConcurrentSupportRunAndShutdown,
    ),
    property(
      "test ConcurrentSupport.futureToValue",
      testConcurrentSupportFutureToValue,
    ),
    property(
      "test ConcurrentSupport.futureToValueAndTerminate",
      testConcurrentSupportFutureToValueAndTerminate,
    ),
  )

  val halfOfAvailableProcessors: Int = Runtime.getRuntime.availableProcessors() >> 1

  implicit val defaultErrorLogger: ErrorLogger[Throwable] = ErrorLogger.printlnDefaultErrorLogger

  def testConcurrentSupportNewExecutorService: Property = for {
    numThread <- Gen.int(Range.linear(1, halfOfAvailableProcessors + 2)).log("numThread")
  } yield {
    val executorService = ConcurrentSupport.newExecutorService(numThread)
    try {
      @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf", "org.wartremover.warts.Throw"))
      val threadPoolExecutor = executorService match {
        case _: ThreadPoolExecutor =>
          executorService.asInstanceOf[ThreadPoolExecutor] // scalafix:ok DisableSyntax.asInstanceOf
        case _ =>
          throw new MessageOnlyException( // scalafix:ok DisableSyntax.throw
            s"executorService should be ThreadPoolExecutor but ${executorService.getClass.getName}"
          )
      }

      val actual = threadPoolExecutor.getMaximumPoolSize
      Result.all(
        List(
          actual ==== numThread or actual ==== halfOfAvailableProcessors,
          Result
            .assert(!executorService.isTerminated)
            .log("executorService shouldn't have been terminated yet but it was."),
        )
      )
    } finally {
      ExecutorServiceOps.shutdownAndAwaitTermination(executorService, 1.second)
    }
  }

  def testConcurrentSupportRunAndShutdown: Property = for {
    n <- Gen.int(Range.linear(1, Int.MaxValue)).log("n")
  } yield {
    val executorService = ConcurrentSupport.newExecutorService(2)

    val actual = ConcurrentSupport.runAndShutdown(executorService, WaitFor(1.second)) {
      n
    }
    actual ==== n and Result
      .assert(executorService.isTerminated)
      .log("executorService should have been already terminated but it wasn't.")
  }

  def testConcurrentSupportFutureToValue: Property = for {
    n <- Gen.int(Range.linear(1, Int.MaxValue)).log("n")
  } yield {
    val executorService               =
      ConcurrentSupport.newExecutorService(2)
    implicit val ec: ExecutionContext =
      ConcurrentSupport.newExecutionContext(executorService, ErrorLogger.printlnExecutionContextErrorLogger)

    try {
      val actual = ConcurrentSupport.futureToValue(Future(n), WaitFor(1.second))
      actual ==== n and Result
        .assert(!executorService.isTerminated)
        .log("executorService shouldn't have been terminated yet but it was.")
    } finally {
      ExecutorServiceOps.shutdownAndAwaitTermination(executorService, 1.second)
    }
  }

  def testConcurrentSupportFutureToValueAndTerminate: Property = for {
    n <- Gen.int(Range.linear(1, Int.MaxValue)).log("n")
  } yield {
    val executorService               = ConcurrentSupport.newExecutorService(2)
    implicit val ec: ExecutionContext =
      ConcurrentSupport.newExecutionContext(executorService, ErrorLogger.printlnExecutionContextErrorLogger)

    val actual = ConcurrentSupport.futureToValueAndTerminate(executorService, WaitFor(1.second)) {
      Future(n)
    }
    actual ==== n and Result
      .assert(executorService.isTerminated)
      .log("executorService should have been already terminated but it wasn't.")
  }

}
