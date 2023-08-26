package extras.strings.syntax

import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2023-08-21
  */
object numericSpec extends Properties {
  override def tests: List[Test] = intsSpec.tests ++ longsSpec.tests

  object intsSpec {
    def tests: List[Test] = List(
      property("test Int.toOrdinal", testToOrdinal)
    )

    def testToOrdinal: Property =
      for {
        nAndExpected <- Gen
                          .int(Range.linear(0, Int.MaxValue))
                          .map(num => num -> ordinal(num.toLong))
                          .log("(n, expected)")
        (n, expected) = nAndExpected
      } yield {
        import extras.strings.syntax.numeric._

        val actual = n.toOrdinal
        actual ==== expected
      }

  }

  object longsSpec {
    def tests: List[Test] = List(
      property("test Long.toOrdinal", testToOrdinal)
    )

    def testToOrdinal: Property =
      for {
        nAndExpected <- Gen
                          .long(Range.linear(0L, Long.MaxValue))
                          .map(num => num -> ordinal(num))
                          .log("(n, expected)")
        (n, expected) = nAndExpected
      } yield {
        import extras.strings.syntax.numeric._

        val actual = n.toOrdinal
        actual ==== expected
      }

  }

  def ordinal(n: Long): String = {
    val suffixes = Array("th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th")

    val i = n % 100L
    val j = if (i > 10L && i < 20L) 0 else (n % 10L)
    n.toString + suffixes(j.toInt)
  }
}
