package extras.reflects.syntax

import extras.reflects.syntax.classes.{ASyntaxWithClass, ClassSyntax}

/** @author Kevin Lee
  * @since 2022-03-14
  */
trait classes {

  implicit def classSyntax[A](a: Class[A]): ClassSyntax[A] = new ClassSyntax[A](a)

  implicit def aSyntaxWithClass[A](a: A): ASyntaxWithClass[A] = new ASyntaxWithClass[A](a)
}

object classes extends classes {

  final class ClassSyntax[A](private val aClass: Class[A]) extends AnyVal {
    def nestedClassName: String = {
      val className = aClass.getName.stripSuffix("$")

      val splitByDollarSign = className.split("\\$")
      if (splitByDollarSign.length > 1) {
        val theTypeName = splitByDollarSign.last
        s"${splitByDollarSign.init.last.split("\\.").last}.$theTypeName"
      } else {
        className.split("\\.").takeRight(2).mkString(".")
      }
    }
  }

  final class ASyntaxWithClass[A](private val a: A) extends AnyVal {

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
