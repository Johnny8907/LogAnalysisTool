package controller

import tornadofx.Controller
import LegalDocUtils

class LegalUpdateController : Controller() {
    fun updateLegalDocs() {
        val fileHandler = LegalDocUtils()
        fileHandler.execute()
    }
}