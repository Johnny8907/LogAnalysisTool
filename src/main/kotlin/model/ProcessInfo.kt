package model

data class ProcessInfo(
    var processId: String = "",
    var processStartTime: String = "",
    var processEndTime: String = "",
    var processLogList: MutableList<String> = mutableListOf()
)
