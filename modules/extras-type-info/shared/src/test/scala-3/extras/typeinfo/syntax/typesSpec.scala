package extras.typeinfo.syntax

import hedgehog.*
import hedgehog.runner.*

import scala.reflect.ClassTag

/** @author Kevin Lee
  * @since 2022-03-14
  */
object typesSpec extends Properties {
  override def tests: List[Test] = List(
    example("test A.typeFullName", testATypeFullName),
    example("test A.typeName", testATypeName),
    example("test (A with ClassTag[A]).nestedTypeName", testAWithClassTagANestedTypeName), // USE IT
    example("test ClassTag[A].nestedTypeName", testClassTagANestedTypeName), // USE IT
  )

  private val thisClassName = getClass.getName.stripSuffix("$")

  def testATypeFullName: Result = {
    import SomeTestTypes.*
    import extras.typeinfo.syntax.types.*
    val expected1 = s"$thisClassName.SomeTestTypes.Aaa.Bbb"
    val actual1   = Aaa.bbb(123).typeFullName

    val expected2 = s"$thisClassName.SomeTestTypes.Aaa.Ccc"
    val actual2   = Aaa.ccc.typeFullName

    val expected3 = s"$thisClassName.SomeTestTypes.Something"
    val actual3   = Something("abc").typeFullName

    val expected4 = s"$thisClassName.SomeTestTypes.Something"
    val actual4   = Something(List("abc")).typeFullName

    val expected5 = s"$thisClassName.SomeTestTypes"
    val actual5   = SomeTestTypes.typeFullName

    val expected6 = s"$thisClassName.SomeTestTypes.Ccc"
    val actual6   = SomeTestTypes.Ccc("ABC").typeFullName

    Result.all(
      List(
        (actual1 ==== expected1).log("Failed: actual1 ==== expected1"),
        (actual2 ==== expected2).log("Failed: actual2 ==== expected2"),
        (actual3 ==== expected3).log("Failed: actual3 ==== expected3"),
        (actual4 ==== expected4).log("Failed: actual4 ==== expected4"),
        (actual5 ==== expected5).log("Failed: actual5 ==== expected5"),
        (actual6 ==== expected6).log("Failed: actual6 ==== expected6"),
      )
    )
  }

  def testATypeName: Result = {
    import SomeTestTypes.*
    import extras.typeinfo.syntax.types.*
    val expected1 = "Aaa.Bbb"
    val actual1   = Aaa.bbb(123).nestedTypeName

    val expected2 = "Aaa.Ccc"
    val actual2   = Aaa.ccc.nestedTypeName

    val expected3 = "SomeTestTypes.Something"
    val actual3   = Something("abc").nestedTypeName

    val expected4 = "SomeTestTypes.Something"
    val actual4   = Something(List("abc")).nestedTypeName

    val expected5 = s"${this.getClass.getSimpleName.stripSuffix("$")}.SomeTestTypes"
    val actual5   = SomeTestTypes.nestedTypeName

    val expected6 = "SomeTestTypes.Ccc"
    val actual6   = SomeTestTypes.Ccc("ABC").nestedTypeName

    Result.all(
      List(
        (actual1 ==== expected1).log("Failed: actual1 ==== expected1"),
        (actual2 ==== expected2).log("Failed: actual2 ==== expected2"),
        (actual3 ==== expected3).log("Failed: actual3 ==== expected3"),
        (actual4 ==== expected4).log("Failed: actual4 ==== expected4"),
        (actual5 ==== expected5).log("Failed: actual5 ==== expected5"),
        (actual6 ==== expected6).log("Failed: actual6 ==== expected6"),
      )
    )
  }

