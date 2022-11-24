package extras.render.refined

import eu.timepit.refined.types.string.NonEmptyString
import extras.render.refined.data.TestData.Name
import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2022-11-22
  */
object packageSpec extends Properties {
  override def tests: List[Test] = List(
    property("testRefinedRender", testRefinedRender)
  )

  def testRefinedRender: Property =
    for {
      s    <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).map(NonEmptyString.unsafeFrom).log("s")
      name <- Gen.constant(Name(s)).log("name")
    } yield {
      import extras.render.syntax._
      val actual   = name.render
      val expected = s.value
      actual ==== expected
    }

}
