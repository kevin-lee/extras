---
sidebar_position: 2
id: 'codecs-encoder'
title: 'Encoder'
---

## Module

```scala
"io.kevinlee" %% "extras-circe" % "@VERSION@"
```
or for `Scala.js`:
```scala
"io.kevinlee" %%% "extras-circe" % "@VERSION@"
```

## Encoder[A].withFields
With `withFields`, you can add additional fields to the JSON encoded by an existing `Encoder`.

```scala
import extras.circe.codecs.encoder._

Encoder[A].withFields { a =>
    List(
      "name1" -> a.someValue1.asJson,
      "name2" -> a.someValue2.asJson
    )
  }
```


### Example

```scala mdoc:reset-object
import io.circe._
import io.circe.syntax._
import io.circe.generic.semiauto._
import extras.circe.codecs.encoder._

final case class Something(n: Int) {
  def name: String = "Foo"
}

object Something {
  implicit val somethingEncoder: Encoder[Something] = deriveEncoder[Something]
    .withFields { a =>
      List(
        "doubled-n" -> (a.n * 2).asJson,
        "name" -> a.name.asJson,
        "some-fixed-value" -> "blah blah".asJson,
      )
    }
}

val something = Something(123)
something.asJson
something.asJson.spaces2
```

### Comparison
Let's compare it with the `Encoder` without `withFields` syntax.
```scala mdoc:reset-object
import io.circe._
import io.circe.syntax._
import io.circe.generic.semiauto._
import extras.circe.codecs.encoder._

final case class Something(n: Int) {
  def name: String = "Foo"
}

object Something {
  val originalSomethingEncoder: Encoder[Something] = deriveEncoder
  implicit val somethingEncoder: Encoder[Something] = originalSomethingEncoder
    .withFields { a =>
      List(
        "doubled-n" -> (a.n * 2).asJson,
        "name" -> a.name.asJson,
        "some-fixed-value" -> "blah blah".asJson,
      )
    }
}

val something = Something(123)

Something.originalSomethingEncoder(something)
Something.originalSomethingEncoder(something).spaces2

something.asJson
something.asJson.spaces2
```

