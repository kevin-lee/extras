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
```scala
import cats.syntax.all._
import io.estatico.newtype.macros.newtype
import eu.timepit.refined.types.string.NonEmptyString

@newtype case class Name(value: NonEmptyString)

val validNameValue = "Kevin"
// validNameValue: String = "Kevin"
NonEmptyString.from(validNameValue)
  .map(Name(_))
  .leftMap(err => s"Failed to create Name: $err")
  .toEitherNec
// res1: cats.data.package.EitherNec[String, Name] = Right(value = Kevin)

val invalidNameValue = ""
// invalidNameValue: String = ""
NonEmptyString.from(invalidNameValue)
  .map(Name(_))
  .leftMap(err => s"Failed to create Name: $err")
  .toEitherNec
// res2: cats.data.package.EitherNec[String, Name] = Left(
//   value = Singleton(
//     a = "Failed to create Name: Predicate isEmpty() did not fail."
//   )
// )
```

or this

```scala
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
// idValue: Int = 999

val id = PositiveInt.from(idValue)
  .map(Id(_))
  .leftMap(err => s"Failed to create Types.Id: $err")
  .toEitherNec
// id: cats.data.package.EitherNec[String, Id] = Right(value = 999)
println(id)
// Right(999)

val nameValue = "Kevin"
// nameValue: String = "Kevin"

val name = NonEmptyString.from(nameValue)
  .map(Name(_))
  .leftMap(err => s"Failed to create Types.Name: $err")
  .toEitherNec
// name: cats.data.package.EitherNec[String, Name] = Right(value = Kevin)
println(name)
// Right(Kevin)

val person = (id, name).parMapN(Person.apply)
// person: cats.data.package.EitherNec[String, Person] = Right(
//   value = Person(id = 999, name = Kevin)
// )
println(person)
// Right(Person(999,Kevin))

```
or invalid case like
```scala
val idValue2 = 0
// idValue2: Int = 0

val id2 = PositiveInt.from(idValue2)
  .map(Id(_))
  .leftMap(err => s"Failed to create Types.Id: $err")
  .toEitherNec
// id2: cats.data.package.EitherNec[String, Id] = Left(
//   value = Singleton(a = "Failed to create Types.Id: Predicate failed: (0 > 0).")
// )
println(id2)
// Left(Chain(Failed to create Types.Id: Predicate failed: (0 > 0).))

val nameValue2 = ""
// nameValue2: String = ""

val name2 = NonEmptyString.from(nameValue2)
  .map(Name(_))
  .leftMap(err => s"Failed to create Types.Name: $err")
  .toEitherNec
// name2: cats.data.package.EitherNec[String, Name] = Left(
//   value = Singleton(
//     a = "Failed to create Types.Name: Predicate isEmpty() did not fail."
//   )
// )
println(name2)
// Left(Chain(Failed to create Types.Name: Predicate isEmpty() did not fail.))

val person2 = (id2, name2).parMapN(Person.apply)
// person2: cats.data.package.EitherNec[String, Person] = Left(
//   value = Append(
//     leftNE = Singleton(
//       a = "Failed to create Types.Id: Predicate failed: (0 > 0)."
//     ),
//     rightNE = Singleton(
//       a = "Failed to create Types.Name: Predicate isEmpty() did not fail."
//     )
//   )
// )
println(person2)
// Left(Chain(Failed to create Types.Id: Predicate failed: (0 > 0)., Failed to create Types.Name: Predicate isEmpty() did not fail.))

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
* [the source code of `extras-refinement` is here](https://github.com/kevin-lee/extras/blob/3aaf342368665ba622f41874639e37b4f130a046/modules/extras-refinement/shared/src/main/scala/extras/refinement/syntax/refinement.scala#L11-L42)
:::

### Example: Valid Case

```scala
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
// idValue: Int = 999

val id  = validateAs[Id](idValue)
// id: cats.data.package.EitherNec[String, Id] = Right(value = 999)
val id2 = idValue.validateAs[Id]
// id2: cats.data.package.EitherNec[String, Id] = Right(value = 999)
println(id)
// Right(999)
println(id2)
// Right(999)

