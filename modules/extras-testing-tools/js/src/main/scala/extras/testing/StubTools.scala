package extras.testing

import extras.testing.StubTools.MissingStubException

import scala.annotation.tailrec
import scala.util.control.NoStackTrace
import scala.util.Try

@SuppressWarnings(Array("org.wartremover.warts.Equals"))
trait StubTools {
  val thisClassName: String = getClass.getName

  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  def isMissing(stackTraceElement: StackTraceElement): Boolean = {
    val className  = stackTraceElement.getClassName
    val methodName = stackTraceElement.getMethodName
    (
      (className == thisClassName.stripSuffix("$") || className == thisClassName)
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

    /* Try to resolve to Scala source location */
    val resolvedElem        = resolveToScalaSource(elem)
    val resolvedStackTraces = truncatedStackTraces.map(resolveToScalaSource)

    MissingStubException(resolvedElem, resolvedStackTraces)
  }

  private def resolveToScalaSource(stackTraceElement: StackTraceElement): StackTraceElement = {
    Try {
      val fileName = stackTraceElement.getFileName

      /* For now, use pattern-based inference to resolve JS locations to Scala sources */
      // TODO: Add full source map integration in future enhancement
      if (fileName != null && fileName.endsWith(".js")) { // scalafix:ok DisableSyntax.null
        /* Direct pattern-based inference for Scala source location */
        inferScalaSourceFromClassName(stackTraceElement)
      } else {
        stackTraceElement
      }
    }.getOrElse(stackTraceElement)
  }

  private def inferScalaSourceFromClassName(stackTraceElement: StackTraceElement): StackTraceElement = {
    val className = stackTraceElement.getClassName

    /* Generic extraction: get the last part of the class name and clean it */
    val scalaFileName = className.split('.').lastOption match {
      case Some(lastPart) =>
        val cleanName = lastPart.replaceAll("\\$.*", "") // Remove Scala name mangling ($anon$1, $1, etc.)

        if (cleanName.nonEmpty) cleanName else "Unknown"
      case None =>
        "Unknown"
    }

    /* Use generic line 0 - better than confusing JS line numbers
     * Future enhancement: integrate with source maps for accurate line numbers if possible
     */
    new StackTraceElement(
      stackTraceElement.getClassName,
      stackTraceElement.getMethodName,
      scalaFileName,
      0,
    )
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
