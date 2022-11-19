package extras.typeinfo.syntax

import hedgehog._
import hedgehog.runner._

import scala.reflect.ClassTag

/** @author Kevin Lee
  * @since 2022-03-14
  */
object tagsSpec extends Properties {
  override def tests: List[Test] = List(
    example("test A.nestedTypeName[WeakTypeTag[A]]", testANestedTypeNameWeakTypeTagA),
    example("test weakTypeTag[A].nestedTypeName", testWeakTypeTagANestedTypeName),
    example("test (A with ClassTag[A]).nestedTypeName", testAWithClassTagANestedTypeName),
    example("test ClassTag[A].nestedTypeName", testClassTagANestedTypeName)
  )

  def testANestedTypeNameWeakTypeTagA: Result = {
    import SomeTestTypes._
    import extras.typeinfo.syntax.types._
    val expected1t = "SomeTestTypes.Aaa"
    val actual1t   = Aaa.bbb(123).nestedTypeName
    val expected1  = "Aaa.Bbb"
    val actual1    = Aaa.Bbb(123).nestedTypeName
    val expected2t = "SomeTestTypes.Aaa"
    val actual2t   = Aaa.ccc.nestedTypeName
    val expected2  = "Aaa.Ccc"
    val actual2    = Aaa.Ccc.nestedTypeName
    val expected3  = "SomeTestTypes.Something[String]"
    val actual3    = Something("abc").nestedTypeName
    val expected4  = s"${this.getClass.getSimpleName.stripSuffix("$")}.SomeTestTypes"
    val actual4    = SomeTestTypes.nestedTypeName
    Result.all(
      List(
        (actual1t ==== expected1t).log("Failed actual1t ==== expected1t"),
        (actual1 ==== expected1).log("Failed actual1 ==== expected1"),
        (actual2t ==== expected2t).log("Failed actual2t ==== expected2t"),
        (actual2 ==== expected2).log("Failed actual2 ==== expected2"),
        (actual3 ==== expected3).log("Failed actual3 ==== expected3"),
        (actual4 ==== expected4).log("Failed actual4 ==== expected4")
      )
    )
  }

  def testWeakTypeTagANestedTypeName: Result = {
    import SomeTestTypes._
    import extras.typeinfo.syntax.types._

    import scala.reflect.runtime.universe._
    val expected1 = "Aaa.Bbb"
    val actual1   = weakTypeTag[Aaa.Bbb].nestedTypeName
    val expected2 = "Aaa.Ccc"
    val actual2   = weakTypeTag[Aaa.Ccc.type].nestedTypeName
    val expected3 = "SomeTestTypes.Something[String]"
    val actual3   = weakTypeTag[Something[String]].nestedTypeName
    val expected4 = s"${this.getClass.getSimpleName.stripSuffix("$")}.SomeTestTypes"
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

  def testAWithClassTagANestedTypeName: Result = {
    import SomeTestTypes._
    import extras.typeinfo.syntax.types._
    val expected1 = "Aaa.Bbb"
    val actual1   = Aaa.bbb(123).nestedRuntimeClassName
    val expected2 = "Aaa.Ccc"
    val actual2   = Aaa.ccc.nestedRuntimeClassName
    val expected3 = "SomeTestTypes.Something"
    val actual3   = Something("abc").nestedRuntimeClassName
    val expected4 = s"${this.getClass.getSimpleName.stripSuffix("$")}.SomeTestTypes"
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

  def testClassTagANestedTypeName: Result = {
    import SomeTestTypes._
    import extras.typeinfo.syntax.types._

    def foo[A: ClassTag](a: A)(implicit ct: ClassTag[A]): String = ct.nestedRuntimeClassName

    val expected1 = "SomeTestTypes.Aaa"
    val actual1   = foo(Aaa.bbb(123))
    val expected2 = "SomeTestTypes.Aaa"
    val actual2   = foo(Aaa.ccc)
    val expected3 = "SomeTestTypes.Something"
    val actual3   = foo(Something("abc"))
    val expected4 = s"${this.getClass.getSimpleName.stripSuffix("$")}.SomeTestTypes"
    val actual4   = foo(SomeTestTypes)

    val expected1Data = "Aaa.Bbb"
    val actual1Data   = foo(Aaa.Bbb(123))
    val expected2Data = "Aaa.Ccc"
    val actual2Data   = foo(Aaa.Ccc)
    val expected3Data = "SomeTestTypes.Something"
    val actual3Data   = foo(Something("abc"))
    val expected4Data = s"${this.getClass.getSimpleName.stripSuffix("$")}.SomeTestTypes"
    val actual4Data   = foo(SomeTestTypes)
    Result.all(
      List(
        actual1 ==== expected1,
        actual2 ==== expected2,
        actual3 ==== expected3,
        actual4 ==== expected4,
        actual1Data ==== expected1Data,
        actual2Data ==== expected2Data,
        actual3Data ==== expected3Data,
        actual4Data ==== expected4Data
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
