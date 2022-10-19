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

## `Render` Syntax
There is `Render` syntax provided for convenience.

### `A.render`
```scala
(a: A).render // when `Render[A]` is available
```

```scala mdoc:reset-object
import extras.core.Render

final case class Foo(id: Int, name: String)
object Foo {
  implicit val fooRender: Render[Foo] =
    foo => s"ID=${foo.id.toString} / Name=${foo.name}"
}

import extras.core.syntax.render._
// or
// import extras.core.syntax.all._

def bar[A: Render](a: A): Unit =
  println(s">> a: ${a.render}")

Foo(1, "Something").render

bar(Foo(1, "Something"))
```

### `List[A].renderString`
```scala
List[A](a..).renderString // when Render[A] is available
List[A](a..).renderString(delimiter) // when Render[A] is available
List[A](a..).renderString(start, delimiter, end) // when Render[A] is available
```


```scala mdoc:reset-object
import extras.core.Render

final case class Foo(id: Int, name: String)
object Foo {
  implicit val fooRender: Render[Foo] =
    foo => s"{ID=${foo.id.toString},Name=${foo.name}}"
}

import extras.core.syntax.render._
// or
// import extras.core.syntax.all._

List(Foo(1, "A"), Foo(2, "B"), Foo(3, "C")).renderString
List(Foo(1, "A"), Foo(2, "B"), Foo(3, "C")).renderString(", ")
List(Foo(1, "A"), Foo(2, "B"), Foo(3, "C")).renderString("[", ", ", "]")
```
