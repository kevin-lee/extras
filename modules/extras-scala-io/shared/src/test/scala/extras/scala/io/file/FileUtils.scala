package extras.scala.io.file

import java.io.File
import scala.annotation.tailrec

/** @author Kevin Lee
  * @since 2022-03-24
  */
object FileUtils {

  def cleanUpFilesInside(file: File): Unit = {
    @tailrec
    def cleanAll(files: List[File]): Unit = files match {
      case file :: rest =>
        if (file.isDirectory) {
          val list = file.listFiles
          if (list.isEmpty) {
            file.delete()
            cleanAll(rest)
          } else {
            cleanAll((list.toList ++ rest) :+ file)
          }
        } else {
          if (file.exists()) {
            file.delete()
            cleanAll(rest)
          } else cleanAll(rest)
        }
      case Nil =>
        ()
    }
    if (file.exists) {
      if (file.isDirectory) {
        cleanAll(file.listFiles.toList)
      } else {
        ()
      }
    } else {
      ()
    }
  }
}
