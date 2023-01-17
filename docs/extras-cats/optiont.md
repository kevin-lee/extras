---
sidebar_position: 3
id: 'optiont'
title: 'OptionT'
---

## Extension Methods for OptionT

```scala
import extras.cats.syntax.option._
```
or
```scala
import extras.cats.syntax.all._
```

### `optionT` / `t` for `F[Option[A]]`
When you have `foa: F[Option[A]]`, instead of `OptionT(foa)`, you can simply do
```scala
foa.optionT // OptionT[F, A]
// or
foa.t // OptionT[F, A]
```

```scala mdoc:reset-object:height=4
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val foa = IO.pure(1.some)
foa.t

val f = IO(println("Hello").some)
f.t
```


### `optionT` / `t` for `Option[A]`
When you have `oa: Option[A]`, instead of `OptionT.fromOption[F](oa)`, you can simply do
```scala
oa.optionT[F] // OptionT[F, A]
// or
oa.t[F] // OptionT[F, A]
```

```scala mdoc:reset-object
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

val oa = 1.some
oa.t[IO]
```


### `someT` for `F[A]`
When you have `fa: F[A]`, instead of `OptionT.liftF(fa)`, you can simply do
```scala
fa.someT // OptionT[F, A]
```

```scala mdoc:reset-object:height=4
import cats.effect._

import extras.cats.syntax.all._

val fa = IO.pure(1)
fa.someT

val f = IO(println("Hello"))
f.someT
```


### `someTF` for `A`
When you have `a: A`, instead of `OptionT.some(a)`, you can simply do
```scala
a.someTF[F] // OptionT[F, A]
```

```scala mdoc:reset-object
import cats.effect._

import extras.cats.syntax.all._

val a = 1
a.someTF[IO]
```


## Example

```scala mdoc:reset-object
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._

final case class MyError(message: String)

def foo[F[_]: Sync](n: Int): F[Int] = Sync[F].pure(n * 2)

def bar[F[_]: Sync](n: Int): F[Option[Int]] =
  if (n < 0)
    Sync[F].pure(none[Int])
  else
    Sync[F].pure((n + 100).some)

def divide[F[_]: Sync](a: Int, b: Int): F[Option[Int]] =
  if (b == 0)
    none[Int].pure[F]
  else
    Sync[F].delay((a / b).some)

def run[F[_]: Sync](): F[Option[Int]] = (for {
  a <- foo(123).someT
  b <- 2.someTF[F]
  c <- bar(b).optionT
  d <- divide(a, c).t
} yield d).value

println(run[IO]().unsafeRunSync())

```
