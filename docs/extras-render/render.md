---
sidebar_position: 1
id: 'render'
title: 'Render'
---

## `Render` type-class
It is to provider `render` functionality to render a given type `A` into `String`.
```scala
trait Render[A] {
  def render(a: A): String
}
```

### Usage Example
```scala mdoc:reset-object
import extras.render.Render

final case class Foo(id: Int, name: String)
object Foo {
  implicit val fooRender: Render[Foo] = new Render[Foo] {
    def render(foo: Foo): String =
      s"ID=${foo.id.toString} / Name=${foo.name}"
  }
//  OR just
//  implicit val fooRender: Render[Foo] =
//    foo => s"ID=${foo.id.toString} / Name=${foo.name}"
}

def bar[A: Render](a: A): Unit =
  println(s">> a: ${Render[A].render(a)}")


bar(Foo(1, "Something"))
```

## `Render.render` constructor method
```scala
Render.render[A](A => String): Render[A]
```

### Usage Example
```scala mdoc:reset-object
import extras.render.Render

final case class Foo(id: Int, name: String)
object Foo {
  implicit val fooRender: Render[Foo] = Render.render(
    foo => s"ID=${foo.id.toString} / Name=${foo.name}"
  )
}

def bar[A: Render](a: A): Unit =
  println(s">> a: ${Render[A].render(a)}")


bar(Foo(1, "Something"))
```
