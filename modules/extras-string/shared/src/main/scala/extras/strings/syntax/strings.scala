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

    /** Extension method for a Seq[String], providing a way to join the elements with a comma and a given conjunction.
      * It joins String values with commas and uses the given conjunction before the last element.
      * @example
      * {{{
      *   List.empty[String].commaWith("BLAH")
      *   // String = ""
      *
      *   List("").commaWith("BLAH")
      *   // String = ""
      *
      *   List("aaa").commaWith("BLAH")
      *   // String = "aaa"
      *
      *   List("aaa", "bbb").commaWith("BLAH")
      *   // String = "aaa BLAH bbb"
      *
      *   List("aaa", "bbb", "ccc").commaWith("BLAH")
      *   // String = "aaa, bbb BLAH ccc"
      *
      *   List("aaa", "bbb", "ccc", "ddd").commaWith("BLAH")
      *   // String = "aaa, bbb, ccc BLAH ddd"
      *
      * }}}
      */
    def commaWith(conjunction: String): String = ss match {
      case Seq() => ""
      case Seq(s) => s
      case Seq(s1, s2) => s"$s1 $conjunction $s2"
      case _ => s"${ss.dropRight(1).mkString(", ")} $conjunction ${ss.last}"
    }

    /** Extension method for a Seq[String], providing a way to join the elements with a serial comma and a given conjunction.
      *
      * It joins String values with commas and uses the given conjunction before the last element.
      *
      * This method employs the serial comma (also known as the Oxford comma),
      * which means it always inserts a comma before the conjunction unless there are only two elements.
      *
      * @example
      * {{{
      *   List.empty[String].serialCommaWith("BLAH")
      *   // String = ""
      *
      *   List("").serialCommaWith("BLAH")
      *   // String = ""
      *
      *   List("aaa").serialCommaWith("BLAH")
      *   // String = "aaa"
      *
      *   List("aaa", "bbb").serialCommaWith("BLAH")
      *   // String = "aaa BLAH bbb"
      *
      *   List("aaa", "bbb", "ccc").serialCommaWith("BLAH")
      *   // String = "aaa, bbb, BLAH ccc"
      *
      *   List("aaa", "bbb", "ccc", "ddd").serialCommaWith("BLAH")
      *   // String = "aaa, bbb, ccc, BLAH ddd"
      *
      * }}}
      */
    @SuppressWarnings(Array("org.wartremover.warts.IterableOps"))
    def serialCommaWith(conjunction: String): String = ss match {
      case Seq() => ""
      case Seq(s) => s
      case Seq(s1, s2) => s"$s1 $conjunction $s2"
      case _ => s"${ss.dropRight(1).mkString(", ")}, $conjunction ${ss.last}"
    }

    /** Format Seq[String] into a human-readable list using comma and the conjunction `and`.
      * It separates elements by commas and uses the term `and` before the last element.
      *
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

    /** Format Seq[String] into a human-readable list using comma and the conjunction `and`.
      * It separates elements by commas and uses the term `and` before the last element following the "Oxford comma" style.
      * e.g.) "aaa, bbb, and ccc".
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
    def serialCommaAnd: String = serialCommaWith("and")

    /** Format Seq[String] into a human-readable list using comma and the conjunction `or`.
      * It separates elements by commas and uses the term `or` before the last element.
      * @example
      * {{{
      *   List.empty[String].commaOr
      *   // String = ""
      *
      *   List("").commaOr
      *   // String = ""
      *
      *   List("aaa").commaOr
      *   // String = "aaa"
      *
      *   List("aaa", "bbb").commaOr
      *   // String = "aaa or bbb"
      *
      *   List("aaa", "bbb", "ccc").commaOr
      *   // String = "aaa, bbb or ccc"
      *
      *   List("aaa", "bbb", "ccc", "ddd").commaOr
      *   // String = "aaa, bbb, ccc or ddd"
      *
      * }}}
      */
    def commaOr: String = commaWith("or")

    /** Format Seq[String] into a human-readable list using comma and the conjunction `or`.
      * It separates elements by commas and uses the term `or` before the last element following the "Oxford comma" style.
      * e.g.) "aaa, bbb, or ccc".
      *
      * @example
      * {{{
      *   List.empty[String].serialCommaOr
      *   // String = ""
      *
      *   List("").serialCommaOr
      *   // String = ""
      *
      *   List("aaa").serialCommaOr
      *   // String = "aaa"
      *
      *   List("aaa", "bbb").serialCommaOr
      *   // String = "aaa or bbb"
      *
      *   List("aaa", "bbb", "ccc").serialCommaOr
      *   // String = "aaa, bbb, or ccc"
      *
      *   List("aaa", "bbb", "ccc", "ddd").serialCommaOr
      *   // String = "aaa, bbb, ccc, or ddd"
      *
      * }}}
      */
    def serialCommaOr: String = serialCommaWith("or")

  }

}
