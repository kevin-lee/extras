---
sidebar_position: 5
id: 'f-option-a'
title: 'F[Option[A]]'
---

## Extension Methods for `F[Option[A]]`

## innerMap

```scala
val foa: F[Option[A]] = ...
foa.innerMap(A => B) // F[Option[B]]
```

### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val foa: IO[Option[Int]] = IO.pure(1.some)

foa.innerMap(_ + 999)
  .unsafeRunSync()
```

## innerFlatMap
```scala
val foa: F[Option[A]] = ...
foa.innerFlatMap(A => Option[B]) // F[Option[B]]
```

### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val foa: IO[Option[Int]] = IO.pure(1.some)

foa.innerFlatMap(a => (a + 999).some)
  .unsafeRunSync()
```

## innerFlatMapF
```scala
val foa: F[Option[A]] = ...
foa.innerFlatMapF(A => F[Option[B]]) // F[Option[B]]
```

### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val foa: IO[Option[Int]] = IO.pure(1.some)

foa.innerFlatMapF(a => IO.pure((a + 999).some))
  .unsafeRunSync()
```

## innerGetOrElse
```scala
val foa: F[Option[A]] = ...
foa.innerGetOrElse[B >: A](=> B) // F[Option[B]]
```

### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val foa: IO[Option[Int]] = IO.pure(999.some)
foa.innerGetOrElse(0)
  .unsafeRunSync()

val foa2: IO[Option[Int]] = IO.pure(none[Int])
foa2.innerGetOrElse(0)
  .unsafeRunSync()

```

## innerGetOrElseF
```scala
val foa: F[Option[A]] = ...
foa.innerGetOrElseF[B >: A](=> F[B]) // F[Option[B]]
```

### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val foa: IO[Option[Int]] = IO.pure(999.some)
foa.innerGetOrElseF(IO.pure(0))
  .unsafeRunSync()

val foa2: IO[Option[Int]] = IO.pure(none[Int])
foa2.innerGetOrElseF(IO.pure(0))
  .unsafeRunSync()
```

## innerFold
```scala
val foa: F[Option[A]] = ...
foa.innerFold[B](=> B)(A => B) // F[Option[B]]
```

### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val foa: IO[Option[Int]] = IO.pure(1.some)
foa.innerFold(0)(_ + 999)
  .unsafeRunSync()

val foa2: IO[Option[Int]] = IO.pure(none[Int])
foa2.innerFold(0)(_ + 999)
  .unsafeRunSync()
```

## innerFoldF
```scala
val foa: F[Option[A]] = ...
foa.innerFoldF[B >: A](=> F[B])(A => F[B]) // F[Option[B]]
```

### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val foa: IO[Option[Int]] = IO.pure(1.some)
foa.innerFoldF(IO.pure(0))(a => IO.pure(a + 999))
  .unsafeRunSync()

val foa2: IO[Option[Int]] = IO.pure(none[Int])
foa2.innerFoldF(IO.pure(0))(a => IO.pure(a + 999))
  .unsafeRunSync()
```
