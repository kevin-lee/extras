package extras.scala.io

import hedgehog.Gen

/** @author Kevin Lee
  * @since 2021-09-19
  */
object ColorGens {
  def genColor: Gen[(Color, String)] = Gen.elementUnsafe(ColorTestUtils.colors)
}
