package extras.typeinfo.syntax

import extras.typeinfo.syntax.types.TypeName

import scala.reflect.ClassTag

/** @author Kevin Lee
  * @since 2022-03-19
  */
trait types {

  def typeFullNameOf[A: TypeName]: String = summon[TypeName[A]].value

  def nestedTypeNameOf[A: TypeName]: String = {
    @SuppressWarnings(Array("org.wartremover.warts.ToString"))
    val typeName = typeFullNameOf[A]

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

    val split = typeNameWithSimpleTypeParam
      .split("\\.")

    (if (split.lastOption == split.dropRight(1).lastOption)
       split
         .dropRight(1)
     else
       split)
      .takeRight(2)
      .mkString(".")

  }

  extension [A: TypeName](a: A) {
    def typeFullName: String = {
      val fullName = types.typeFullNameOf[A]

      import classes.*
      val className = a.fullClassName

      decideName(namesWithTypeParam(dropTypeSuffix(fullName)), className, NameType.Full)
    }

    def nestedTypeName: String = {
      val fullName = types.typeFullNameOf[A]

      import classes.*
      val className = a.fullClassName

      decideName(namesWithTypeParam(dropTypeSuffix(fullName)), className, NameType.Nested)
    }

    private def dropTypeSuffix(s: String): String =
      s.stripSuffix(".type")

    private def namesWithTypeParam(s: String): String = {
      val paramStart = s.indexOf('[')
      if paramStart >= 0 then s.substring(0, paramStart)
      else s
    }

    private def decideName(fullName: String, className: String, nameType: NameType): String =
      if (fullName == className) {
        nameType match {
          case NameType.Full =>
            fullName
          case NameType.Nested =>
            val fullNames = fullName.split("\\.")
            fullNames.takeRight(2).mkString(".")
        }
      } else {
        val fullNames                = fullName.split("\\.")
        val maybeLastName            = fullNames.lastOption
        val classNames               = className.split("\\.")
        val maybeLastClassName       = classNames.lastOption
        val maybeSecondLastClassName = classNames.dropRight(1).lastOption
        if (maybeSecondLastClassName == Option("anon")) {
          nameType match {
            case NameType.Full =>
              s"$fullName.${a.toString}"
            case NameType.Nested =>
              s"${fullNames.takeRight(1).mkString}.${a.toString}"
          }
        } else {
          /*
           * Drop the last String value if the last and the second last are equal.
           */
          def dropLastDuplicate(names: Array[String]): Array[String] =
            names.takeRight(2) match {
              case Array(secondLast, last) =>
                if (secondLast == last)
                  if (names.length > 2)
                    names.dropRight(1)
                  else
                    names
                else
                  names
              case _ =>
                names
            }

          val (theClassName, theFullName) = nameType match {
            case NameType.Full =>
              (dropLastDuplicate(classNames).mkString("."), dropLastDuplicate(fullNames).mkString("."))
            case NameType.Nested =>
              val lastTwoClassName = dropLastDuplicate(classNames).takeRight(2)
              val lastTwoFullName  = dropLastDuplicate(fullNames).takeRight(2)
              (lastTwoClassName.mkString("."), lastTwoFullName.mkString("."))
          }
          if (maybeLastClassName == maybeLastName || maybeSecondLastClassName == maybeLastName)
            theClassName
          else
            theFullName
        }
      }

    def nestedRuntimeClassName: String = {
      val runtimeClass = ClassTag[A](a.getClass).runtimeClass
      val className    = runtimeClass.getTypeName
      classes.getNestedName(className)
    }

  }

  private enum NameType {
    case Full
    case Nested
  }

  extension [A](aClassTag: ClassTag[A]) {
    def nestedRuntimeClassName: String = {
      val runtimeClass = aClassTag.runtimeClass
      val className    = runtimeClass.getTypeName
      classes.getNestedName(className)
    }
  }

}

object types extends types {

  import scala.quoted.*

  import scala.compiletime.*

  trait TypeNamed {
    inline given [A]: TypeName[A] =
      ${ TypeNamed.expr }
  }
  object TypeNamed {
    def expr[T: Type](using Quotes): Expr[TypeName[T]] =
      '{ TypeName[T](${ Expr(Type.show[T]) }) }
  }

  type TypeName[A] = TypeName.TypeName[A]
  object TypeName extends TypeNamed {
    opaque type TypeName[A] = String
    def apply[A](typeName: String): TypeName[A] = typeName

    given typeNameCanEqual[A]: CanEqual[TypeName[A], TypeName[A]] = CanEqual.derived

    extension [A](typeName: TypeName[A]) {
      def value: String = typeName
    }
  }

}
