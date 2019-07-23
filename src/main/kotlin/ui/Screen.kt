package ui

import controller.LegalUpdateController
import controller.MainController
import controller.StringUpdateController
import javafx.scene.control.TextField
import model.ProcessInfo
import org.apache.http.util.TextUtils
import tornadofx.*

class FileHandlerScreen : Fragment("文件操作") {
    private val controller: MainController by inject()
    private val legalUpdateController: LegalUpdateController by inject()
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

            button("更新法律文件") {
                action {
                    updateLegalDocs()
                }
            }

            button("删除String中的特定字符串") {
                action {
                    openInternalWindow<MyView>()
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
        runAsync { controller.formatMainFiles() }
    }

    private fun loadLogs() {
        runAsync { controller.loadLogsToMemoryAndGenerateTag() }
    }

    private fun generateProcessFile() {
        runAsync { controller.generateProcessIDFiles() }
    }

    private fun generateProcessTable() {
        runAsync { controller.generateProcessInfoTable() }
    }

    private fun updateLegalDocs() {
        runAsync { legalUpdateController.updateLegalDocs() }
    }

    class MyView : View() {
        private val stringUpdateController: StringUpdateController by inject()
        var key: TextField by singleAssign()
        var deleteChar: TextField by singleAssign()
        override val root = vbox {
            hbox {
                label("字符串Key")
                key = textfield()
            }
            hbox {
                label("要删除的字符")
                deleteChar = textfield()
            }
            button("LOGIN") {
                action {
                    deleteChar(key.text, deleteChar.text)
                }
            }
        }

        private fun deleteChar(key: String, deleteChar: String) {
            if (key.isNotEmpty() && deleteChar.isNotEmpty()) {
                runAsync { stringUpdateController.updateString(key, deleteChar) }
            }
        }
    }
}