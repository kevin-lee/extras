---
sidebar_position: 2
id: 'syntax'
title: 'Refinement Syntax'
---
## Why `refinement` syntax?
When you use [newtype](https://github.com/estatico/scala-newtype) and [refined](https://github.com/fthomas/refined) together 
to have better type-safety, you often have some boilerplate code for runtime value validation 
when creating newtype + refinement type just like this.

```scala
YourRefinementType.from(value)
  .map(YourNewtype(_))
  .leftMap(err => s"Failed to create YourNewtype: $err")
  .toEitherNec
```

In practice, it may look like
```scala mdoc:reset-object
import cats.syntax.all._
import io.estatico.newtype.macros.newtype
import eu.timepit.refined.types.string.NonEmptyString

@newtype case class Name(value: NonEmptyString)

val validNameValue = "Kevin"
NonEmptyString.from(validNameValue)
  .map(Name(_))
  .leftMap(err => s"Failed to create Name: $err")
  .toEitherNec

val invalidNameValue = ""
NonEmptyString.from(invalidNameValue)
  .map(Name(_))
  .leftMap(err => s"Failed to create Name: $err")
  .toEitherNec
```

or this

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

val id = PositiveInt.from(idValue)
          .map(Id(_))
          .leftMap(err => s"Failed to create Types.Id: $err")
          .toEitherNec
println(id)

val nameValue = "Kevin"

val name = NonEmptyString.from(nameValue)
            .map(Name(_))
            .leftMap(err => s"Failed to create Types.Name: $err")
            .toEitherNec
println(name)

val person = (id, name).parMapN(Person.apply)
println(person)

```
or invalid case like
```scala mdoc
val idValue2 = 0

val id2 = PositiveInt.from(idValue2)
          .map(Id(_))
          .leftMap(err => s"Failed to create Types.Id: $err")
          .toEitherNec
println(id2)

val nameValue2 = ""

val name2 = NonEmptyString.from(nameValue2)
            .map(Name(_))
            .leftMap(err => s"Failed to create Types.Name: $err")
            .toEitherNec
println(name2)

val person2 = (id2, name2).parMapN(Person.apply)
println(person2)

```

## `refinement` syntax

The boilerplate code issue in newtype + refinement type creation can be fixed with `extras` `refinement` syntax so the following code snippet
```scala
YourRefinementType.from(value)
  .map(YourNewtype(_))
  .leftMap(err => s"Failed to create YourNewtype: $err")
  .toEitherNec
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
The syntax is not exactly the same, but the most important core logic of using `Coercible` is the same.

If you are interested in the difference,
* [the source code from `Practical FP in Scala` is here](https://github.com/gvolpe/pfps-examples/blob/e49adf61fe5cdc84b0ca50995f20fb5a79f91cea/src/main/scala/examples/validation/RuntimeValidation.scala#L268-L294) and
* [the source code of `extras-refinement` is here](https://github.com/Kevin-Lee/extras/blob/3aaf342368665ba622f41874639e37b4f130a046/modules/extras-refinement/shared/src/main/scala/extras/refinement/syntax/refinement.scala#L11-L42)
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
