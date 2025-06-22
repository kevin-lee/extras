package extras.cats.testing

import scala.concurrent.ExecutionContext

final class ExecutionContextProvider {
  given ec: ExecutionContext = org.scalajs.macrotaskexecutor.MacrotaskExecutor.Implicits.global
}
