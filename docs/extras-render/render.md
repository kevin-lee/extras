---
sidebar_position: 1
id: 'render'
title: 'Render'
---

## Module

* The `core` module:
  ```scala
  "io.kevinlee" %% "extras-render" % "@VERSION@"
  ```

* For `refined` library:
  ```scala
  "io.kevinlee" %% "extras-render-refined" % "@VERSION@"
  ```

or for `Scala.js`:

* The `core` module:
  ```scala
  "io.kevinlee" %%% "extras-render" % "@VERSION@"
  ```

* For `refined`:
  ```scala
  "io.kevinlee" %%% "extras-render-refined" % "@VERSION@"
  ```


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

## `Render` from Existing `Render`
If the value of your type can be rendered by an existing `Render`, you can easily create `Render` for your type from the existing one with `contramap`.

```scala
Render[A].contramap[B](A => B): Render[B]
```

### Usage Example
```scala mdoc:reset-object
import java.util.UUID
import extras.render.Render

final case class Id(value: UUID) extends AnyVal
object Id {
  implicit val idRender: Render[Id] = Render[UUID].contramap(_.value)
}

import extras.render.syntax._
Id(UUID.randomUUID()).render

final case class Name(value: String) extends AnyVal
object Name {
  implicit val nameRender: Render[Name] = Render[String].contramap(_.value)
}

Name("Kevin").render
```

