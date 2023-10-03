package extras.strings.syntax

import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2023-08-21
  */
object numericSpec extends Properties {
  override def tests: List[Test] = new allNumericSpec(extras.strings.syntax.numeric).tests

  final class allNumericSpec(numeric: extras.strings.syntax.numeric) {
    def tests: List[Test] =
      intsSpec(numeric).tests ++ longsSpec(numeric).tests
  }
  object allNumericSpec {
    def apply(numeric: extras.strings.syntax.numeric): allNumericSpec = new allNumericSpec(numeric)
  }

  final class intsSpec(numeric: extras.strings.syntax.numeric) {
    import numeric._

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

        val actual = n.toOrdinal
        actual ==== expected
      }

  }
  object intsSpec {
    def apply(numeric: extras.strings.syntax.numeric): intsSpec = new intsSpec(numeric)
  }

  final class longsSpec(numeric: extras.strings.syntax.numeric) {
    import numeric._

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

        val actual = n.toOrdinal
        actual ==== expected
      }

  }
  object longsSpec {
    def apply(numeric: extras.strings.syntax.numeric): longsSpec = new longsSpec(numeric)
  }

  def ordinal(n: Long): String = {
    val suffixes = Array("th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th")

    val i = n % 100L
    val j = if (i > 10L && i < 20L) 0 else (n % 10L)
    n.toString + suffixes(j.toInt)
  }
}
