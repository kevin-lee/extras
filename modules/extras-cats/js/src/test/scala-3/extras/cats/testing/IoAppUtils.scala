package extras.cats.testing

import cats.syntax.all.*
import cats.effect.unsafe.{IORuntime, IORuntimeConfig}

import scala.concurrent.ExecutionContext

/** @author Kevin Lee
  * @since 2021-07-22
  */
object IoAppUtils {

  def runtime(using executionContext: ExecutionContext): IORuntime =
    runtimeWithExecutionContext(executionContext.some)

  def runtimeWithExecutionContext(maybeExecutionContext: Option[ExecutionContext]): IORuntime = {
    lazy val runtime: IORuntime = {

      val ec = maybeExecutionContext.getOrElse(IORuntime.defaultComputeExecutionContext)

      val scheduler = IORuntime.defaultScheduler

      IORuntime(
        ec,
        ec,
        scheduler,
        { () => },
        IORuntimeConfig(),
      )
    }
    runtime
  }
}
