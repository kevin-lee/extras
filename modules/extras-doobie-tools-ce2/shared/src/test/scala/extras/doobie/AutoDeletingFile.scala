package extras.doobie

import java.io.File

/** @author Kevin Lee
  * @since 2022-11-27
  */
final case class AutoDeletingFile(file: File) extends AutoCloseable {
  override def close(): Unit = {
    val _ = file.delete()
    ()
  }
}
