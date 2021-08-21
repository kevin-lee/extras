package extras.cats.testing

import extras.concurrent.testing.ConcurrentSupport

import java.util.concurrent.ExecutorService
import scala.concurrent.ExecutionContext

final class ExecutionContextProvider {
  val es: ExecutorService    = ConcurrentSupport.newExecutorService()
  given ec: ExecutionContext = ConcurrentSupport.newExecutionContextWithLogger(es, println(_))
}