  def testAWithClassTagANestedTypeName: Result = {
    import SomeTestTypes.*
    import extras.typeinfo.syntax.types.*
    val expected1 = "Aaa.Bbb"
    val actual1   = Aaa.bbb(123).nestedRuntimeClassName

    val expected2 = "Aaa.Ccc"
    val actual2   = Aaa.ccc.nestedRuntimeClassName

    val expected3 = "SomeTestTypes.Something"
    val actual3   = Something("abc").nestedRuntimeClassName

    val expected4 = "SomeTestTypes.Something"
    val actual4   = Something(List("abc")).nestedRuntimeClassName

    val expected5 = s"${this.getClass.getSimpleName.stripSuffix("$")}.SomeTestTypes"
    val actual5   = SomeTestTypes.nestedRuntimeClassName

    /* using reflection doesn't work for opaque type because there's no runtime type for opaque type */
    val expected6 = "lang.String"
    val actual6   = SomeTestTypes.Ccc("ABC").nestedRuntimeClassName

    Result.all(
      List(
        (actual1 ==== expected1).log("Failed: actual1 ==== expected1"),
        (actual2 ==== expected2).log("Failed: actual2 ==== expected2"),
        (actual3 ==== expected3).log("Failed: actual3 ==== expected3"),
        (actual4 ==== expected4).log("Failed: actual4 ==== expected4"),
        (actual5 ==== expected5).log("Failed: actual5 ==== expected5"),
        (actual6 ==== expected6).log("Failed: actual6 ==== expected6"),
      )
    )
  }

  def testClassTagANestedTypeName: Result = {
    import SomeTestTypes.*
    import extras.typeinfo.syntax.types.*

    def foo[A](a: A)(using ct: ClassTag[A]): String = ct.nestedRuntimeClassName

    val expected1 = "SomeTestTypes.Aaa"
    val actual1   = foo(Aaa.bbb(123))
    val expected2 = "SomeTestTypes.Aaa"
    val actual2   = foo(Aaa.ccc)
    val expected3 = "SomeTestTypes.Something"
    val actual3   = foo(Something("abc"))
    val expected4 = "SomeTestTypes.Something"
    val actual4   = foo(Something(List("abc")))
    val expected5 = s"${this.getClass.getSimpleName.stripSuffix("$")}.SomeTestTypes"
    val actual5   = foo(SomeTestTypes)

    val expected1Data = "Aaa.Bbb"
    val actual1Data   = foo(Aaa.Bbb(123))
    val expected2Data = "Aaa.Ccc"
    val actual2Data   = foo(Aaa.Ccc)
    val expected3Data = "SomeTestTypes.Something"
    val actual3Data   = foo(Something("abc"))
    val expected4Data = "SomeTestTypes.Something"
    val actual4Data   = foo(Something(List("abc")))
    val expected5Data = s"${this.getClass.getSimpleName.stripSuffix("$")}.SomeTestTypes"
    val actual5Data   = foo(SomeTestTypes)

    /* using reflection doesn't work for opaque type because there's no runtime type for opaque type */
    val expected6a = "lang.String"
    val actual6a   = foo(SomeTestTypes.Ccc("ABC"))

    val expected6b = "SomeTestTypes.Ccc"
    val actual6b   = foo(SomeTestTypes.Ccc)

    Result.all(
      List(
        (actual1 ==== expected1).log("Failed: actual1 ==== expected1"),
        (actual2 ==== expected2).log("Failed: actual2 ==== expected2"),
        (actual3 ==== expected3).log("Failed: actual3 ==== expected3"),
        (actual4 ==== expected4).log("Failed: actual4 ==== expected4"),
        (actual5 ==== expected5).log("Failed: actual5 ==== expected5"),
        (actual1Data ==== expected1Data).log("Failed: actual1Data ==== expected1Data"),
        (actual2Data ==== expected2Data).log("Failed: actual2Data ==== expected2Data"),
        (actual3Data ==== expected3Data).log("Failed: actual3Data ==== expected3Data"),
        (actual4Data ==== expected4Data).log("Failed: actual4Data ==== expected4Data"),
        (actual5Data ==== expected5Data).log("Failed: actual5Data ==== expected5Data"),
        (actual6a ==== expected6a).log("Failed: actual6a ==== expected6a"),
        (actual6b ==== expected6b).log("Failed: actual6b ==== expected6b"),
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

    type Ccc = Ccc.Ccc
    object Ccc {
      opaque type Ccc = String
      def apply(ccc: String): Ccc = ccc

      given cccCanEqual: CanEqual[Ccc, Ccc] = CanEqual.derived

      extension (ccc: Ccc) {
        def value: String = ccc
      }
    }

  }

}
