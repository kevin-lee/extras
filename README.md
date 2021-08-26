# extras
A few little extra tools

# Get extras
## `extras-cats`

```sbt
lazy val root = (project in file("."))
  .settings(
    name := "my-project",
    libraryDependencies += "io.kevinlee" %% "extras-cats" % "0.1.0"
  )
```

### Usage
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

***

## `extras-concurrent`
```sbt
lazy val root = (project in file("."))
  .settings(
    name := "my-project",
    libraryDependencies += "io.kevinlee" %% "extras-concurrent" % "0.1.0"
  )
```

## `extras-concurrent-testing`
```sbt
lazy val root = (project in file("."))
  .settings(
    name := "my-project",
    libraryDependencies += "io.kevinlee" %% "extras-concurrent-testing" % "0.1.0" % Test
  )
```
