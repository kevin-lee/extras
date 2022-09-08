---
sidebar_position: 1
id: 'refinement'
title: 'refinement Syntax'
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

There are a few issues here.
* First, you need to create your `newtype` with the newtype constructor and the validated value. e.g.) `.map(YourNewType(_))`
* If it is invalid, you probably want to add the type name for debugging with `leftMap`. e.g.) `.leftMap(err => s"Failed to create YourNewtype: $err")`
* Finally, depending on how to validate, you probably turn the `Either[String, YourNewType]` from the validation into `EitherNec` since you may want to accumulate all the errors from multiple validations. e.g.) `.toEitherNec`

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

## `validateAs`

The boilerplate code issue in newtype + refinement type creation can be fixed with `extras` `refinement` syntax so the following code
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

## `toValue`
If you want to get the underlying value of a refined newtype,
you can do it easily with `extras.refinement.syntax.refinement`.
```scala
val name = Name(NonEmptyString("Kevin"))
name.value
// NonEmptyString = Kevin

name.value.value
// String = "Kevin"

import eu.timepit.refined.auto._
name.toValue
// String = "Kevin" 
```

```scala mdoc:reset-object
import eu.timepit.refined.api._
import eu.timepit.refined.numeric._
import eu.timepit.refined.types.string.NonEmptyString
import io.estatico.newtype.macros.newtype

object Types {
  type PositiveInt = Int Refined Positive
  object PositiveInt extends RefinedTypeOps[PositiveInt, Int]
  @newtype case class Num(value: PositiveInt)
  
  @newtype case class Name(value: NonEmptyString)
  
}
```
```scala mdoc
import Types._

def foo(n: Int): Int = n * 2
def hello(s: String): Unit = println(s"Hello $s")

val n = 1
val num = Num(PositiveInt.unsafeFrom(n))

val nameString = "Kevin"
val name = Name(NonEmptyString.unsafeFrom(nameString))
```
```scala mdoc:fail
foo(num.value)
```
```scala mdoc:fail
hello(name.value)
```
You can solve with `extras-refinement`.
```scala mdoc
import extras.refinement.syntax.refinement._

num.value
num.value.value
num.toValue
foo(num.toValue)

name.value
name.value.value
name.toValue
hello(name.toValue)
```

You can also use `eu.timepit.refined.auto` like
```scala mdoc
import eu.timepit.refined.auto._

num.value
foo(num.value)

name.value
hello(name.value)
```
However, `.value` with `eu.timepit.refined.auto` does an `implicit` conversion from the `refined type` to the underlying type
whereas the syntax from `extras-refinement` is an explicit way to get the underlying type value of the ``refined newtype``.
