package controller

import tornadofx.Controller
import FileHandler

class LegalUpdateController : Controller() {
    fun updateLegalDocs() {
        val fileHandler = FileHandler()
        fileHandler.execute()
    }
}