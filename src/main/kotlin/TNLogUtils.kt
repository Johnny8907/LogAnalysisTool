interface TNLogUtils {
    fun getProcessStartTime(processID:Int, processDataMap: MutableMap<Int, MutableList<String>>): String
    fun getProcessIDFromLine(str: String): Int
    fun getTimeStrFromLine(str: String): String
}