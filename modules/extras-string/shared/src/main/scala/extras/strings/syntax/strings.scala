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

    /** Join Seq[String] with , (comma) expect for the last and the second last one which are connected with the given conjunction.
      *
      * @example
      * {{{
      *   List.empty[String].commaWith("blah")
      *   // String = ""
      *
      *   List("").commaWith("blah")
      *   // String = ""
      *
      *   List("aaa").commaWith("blah")
      *   // String = "aaa"
      *
      *   List("aaa", "bbb").commaWith("blah")
      *   // String = "aaa blah bbb"
      *
      *   List("aaa", "bbb", "ccc").commaWith("blah")
      *   // String = "aaa, bbb blah ccc"
      *
      *   List("aaa", "bbb", "ccc", "ddd").commaWith("blah")
      *   // String = "aaa, bbb, ccc blah ddd"
      *
      * }}}
      */
    def commaWith(conjunction: String): String = ss match {
      case Seq() => ""
      case Seq(s) => s
      case Seq(s1, s2) => s"$s1 $conjunction $s2"
      case _ => s"${ss.dropRight(1).mkString(", ")} $conjunction ${ss.last}"
    }

    /** join Seq[String] with , (comma) expect for the last and the second last one which are connected with `and`.
      * @example
      * {{{
      *   List.empty[String].commaAnd
      *   // String = ""
      *
      *   List("").commaAnd
      *   // String = ""
      *
      *   List("aaa").commaAnd
      *   // String = "aaa"
      *
      *   List("aaa", "bbb").commaAnd
      *   // String = "aaa and bbb"
      *
      *   List("aaa", "bbb", "ccc").commaAnd
      *   // String = "aaa, bbb and ccc"
      *
      *   List("aaa", "bbb", "ccc", "ddd").commaAnd
      *   // String = "aaa, bbb, ccc and ddd"
      *
      * }}}
      */
    def commaAnd: String = commaWith("and")

    /** join a Seq[String] with , (comma) and the last and the second last elements should be also connected with and (Oxford comma / Harvard comma).
      *
      * @example
      * {{{
      *   List.empty[String].serialCommaAnd
      *   // String = ""
      *
      *   List("").serialCommaAnd
      *   // String = ""
      *
      *   List("aaa").serialCommaAnd
      *   // String = "aaa"
      *
      *   List("aaa", "bbb").serialCommaAnd
      *   // String = "aaa and bbb"
      *
      *   List("aaa", "bbb", "ccc").serialCommaAnd
      *   // String = "aaa, bbb, and ccc"
      *
      *   List("aaa", "bbb", "ccc", "ddd").serialCommaAnd
      *   // String = "aaa, bbb, ccc, and ddd"
      *
      * }}}
      */
    @SuppressWarnings(Array("org.wartremover.warts.IterableOps"))
    def serialCommaAnd: String = ss match {
      case Seq() => ""
      case Seq(s) => s
      case Seq(s1, s2) => s"$s1 and $s2"
      case _ => s"${ss.dropRight(1).mkString(", ")}, and ${ss.last}"
    }
  }
}
