class DenaliLogUtils : TNLogUtils{

    override fun getProcessStartTime(processID:Int, processDataMap: MutableMap<Int, MutableList<String>>): String {
        var resultStr = NO_RESULT_FOUND
        if (processDataMap.containsKey(processID)) {
            processDataMap[processID]?.forEach {
                if (it.contains(DENALI_START_LOG)) resultStr = getTimeStrFromLine(it)
            }
        }

        return resultStr
    }

    override fun getProcessIDFromLine(str: String): Int {
        return str.split("\\s+".toRegex())[PROCESS_ID_INDEX].toInt()
    }

    override fun getTimeStrFromLine(str: String): String {
        return str.split("\\s+".toRegex())[TIME_COLUM_INDEX]
    }

    companion object {
        const val NO_RESULT_FOUND = "No result found"
        const val DENALI_START_LOG = "a GM HeadUnit"
        const val TIME_COLUM_INDEX = 1
        const val PROCESS_ID_INDEX = 2
    }

}