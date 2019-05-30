package controller

import DenaliTemplate
import GMLoggerUtils
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import model.ProcessInfo
import model.SequenceChartX
import tornadofx.Controller

class MainController : Controller() {
    private val gmLoggerUtils: GMLoggerUtils = GMLoggerUtils()
    private var mainProcessLogMap = HashMap<Int, MutableList<String>>() // HashMap<ProcessID, List<Logs>>
    private var mainProcessLogTagMap =
        HashMap<Int, HashMap<Int, SequenceChartX>>() // HashMap<ProcessID, HashMap<LineNumber, Tag>>
    var originalProcessInfo: ObservableList<ProcessInfo> = FXCollections.observableArrayList(arrayListOf<ProcessInfo>())
    var filteredProcessInfo: ObservableList<String> = FXCollections.observableArrayList(arrayListOf<String>("1", "2"))
    fun formatMainFiles() {
        gmLoggerUtils.formatGMLogger("gmlogger")
        println("formatMainFiles end!")
    }

    fun loadLogsToMemoryAndGenerateTag() {
        val fileArray = gmLoggerUtils.getMainLogs("gmlogger")
        fileArray?.forEach { file ->
            val mainProcessIDList = gmLoggerUtils.getMainProcessIDList(file)
            mainProcessLogMap = gmLoggerUtils.filterTNProcessID(file, mainProcessIDList)
        }
        //生成tagMap
        mainProcessLogTagMap = gmLoggerUtils.generateTagMap(mainProcessLogMap, DenaliTemplate())
        println("loadLogsToMemoryAndGenerateTag end!")
    }

    fun generateProcessIDFiles() {
        gmLoggerUtils.writeProcessLogToFile(mainProcessLogMap)
        println("generateProcessIDFiles end!")
    }

    fun generateProcessInfoTable() {
        originalProcessInfo.addAll(gmLoggerUtils.generateProcessInfoTable(mainProcessLogMap))
        println("generateProcessInfoTable end!")
    }
}