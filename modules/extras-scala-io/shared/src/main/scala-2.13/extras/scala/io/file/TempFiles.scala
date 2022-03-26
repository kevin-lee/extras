package extras.scala.io.file

import extras.scala.io.CanClose

import java.io.File
import java.nio.file.Files
import scala.util.Using

/** @author Kevin Lee
  * @since 2022-03-24
  */
object TempFiles {

  def runWithTempDir[A](dirPrefix: String)(run: TempDir => A): Either[Throwable, A] =
    Using(
      TempDir(Files.createTempDirectory(dirPrefix).toFile)
    )(run).toEither

  final case class TempDir(value: File) extends AnyVal
  object TempDir {
    implicit val deleteTempFileWhenRelease: CanClose[TempDir] = tempDir => ops.deleteAllRecursively(tempDir.value)
  }
}
