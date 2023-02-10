package extras.testing

import cats.MonadThrow
import cats.syntax.all._

trait TestType[F[*]] {
  def foo(n: Int): F[Int]
  def bar(n: Int): F[Int]
}

object TestTypeStub {
  def apply[F[*]: MonadThrow](f4Foo: => Option[Int => Int], f4Bar: => Option[Int => F[Int]]): TestType[F] =
    new TestType[F] {

      override def foo(n: Int): F[Int] =
        StubToolsCats.stub(f4Foo).map(_(n))

      override def bar(n: Int): F[Int] =
        StubToolsCats.stub(f4Bar).flatMap(_(n))
    }
}
