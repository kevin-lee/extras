---
sidebar_position: 2
id: 'either'
title: 'Either'
---

## Extension Methods for EitherT

```scala
import cats._
import cats.syntax.all._
import cats.effect._

import extras.cats.syntax.all._


final case class MyError(message: String)

def divide[F[_]: Sync](a: Int, b: Int): F[Either[MyError, Int]] =
  if (b == 0)
    MyError(s"You can divide number by 0. [a: $a, b: $b]").asLeft.pure[F]
  else
    Sync[F].delay((a / b).asRight)


def foo[F[_]: Sync](n: Int): F[Int] = Sync[F].pure(n * 2)


def run[F[_]: Sync](): F[Either[MyError, Int]] = (for {
  a <- foo(123).rightT
  b <- 2.rightTF[F, MyError]
  c <- divide(a, b).eitherT
} yield c).value

println(run[IO]().unsafeRunSync())

// Right(123)
```
