---
sidebar_position: 2
id: 'render-syntax'
title: 'Syntax'
---

## `Render` Syntax
There is `Render` syntax provided for convenience.

### `A.render`
```scala
(a: A).render // when `Render[A]` is available
```

```scala mdoc:reset-object
import extras.render.Render

final case class Foo(id: Int, name: String)
object Foo {
  implicit val fooRender: Render[Foo] =
    foo => s"ID=${foo.id.toString} / Name=${foo.name}"
}

import extras.render.syntax._

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
import extras.render.Render

final case class Foo(id: Int, name: String)
object Foo {
  implicit val fooRender: Render[Foo] =
    foo => s"{ID=${foo.id.toString},Name=${foo.name}}"
}

import extras.render.syntax._

List(Foo(1, "A"), Foo(2, "B"), Foo(3, "C")).renderString
List(Foo(1, "A"), Foo(2, "B"), Foo(3, "C")).renderString(", ")
List(Foo(1, "A"), Foo(2, "B"), Foo(3, "C")).renderString("[", ", ", "]")
```

## `render` String interpolation

```scala
render"TEXT $someRenderable"
```

```scala mdoc:reset-object
import extras.render.Render
import extras.render.syntax._

final case class Foo(id: Int, name: String)

object Foo {
  implicit val fooRender: Render[Foo] =
    foo => s"{ID=${foo.id.toString},Name=${foo.name}}"
}

val foo1 = Foo(1, "A")
val foo2 = Foo(2, "B")
val foo3 = Foo(3, "C")

render">>> $foo1 > $foo2 >> $foo3 <<<"

val foos = List(foo1, foo2, foo3)

render">>> $foo1 > $foo2 >> $foo3 - ${foos.renderString("[", ", ", "]")} <<<"
```