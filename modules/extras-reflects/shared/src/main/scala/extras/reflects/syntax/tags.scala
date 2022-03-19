package extras.reflects.syntax

import extras.reflects.syntax.tags.{ASyntaxWithTags, ClassTagSyntax, WeakTypeTagSyntax}

import scala.reflect.ClassTag
import scala.reflect.runtime.universe._

/** @author Kevin Lee
  * @since 2022-03-19
  */
trait tags {

  implicit def weakTypeTagSyntax[A](weakTypeTag: WeakTypeTag[A]): WeakTypeTagSyntax[A] =
    new WeakTypeTagSyntax[A](weakTypeTag)

  implicit def classTagSyntax[A](aClassTag: ClassTag[A]): ClassTagSyntax[A] = new ClassTagSyntax[A](aClassTag)

  implicit def aSyntaxWithTags[A](a: A): ASyntaxWithTags[A] = new ASyntaxWithTags[A](a)

}

object tags extends tags {

  final class WeakTypeTagSyntax[A](private val weakTypeTag: WeakTypeTag[A]) extends AnyVal {
    def nestedTypeName: String = {
      val typeName = weakTypeTag
        .tpe
        .toString
        .stripSuffix(".type")

      /* Logic to handle a.b.c.D.E[java.lang.Blah] case to simplify it to a.b.c.D.E[Blah] */
      val typeNameWithSimpleTypeParam =
        if (typeName.endsWith("]")) {
          val open = typeName.lastIndexOf("[")
          if (open >= 0) {
            val typeNameBeforeTypeParam = typeName.substring(0, open)
            val typeParam               = typeName.substring(open + 1, typeName.length - 1)
            val simpleTypeParam         = typeParam.split("\\.").takeRight(1).mkString
            s"$typeNameBeforeTypeParam[$simpleTypeParam]"
          } else {
            typeName
          }
        } else {
          typeName
        }
      typeNameWithSimpleTypeParam
        .split("\\.")
        .takeRight(2)
        .mkString(".")
    }
  }

  final class ClassTagSyntax[A](private val aClassTag: ClassTag[A]) extends AnyVal {
    def nestedRuntimeClassName: String = {
      val runtimeClass = aClassTag.runtimeClass
      val className    = runtimeClass.getTypeName
      classes.getNestedName(className)
    }
  }

  final class ASyntaxWithTags[A](private val a: A) extends AnyVal {
    def nestedTypeName(implicit weakTypeTag: WeakTypeTag[A]): String = weakTypeTag.nestedTypeName

    def nestedRuntimeClassName: String = {
      val runtimeClass = ClassTag[A](a.getClass).runtimeClass
      val className    = runtimeClass.getTypeName
      classes.getNestedName(className)
    }
  }

}
