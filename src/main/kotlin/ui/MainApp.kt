package ui

import DenaliTemplate
import GMLoggerUtils
import javafx.scene.Parent
import model.SequenceChartX
import tornadofx.*

/**
 * @Author: Johnny Zhang
 * @Date: 2019/5/19 17:12
 */
class MainApp : App(MainView::class)
class MenuView: View() {
    private val gmLoggerUtils: GMLoggerUtils = GMLoggerUtils()
    private var mainProcessLogMap = HashMap<Int, MutableList<String>>() // HashMap<ProcessID, List<Logs>>
    private var mainProcessLogTagMap =
        HashMap<Int, HashMap<Int, SequenceChartX>>() // HashMap<ProcessID, HashMap<LineNumber, Tag>>
    override val root = vbox{
        menubar {
            menu("解压gmlogger文件") { action { gmLoggerUtils.formatGMLogger("gmlogger") } }
            menu("将log加载到内存中并生成tag") {
                action {
                    val fileArray = gmLoggerUtils.getMainLogs("gmlogger")
                    fileArray?.forEach { file ->
                        val mainProcessIDList = gmLoggerUtils.getMainProcessIDList(file)
                        mainProcessLogMap = gmLoggerUtils.filterTNProcessID(file, mainProcessIDList)
                    }
                    //生成tagMap
                    mainProcessLogTagMap = gmLoggerUtils.generateTagMap(mainProcessLogMap, DenaliTemplate())
                }
            }
            menu("生成Denali各个进程文件") { action { gmLoggerUtils.writeProcessLogToFile(mainProcessLogMap) } }
        }
    }
}
class ContentView: View() {
    override val root = vbox{
        text {

        }
    }
}
class MainView : View() {
    val topView = find(MenuView::class)
    // Create a lazy reference to BottomView
    val bottomView = find(ContentView::class)

    override val root = borderpane {
        top = topView.root
        bottom = bottomView.root
    }
}