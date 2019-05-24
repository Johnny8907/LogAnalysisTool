import java.io.File

interface Template {
    fun getDefaultTemplate(): MutableMap<String, String>
    fun getTemplateFromFile(file: File): MutableMap<String, String>
    fun getProcessStartTime(processID:Int, processDataMap: MutableMap<Int, MutableList<String>>): String
    fun getProcessIDFromLine(str: String): Int
    fun getTimeFromLine(str: String): String
    fun getDateFromLine(str: String): String
}