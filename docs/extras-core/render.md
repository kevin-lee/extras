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
import extras.core.Render

final case class Foo(id: Int, name: String)
object Foo {
  implicit val fooRender: Render[Foo] =
    foo => s"ID=${foo.id.toString} / Name=${foo.name}"
}

def bar[A: Render](a: A): Unit =
  println(s">> a: ${Render[A].render(a)}")


bar(Foo(1, "Something"))
```