val nameValue = "Kevin"
// nameValue: String = "Kevin"

val name  = validateAs[Name](nameValue)
// name: cats.data.package.EitherNec[String, Name] = Right(value = Kevin)
val name2 = nameValue.validateAs[Name]
// name2: cats.data.package.EitherNec[String, Name] = Right(value = Kevin)
println(name)
// Right(Kevin)
println(name2)
// Right(Kevin)

val person = (id, name).parMapN(Person.apply)
// person: cats.data.package.EitherNec[String, Person] = Right(
//   value = Person(id = 999, name = Kevin)
// )
println(person)
// Right(Person(999,Kevin))
```

### Example: Invalid Case

#### Only of them is invalid

```scala
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
// idValue: Int = 0

val id  = validateAs[Id](idValue)
// id: cats.data.package.EitherNec[String, Id] = Left(
//   value = Singleton(a = "Failed to create Types.Id: Predicate failed: (0 > 0).")
// )
val id2 = idValue.validateAs[Id]
// id2: cats.data.package.EitherNec[String, Id] = Left(
//   value = Singleton(a = "Failed to create Types.Id: Predicate failed: (0 > 0).")
// )
println(id)
// Left(Chain(Failed to create Types.Id: Predicate failed: (0 > 0).))
println(id2)
// Left(Chain(Failed to create Types.Id: Predicate failed: (0 > 0).))

val nameValue = "Kevin"
// nameValue: String = "Kevin"

val name  = validateAs[Name](nameValue)
// name: cats.data.package.EitherNec[String, Name] = Right(value = Kevin)
val name2 = nameValue.validateAs[Name]
// name2: cats.data.package.EitherNec[String, Name] = Right(value = Kevin)
println(name)
// Right(Kevin)
println(name2)
// Right(Kevin)

val person = (id, name).parMapN(Person.apply)
// person: cats.data.package.EitherNec[String, Person] = Left(
//   value = Singleton(a = "Failed to create Types.Id: Predicate failed: (0 > 0).")
// )
println(person)
// Left(Chain(Failed to create Types.Id: Predicate failed: (0 > 0).))
```

#### The other one is invalid

```scala
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
// idValue: Int = 999

val id  = validateAs[Id](idValue)
// id: cats.data.package.EitherNec[String, Id] = Right(value = 999)
val id2 = idValue.validateAs[Id]
// id2: cats.data.package.EitherNec[String, Id] = Right(value = 999)
println(id)
// Right(999)
println(id2)
// Right(999)

val nameValue = ""
// nameValue: String = ""

val name  = validateAs[Name](nameValue)
// name: cats.data.package.EitherNec[String, Name] = Left(
//   value = Singleton(
//     a = "Failed to create Types.Name: Predicate isEmpty() did not fail."
//   )
// )
val name2 = nameValue.validateAs[Name]
// name2: cats.data.package.EitherNec[String, Name] = Left(
//   value = Singleton(
//     a = "Failed to create Types.Name: Predicate isEmpty() did not fail."
//   )
// )
println(name)
// Left(Chain(Failed to create Types.Name: Predicate isEmpty() did not fail.))
println(name2)
// Left(Chain(Failed to create Types.Name: Predicate isEmpty() did not fail.))

val person = (id, name).parMapN(Person.apply)
// person: cats.data.package.EitherNec[String, Person] = Left(
//   value = Singleton(
//     a = "Failed to create Types.Name: Predicate isEmpty() did not fail."
//   )
// )
println(person)
// Left(Chain(Failed to create Types.Name: Predicate isEmpty() did not fail.))
```

#### More than one invalid
```scala
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
// idValue: Int = 0

val id  = validateAs[Id](idValue)
// id: cats.data.package.EitherNec[String, Id] = Left(
//   value = Singleton(a = "Failed to create Types.Id: Predicate failed: (0 > 0).")
// )
val id2 = idValue.validateAs[Id]
// id2: cats.data.package.EitherNec[String, Id] = Left(
//   value = Singleton(a = "Failed to create Types.Id: Predicate failed: (0 > 0).")
// )
println(id)
// Left(Chain(Failed to create Types.Id: Predicate failed: (0 > 0).))
println(id2)
// Left(Chain(Failed to create Types.Id: Predicate failed: (0 > 0).))

