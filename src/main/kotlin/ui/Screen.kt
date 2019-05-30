package ui

import controller.MainController
import javafx.scene.Parent
import model.ProcessInfo
import tornadofx.*

class FileHandlerScreen : Fragment("文件操作") {
    private val controller: MainController by inject()
    override val root = vbox {
        hbox {
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
        tableview<ProcessInfo> {
            items = controller.originalProcessInfo
            column("process id", ProcessInfo::processId)
            column("process start time", ProcessInfo::processStartTime)
            column("process end time", ProcessInfo::processEndTime)
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
        runAsync { controller.generateProcessInfoTable() }
    }
}

class LogFilterScreen: Fragment("Log过滤") {
    override val root = vbox {

    }
}