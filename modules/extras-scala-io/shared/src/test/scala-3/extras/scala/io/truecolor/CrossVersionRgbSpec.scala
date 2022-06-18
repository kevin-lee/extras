package extras.scala.io.truecolor

import hedgehog.runner._
import hedgehog._

/** @author Kevin Lee
  * @since 2022-06-19
  */
trait CrossVersionRgbSpec {

  protected def corssVersionTests: List[Test] = List(
    example("Rgb(valid rgbInt) should return Rgb(rgbInt)", testRgbApplyValid),
//    example("Rgb(-1)", testRgbApplyLessThan0),
//    example("Rgb(0xffffff + 1)", testRgbApplyGreaterThan0xffffff),
  )

  def testRgbApplyValid: Result = {
    Result.all(
      List(
        Rgb(0x000000) ==== Rgb.unsafeFromInt(0x000000),
        Rgb(0x000001) ==== Rgb.unsafeFromInt(0x000001),
        Rgb(0x0000ff) ==== Rgb.unsafeFromInt(0x0000ff),
        Rgb(0x00ff00) ==== Rgb.unsafeFromInt(0x00ff00),
        Rgb(0xff0000) ==== Rgb.unsafeFromInt(0xff0000),
        Rgb(0xfffffe) ==== Rgb.unsafeFromInt(0xfffffe),
        Rgb(0xffffff) ==== Rgb.unsafeFromInt(0xffffff),
      )
    )
  }

//  def testRgbApplyLessThan0: Result = {
//    val rgb = Rgb(-1)
//    Result.success
//  }
//
//  def testRgbApplyGreaterThan0xffffff: Result = {
//    val rgb = Rgb(0xffffff + 1)
//    Result.success
//  }

}