val nameValue = ""
// nameValue: String = ""

val name  = validateAs[Name](nameValue)
// name: cats.data.package.EitherNec[String, Name] = Left(
//   value = Singleton(
//     a = "Failed to create Types.Name: Predicate isEmpty() did not fail."
//   )
// )
val name2 = nameValue.validateAs[Name]
// name2: cats.data.package.EitherNec[String, Name] = Left(
//   value = Singleton(
//     a = "Failed to create Types.Name: Predicate isEmpty() did not fail."
//   )
// )
println(name)
// Left(Chain(Failed to create Types.Name: Predicate isEmpty() did not fail.))
println(name2)
// Left(Chain(Failed to create Types.Name: Predicate isEmpty() did not fail.))

val person = (id, name).parMapN(Person.apply)
// person: cats.data.package.EitherNec[String, Person] = Left(
//   value = Append(
//     leftNE = Singleton(
//       a = "Failed to create Types.Id: Predicate failed: (0 > 0)."
//     ),
//     rightNE = Singleton(
//       a = "Failed to create Types.Name: Predicate isEmpty() did not fail."
//     )
//   )
// )
println(person)
// Left(Chain(Failed to create Types.Id: Predicate failed: (0 > 0)., Failed to create Types.Name: Predicate isEmpty() did not fail.))
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

import extras.refinement.syntax.refinement._
name.toValue
// String = "Kevin" 
```

```scala
import eu.timepit.refined.api._
import eu.timepit.refined.types.all._
import io.estatico.newtype.macros.newtype

object Types {
  @newtype case class Num(value: PosInt)
  
  @newtype case class Name(value: NonEmptyString)
  
}
```
```scala
import Types._

def foo(n: Int): Int = n * 2
def hello(s: String): Unit = println(s"Hello $s")

val n = 1
// n: Int = 1
val num = Num(PositiveInt.unsafeFrom(n))
// num: Num = 1

val nameString = "Kevin"
// nameString: String = "Kevin"
val name = Name(NonEmptyString.unsafeFrom(nameString))
// name: Name = Kevin
```
```scala
foo(num.value)
// error: type mismatch;
//  found   : repl.MdocSession.MdocApp6.Types.PositiveInt
//     (which expands to)  eu.timepit.refined.api.Refined[Int,eu.timepit.refined.numeric.Greater[shapeless._0]]
//  required: Int
// foo(num.value)
//     ^^^^^^^^^
```
```scala
hello(name.value)
// error: type mismatch;
//  found   : eu.timepit.refined.types.string.NonEmptyString
//     (which expands to)  eu.timepit.refined.api.Refined[String,eu.timepit.refined.boolean.Not[eu.timepit.refined.collection.Empty]]
//  required: String
// hello(name.value)
//       ^^^^^^^^^^
```
You can solve with `extras-refinement`.
```scala
import extras.refinement.syntax.refinement._

num.value
// res37: PositiveInt = 1
num.value.value
// res38: Int = 1
num.toValue
// res39: Int = 1
foo(num.toValue)
// res40: Int = 2

name.value
// res41: NonEmptyString = Kevin
name.value.value
// res42: String = "Kevin"
name.toValue
// res43: String = "Kevin"
hello(name.toValue)
// Hello Kevin
```

You can also use `eu.timepit.refined.auto` like
```scala
import eu.timepit.refined.auto._

num.value
// res45: PositiveInt = 1
foo(num.value)
// res46: Int = 2

name.value
// res47: NonEmptyString = Kevin
hello(name.value)
// Hello Kevin
```
However, `.value` with `eu.timepit.refined.auto` does an `implicit` conversion from the `refined type` to the underlying type
whereas the syntax from `extras-refinement` is an explicit way to get the underlying type value of the ``refined newtype``.
