package extras.testing

import extras.testing.StubTools.MissingStubException

import scala.annotation.tailrec
import scala.util.control.NoStackTrace

trait StubTools {
  val thisClassName: String = getClass.getName

  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  def isMissing(stackTraceElement: StackTraceElement): Boolean = {
    val className  = stackTraceElement.getClassName
    val methodName = stackTraceElement.getMethodName
    (className == thisClassName.stripSuffix("$") || className == thisClassName) &&
    (methodName == "missing" || methodName.matches("^missing(?:[\\$]+[\\d]*)+"))
  }

  def missing: MissingStubException = {
    val stackTrace = Thread.currentThread.getStackTrace.toList
    @SuppressWarnings(Array("org.wartremover.warts.Equals"))
    val index      = stackTrace.indexWhere(_.getMethodName == "missing")

    val (elem, truncatedStackTraces) =
      if (index < 0) (stackTrace(2), stackTrace.drop(2))
      else {
        @tailrec
        def findMethodCalledMissing(
          stack: List[StackTraceElement]
        ): Option[(StackTraceElement, List[StackTraceElement])] =
          stack match {
            case x :: xs if isMissing(x) => findMethodCalledMissing(xs)
            case x :: xs => Option((x, x :: xs))
            case Nil => None
          }
        findMethodCalledMissing(stackTrace.drop(index))
          .getOrElse((stackTrace(2), stackTrace.drop(2)))
      }
    MissingStubException(elem, truncatedStackTraces)
  }

}

object StubTools extends StubTools {

  final case class MissingStubException(
    stackTraceElement: StackTraceElement,
    allStackTraceElements: List[StackTraceElement],
  ) extends NoStackTrace {
    override def getMessage: String =
      s"""
         |>> Missing Stub implementation at
         |>>   ${stackTraceElement.toString}
         |>>   ---
         |>>   Details:
         |>>   ${allStackTraceElements.map(_.toString).mkString("\n       at ")}
         |""".stripMargin
  }

}
