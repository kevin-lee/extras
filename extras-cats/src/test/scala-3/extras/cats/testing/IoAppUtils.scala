package extras.cats.testing

import cats.effect.unsafe.{IORuntime, IORuntimeConfig}
import extras.concurrent.testing.ConcurrentSupport

import java.util.concurrent.ExecutorService

/** @author Kevin Lee
  * @since 2021-07-22
  */
object IoAppUtils {

  def runtime(es: ExecutorService): IORuntime = {
    lazy val runtime: IORuntime = {
//      val computeWorkerThreadCount = Math.max(2, Runtime.getRuntime().availableProcessors() >> 2)
//      val (compute, compDown) =
//        IORuntime.createDefaultComputeThreadPool(runtime, threads = computeWorkerThreadCount)

      val ec = ConcurrentSupport.newExecutionContextWithLogger(es, println(_))

      val (blocking, blockDown) =
        IORuntime.createDefaultBlockingExecutionContext()

      val (scheduler, schedDown) =
        IORuntime.createDefaultScheduler()

      IORuntime(
        ec,
        blocking,
        scheduler,
        { () =>
          es.shutdown()
          blockDown()
          schedDown()
        },
        IORuntimeConfig()
      )
    }
    runtime
  }
}
