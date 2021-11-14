package extras.concurrent.testing

import java.io.{PrintWriter, StringWriter}
import scala.concurrent.TimeoutException
import scala.concurrent.duration.FiniteDuration
import scala.util.control.NonFatal

/** @author Kevin Lee
  * @since 2021-11-14
  */
object types {

  final case class WaitFor(waitFor: FiniteDuration) extends AnyVal

  sealed trait ErrorLogger[-A <: Throwable] extends (A => Unit)
  sealed trait ExecutionContextErrorLogger  extends ErrorLogger[Throwable]
  object ErrorLogger {
    final case class DefaultExecutionContextErrorLogger(errorLogger: String => Unit)
        extends ExecutionContextErrorLogger {

      override def apply(throwable: Throwable): Unit = {
        val stringWriter = new StringWriter()
        val printWriter  = new PrintWriter(stringWriter)
        throwable.printStackTrace(printWriter)
        errorLogger(s"""⚠️ Error in ExecutionContext: ${throwable.getMessage}
                       |# StackTrace:
                       |${stringWriter.toString}
                       |""".stripMargin)
      }

    }

    final case class DefaultErrorLogger(errorLogger: String => Unit) extends ErrorLogger[Throwable] {
      override def apply(throwable: Throwable): Unit = throwable match {
        case NonFatal(ex: TimeoutException) =>
          defaultTimeoutExceptionLogger(errorLogger)(ex)

        case NonFatal(ex) =>
          errorLogger(ex.toString)
      }
    }

    final case class DefaultTimeoutExceptionLogger(logger: String => Unit) extends ErrorLogger[TimeoutException] {
      override def apply(timeoutException: TimeoutException): Unit = {
        @SuppressWarnings(Array("org.wartremover.warts.ToString"))
        val message = timeoutException.toString
        logger(s"TimeoutException ⚠️: $message")
      }
    }

    def defaultExecutionContextErrorLogger(errorLogger: String => Unit): ExecutionContextErrorLogger =
      DefaultExecutionContextErrorLogger(errorLogger)

    lazy val printlnExecutionContextErrorLogger: ExecutionContextErrorLogger = defaultExecutionContextErrorLogger(
      println(_)
    )

    def defaultTimeoutExceptionLogger(logger: String => Unit): ErrorLogger[TimeoutException] =
      DefaultTimeoutExceptionLogger(logger)

    lazy val printlnTimeoutExceptionLogger: ErrorLogger[TimeoutException] =
      DefaultTimeoutExceptionLogger(println(_))

    def defaultErrorLogger(errorLogger: String => Unit): ErrorLogger[Throwable] = DefaultErrorLogger(errorLogger)

    lazy val printlnDefaultErrorLogger: ErrorLogger[Throwable] = DefaultErrorLogger(println(_))

  }

}
