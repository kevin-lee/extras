---
sidebar_position: 3
id: 'f-either-ab'
title: 'F[Either[A, B]]'
---

## Extension Methods for `F[Either[A, B]]`

### innerMap

```scala
val foa: F[Either[A, B]] = ...
foa.innerMap(B => D) // F[Either[A, D]]
```
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._
val foa = IO.pure(1.asRight[String])

foa.innerMap(_ + 999)
  .unsafeRunSync()
```


### innerFlatMap
```scala
val feab: F[Either[A, B]] = ...
feab.innerFlatMap(B => Either[A, D]) // F[Either[A, D]]
```
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val feab = IO.pure(1.asRight[String])

feab.innerFlatMap(b => (b + 999).asRight[String])
  .unsafeRunSync()
```

### innerFlatMapF
```scala
val feab: F[Either[A, B]] = ...

feab.innerFlatMapF(B => F[Either[A, D]]) // F[Either[A, D]]
```
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val feab = IO.pure(1.asRight[String])

feab.innerFlatMapF(b => IO.pure((b + 999).asRight))
  .unsafeRunSync()
```

### innerGetOrElse
```scala
val feab: F[Either[A, B]] = ...
feab.innerGetOrElse[D >: B](=> D) // F[D]
```
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val feab = IO.pure(999.asRight[String])

feab.innerGetOrElse(0)
  .unsafeRunSync()

val feab2 = IO.pure("Error".asLeft[Int])

feab2.innerGetOrElse(0)
  .unsafeRunSync()
```

### innerGetOrElseF
```scala
val feab: F[Either[A, B]] = ...
feab.innerGetOrElseF[D >: B](=> F[D]) // F[D]
```
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val feab = IO.pure(999.asRight[String])

feab.innerGetOrElseF(IO.pure(0))
  .unsafeRunSync()

val feab2 = IO.pure("Error".asLeft[Int])

feab2.innerGetOrElseF(IO.pure(0))
  .unsafeRunSync()
```

### innerFold
```scala
val feab: F[Either[A, B]] = ...
feab.innerFold[D](=> D)(B => D) // F[D]
```
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val feab = IO.pure(1.asRight[String])

feab.innerFold(_ => 0)(_ + 999)
  .unsafeRunSync()

val feab2 = IO.pure("Error".asLeft[Int])

feab2.innerFold(_ => 0)(_ + 999)
  .unsafeRunSync()
```

### innerFoldF
```scala
val feab: F[Either[A, B]] = ...
feab.innerFoldF[D](=> F[D])(B => F[D]) // F[D]
```
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val feab = IO.pure(1.asRight[String])

feab.innerFoldF(_ => IO.pure(0))(b => IO.pure(b + 999))
  .unsafeRunSync()

val feab2 = IO.pure("Error".asLeft[Int])

feab2.innerFoldF(_ => IO.pure(0))(b => IO.pure(b + 999))
  .unsafeRunSync()
```
