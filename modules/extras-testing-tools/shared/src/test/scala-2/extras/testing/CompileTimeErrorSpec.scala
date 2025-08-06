package extras.testing

import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2025-08-06
  */
object CompileTimeErrorSpec extends Properties {

  override def tests: List[Test] = List(
    example("test CompileTimeError.from - no error case", testCompileTimeErrorNoErrorCase),
    example("test CompileTimeError.from - error case", testCompileTimeErrorErrorCase),
  )

  def testCompileTimeErrorNoErrorCase: Result = {
    val actual = CompileTimeError.from(
      """
      val n = 2
      println("n * 2")
      """
    )

    actual ==== ""
  }

  def testCompileTimeErrorErrorCase: Result = {
    val actual = CompileTimeError.from("n * 2")

    val expected =
      """error: not found: value n
        |n * 2
        |^""".stripMargin
    actual ==== expected
  }

}
