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
    override val root = tabpane {
        tab<FileHandlerScreen>()
//        tab<LogFilterScreen>()
    }
}