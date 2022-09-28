package extras.hedgehog.ce3

import Ticker.TestContext

import scala.annotation.implicitNotFound

/** Moved from CatsEffectRunner
  * @param ctx
  */
@implicitNotFound(
  "could not find an instance of Ticker; try using `withIO { implicit ticker =>` " +
    "or create one by yourself e.g.) `implicit val ticker: Ticker = Ticker.withNewTestContext()`"
)
final case class Ticker(ctx: TestContext) extends AnyVal

object Ticker {
  type TestContext = cats.effect.kernel.testkit.TestContext
  val TestContext: cats.effect.kernel.testkit.TestContext.type = cats.effect.kernel.testkit.TestContext

  def withNewTestContext(): Ticker = Ticker(TestContext())
}
