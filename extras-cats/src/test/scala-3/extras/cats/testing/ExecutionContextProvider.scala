package extras.cats.testing

import extras.concurrent.testing.ConcurrentSupport
import extras.concurrent.testing.types.ErrorLogger

import java.util.concurrent.ExecutorService
import scala.concurrent.ExecutionContext

final class ExecutionContextProvider {
  val es: ExecutorService    = ConcurrentSupport.newExecutorService(2)
  given ec: ExecutionContext =
    ConcurrentSupport.newExecutionContextWithLogger(es, ErrorLogger.printlnExecutionContextErrorLogger)
}
