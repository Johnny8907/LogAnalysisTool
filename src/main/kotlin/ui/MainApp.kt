package ui

import controller.MainController
import model.ProcessInfo
import tornadofx.*

/**
 * @Author: Johnny Zhang
 * @Date: 2019/5/19 17:12
 */
class MainApp : App(MainView::class)

class MenuView : View() {
    private val controller: MainController by inject()

    override val root = borderpane {
        left = vbox {
            button("解压gmlogger文件") {
                action {
                    runAsync { controller.formatMainFiles() }
                }
            }
            button("将log加载到内存中并生成tag") {
                action {
                    runAsync { controller.loadLogsToMemoryAndGenerateTag() }
                }
            }
            button("生成Denali各个进程文件") {
                action {
                    runAsync { controller.generateProcessIDFiles() }
                }
            }
            button("生成Denali各个进程时间信息表格") {
                action {
                    runAsync {
                        controller.generateProcessInfoTable()
                    }
                }
            }
        }
        center = tableview<ProcessInfo> {
            items = controller.processInfo.asObservable()

            column("Process ID", ProcessInfo::processId)
            column("Process start time", ProcessInfo::processStartTime)
            column("Process end time", ProcessInfo::processEndTime)
        }
    }
}

class ContentView : View() {
    override val root = vbox {
        text {

        }
    }
}

class MainView : View() {
    private val topView = find(MenuView::class)
    // Create a lazy reference to BottomView
    private val bottomView = find(ContentView::class)

    override val root = borderpane {
        top = topView.root
        bottom = bottomView.root
    }
}