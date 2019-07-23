package controller

import tornadofx.Controller
import MasterStringUtils

class StringUpdateController :Controller(){
    var stringUtil: MasterStringUtils = MasterStringUtils()
    fun updateString(key: String, str: String) {
        stringUtil.removeChar(key, str)
    }
}