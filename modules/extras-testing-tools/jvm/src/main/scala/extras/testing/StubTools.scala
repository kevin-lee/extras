package extras.testing

import extras.testing.StubTools.MissingStubException

import scala.annotation.tailrec
import scala.util.control.NoStackTrace

trait StubTools {
  val thisClassName: String = getClass.getName

  val firstInterfaceNames: List[String] = getClass.getInterfaces.map(_.getName).toList

  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  def isMissing(stackTraceElement: StackTraceElement): Boolean = {
    val className  = stackTraceElement.getClassName
    val methodName = stackTraceElement.getMethodName
    (
      (className == thisClassName.stripSuffix("$") || className == thisClassName) ||
        firstInterfaceNames.exists(interfaceName =>
          (className == interfaceName.stripSuffix("$") || className == interfaceName)
        )
    ) &&
    (methodName == "missing" || methodName.matches("^missing(?:[\\$]+[\\d]*)+")) ||
    (methodName == "missingStubException" || methodName.matches("^missingStubException(?:[\\$]+[\\d]*)+"))
  }

  def missing: MissingStubException[Nothing] = missingStubException

  @SuppressWarnings(
    // TODO: Find a better way to handle these warts
    Array("org.wartremover.warts.IterableOps", "org.wartremover.warts.SeqApply", "org.wartremover.warts.SizeIs")
  )
  def missingStubException[A]: MissingStubException[A] = {
    val stackTrace = Thread.currentThread.getStackTrace.toList

    @SuppressWarnings(Array("org.wartremover.warts.Equals"))
    val index = {
      val missingIndex = stackTrace.indexWhere(_.getMethodName == "missing")
      if (missingIndex < 0)
        stackTrace.indexWhere(_.getMethodName == "missingStubException")
      else
        missingIndex
    }

    val (elem, truncatedStackTraces) =
      if (index < 0) {
        if (stackTrace.length < 3)
          (stackTrace.last, List.empty)
        else
          (stackTrace(2), stackTrace.drop(2))
      } else {
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

  final case class MissingStubException[+A](
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
