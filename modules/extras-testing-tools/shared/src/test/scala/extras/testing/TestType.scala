package extras.testing

trait TestType {
  def foo(n: Int): Int
}

object TestTypeStub {
  def apply(f: => Option[Int => Int]): TestType = new TestType {
    @SuppressWarnings(Array("org.wartremover.warts.Throw"))
    override def foo(n: Int): Int =
      f.fold[Int](throw StubTools.missing)(_(n)) // scalafix:ok DisableSyntax.throw
  }
}