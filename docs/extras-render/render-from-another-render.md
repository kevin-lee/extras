---
sidebar_position: 3
id: 'render-from-another-render'
title: 'Render from Another'
---

## Module

* The `core` module:
  ```scala
  "io.kevinlee" %% "extras-render" % "@VERSION@"
  ```

or for `Scala.js`:

* The `core` module:
  ```scala
  "io.kevinlee" %%% "extras-render" % "@VERSION@"
  ```

## `contramap`

If you have a `Render[A]` and know how to `B => A`, you can get `Render[B]` out of the box with `contramap`!

```scala mdoc:reset-object
import extras.render.Render
import extras.render.syntax._

final case class Id(value: Int) extends AnyVal

/*
 * Get `Render[Id]` from `Render[Id]` with `Id => Int` (i.e. `Id.value`)
 */
implicit val renderId: Render[Id] =
  Render[Int].contramap(_.value) // Render[Int] is already provided by extras.

Id(123).render

```

## `Contravariant`

If your project uses [cats](https://typelevel.org/cats/), you get `Contravariant[Render]` out of the box! With `Contravariant[Render]`, you can get `Render[B]` from `Render[A]` if you know how to `B => A`.

```scala mdoc:reset-object
import cats._

import extras.render.Render
import extras.render.syntax._

final case class Id(value: Int) extends AnyVal

/*
 * With `Contravariant[Render]`,
 *   get `Render[Id]` from `Render[Id]` with `Id => Int` (i.e. `Id.value`)
 */
implicit val renderId: Render[Id] =
  Contravariant[Render].contramap(Render[Int])(_.value) // Render[Int] is already provided by extras.

Id(123).render

```

:::info
[`Contravariant`](https://typelevel.org/cats/typeclasses/contravariant.html)
:::