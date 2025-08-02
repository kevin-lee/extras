---
sidebar_position: 3
id: 'f-either-ab'
title: 'F[Either[A, B]]'
---

## Module
```scala
"io.kevinlee" %% "extras-cats" % "@VERSION@"
```
or for `Scala.js`:
```scala
"io.kevinlee" %%% "extras-cats" % "@VERSION@"
```

## Extension Methods for `F[Either[A, B]]`
```scala
import extras.cats.syntax.all._
```
## Change Value
### `innerMap`

```scala
val foa: F[Either[A, B]] = ...
foa.innerMap(B => D) // F[Either[A, D]]
```

#### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._
val foa = IO.pure(1.asRight[String])

foa.innerMap(_ + 999)
  .unsafeRunSync()
```


### `innerFlatMap`
```scala
val feab: F[Either[A, B]] = ...
feab.innerFlatMap(B => Either[A, D]) // F[Either[A, D]]
```

#### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val feab = IO.pure(1.asRight[String])

feab.innerFlatMap(b => (b + 999).asRight[String])
  .unsafeRunSync()
```

### `innerFlatMapF`
```scala
val feab: F[Either[A, B]] = ...

feab.innerFlatMapF(B => F[Either[A, D]]) // F[Either[A, D]]
```

#### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val feab = IO.pure(1.asRight[String])

feab.innerFlatMapF(b => IO.pure((b + 999).asRight))
  .unsafeRunSync()
```


### `innerLeftMap`
```scala
val feab: F[Either[A, B]] = ...
feab.innerLeftMap(A => C) // F[Either[C, B]]
```

#### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val feab = IO.pure("Error".asLeft[Int])

feab.innerLeftMap("Failed: " + _)
  .unsafeRunSync()
```

### `innerLeftFlatMap`
```scala
val feab: F[Either[A, B]] = ...
feab.innerFlatMap(A => Either[C, B]) // F[Either[C, B]]
```

#### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val feab = IO.pure("Error".asLeft[Int])

feab.innerLeftFlatMap(a => ("Failed: " + a).asLeft[Int])
  .unsafeRunSync()
```

### `innerLeftFlatMapF`
```scala
val feab: F[Either[A, B]] = ...
feab.innerFlatMapF(A => F[Either[C, B]]) // F[Either[C, B]]
```

#### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val feab = IO.pure("Error".asLeft[Int])

feab.innerLeftFlatMapF(a => IO.pure(("Failed: " + a).asLeft[Int]))
  .unsafeRunSync()
```

## Get Value
### `innerGetOrElse`
```scala
val feab: F[Either[A, B]] = ...
feab.innerGetOrElse[D >: B](=> D) // F[D]
```

#### Example
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

### `innerGetOrElseF`
```scala
val feab: F[Either[A, B]] = ...
feab.innerGetOrElseF[D >: B](=> F[D]) // F[D]
```

#### Example
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

### `innerFold`
```scala
val feab: F[Either[A, B]] = ...
feab.innerFold[D](=> D)(B => D) // F[D]
```

#### Example
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

### `innerFoldF`
```scala
val feab: F[Either[A, B]] = ...
feab.innerFoldF[D](=> F[D])(B => F[D]) // F[D]
```

#### Example
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

### `innerOrElse`
```scala
val feab: F[Either[A, B]] = ...
feab.innerOrElse[C >: A, D >: B](=> Either[C, D]): F[Either[C, D]]
```

#### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val feab = IO(1.asRight[String])

feab.innerOrElse(0.asRight[String])
  .unsafeRunSync()

val feab2 = IO("Error".asLeft[Int])

feab2.innerOrElse(0.asRight[String])
  .unsafeRunSync()
```

### `innerOrElseF`
```scala
val feab: F[Either[A, B]] = ...
feab.innerOrElseF[C >: A, D >: B](=> F[Either[C, D]]): F[Either[C, D]]
```

#### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val feab = IO(1.asRight[String])

feab.innerOrElseF(IO.pure(0.asRight[String]))
  .unsafeRunSync()

val feab2 = IO("Error".asLeft[Int])

feab2.innerOrElseF(IO.pure(0.asRight[String]))
  .unsafeRunSync()
```

