package extras

import java.io.{PrintWriter, StringWriter}

/**
 * @author Kevin Lee
 * @since 2021-05-16
 */
object tools {

  implicit final class ThrowableOps(private val throwable: Throwable) extends AnyVal {
    def stackTraceString: String = {
      val stringWriter = new StringWriter()
      val printWriter  = new PrintWriter(stringWriter)
      throwable.printStackTrace(printWriter)
      stringWriter.toString
    }
  }

}
