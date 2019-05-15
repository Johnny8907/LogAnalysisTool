import java.io.File

class DenaliTemplate: Template {
    override fun getTemplateFromFile(file: File): MutableMap<String, String> {
        return hashMapOf()
    }

    override fun getDefaultTemplate(): MutableMap<String, String> {
        return hashMapOf("a GM HeadUnit" to "Denali_app_start",
            "[init] start app version :" to "Start app version",
            "GLEngine version: " to "GLEngine version",
            "[init] initAutoSdk async thread starts preparation" to "Auto sdk init",
            "wayPoints size: 0" to "way point size")
    }
}