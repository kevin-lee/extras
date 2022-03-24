package extras.scala.io.file

import java.io.File
import scala.annotation.tailrec
import scala.util.control.NonFatal

/** @author Kevin Lee
  * @since 2022-03-24
  */
object ops {

  def getAllFiles(file: File): List[File] = {
    @tailrec
    def getAllFilesRecursively(files: List[File], acc: List[File]): List[File] = files match {
      case file :: rest =>
        if (file.isDirectory) {
          getAllFilesRecursively(file.listFiles.toList ++ rest, file :: acc)
        } else {
          getAllFilesRecursively(rest, file :: acc)
        }

      case Nil =>
        acc
    }
    getAllFilesRecursively(List(file), List.empty)
  }

  def deleteFileRecursively(file: File): Unit =
    getAllFiles(file).foreach { each =>
      if (each.exists) {
        try each.delete()
        catch {
          case NonFatal(_) =>
            ()
        }
      } else ()
    }
}
