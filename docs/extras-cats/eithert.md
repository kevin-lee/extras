---
sidebar_position: 2
id: 'eithert'
title: 'EitherT'
---

## Extension Methods for EitherT

```scala
import extras.cats.syntax.either._
```
or
```scala
import extras.cats.syntax.all._
```

### `eitherT` / `t` for `F[Either[A, B]]`
When you have `fab: F[Either[A, B]]`, instead of `EitherT(fab)`, you can simply do
```scala
fab.eitherT // EitherT[F, A, B]
// or
fab.t // EitherT[F, A, B]
```

```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val fab = IO.pure(1.asRight[String])
fab.t

val f = IO(println("Hello").asRight[String])
f.t
```


### `eitherT` / `t` for `Either[A, B]`
When you have `ab: Either[A, B]`, instead of `EitherT.fromEither[F](ab)`, you can simply do
```scala
ab.eitherT[F] // EitherT[F, A, B]
// or
ab.t[F] // EitherT[F, A, B]
```

```scala mdoc:reset-object
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val ab = 1.asRight[String]
ab.t[IO]
```


### `rightT` for `F[B]`
When you have `fb: F[B]`, instead of `EitherT.right[A](fb)`, you can simply do
```scala
fb.rightT[A] // EitherT[F, A, B]
```

```scala mdoc:reset-object:height=4
import cats.effect._

import extras.cats.syntax.all._

val fb = IO.pure(1)
fb.rightT[String]

val f = IO(println("Hello"))
f.rightT[String]
```


### `leftT` for `F[A]`
When you have `fa: F[A]`, instead of `EitherT.left[B](fa)`, you can simply do
```scala
fa.leftT[B] // EitherT[F, A, B]
```

```scala mdoc:reset-object:height=4
import cats.effect._

import extras.cats.syntax.all._

val fa = IO.pure("ERROR!!!")
fa.leftT[Int]

val f = IO(println("ERROR!!!"))
f.leftT[Int]
```


## Example

```scala mdoc:reset-object
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

final case class MyError(message: String)

def foo[F[_]: Sync](n: Int): F[Int] = Sync[F].pure(n * 2)

def bar[F[_]: Sync](n: Int): F[Either[MyError, Int]] =
  if (n < 0)
    Sync[F].pure(MyError(s"n cannot be a negative number. [n: $n]").asLeft)
  else
    Sync[F].pure((n + 100).asRight)

def divide[F[_]: Sync](a: Int, b: Int): F[Either[MyError, Int]] =
  if (b == 0)
    MyError(s"You can divide number by 0. [a: $a, b: $b]").asLeft.pure[F]
  else
    Sync[F].delay((a / b).asRight)

def run[F[_]: Sync](): F[Either[MyError, Int]] = (for {
  a <- foo(123).rightT
  b <- 2.rightTF[F, MyError]
  c <- bar(b).eitherT
  d <- divide(a, c).t
} yield d).value

println(run[IO]().unsafeRunSync())

```
