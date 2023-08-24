package extras.strings.syntax

/** @author Kevin Lee
  * @since 2023-08-22
  */
trait strings {
  implicit def stringListOps(ss: Seq[String]): strings.StringListOps = new strings.StringListOps(ss)
}
object strings extends strings {
  @SuppressWarnings(Array("org.wartremover.warts.IterableOps"))
  final class StringListOps(private val ss: Seq[String]) extends AnyVal {
    def commaAnd: String = ss match {
      case Seq() => ""
      case Seq(s) => s
      case Seq(s1, s2) => s"$s1 and $s2"
      case _ => s"${ss.dropRight(1).mkString(", ")} and ${ss.last}"
    }
  }
}
