package extras.strings.syntax

import hedgehog.runner._

/** @author Kevin Lee
  * @since 2023-10-03
  */
object allSpec extends Properties {
  override def tests: List[Test] =
    numericSpec.allNumericSpec(extras.strings.syntax.all).tests ++
      stringsSpec.allStringsSpec(extras.strings.syntax.all).tests
}
