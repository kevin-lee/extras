---
sidebar_position: 2
id: 'syntax'
title: 'Refinement Syntax'
---
## Why `refinement` syntax?
When you use `newtype` and `refined` together to have better type-safety, you often have some boilerplate code for runtime value validation when creating newtype + refinement type just like this.

```scala
YourRefinementType.from(value)
  .toEither
  .map(YourNewtype(_))
  .leftMap(err => s"Failed to create YourNewtype: $err")
```

```scala mdoc:reset-object
import cats.syntax.all._
import eu.timepit.refined.api._
import eu.timepit.refined.numeric._
import eu.timepit.refined.types.string.NonEmptyString
import io.estatico.newtype.macros.newtype
import io.estatico.newtype.ops._

object Types {
  type PositiveInt = Int Refined Positive
  object PositiveInt extends RefinedTypeOps[PositiveInt, Int]
  @newtype case class Id(value: PositiveInt)
  
  @newtype case class Name(value: NonEmptyString)
  
  final case class Person(id: Id, name: Name)
}
import Types._

val idValue = 999

val id  = PositiveInt.from(idValue).toEitherNel.map(Id(_)).leftMap(err => s"Failed to create Types.Id: $err")
println(id)

val nameValue = "Kevin"

val name  = NonEmptyString.from(nameValue).toEitherNel.map(Name(_)).leftMap(err => s"Failed to create Types.Name: $err")
println(name)

val person = (id, name).parMapN(Person.apply)
println(person)

```

## `refinement` syntax

The boilerplate code issue in newtype + refinement type creation can be fixed with `extras` `refinement` syntax so the following code snippet
```scala
YourRefinementType.from(value)
  .toEither
  .map(YourNewtype(_))
  .leftMap(err => s"Failed to create YourNewtype: $err")
```
becomes just
```scala
validateAs[YourNewtype](value)
```
or
```scala
value.validateAs[YourNewtype]
```

:::note
The idea of `validateAs[A](value)` and `value.validateAs[A]` is from [Practical FP in Scala](https://leanpub.com/pfp-scala).
:::

### Example: Valid Case

```scala mdoc:reset-object
import cats.syntax.all._
import eu.timepit.refined.api._
import eu.timepit.refined.numeric._
import eu.timepit.refined.types.string.NonEmptyString
import io.estatico.newtype.macros.newtype
import extras.refinement.syntax.refinement._

object Types {
  type PositiveInt = Int Refined Positive
  object PositiveInt extends RefinedTypeOps[PositiveInt, Int]
  @newtype case class Id(value: PositiveInt)
  
  @newtype case class Name(value: NonEmptyString)
  
  final case class Person(id: Id, name: Name)
}
import Types._

val idValue = 999

val id  = validateAs[Id](idValue)
val id2 = idValue.validateAs[Id]
println(id)
println(id2)

val nameValue = "Kevin"

val name  = validateAs[Name](nameValue)
val name2 = nameValue.validateAs[Name]
println(name)
println(name2)

val person = (id, name).parMapN(Person.apply)
println(person)
```

### Example: Invalid Case

#### Only of them is invalid

```scala mdoc:reset-object
import cats.syntax.all._
import eu.timepit.refined.api._
import eu.timepit.refined.numeric._
import eu.timepit.refined.types.string.NonEmptyString
import io.estatico.newtype.macros.newtype
import extras.refinement.syntax.refinement._

object Types {
  type PositiveInt = Int Refined Positive
  object PositiveInt extends RefinedTypeOps[PositiveInt, Int]
  @newtype case class Id(value: PositiveInt)
  
  @newtype case class Name(value: NonEmptyString)
  
  final case class Person(id: Id, name: Name)
}
import Types._

val idValue = 0

val id  = validateAs[Id](idValue)
val id2 = idValue.validateAs[Id]
println(id)
println(id2)

val nameValue = "Kevin"

val name  = validateAs[Name](nameValue)
val name2 = nameValue.validateAs[Name]
println(name)
println(name2)

val person = (id, name).parMapN(Person.apply)
println(person)
```

#### The other one is invalid

```scala mdoc:reset-object
import cats.syntax.all._
import eu.timepit.refined.api._
import eu.timepit.refined.numeric._
import eu.timepit.refined.types.string.NonEmptyString
import io.estatico.newtype.macros.newtype
import extras.refinement.syntax.refinement._

object Types {
  type PositiveInt = Int Refined Positive
  object PositiveInt extends RefinedTypeOps[PositiveInt, Int]
  @newtype case class Id(value: PositiveInt)
  
  @newtype case class Name(value: NonEmptyString)
  
  final case class Person(id: Id, name: Name)
}
import Types._

val idValue = 999

val id  = validateAs[Id](idValue)
val id2 = idValue.validateAs[Id]
println(id)
println(id2)

val nameValue = ""

val name  = validateAs[Name](nameValue)
val name2 = nameValue.validateAs[Name]
println(name)
println(name2)

val person = (id, name).parMapN(Person.apply)
println(person)
```

#### More than one invalid
```scala mdoc:reset-object
import cats.syntax.all._
import eu.timepit.refined.api._
import eu.timepit.refined.numeric._
import eu.timepit.refined.types.string.NonEmptyString
import io.estatico.newtype.macros.newtype
import extras.refinement.syntax.refinement._

object Types {
  type PositiveInt = Int Refined Positive
  object PositiveInt extends RefinedTypeOps[PositiveInt, Int]
  @newtype case class Id(value: PositiveInt)
  
  @newtype case class Name(value: NonEmptyString)
  
  final case class Person(id: Id, name: Name)
}
import Types._

val idValue = 0

val id  = validateAs[Id](idValue)
val id2 = idValue.validateAs[Id]
println(id)
println(id2)

val nameValue = ""

val name  = validateAs[Name](nameValue)
val name2 = nameValue.validateAs[Name]
println(name)
println(name2)

val person = (id, name).parMapN(Person.apply)
println(person)
```
