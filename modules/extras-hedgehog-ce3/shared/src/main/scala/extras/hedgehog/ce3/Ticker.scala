package extras.hedgehog.ce3

import Ticker.TestContext

/** Moved from CatsEffectRunner
  * @param ctx
  */
final case class Ticker(ctx: TestContext)

object Ticker {
  type TestContext = cats.effect.kernel.testkit.TestContext
  val TestContext: cats.effect.kernel.testkit.TestContext.type = cats.effect.kernel.testkit.TestContext

  def withNewTestContext(): Ticker = Ticker(TestContext())
}
