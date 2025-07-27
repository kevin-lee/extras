package extras.render

import hedgehog.*
import hedgehog.runner.*

import extras.render.syntax.*

/** @author Kevin Lee
  * @since 2025-07-27
  */
trait ScalaVersionSpecificSyntaxSpec extends Properties {
  import ScalaVersionSpecificSyntaxSpec.*

  val scalaVersionSpecificTests: List[Test] = List(
    property("test MyOwnType.render", testMyOwnTypeRender),
    property("test MyOwnTypeWithUnionType.render", testMyOwnTypeWithUnionTypeRender),
    property("test MyOwnTypeInsideCaseClass.render", testMyOwnTypeInsideCaseClassRender),
  )

  def testMyOwnTypeRender: Property =
    for {
      s <- Gen.string(Gen.unicodeAll, Range.linear(1, 100)).map(Name(_)).log("s")
    } yield {
      s.render ==== s
    }

  def testMyOwnTypeWithUnionTypeRender: Property =
    for {
      n           <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
      s           <- Gen.string(Gen.unicodeAll, Range.linear(1, 100)).log("s")
      myUnionType <-
        Gen.element1[CaseA | CaseB | CaseC.type](CaseA(n), CaseB(s), CaseC).map(MyUnionType(_)).log("myUnionType")
    } yield {
      myUnionType.render ==== (myUnionType.value match {
        case CaseA(n) => n.toString
        case CaseB(name) => name
        case CaseC => "CaseC"
      })
    }

  def testMyOwnTypeInsideCaseClassRender: Property =
    for {
      something <- Gen.string(Gen.unicodeAll, Range.linear(1, 100)).map(MyOwnCaseClass.Something(_)).log("something")
      myOwnCaseClass <- Gen.constant(MyOwnCaseClass(something)).log("myOwnCaseClass")
    } yield {
      myOwnCaseClass.render ==== s"MyOwnCaseClass(something=${myOwnCaseClass.something.value})"
    }

}
object ScalaVersionSpecificSyntaxSpec {
  type Name = Name.Type
  object Name {
    opaque type Type = String
    def apply(name: String): Name = name

    given nameCanEqual: CanEqual[Name, Name] = CanEqual.derived

    extension (name: Name) {
      def value: String = name
    }

    given RenderName: Render[Name] = _.value

  }

  final case class CaseA(n: Int)
  final case class CaseB(name: String)
  case object CaseC

  type MyUnionType = MyUnionType.Type
  object MyUnionType {
    opaque type Type = CaseA | CaseB | CaseC.type
    def apply(myUnionType: CaseA | CaseB | CaseC.type): MyUnionType = myUnionType

    given myUnionTypeCanEqual: CanEqual[MyUnionType, MyUnionType] = CanEqual.derived

    extension (myUnionType: MyUnionType) {
      def value: CaseA | CaseB | CaseC.type = myUnionType
    }

    given renderMyUnionType: Render[MyUnionType] with {
      override def render(a: MyUnionType): String = a match {
        case CaseA(n) => n.render
        case CaseB(name) => name.render
        case CaseC => "CaseC"
      }
    }
  }

  final case class MyOwnCaseClass(
    something: MyOwnCaseClass.Something
  )
  object MyOwnCaseClass {

    given renderMyOwnCaseClass: Render[MyOwnCaseClass] with {
      override def render(a: MyOwnCaseClass): String =
        s"MyOwnCaseClass(something=${a.something.render})"
    }

    /* This doesn't work with the existing render from the extension method of A using Render[A]
     */
//    extension (myOwnCaseClass: MyOwnCaseClass) {
//      def render: String = s"MyOwnCaseClass(something=${myOwnCaseClass.something.render})"
//    }

    type Something = Something.Type
    object Something {
      opaque type Type = String
      def apply(something: String): Something = something

      given somethingCanEqual: CanEqual[Something, Something] = CanEqual.derived

      extension (something: Something) {
        def value: String = something
      }

      given RenderSomething: Render[Something] with {
        override def render(a: Something): String = a.value
      }
    }

  }

}
