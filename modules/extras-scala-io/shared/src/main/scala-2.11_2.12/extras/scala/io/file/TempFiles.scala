package extras.scala.io.file

import extras.scala.io.CanClose

import java.io.File
import java.nio.file.Files
import scala.util.{Failure, Success, Try}

/** @author Kevin Lee
  * @since 2022-03-24
  */
object TempFiles {

  def runWithTempDir[A](dirPrefix: String)(run: TempDir => A): Either[Throwable, A] = {
    val tempDir = TempDir(Files.createTempDirectory(dirPrefix).toFile)
    try
      Try(run(tempDir)) match {
        case Success(result) => Right(result)
        case Failure(error) => Left(error)
      }
    finally {
      CanClose[TempDir].close(tempDir)
    }
  }

  final case class TempDir(value: File) extends AnyVal
  object TempDir {
    implicit val deleteTempFileWhenRelease: CanClose[TempDir] = new CanClose[TempDir] {
      def close(tempDir: TempDir): Unit = ops.deleteFileRecursively(tempDir.value)
    }
  }
}
