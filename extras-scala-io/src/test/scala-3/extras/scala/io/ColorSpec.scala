package extras.scala.io

import hedgehog.*
import hedgehog.runner.*

/** @author Kevin Lee
  * @since 2021-09-19
  */
object ColorSpec extends Properties {
  
  override def tests: List[Test] = List(
    example("test all Color.toAnsi", ColorTestUtils.testColorToAnsi)
  )

}
