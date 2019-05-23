import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.io.FileOutputStream
import java.util.zip.GZIPInputStream
import java.io.FileInputStream
import model.SequenceChartX


class GMLoggerUtils {
    companion object {
        const val MAIN_FOLDER_NAME = "main"
        const val MAIN_FILE_NAME_KEY_WORD = "main.log"
    }

    private val denaliTemplate = DenaliTemplate()
    fun formatGMLogger(path: String) {
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
                val outPutFile = File(
                    pathBuilder.append(mainFolder.path).append("/").append(
                        file.name.slice(
                            IntRange(
                                0,
                                file.name.length - 3
                            )
                        )
                    ).toString()
                )
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

    fun getMainLogs(path: String): Array<File>? {
        val inputFile = File("$path/$MAIN_FOLDER_NAME")
        if (inputFile.exists()) {
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

//    fun filterLogByTemplate(
//        mainProcessMap: HashMap<Int, MutableList<String>>,
//        template: Template
//    ): HashMap<Int, MutableList<String>> {
//        val resultMap = hashMapOf<Int, MutableList<String>>()
//        mainProcessMap.forEach { (key, value) ->
//            resultMap[key] = filterLogByTemplate(value, template)
//        }
//        return resultMap
//    }

    fun generateTagMap(
        mainProcessMap: HashMap<Int, MutableList<String>>,
        template: Template
    ): HashMap<Int, HashMap<Int, SequenceChartX>> {
        val tagMap = hashMapOf<Int, HashMap<Int, SequenceChartX>>()
        mainProcessMap.forEach { (processID, strList) ->
            tagMap[processID] = filterLogByTemplate(strList, template)
        }
        return tagMap
    }

    private fun filterLogByTemplate(list: MutableList<String>, template: Template): HashMap<Int, SequenceChartX> {
        val templateMap = template.getDefaultTemplate()
        val resultList = hashMapOf<Int, SequenceChartX>()
        for ((index, value) in list.withIndex()) {
            for (keyword in templateMap.keys) {
                //If this line contains the template key string, add tag for it. else remove this line from log
                if (value.contains(keyword)) {
                    resultList[index] = SequenceChartX(denaliTemplate.getTimeStrFromLine(value), keyword)
                }
            }
        }
        return resultList
    }

    fun getMainProcessIDList(file: File): MutableList<Int> {
        val result = mutableListOf<Int>()
        for (readLine in file.readLines()) {
            if (isTNMainProcess(readLine)) {
                val processID = denaliTemplate.getProcessIDFromLine(readLine)
                if (!result.contains(processID)) {
                    result.add(processID)
                }
            }
        }
        return result
    }

    fun filterTNProcessID(file: File, processIDList: MutableList<Int>): HashMap<Int, MutableList<String>> {
        val result = hashMapOf<Int, MutableList<String>>()
        for (readLine in file.readLines()) {
            val processID = denaliTemplate.getProcessIDFromLine(readLine)
            if (processIDList.contains(processID)) {
                if (result.containsKey(processID)) {
                    result[processID]?.add(readLine)
                } else {
                    val strList = mutableListOf<String>()
                    strList.add(readLine)
                    result[processID] = strList
                }
            }
        }
        return result
    }

    fun writeProcessLogToFile(processDataMap: MutableMap<Int, MutableList<String>>) {
        processDataMap.forEach { (key, value) ->
            val file = File("main_process_$key.log")
            FileUtils.writeLines(file, value, true)
        }
    }

    private fun isTNMainProcess(str: String): Boolean {
        return str.contains("com.telenav.arp: Denali-HMI:") && !str.contains("cluster", true)
    }

    fun isTNClusterProcess(str: String): Boolean {
        return str.contains("com.telenav.arp: Denali-HMI:") && str.contains("cluster", true)
    }
}