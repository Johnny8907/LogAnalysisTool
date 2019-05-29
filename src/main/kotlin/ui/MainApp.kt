package ui

import controller.MainController
import model.ProcessInfo
import tornadofx.*

/**
 * @Author: Johnny Zhang
 * @Date: 2019/5/19 17:12
 */
class MainApp : App(MainView::class)

class MainView : View() {
    private val controller: MainController by inject()
    override val root = borderpane()

    init {
        with(root) {
            left = vbox {
                button("解压gmlogger文件") {
                    action {
                        uncompressFile()
                    }
                }

                button("将log加载到内存中并生成tag") {
                    action {
                        loadLogs()
                    }
                }

                button("生成Denali各个进程文件") {
                    action {
                        generateProcessFile()
                    }
                }

                button("生成Denali各个进程时间信息表格") {
                    action {
                        generateProcessTable()
                    }
                }
            }
            center = tableview<ProcessInfo> {
                items = controller.processInfo
                column("process id",ProcessInfo::processId)
                column("process start time", ProcessInfo::processStartTime)
                column("process end time", ProcessInfo::processEndTime)
            }
        }
    }

    private fun uncompressFile() {
        runAsync {
            controller.formatMainFiles()
        }
    }

    private fun loadLogs() {
        runAsync { controller.loadLogsToMemoryAndGenerateTag() }
    }

    private fun generateProcessFile() {
        runAsync {
            controller.generateProcessIDFiles()
        }
    }
    private fun generateProcessTable() {
        runAsync { controller.generateProcessInfoTable()}
    }
}