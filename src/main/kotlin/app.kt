import org.apache.commons.io.FileUtils
import java.io.File

val denaliLogUtils = DenaliLogUtils()
val gmLoggerUtils = GMLoggerUtils()
var mainProcessMap = HashMap<Int, MutableList<String>>()
val clusterProcessMap = HashMap<Int, MutableList<String>>()

fun main() {
    //Format file
//    gmLoggerUtils.formatGMLogger("gmlogger_2019_4_23_10_13_14")
    //Parse file
    val fileArray = gmLoggerUtils.getMainLogs("gmlogger_2019_4_23_10_13_14")
    fileArray?.forEach { file ->
        val mainProcessIDList = getMainProcessIDList(file)
        mainProcessMap = filterTNProcessID(file, mainProcessIDList)
        writeProcessLogToFile(filterLogByTemplate(mainProcessMap, DenaliTemplate()))
    }
//    val file = File("main.log")
//    val mainProcessIDList = getMainProcessIDList(file)
//    mainProcessMap = filterTNProcessID(file, mainProcessIDList)
//    writeProcessLogToFile(filterLogByTemplate(mainProcessMap, DenaliTemplate()))
}

fun filterLogByTemplate(
    mainProcessMap: HashMap<Int, MutableList<String>>,
    template: Template
): HashMap<Int, MutableList<String>> {
    val resultMap = hashMapOf<Int, MutableList<String>>()
    mainProcessMap.forEach { (key, value) ->
        resultMap[key] = filterLogByTemplate(value, template)
    }
    return resultMap
}

fun filterLogByTemplate(list: MutableList<String>, template: Template): MutableList<String> {
    val templateMap = template.getDefaultTemplate()
    val resultList = mutableListOf<String>()
    for (item in list.iterator()) {
        for (key: String in templateMap.keys) {
            //If this line contains the template key string, add tag for it. else remove this line from log
            if (item.contains(key)) {
                val builder: StringBuilder = StringBuilder()
                builder.append("Tag: ${templateMap[key]} Log: $item")
                resultList.add(builder.toString())
            }
        }
    }
    return resultList
}

fun getMainProcessIDList(file: File): MutableList<Int> {
    val result = mutableListOf<Int>()
    for (readLine in file.readLines()) {
        if (isTNMainProcess(readLine)) {
            val processID = denaliLogUtils.getProcessIDFromLine(readLine)
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
        val processID = denaliLogUtils.getProcessIDFromLine(readLine)
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

fun isTNMainProcess(str: String): Boolean {
    return str.contains("com.telenav.arp: Denali-HMI:") && !str.contains("cluster", true)
}

fun isTNClusterProcess(str: String): Boolean {
    return str.contains("com.telenav.arp: Denali-HMI:") && str.contains("cluster", true)
}