## Do

### `innerForeach`
```scala
val feab: F[Either[A, B]] = ...
feab.innerForeach(B => Unit) // F[Unit]
```

#### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val feab = IO.pure(1.asRight[String])

feab.innerForeach(println)
  .unsafeRunSync()

val feab2 = IO.pure("Error".asLeft[Int])

feab2.innerForeach(println)
  .unsafeRunSync()
```

### `innerForeachF`
```scala
val feab: F[Either[A, B]] = ...
feab.innerForeachF(B => F[Unit]) // F[Unit]
```

#### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val feab = IO.pure(1.asRight[String])

feab.innerForeachF(b => IO.delay(println(b)))
  .unsafeRunSync()

val feab2 = IO.pure("Error".asLeft[Int])

feab2.innerForeachF(b => IO.delay(println(b)))
  .unsafeRunSync()
```


## Check and Search

### `innerFind`
```scala
val feab: F[Either[A, B]] = ...
feab.innerFind(B => Boolean): F[Option[B]]
```

#### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val feab = IO(1.asRight[String])

feab.innerFind(_ > 0)
  .unsafeRunSync()

feab.innerFind(_ > 1)
  .unsafeRunSync()
```

### `innerFilterOrElse`
```scala
val feab: F[Either[A, B]] = ...
feab.innerFilterOrElse[C >: A](B => Boolean, => C): F[Either[C, B]]
```

#### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val feab = IO(1.asRight[String])

feab.innerFilterOrElse(_ > 0, "Error")
  .unsafeRunSync()

feab.innerFilterOrElse(_ > 1, "Error")
  .unsafeRunSync()
```

### `innerExists`
```scala
val feab: F[Either[A, B]] = ...
feab.innerExists(B => Boolean): F[Boolean]
```

#### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val feab = IO(1.asRight[String])

feab.innerExists(_ > 0)
  .unsafeRunSync()

feab.innerExists(_ > 1)
  .unsafeRunSync()
```

### `innerForall`
```scala
val feab: F[Either[A, B]] = ...
feab.innerForall(B => Boolean): F[Boolean]
```

#### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val feab = IO(1.asRight[String])

feab.innerForall(_ > 0)
  .unsafeRunSync()

feab.innerForall(_ > 1)
  .unsafeRunSync()

val feab2 = IO("Error".asLeft[Int])

feab2.innerForall(_ > 1)
  .unsafeRunSync()
```

### `innerContains`
```scala
val feab: F[Either[A, B]] = ...
feab.innerContains(B): F[Boolean]
```

#### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val feab = IO(1.asRight[String])

feab.innerContains(1)
  .unsafeRunSync()

feab.innerContains(0)
  .unsafeRunSync()

val feab2 = IO("Error".asLeft[Int])

feab2.innerContains(1)
  .unsafeRunSync()
```

### `innerCollectFirst`
```scala
val feab: F[Either[A, B]] = ...
feab.innerCollectFirst[D](PartialFunction[B, D]): F[Option[D]]
```

#### Example
```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val feab = IO(1.asRight[String])

feab.innerCollectFirst {
  case 1 => 0
  case 2 => 999
}
  .unsafeRunSync()

val feab2 = IO(2.asRight[String])
// IO[Either[String, Int]] = IO(Right(2))
feab2.innerCollectFirst {
  case 1 => 0
  case 2 => 999
}
  .unsafeRunSync()

val feab3 = IO(3.asRight[String])
// IO[Either[String, Int]] = IO(Right(3))
feab3.innerCollectFirst {
  case 1 => 0
  case 2 => 999
}
  .unsafeRunSync()

val feab4 = IO("Error".asLeft[Int])
// IO[Either[String, Int]] = IO(Left("Error"))
feab4.innerCollectFirst {
  case 1 => 0
  case 2 => 999
}
  .unsafeRunSync()
```
