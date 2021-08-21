package extras.concurrent

import java.util.concurrent.{ExecutorService, TimeUnit}

import scala.concurrent.duration.FiniteDuration

/** @author Kevin Lee
  * @since 2020-07-31
  */
trait ExecutorServiceOps {

  private def noLogger(s: => String): Unit = ()

  def shutdownAndAwaitTermination(
    executorService: ExecutorService,
    waitFor: FiniteDuration
  ): Unit =
    shutdownAndAwaitTerminationWithLogger(executorService, waitFor)(noLogger)

  @SuppressWarnings(Array("org.wartremover.warts.StringPlusAny"))
  def shutdownAndAwaitTerminationWithLogger(
    executorService: ExecutorService,
    waitFor: FiniteDuration
  )(
    logger: (=> String) => Unit
  ): Unit = {
    logger("About to start executorService.shutdown()")
    executorService.shutdown()

    try {
      logger(
        "executorService.shutdown() has been called " +
          s"and await termination for ${waitFor.toMillis} ms (${waitFor.toSeconds} s)"
      )
      if (!executorService.awaitTermination(waitFor.toMillis, TimeUnit.MILLISECONDS)) {
        logger(s"calling executorService.shutdownNow()")
        val _ = executorService.shutdownNow()

        logger(
          "executorService.shutdownNow() has been called " +
            s"and await termination for ${waitFor.toMillis} ms (${waitFor.toSeconds} s)"
        )
        if (!executorService.awaitTermination(waitFor.toMillis, TimeUnit.MILLISECONDS)) {
          logger(
            "ExecutorService did not terminate " +
              s"after awaiting for ${waitFor.toMillis} ms (${waitFor.toSeconds} s)."
          )
        } else {
          logger("ExecutorService has been terminated.")
          ()
        }
      } else {
        logger("ExecutorService has been terminated.")
        ()
      }
    } catch {
      case ie: InterruptedException =>
        logger("InterruptedException has been caught while terminating ExecutorService.")
        logger("Calling executorService.shutdownNow()")
        val _ = executorService.shutdownNow()
        logger(
          "executorService.shutdownNow() has been called" +
            " and calling Thread.currentThread.interrupt()"
        )
        Thread.currentThread.interrupt()
    }
  }

}

object ExecutorServiceOps extends ExecutorServiceOps
