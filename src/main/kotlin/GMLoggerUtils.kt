import java.io.File
import java.io.IOException
import java.io.FileOutputStream
import java.util.zip.GZIPInputStream
import java.io.FileInputStream



class GMLoggerUtils {
    companion object {
        const val MAIN_FOLDER_NAME = "main"
        const val MAIN_FILE_NAME_KEY_WORD = "main.log"
    }
    fun formatGMLogger(path:String) {
        //1. create a main folder under given path
        val inputFile = File(path)
        if (!inputFile.exists()) {
            println("$path not exist!")
            return
        }
        val pathBuilder = StringBuilder()
        val mainFolder = File(pathBuilder.append(path).append("/").append(MAIN_FOLDER_NAME).toString())
        if (mainFolder.exists()) {
            println("Main folder exist!")
        }
        mainFolder.mkdirs()
        //2. go through all the zip file under GM folder and unzip them to main folder
        val files = inputFile.listFiles()
        files.forEach { file ->
            if (file.isFile && file.extension == "gz" && file.name.contains(MAIN_FILE_NAME_KEY_WORD)) {
                //unzip file and move it to "main" folder
                pathBuilder.clear()
                val outPutFile = File(pathBuilder.append(mainFolder.path).append("/").append(file.name.slice(IntRange(0, file.name.length - 3))).toString())
                unGzipFile(file, outPutFile)
            } else if (file.isFile && file.name == MAIN_FILE_NAME_KEY_WORD) {
                //3. copy main.log to main folder
                try {
                    file.copyTo(File(mainFolder.path + "/" + file.name))
                } catch (e: FileAlreadyExistsException) {
                    println(e.toString())
                }
            }
        }
    }

    fun getMainLogs(path: String):Array<File>? {
        val inputFile = File("$path/$MAIN_FOLDER_NAME")
        if (inputFile.exists())  {
            return inputFile.listFiles()
        } else {
            return null
        }
    }

    private fun unGzipFile(compressedFile: File, decompressedFile: File) {

        val buffer = ByteArray(1024)

        try {

            val fileIn = FileInputStream(compressedFile)

            val gZIPInputStream = GZIPInputStream(fileIn)

            val fileOutputStream = FileOutputStream(decompressedFile)

            var bytes_read: Int

            while (true) {
                bytes_read = gZIPInputStream.read(buffer)
                if (bytes_read > 0) {
                    fileOutputStream.write(buffer, 0, bytes_read)
                } else {
                    break
                }
            }

            gZIPInputStream.close()
            fileOutputStream.close()

            println("The file was decompressed successfully!")

        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }
}