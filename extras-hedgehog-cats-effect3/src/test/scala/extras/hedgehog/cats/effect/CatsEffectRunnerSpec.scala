package extras.hedgehog.cats.effect

import cats.effect.IO
import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2021-12-12
  */
object CatsEffectRunnerSpec extends Properties {

  override def tests: List[Test] = List(
    property("testCatsEffectRunner", testCatsEffectRunner)
  )

  def testCatsEffectRunner: Property = for {
    n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")
  } yield {
    import CatsEffectRunner._
    implicit val ticker: Ticker = Ticker.withNewTestContext()

    val expected = n
    val actual   = IO(n)

    actual.completeAs(expected)
  }

}
