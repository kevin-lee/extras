---
sidebar_position: 1
sidebar_label: 'fs2-v3-text'
id: 'extras-fs2-v3-text'
title: 'Fs2 v3 text'
---

It's only for Fs2 v3 so if you use v2, please use [extras-fs2-v2-text](../v2/extras-fs2-v2-text).
```scala
"io.kevinlee" %% "extras-fs2-v3-text" % "@VERSION@"
```


## Stream[F, Byte].utf8String

```scala
Stream[F, Byte].utf8String // F[String]
```

```scala mdoc:reset-object
import cats.effect._
import extras.fs2.text.syntax._
import fs2.Stream

import java.nio.charset.StandardCharsets

import cats.effect.unsafe.implicits.global

Stream[IO, Byte]("blah blah".getBytes(StandardCharsets.UTF_8).toList: _*)
  .utf8String // IO[String]
  .unsafeRunSync()
```

```scala mdoc:reset-object
import cats.effect._
import fs2.Stream

import java.nio.charset.StandardCharsets

import cats.effect.unsafe.implicits.global

def toByteStream[F[*]](s: String): Stream[F, Byte] =
  Stream[F, Byte](s.getBytes(StandardCharsets.UTF_8).toList: _*)

def bytesToString[F[*]: Sync](stream: Stream[F, Byte]): F[String] = {
  import extras.fs2.text.syntax._
  stream.utf8String
}

val s = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."
val byteStream = toByteStream[IO](s)

bytesToString[IO](byteStream)
  .unsafeRunSync()
```

## utf8String for http4s
```scala mdoc:reset-object:height=9
import org.http4s.dsl.io._
import cats.effect.unsafe.implicits.global

val response = Ok("Hello world!")
val responseToString = response
  .flatMap { response =>
    import extras.fs2.text.syntax._
    response
      .body
      .utf8String
  }
responseToString.unsafeRunSync()
```
