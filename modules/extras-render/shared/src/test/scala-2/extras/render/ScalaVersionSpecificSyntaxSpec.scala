package extras.render

import hedgehog._
import hedgehog.runner._

import extras.render.syntax._

/** @author Kevin Lee
  * @since 2025-07-27
  */
trait ScalaVersionSpecificSyntaxSpec extends Properties {
  import ScalaVersionSpecificSyntaxSpec._

  val scalaVersionSpecificTests: List[Test] = List(
    property("test MyOwnType.render", testMyOwnTypeRender),
    property("test MyOwnTypeWithAdt.render", testMyOwnTypeWithAdtRender),
    property("test MyOwnTypeInsideCaseClass.render", testMyOwnTypeInsideCaseClassRender),
  )

  def testMyOwnTypeRender: Property =
    for {
      s <- Gen.string(Gen.unicodeAll, Range.linear(1, 100)).map(Name(_)).log("s")
    } yield {
      s.render ==== s.value
    }

  def testMyOwnTypeWithAdtRender: Property =
    for {
      n     <- Gen.int(Range.linear(0, Int.MaxValue)).log("n")
      s     <- Gen.string(Gen.unicodeAll, Range.linear(1, 100)).log("s")
      myAdt <-
        Gen.element1(MyAdt.caseA(n), MyAdt.caseB(s), MyAdt.caseC).log("myAdt")
    } yield {
      myAdt.render ==== (myAdt match {
        case MyAdt.CaseA(n) => n.toString
        case MyAdt.CaseB(name) => name
        case MyAdt.CaseC => "CaseC"
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
  final case class Name(value: String) extends AnyVal
  object Name {
    implicit val renderName: Render[Name] = _.value
  }

  sealed trait MyAdt
  object MyAdt {

    final case class CaseA(n: Int) extends MyAdt
    final case class CaseB(name: String) extends MyAdt
    case object CaseC extends MyAdt

    def caseA(n: Int): MyAdt       = CaseA(n)
    def caseB(name: String): MyAdt = CaseB(name)
    def caseC: MyAdt               = CaseC

    implicit val renderMyAdt: Render[MyAdt] = new Render[MyAdt] {
      override def render(a: MyAdt): String = a match {
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

    implicit val renderMyOwnCaseClass: Render[MyOwnCaseClass] = new Render[MyOwnCaseClass] {
      override def render(a: MyOwnCaseClass): String =
        s"MyOwnCaseClass(something=${a.something.render})"
    }

    /* This doesn't work with the existing render from the extension method of A using Render[A]
     */
//    extension (myOwnCaseClass: MyOwnCaseClass) {
//      def render: String = s"MyOwnCaseClass(something=${myOwnCaseClass.something.render})"
//    }

    final case class Something(value: String) extends AnyVal
    object Something {
      implicit val renderSomething: Render[Something] = new Render[Something] {
        override def render(a: Something): String = a.value
      }
    }

  }

}
