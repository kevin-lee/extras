package extras.refinement.syntax

import eu.timepit.refined.types.string.NonEmptyString
import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2022-09-07
  */
object stringSpec extends Properties {
  override def tests: List[Test] = allTests(extras.refinement.syntax.string)

  def allTests(stringSyntax: string): List[Test] = List(
    property(
      "NonEmptyString(value1) ++ NonEmptyString(value2) should return NonEmptyString(value1 + value2)",
      testRefinementNonEmptyStringSyntaxConcat1(stringSyntax),
    ),
    property(
      """NonEmptyString(value1) ++ NonEmptyString(" ") ++ NonEmptyString(value2) should return NonEmptyString(value1 + value2)""",
      testRefinementNonEmptyStringSyntaxConcat1(stringSyntax),
    ),
  )

  def testRefinementNonEmptyStringSyntaxConcat1(stringSyntax: string): Property = for {
    nonEmptyString1 <-
      Gen.string(Gen.unicode, Range.linear(1, 10)).map(NonEmptyString.unsafeFrom).log("nonEmptyString1")
    nonEmptyString2 <-
      Gen.string(Gen.unicode, Range.linear(1, 10)).map(NonEmptyString.unsafeFrom).log("nonEmptyString2")
  } yield {
    import stringSyntax._
    val expected = NonEmptyString.unsafeFrom(nonEmptyString1.value + nonEmptyString2.value)
    val actual   = nonEmptyString1 ++ nonEmptyString2
    (actual ==== expected).log("Failed: nonEmptyString1 ++ nonEmptyString2")
  }

  def testRefinementNonEmptyStringSyntaxConcat2(stringSyntax: string): Property = for {
    nonEmptyString1 <-
      Gen.string(Gen.unicode, Range.linear(1, 10)).map(NonEmptyString.unsafeFrom).log("nonEmptyString1")
    nonEmptyString2 <-
      Gen.string(Gen.unicode, Range.linear(1, 10)).map(NonEmptyString.unsafeFrom).log("nonEmptyString2")
  } yield {
    import stringSyntax._
    val expected = NonEmptyString.unsafeFrom(nonEmptyString1.value + " " + nonEmptyString2.value)
    val actual   = nonEmptyString1 ++ NonEmptyString(" ") ++ nonEmptyString2
    (actual ==== expected).log("""Failed: nonEmptyString1 ++ NonEmptyString(" ") ++ nonEmptyString2""")
  }

}
