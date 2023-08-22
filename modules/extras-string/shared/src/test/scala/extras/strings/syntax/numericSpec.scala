package extras.strings.syntax

import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2023-08-21
  */
object numericSpec extends Properties {
  override def tests: List[Test] = intsSpec.tests

  object intsSpec {
    def tests: List[Test] = List(
      property("test Int.toOrdinal", testToOrdinal)
    )

    def testToOrdinal: Property =
      for {
        (n, expected) <- Gen
                           .int(Range.linear(0, Int.MaxValue))
                           .map(num => num -> ordinal(num))
                           .log("(n, expected)")
      } yield {
        import extras.strings.syntax.numeric._

        val actual = n.toOrdinal
        actual ==== expected
      }

  }

  def ordinal(n: Int): String = {
    val suffixes = Array("th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th")
    val i        = n % 100
    val j        = if (i > 10 && i < 20) 0 else (n % 10)
    n.toString + suffixes(j)
  }
}
