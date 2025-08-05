package extras.testing

trait TestType2 {
  def bar(n: Int): Int
}

object TestTypeStub2 {
  def apply(f: => Option[Int => Int]): TestType2 = new TestType2 {
    @SuppressWarnings(Array("org.wartremover.warts.Throw"))
    override def bar(n: Int): Int =
      f.fold[Int](throw StubTools.missing)(_(n)) // scalafix:ok DisableSyntax.throw
  }
}
