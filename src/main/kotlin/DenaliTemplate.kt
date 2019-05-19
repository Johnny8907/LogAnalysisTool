import java.io.File

class DenaliTemplate: Template {
    override fun getTemplateFromFile(file: File): MutableMap<String, String> {
        return hashMapOf()
    }

    override fun getDefaultTemplate(): MutableMap<String, String> {
        return hashMapOf("a GM HeadUnit" to "程序启动",
            "[init] start app version :" to "应用版本",
            "GLEngine version: " to "GLEngine version",
            "[init] initAutoSdk async thread starts preparation" to "Auto sdk init",
            "wayPoints size: 0" to "way point 数量")
    }
}