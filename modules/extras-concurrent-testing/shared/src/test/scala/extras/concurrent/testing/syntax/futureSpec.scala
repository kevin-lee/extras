package extras.concurrent.testing.syntax

import hedgehog.runner._

/** @author Kevin Lee
  * @since 2021-11-14
  */
object futureSpec extends Properties {

  override def tests: List[Test] = List(
    property("test future syntax Future(someValue).toValue", FutureSyntaxSpec.testToValue)
  )

}
