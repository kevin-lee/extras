package extras.refinement.syntax

import hedgehog.runner._

/** @author Kevin Lee
  * @since 2022-09-07
  */
object allSpec extends Properties {
  override def tests: List[Test] =
    refinementSpec.allTests(extras.refinement.syntax.all) ++
      stringSpec.allTests(extras.refinement.syntax.all)
}
