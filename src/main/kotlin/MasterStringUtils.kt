import java.io.File

class MasterStringUtils {
    private val masterStringRootPath: String = "C:\\CodeBase\\hmi-common\\NavHome\\Apps\\Denali\\HMI\\src\\main\\res"
    fun removeChar(key: String, deleteChar: String) {
        val keySet = key.split(" ")
        val fileDir = File(masterStringRootPath)
        val fileTree = fileDir.walk()
        fileTree.maxDepth(2).filter { it.isFile }.filter { it.name == "string_values.xml" }.forEach {
            val tempFile = createTempFile()
            tempFile.printWriter().use { writer ->
                it.forEachLine { line ->
                    var tempLine = line
                    keySet.forEach { eachKey ->
                        if (line.contains(eachKey)) {
                            tempLine = line.replace(deleteChar, "")
                        }
                    }
                    writer.println(tempLine)
                }
            }
            check(it.delete() && tempFile.renameTo(it)) {"failed to replace file"}
        }
    }
}