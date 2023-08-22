package extras.strings.syntax

/** @author Kevin Lee
  * @since 2023-08-21
  */
trait numeric {
  implicit def ints(n: Int): numeric.Ints = new numeric.Ints(n)

  implicit def longs(n: Long): numeric.Longs = new numeric.Longs(n)
}
object numeric extends numeric {
  final class Ints(private val n: Int) extends AnyVal {
    @SuppressWarnings(Array("org.wartremover.warts.StringPlusAny"))
    def toOrdinal: String =
      n.toString +
        (n % 100 match {
          case 11 | 12 | 13 => "th"
          case _ =>
            n % 10 match {
              case 1 => "st"
              case 2 => "nd"
              case 3 => "rd"
              case _ => "th"
            }
        })
  }

  final class Longs(private val n: Long) extends AnyVal {
    @SuppressWarnings(Array("org.wartremover.warts.StringPlusAny"))
    def toOrdinal: String =
      n.toString +
        (n % 100L match {
          case 11L | 12L | 13L => "th"
          case _ =>
            n % 10L match {
              case 1L => "st"
              case 2L => "nd"
              case 3L => "rd"
              case _ => "th"
            }
        })
  }

}
