package extras.reflects.syntax

import extras.reflects.syntax.reflects.{ClassSyntax, ClassTagSyntax, WeakTypeTagSyntax}

import scala.reflect.ClassTag
import scala.reflect.runtime.universe._

/** @author Kevin Lee
  * @since 2022-03-14
  */
trait reflects {
  implicit def weakTypeTagSyntax[A](weakTypeTag: WeakTypeTag[A]): WeakTypeTagSyntax[A] =
    new WeakTypeTagSyntax[A](weakTypeTag)

  implicit def classTagSyntax[A](a: A): ClassTagSyntax[A] = new ClassTagSyntax[A](a: A)

  implicit def ClassSyntax[A](a: A): ClassSyntax[A] = new ClassSyntax[A](a: A)
}

object reflects extends reflects {

  final class WeakTypeTagSyntax[A](private val weakTypeTag: WeakTypeTag[A]) extends AnyVal {
    def nestedTypeName: String =
      weakTypeTag
        .tpe
        .toString
        .stripSuffix(".type")
        .split("\\.")
        .takeRight(2)
        .mkString(".")
  }

  final class ClassTagSyntax[A](private val a: A) extends AnyVal {
    def nestedRuntimeClassName: String = {
      val runtimeClass = ClassTag[A](a.getClass).runtimeClass

      val className         = runtimeClass.getTypeName.stripSuffix("$")
      val splitByDollarSign = className.split("\\$")

      if (splitByDollarSign.length > 1) {
        val theTypeName = splitByDollarSign.last
        s"${splitByDollarSign.init.last.split("\\.").last}.$theTypeName"
      } else {
        className.split("\\.").takeRight(2).mkString(".")
      }
    }
  }

  final class ClassSyntax[A](private val a: A) extends AnyVal {
    def nestedClassName: String = {
      val className = a.getClass.getName.stripSuffix("$")

      val splitByDollarSign = className.split("\\$")
      if (splitByDollarSign.length > 1) {
        val theTypeName = splitByDollarSign.last
        s"${splitByDollarSign.init.last.split("\\.").last}.$theTypeName"
      } else {
        className.split("\\.").takeRight(2).mkString(".")
      }
    }
  }

}
