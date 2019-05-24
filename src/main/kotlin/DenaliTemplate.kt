import java.io.File

class DenaliTemplate: Template {
    companion object {
        const val NO_RESULT_FOUND = "No result found"
        const val DENALI_START_LOG = "a GM HeadUnit"
        const val DATE_INDEX = 0
        const val TIME_INDEX = 1
        const val PROCESS_ID_INDEX = 2
    }

    override fun getTemplateFromFile(file: File): MutableMap<String, String> {
        return hashMapOf()
    }

    override fun getDefaultTemplate(): MutableMap<String, String> {
        return hashMapOf("a GM HeadUnit" to "程序启动",
            "[init] start app version :" to "应用版本",
            "GLEngine version: " to "GLEngine version",
            "[init] initAutoSdk async thread starts preparation" to "Auto sdk init",
            "wayPoints size: 0" to "way point 数量")
    }

    override fun getProcessStartTime(processID:Int, processDataMap: MutableMap<Int, MutableList<String>>): String {
        var resultStr = NO_RESULT_FOUND
        if (processDataMap.containsKey(processID)) {
            processDataMap[processID]?.forEach {
                if (it.contains(DENALI_START_LOG)) resultStr = getTimeFromLine(it)
            }
        }

        return resultStr
    }

    override fun getProcessIDFromLine(str: String): Int {
        try {
            return str.split("\\s+".toRegex())[PROCESS_ID_INDEX].toInt()
        } catch (e:Exception) {
            println("$str met Exception")
        }
        return 0
    }

    override fun getDateFromLine(str: String): String {
        try {
            return str.split("\\s+".toRegex())[DATE_INDEX]
        } catch (e:Exception) {
            println("$str met Exception")
        }
        return ""
    }
    override fun getTimeFromLine(str: String): String {
        try {
            return str.split("\\s+".toRegex())[TIME_INDEX]
        } catch (e:Exception) {
            println("$str met Exception")
        }
        return ""
    }
}