---
sidebar_position: 4
sidebar_label: 'Effectie'
id: 'effectie'
title: 'Extras - Testing Tools for Effectie'
---

## Module

```scala
"io.kevinlee" %% "extras-testing-tools-effectie" % "@VERSION@"
```

## `StubToolsFx.stub`
`StubToolsFx.stub` is a tool for a stub (a simple function for testing) so that you don't need to use mock frameworks.

```scala
import cats.{Functor, Monad}
import cats.syntax.all._

import effectie.core.Fx

import extras.testing.StubToolsFx
```

```scala
def fooStub[F[_]: Fx](f: => Option[A]): FooStub[F] = new FooStub[F] {
  def foo: F[A] = StubToolsFx.stub(f) // F[A]
}
// If f is None, it will raise MissingStubException with the line number pointing where it's missing
```

```scala
def fooStub[F[_]: Fx: Functor](f: => Option[() => A]): FooStub[F] = new FooStub[F] {
  def foo(): F[A] = StubToolsFx.stub(f).map(_()) // F[A]
}
// If f is None, it will raise MissingStubException with the line number pointing where it's missing
```
```scala
def fooStub[F[_]: Fx: Functor](f: => Option[A => B]): FooStub[F] = new FooStub[F] {
  def foo(a: A): F[B] = StubToolsFx.stub(f).map(_(a)) // F[B]
}
// If f is None, it will raise MissingStubException with the line number pointing where it's missing
```
```scala
def fooStub[F[_]: Fx: Monad](f: => Option[A => F[B]]): FooStub[F] = new FooStub[F] {
  def foo(a: A): F[B] = StubToolsFx.stub(f).flatMap(_(a)) // F[B]
}
// If f is None, it will raise MissingStubException with the line number pointing where it's missing
```

### Example

e.g.)

```scala mdoc:reset-object:height=4
import cats.Monad
import cats.syntax.all._
import eu.timepit.refined.types.all._
import eu.timepit.refined.cats._
import eu.timepit.refined.auto._
import io.estatico.newtype.macros.newtype
import extras.testing.StubToolsFx

import effectie.core._
import effectie.syntax.all._

object types {
  @newtype case class Id(value: PosInt)
  @newtype case class Name(value: NonEmptyString)
}
import types._

trait MyService[F[_]] {
  def getName(id: Id): F[Option[Name]]
}

object MyServiceStub {
  def apply[F[_]: Fx: Monad](f: => Option[Id => F[Option[Name]]]): MyService[F] = new MyService[F] {
    override def getName(id: Id): F[Option[Name]] = StubToolsFx.stub(f).flatMap(_(id))
  }
}

class Hello[F[_]: Monad](myService: MyService[F]) {
  def hello(id: Id): F[String] = {
    myService.getName(id)
      .map { maybeName =>
        maybeName.fold(s"No name found for id ${id.value}")(name => s"Hello ${name.value}")
      }
  }
}

val expectedId = Id(1)
val expectedName = Name("Kevin")

import cats.effect._

import effectie.instances.ce2.fx._

val myService: MyService[IO] = MyServiceStub(((id: Id) =>
  if (id.value === expectedId.value)
    pureOfSome(expectedName)
  else
    pureOfNone
).some)
```
```scala mdoc:nest
val hello = new Hello[IO](myService)
hello.hello(Id(1))
  .map(println)
  .unsafeRunSync()
```
```scala mdoc:nest
hello.hello(Id(2))
  .map(println)
  .unsafeRunSync()
```

```scala mdoc:nest:height=6
/* If you don't expect Hello to use MyService.getName,
 * you can simply remove feeding the function for that operation
 * and StubTools let you know where it fails if Hello uses MySerivce.getName.
 */
val myService2: MyService[IO] = MyServiceStub(none)
val hello2 = new Hello(myService2)
hello2.hello(Id(1))
  .attempt
  .map(println)
  .unsafeRunSync()
```

:::caution NOTE

Why not just use mock framework for convenience? To answer that, please read [Pitfalls of Mocking in tests](https://xebia.com/blog/pitfalls-mocking-tests-how-to-avoid) from [Xebia Functional](https://xebia.com) (formerly known as 47 Degrees)

Besides what the blog tells you, mock frameworks often make you do bad practice like testing the implementation details with `verify`.

There is also an issue when your mock is not correctly set. You may get a `NullPointerException` for that, but it doesn't tell you where it's from and why you get it.

:::
