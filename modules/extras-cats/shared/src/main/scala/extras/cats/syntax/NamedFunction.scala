package extras.cats.syntax

import scala.reflect.ClassTag

/** @author Kevin Lee
  * @since 2023-03-11
  */
final case class NamedFunction[A: ClassTag, B: ClassTag](name: String)(f: A => B) extends (A => B) {
  private val paramAName: String = implicitly[ClassTag[A]].runtimeClass.getSimpleName
  private val paramBName: String = implicitly[ClassTag[B]].runtimeClass.getSimpleName

  def apply(a: A): B = f(a)

  override val toString: String =
    s"($paramAName => $paramBName): $name"
}
