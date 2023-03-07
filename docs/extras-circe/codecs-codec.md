---
sidebar_position: 3
id: 'codecs-codec'
title: 'Codec'
---

There are extension methods for `Encoder`, `Decoder` and `Codec`.

## Encoder[A].renameFields
With `renameFields`, you can make the existing JSON `Encoder` to rename the existing fields.

```scala
import extras.circe.codecs.encoder._

Encoder[A].renameFields (
  "name1" -> "newName1",
  "name2" -> "newName2"
) // Encoder[A]
```


### Example

```scala mdoc:reset-object
import io.circe._
import io.circe.syntax._
import io.circe.generic.semiauto._
import extras.circe.codecs.encoder._

final case class Something(n: Int, s: String, price: BigDecimal)
object Something {
  implicit val somethingEncoder: Encoder[Something] =
    deriveEncoder[Something]
      .renameFields(
        "n" -> "id",
        "s" -> "name"
      )
}

val something = Something(1, "Vibranium Shield", BigDecimal(999999999))
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

final case class Something(n: Int, s: String, price: BigDecimal)
object Something {
  val originalSomethingEncoder: Encoder[Something] = deriveEncoder
  implicit val somethingEncoder: Encoder[Something] = originalSomethingEncoder
    .renameFields(
      "n" -> "id",
      "s" -> "name"
    )
}

val something = Something(1, "Vibranium Shield", BigDecimal(999999999))

Something.originalSomethingEncoder(something)
Something.originalSomethingEncoder(something).spaces2

something.asJson
something.asJson.spaces2
```


## Decoder[A].renameFields
With `renameFields`, you can make the existing JSON `Decoder` to rename the existing fields.

```scala
import extras.circe.codecs.encoder._

Decoder[A].renameFields (
  "name1" -> "newName1",
  "name2" -> "newName2"
) // Decoder[A]
```


### Example

```scala mdoc:reset-object
import io.circe._
import io.circe.syntax._
import io.circe.generic.semiauto._
import extras.circe.codecs.decoder._

final case class Something(n: Int, s: String, price: BigDecimal)
object Something {
  implicit val somethingDecoder: Decoder[Something] =
    deriveDecoder[Something]
      .renameFields(
        "n" -> "id",
        "s" -> "name"
      )
}

val jsonString =
  """
  {
    "id": 1,
    "name": "Vibranium Shield",
    "price": 999999999
  }
  """ 

import io.circe.parser._
decode[Something](jsonString)
```

### Comparison
Let's compare it with the `Encoder` without `withFields` syntax.
```scala mdoc:reset-object
import io.circe._
import io.circe.syntax._
import io.circe.generic.semiauto._
import extras.circe.codecs.decoder._

final case class Something(n: Int, s: String, price: BigDecimal)
object Something {
  val originalSomethingDecoder: Decoder[Something] = deriveDecoder
  implicit val somethingDecoder: Decoder[Something] =
    originalSomethingDecoder
      .renameFields(
        "n" -> "id",
        "s" -> "name"
      )
}

val jsonString =
  """
  {
    "id": 1,
    "name": "Vibranium Shield",
    "price": 999999999
  }
  """

import io.circe.parser._
decode[Something](jsonString)(Something.originalSomethingDecoder)

decode[Something](jsonString)
```

## Codec[A].renameFields (Encoder +  Decoder)
With `renameFields`, you can make the existing JSON `Codec` to rename the existing fields.

```scala
import extras.circe.codecs.codec._

Codec[A].renameFields (
  "name1" -> "newName1",
  "name2" -> "newName2"
) // Codec[A]
```


### Example

```scala mdoc:reset-object
import io.circe._
import io.circe.syntax._
import io.circe.generic.semiauto._
import extras.circe.codecs.codec._

final case class Something(n: Int, s: String, price: BigDecimal)
object Something {
  implicit val somethingCodec: Codec[Something] =
    deriveCodec[Something]
      .renameFields(
        "n" -> "id",
        "s" -> "name"
      )
}

val something = Something(1, "Vibranium Shield", BigDecimal(999999999))
something.asJson
val jsonString = something.asJson.spaces2

import io.circe.parser._
decode[Something](jsonString)
```

### Comparison
Let's compare it with the `Encoder` without `withFields` syntax.
```scala mdoc:reset-object
import io.circe._
import io.circe.syntax._
import io.circe.generic.semiauto._
import extras.circe.codecs.codec._

final case class Something(n: Int, s: String, price: BigDecimal)
object Something {
  val originalSomethingCodec: Codec[Something] = deriveCodec
  implicit val somethingCodec: Codec[Something] = originalSomethingCodec
    .renameFields(
      "n" -> "id",
      "s" -> "name"
    )
}

val something = Something(1, "Vibranium Shield", BigDecimal(999999999))

Something.originalSomethingCodec(something)
Something.originalSomethingCodec(something).spaces2

something.asJson
val jsonString = something.asJson.spaces2

import io.circe.parser._
decode[Something](jsonString)(Something.originalSomethingCodec)

decode[Something](jsonString)
```

