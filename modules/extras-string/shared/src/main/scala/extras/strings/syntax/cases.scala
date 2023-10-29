package extras.strings.syntax

import java.util.Locale

/** @author Kevin Lee
  * @since 2023-10-16
  */
trait cases {
  implicit def stringCaseOps(s: String): cases.StringCaseOps = new cases.StringCaseOps(s)

  implicit def stringSeqCaseOps(ss: Seq[String]): cases.StringSeqCaseOps = new cases.StringSeqCaseOps(ss)
}
object cases extends cases {

  final class StringCaseOps(private val s: String) extends AnyVal {

    def toPascalCase: String =
      s.split("[\\s_-]+").flatMap(_.splitByCase).map(_.toOnePascalCase).mkString

    def toOnePascalCase: String =
      s.headOption.fold("")(_.toUpper.toString) + s.drop(1).toLowerCase(Locale.ENGLISH)

    def toCamelCase: String = {
      val splitCases = s.split("[\\s_-]+").flatMap(_.splitByCase)

      val head = splitCases
        .headOption
        .fold("")(s => s.headOption.fold("")(_.toLower.toString) + s.drop(1).toLowerCase(Locale.ENGLISH))
      val tail = splitCases.drop(1).map(_.toOnePascalCase).mkString
      head + tail
    }

    def toSnakeCase: String =
      s.split("[\\s_-]+")
        .flatMap(_.splitByCase)
        .mkString("_")

    def toSnakeUpperCase: String =
      s.split("[\\s_-]+")
        .flatMap(_.splitByCase)
        .map(_.toUpperCase(Locale.ENGLISH))
        .mkString("_")

    def toSnakeLowerCase: String =
      s.split("[\\s_-]+")
        .flatMap(_.splitByCase)
        .map(_.toLowerCase(Locale.ENGLISH))
        .mkString("_")

    def toKebabCase: String =
      s.split("[\\s_-]+")
        .flatMap(_.splitByCase)
        .mkString("-")

    @SuppressWarnings(Array("org.wartremover.warts.Equals"))
    def splitByCase: Vector[String] = {
      @scala.annotation.tailrec
      def splitEach(cs: List[Char], last: Char, current: StringBuilder, acc: Vector[String]): Vector[String] =
        cs match {
          case Nil =>
            acc :+ current.append(last).toString

          case head :: Nil if last.isUpper && head.isLower =>
            if (current.isEmpty)
              acc :+ (last.toString + head.toString)
            else
              acc :+ current.toString :+ (last.toString + head.toString)

          case head :: Nil if last.isLower && head.isUpper =>
            acc :+ current.append(last).toString :+ head.toString

          case head :: Nil =>
            acc :+ current.append(last).append(head).toString

          case head :: tail if last.isUpper && head.isLower =>
            splitEach(
              tail,
              head,
              new StringBuilder().append(last),
              if (current.isEmpty) acc else acc :+ current.toString,
            )

          case head :: tail if last.isLower && head.isUpper =>
            splitEach(tail, head, new StringBuilder(), acc :+ current.append(last).toString)

          case head :: tail =>
            splitEach(tail, head, current.append(last), acc)
        }

      if (s == null) // scalafix:ok DisableSyntax.null
        Vector.empty
      else
        s.toList match {
          case Nil => Vector.empty
          case _ :: Nil => Vector(s)
          case head :: tail => splitEach(tail, head, new StringBuilder(), Vector.empty)
        }
    }

  }

  final class StringSeqCaseOps(private val ss: Seq[String]) extends AnyVal {
    def mkPascalCaseString: String =
      ss.map { s =>
        (for {
          split  <- s.split("[\\s_-]+")
          byCase <- split.splitByCase
          pascal = byCase.toOnePascalCase
        } yield pascal).mkString
      }.mkString

    def mkCamelCaseString: String =
      ss.headOption.fold("")(_.toCamelCase) +
        ss.drop(1).mkPascalCaseString

  }

}
