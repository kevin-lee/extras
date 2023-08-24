package extras.strings.syntax

/** @author Kevin Lee
  * @since 2023-08-22
  */
trait strings {
  implicit def stringSeqOps(ss: Seq[String]): strings.StringSeqOps = new strings.StringSeqOps(ss)
}
object strings extends strings {
  @SuppressWarnings(Array("org.wartremover.warts.IterableOps"))
  final class StringSeqOps(private val ss: Seq[String]) extends AnyVal {

    /** join Seq[String] with , (comma) expect for the last and the second last one which are connected with `and`.
      * @example
      * {{{
      *   List.empty
      *   // String = ""
      *
      *   List("")
      *   // String = ""
      *
      *   List("aaa")
      *   // String = "aaa"
      *
      *   List("aaa", "bbb")
      *   // String = "aaa and bbb"
      *
      *   List("aaa", "bbb", "ccc")
      *   // String = "aaa, bbb and ccc"
      *
      *   List("aaa", "bbb", "ccc", "ddd")
      *   // String = "aaa, bbb, ccc and ddd"
      *
      * }}}
      */
    def commaAnd: String = ss match {
      case Seq() => ""
      case Seq(s) => s
      case Seq(s1, s2) => s"$s1 and $s2"
      case _ => s"${ss.dropRight(1).mkString(", ")} and ${ss.last}"
    }
  }
}
