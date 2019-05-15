import java.io.File

interface Template {
    fun getDefaultTemplate(): MutableMap<String, String>
    fun getTemplateFromFile(file: File): MutableMap<String, String>
}