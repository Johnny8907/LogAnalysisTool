package ui

import GMLoggerUtils
import DenaliTemplate
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import model.SequenceChartX
import tornadofx.*

/**
 * @Author: Johnny Zhang
 * @Date: 2019/5/19 17:12
 */
class MainApp : App(MainView::class)

class MainView : View() {
    private val gmLoggerUtils: GMLoggerUtils = GMLoggerUtils()
    private var mainProcessLogMap = HashMap<Int, MutableList<String>>() // HashMap<ProcessID, List<Logs>>
    private var mainProcessLogTagMap =
        HashMap<Int, HashMap<Int, SequenceChartX>>() // HashMap<ProcessID, HashMap<LineNumber, Tag>>
    override val root = vbox {
        button("解压gmlogger文件") {
            action { gmLoggerUtils.formatGMLogger("gmlogger") }
        }
        button("将log加载到内存中 并生成tag") {
            action {
                //生成mainProcessMap
                val fileArray = gmLoggerUtils.getMainLogs("gmlogger")
                fileArray?.forEach { file ->
                    val mainProcessIDList = gmLoggerUtils.getMainProcessIDList(file)
                    mainProcessLogMap = gmLoggerUtils.filterTNProcessID(file, mainProcessIDList)
                }
                //生成tagMap
                mainProcessLogTagMap = gmLoggerUtils.generateTagMap(mainProcessLogMap, DenaliTemplate())
            }
        }
        button("生成Denali Process文件") {
            action {
                gmLoggerUtils.writeProcessLogToFile(mainProcessLogMap)
            }
        }
        button("生成时序图") {
            action {
                for (processID in mainProcessLogTagMap.keys) {
                    linechart("时序图", CategoryAxis(), NumberAxis()) {
                        series("$processID") {
                            val map = mainProcessLogTagMap[processID]
                            map?.forEach { (lineNumber: Int, x: SequenceChartX) ->
                                data(x.toString(), lineNumber)
                            }
                        }
                    }
                }
            }
        }
    }
}