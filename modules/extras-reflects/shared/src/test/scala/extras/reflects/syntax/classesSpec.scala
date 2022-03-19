package extras.reflects.syntax

import hedgehog._
import hedgehog.runner._

/** @author Kevin Lee
  * @since 2022-03-14
  */
object classesSpec extends Properties {
  override def tests: List[Test] = List(
    example("test A.nestedTypeName", testANestedClassName)
  )

  def testANestedClassName: Result = {
    import SomeTestTypes._
    import extras.reflects.syntax.classes._
    val expected1 = "Aaa.Bbb"
    val actual1a  = Aaa.bbb(123).nestedClassName
    val expected2 = "Aaa.Ccc"
    val actual2a  = Aaa.ccc.nestedClassName
    val expected3 = "SomeTestTypes.Something"
    val actual3a  = Something("abc").nestedClassName
    val expected4 = s"${this.getClass.getSimpleName.stripSuffix("$")}.SomeTestTypes"
    val actual4a  = SomeTestTypes.nestedClassName

    val actual1 = Aaa.Bbb.getClass.nestedClassName
    val actual2 = Aaa.Ccc.getClass.nestedClassName
    val actual3 = Something.getClass.nestedClassName
    val actual4 = SomeTestTypes.getClass.nestedClassName

    Result.all(
      List(
        actual1a ==== expected1,
        actual2a ==== expected2,
        actual3a ==== expected3,
        actual4a ==== expected4,
        actual1 ==== expected1,
        actual2 ==== expected2,
        actual3 ==== expected3,
        actual4 ==== expected4
      )
    )

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

}
