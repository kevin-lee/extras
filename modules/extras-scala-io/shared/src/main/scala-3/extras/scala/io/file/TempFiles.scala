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

  type TempDir = TempDir.TempDir
  object TempDir {
    opaque type TempDir = File
    def apply(tempDir: File): TempDir = tempDir

    extension (tempDir: TempDir) {
      def value: File = tempDir
    }

    given tempDirCanEqual: CanEqual[TempDir, TempDir] = CanEqual.derived

    given deleteTempFileWhenRelease: CanClose[TempDir] = tempDir => syntax.deleteAllRecursively(tempDir.value)
  }
}
