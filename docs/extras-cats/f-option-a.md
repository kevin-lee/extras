---
sidebar_position: 5
id: 'f-option-a'
title: 'F[Option[A]]'
---

## Extension Methods for `F[Option[A]]`

## Change Value
### `innerMap`

```scala
val foa: F[Option[A]] = ...
foa.innerMap(A => B) // F[Option[B]]
```

#### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val foa = IO.pure(1.some)

foa.innerMap(_ + 999)
  .unsafeRunSync()
```

### `innerFlatMap`
```scala
val foa: F[Option[A]] = ...
foa.innerFlatMap(A => Option[B]) // F[Option[B]]
```

#### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val foa = IO.pure(1.some)

foa.innerFlatMap(a => (a + 999).some)
  .unsafeRunSync()
```

### `innerFlatMapF`
```scala
val foa: F[Option[A]] = ...
foa.innerFlatMapF(A => F[Option[B]]) // F[Option[B]]
```

#### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val foa = IO.pure(1.some)

foa.innerFlatMapF(a => IO.pure((a + 999).some))
  .unsafeRunSync()
```

## Get Value

### `innerGetOrElse`
```scala
val foa: F[Option[A]] = ...
foa.innerGetOrElse[B >: A](=> B) // F[Option[B]]
```

#### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val foa = IO.pure(999.some)
foa.innerGetOrElse(0)
  .unsafeRunSync()

val foa2 = IO.pure(none[Int])
foa2.innerGetOrElse(0)
  .unsafeRunSync()

```

### `innerGetOrElseF`
```scala
val foa: F[Option[A]] = ...
foa.innerGetOrElseF[B >: A](=> F[B]) // F[Option[B]]
```

#### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val foa = IO.pure(999.some)
foa.innerGetOrElseF(IO.pure(0))
  .unsafeRunSync()

val foa2 = IO.pure(none[Int])
foa2.innerGetOrElseF(IO.pure(0))
  .unsafeRunSync()
```

### `innerFold`
```scala
val foa: F[Option[A]] = ...
foa.innerFold[B](=> B)(A => B) // F[Option[B]]
```

#### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val foa = IO.pure(1.some)
foa.innerFold(0)(_ + 999)
  .unsafeRunSync()

val foa2 = IO.pure(none[Int])
foa2.innerFold(0)(_ + 999)
  .unsafeRunSync()
```

### `innerFoldF`
```scala
val foa: F[Option[A]] = ...
foa.innerFoldF[B >: A](=> F[B])(A => F[B]) // F[Option[B]]
```

#### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val foa = IO.pure(1.some)

foa.innerFoldF(IO.pure(0))(a => IO.pure(a + 999))
  .unsafeRunSync()

val foa2 = IO.pure(none[Int])

foa2.innerFoldF(IO.pure(0))(a => IO.pure(a + 999))
  .unsafeRunSync()
```

### `innerOrElse`
```scala
val foa: F[Option[A]] = ...
foa.innerOrElse[B >: A](Option[B]) // F[Option[B]]
```

#### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val foa = IO.pure(1.some)

foa.innerOrElse(0.some)
  .unsafeRunSync()

val foa2 = IO.pure(none[Int])

foa2.innerOrElse(0.some)
  .unsafeRunSync()
```

### `innerOrElseF`
```scala
val foa: F[Option[A]] = ...
foa.innerOrElse[B >: A](F[Option[B]]) // F[Option[B]]
```
#### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val foa = IO.pure(1.some)

foa.innerOrElseF(IO.pure(0.some))
  .unsafeRunSync()

val foa2 = IO.pure(none[Int])

foa2.innerOrElseF(IO.pure(0.some))
  .unsafeRunSync()
```

## Check and Search

### `innerFilter`
```scala
val foa: F[Option[A]] = ...
foa.innerFilter(A => Boolean) // F[Option[A]]
```
#### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val foa = IO.pure(1.some)

foa.innerFilter(_ > 0)
  .unsafeRunSync()

foa.innerFilter(_ > 1)
  .unsafeRunSync()
```

### `innerExists`
```scala
val foa: F[Option[A]] = ...
foa.innerExists(A => Boolean) // F[Boolean]
```
#### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val foa = IO.pure(1.some)

foa.innerExists(_ > 0)
  .unsafeRunSync()

foa.innerExists(_ > 1)
  .unsafeRunSync()
```

### `innerContains`
```scala
val foa: F[Option[A]] = ...
foa.innerContains(A) // F[Boolean]
```
#### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val foa = IO.pure(1.some)

foa.innerContains(1)
  .unsafeRunSync()

foa.innerContains(0)
  .unsafeRunSync()
```

### `innerForall`
```scala
val foa: F[Option[A]] = ...
foa.innerForall(A) // F[Boolean]
```
#### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val foa = IO.pure(1.some)

foa.innerForall(_ > 0)
  .unsafeRunSync()

foa.innerForall(_ > 1)
  .unsafeRunSync()

val foa2 = IO.pure(none[Int])

foa2.innerForall(_ > 1)
  .unsafeRunSync()
```

### `innerCollect`
```scala
val foa: F[Option[A]] = ...
foa.innerCollect(PartialFunction[A, B]) // F[Option[B]]
```

#### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val foa = IO.pure(1.some)

foa.innerCollect {
  case 1 => 0
  case 2 => 999
}
  .unsafeRunSync()

val foa2 = IO.pure(2.some)

foa2.innerCollect {
  case 1 => 0
  case 2 => 999
}
  .unsafeRunSync()

val foa3 = IO.pure(3.some)

foa3.innerCollect {
  case 1 => 0
  case 2 => 999
}
  .unsafeRunSync()
```
