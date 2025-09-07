package extras.core.syntax

import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2022-06-25
  */
object stringsSpec extends Properties {
  override def tests: List[Test] = List(
    property("test String.encodeToUnicode", testEncodeToUnicode)
  )

  def testEncodeToUnicode: Property = for {
    s <- Gen.string(Gen.unicode, Range.linear(1, 100)).log("s")
  } yield {
    import strings._
    val expected = s
      .map(_.toInt)
      .map { c =>
        val hex  = c.toHexString
        val diff = 4 - hex.length
        "\\u" + ("0" * diff) + hex
      }
      .mkString
    val actual   = s.encodeToUnicode
    actual ==== expected
  }
}
