package extras.reflects.syntax

import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2022-03-14
  */
object reflectsSpec extends Properties {
  override def tests: List[Test] = List(
    example("test weakTypeTag[A].nestedTypeName", testWeakTypeTagANestedTypeName),
    example("test classTag[A].nestedTypeName", testClassTagANestedTypeName),
    example("test A.nestedTypeName", testANestedTypeName)
  )

  def testWeakTypeTagANestedTypeName: Result = {
    import SomeTestTypes._
    import extras.reflects.syntax.reflects._

    import scala.reflect.runtime.universe._
    val expected1 = "Aaa.Bbb"
    val actual1   = weakTypeTag[Aaa.Bbb].nestedTypeName
    val expected2 = "Aaa.Ccc"
    val actual2   = weakTypeTag[Aaa.Ccc.type].nestedTypeName
    val expected3 = "SomeTestTypes.Something[String]"
    val actual3   = weakTypeTag[Something[String]].nestedTypeName
    val expected4 = "syntax.SomeTestTypes"
    val actual4   = weakTypeTag[SomeTestTypes.type].nestedTypeName
    Result.all(
      List(
        actual1 ==== expected1,
        actual2 ==== expected2,
        actual3 ==== expected3,
        actual4 ==== expected4
      )
    )
  }

  def testClassTagANestedTypeName: Result = {
    import SomeTestTypes._
    import extras.reflects.syntax.reflects._
    val expected1 = "Aaa.Bbb"
    val actual1   = Aaa.bbb(123).nestedRuntimeClassName
    val expected2 = "Aaa.Ccc"
    val actual2   = Aaa.ccc.nestedRuntimeClassName
    val expected3 = "SomeTestTypes.Something"
    val actual3   = Something("abc").nestedRuntimeClassName
    val expected4 = "syntax.SomeTestTypes"
    val actual4   = SomeTestTypes.nestedRuntimeClassName
    Result.all(
      List(
        actual1 ==== expected1,
        actual2 ==== expected2,
        actual3 ==== expected3,
        actual4 ==== expected4
      )
    )
  }

  def testANestedTypeName: Result = {
    import SomeTestTypes._
    import extras.reflects.syntax.reflects._
    val expected1 = "Aaa.Bbb"
    val actual1   = Aaa.bbb(123).nestedClassName
    val expected2 = "Aaa.Ccc"
    val actual2   = Aaa.ccc.nestedClassName
    val expected3 = "SomeTestTypes.Something"
    val actual3   = Something("abc").nestedClassName
    val expected4 = "syntax.SomeTestTypes"
    val actual4   = SomeTestTypes.nestedClassName

    Result.all(
      List(
        actual1 ==== expected1,
        actual2 ==== expected2,
        actual3 ==== expected3,
        actual4 ==== expected4
      )
    )

  }

}

object SomeTestTypes {
  sealed trait Aaa
  object Aaa {
    final case class Bbb(n: Int) extends Aaa
    case object Ccc extends Aaa

    def bbb(n: Int): Aaa = Bbb(n)
    def ccc: Aaa         = Ccc
  }

  final case class Something[A](a: A)
}
