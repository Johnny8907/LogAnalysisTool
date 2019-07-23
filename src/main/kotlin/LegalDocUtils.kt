import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.ArrayList
import java.util.HashMap

class LegalDocUtils {
    private val fileNameMap = HashMap<String, String>()
    private var fileList: MutableList<File>
    private var inputFileListDir: String
    private var outputFileListDir: String

    init {
        initFileMap()
        fileList = ArrayList()
        inputFileListDir = "C:\\Users\\bzhang2\\Desktop\\Download\\downloadzbZU6223417"
        outputFileListDir = "C:\\CodeBase\\hmi-common\\NavHome\\Apps\\Denali\\HMI\\src\\main\\res"
        val fileDir = File(inputFileListDir)
        val arrayOfFiles = fileDir.listFiles()
        if (arrayOfFiles != null) {
            for (file in arrayOfFiles) {
                if (file.isFile) {
                    fileList.add(file)
                }
            }
        } else {
            println("arrayOfFiles is null!")
        }
    }

    fun execute() {
        for (file in fileList) {
            val fileName = file.name
            val keySet = fileNameMap.keys
            var fileFinded = false
            for (key in keySet) {
                if (fileName == key) {
                    val destFilePath = "$outputFileListDir/${fileNameMap[key]}"
                    copyFile(file, destFilePath, outputFileListDir)
                    fileFinded = true
                }
            }
            if (!fileFinded) {
                println("$fileName no match any one in translation map")
            }
        }
        println("LegalDocUtils execute done")
    }

    private fun initFileMap() {
        //KR
        fileNameMap["GM21 Korea_Telenav Korea Location Information and Location-Based Services_05-14-19.txt"] = "/raw/lib_statement_kr.txt"
        fileNameMap["GM21 Korea_Telenav Software End User License Agreement_05-14-19.txt"] = "/raw/terms_and_conditions_kr.txt"
        fileNameMap["GM21 Korea_Telenav Software End User License Agreement_Korean_05-14-19.txt"] = "/raw-ko/terms_and_conditions_kr.txt"
        fileNameMap["GM68A1~1.TXT"] = "/raw-ko/lib_statement_kr.txt"

        //AF
        fileNameMap["GM21_Africa EULA - English_07-14-19.txt"] = "/raw/terms_and_conditions_af.txt"
        fileNameMap["GM21_Africa PRIVACY POLICY - English_07-14-19.txt"] = "/raw/privacy_statement_af.txt"
        //TODO:raw-en-rZA need to copy by hand

        //ANZ
        fileNameMap["GM21_ANZ EULA - English_07-14-19.txt"] = "/raw-en-rAU/terms_and_conditions_anz.txt"
        fileNameMap["GM21_ANZ PRIVACY POLICY - English_07-14-19.txt"] = "/raw-en-rAU/privacy_statement_anz.txt"

        //CN
        fileNameMap["GM21_China EULA - Chinese_05-14-19.txt"] = "/raw-zh-rCN/terms_and_conditions_cn.txt"
        fileNameMap["GM21_China PRIVACY POLICY - Chinese_07-14-19.txt"] = "/raw-zh-rCN/privacy_statement_cn.txt"
        fileNameMap["GM21_China PRIVACY POLICY - English_07-14-19.txt"] = "/raw/privacy_statement_cn.txt"
        fileNameMap["GM21_China EULA - English_05-14-19.txt"] = "/raw/terms_and_conditions_cn.txt"

        //EU t&c
        fileNameMap["GM21_EU EULA - Bulgarian_07-14-19.txt"] = "/raw-bg-rBG/terms_and_conditions_eu.txt"
        fileNameMap["GM21_EU EULA - Croatian_07-14-19.txt"] = "/raw-hr-rHR/terms_and_conditions_eu.txt"
        fileNameMap["GM21_EU EULA - Czech_07-14-19.txt"] = "/raw-cs-rCZ/terms_and_conditions_eu.txt"
        fileNameMap["GM21_EU EULA - Danish_07-14-19.txt"] = "/raw-da-rDK/terms_and_conditions_eu.txt"
        fileNameMap["GM21_EU EULA - Dutch_07-14-19.txt"] = "/raw-nl-rNL/terms_and_conditions_eu.txt"
        fileNameMap["GM21_EU EULA - English_07-14-19.txt"] = "/raw/terms_and_conditions_eu.txt"
        fileNameMap["GM21_EU EULA - Finnish_07-14-19.txt"] = "/raw-fi-rFI/terms_and_conditions_eu.txt"
        fileNameMap["GM21_EU EULA - French_07-14-19.txt"] = "/raw-fr-rFR/terms_and_conditions_eu.txt"
        fileNameMap["GM21_EU EULA - German_07-14-19.txt"] = "/raw-de-rDE/terms_and_conditions_eu.txt"
        fileNameMap["GM21_EU EULA - Greek_07-14-19.txt"] = "/raw-el-rGR/terms_and_conditions_eu.txt"
        fileNameMap["GM21_EU EULA - Hungarian_07-14-19.txt"] = "/raw-hu-rHU/terms_and_conditions_eu.txt"
        fileNameMap["GM21_EU EULA - Italian_07-14-19.txt"] = "/raw-it-rIT/terms_and_conditions_eu.txt"
        fileNameMap["GM21_EU EULA - Norwegian_07-14-19.txt"] = "/raw-no-rNO/terms_and_conditions_eu.txt"
        fileNameMap["GM21_EU EULA - Polish_07-14-19.txt"] = "/raw-pl-rPL/terms_and_conditions_eu.txt"
        fileNameMap["GM21_EU EULA - Portuguese_07-14-19.txt"] = "/raw-pt-rPT/terms_and_conditions_eu.txt"
        fileNameMap["GM21_EU EULA - Romanian_07-14-19.txt"] = "/raw-ro-rRO/terms_and_conditions_eu.txt"
        fileNameMap["GM21_EU EULA - Russian_07-14-19.txt"] = "/raw-ru-rRU/terms_and_conditions_eu.txt"
        fileNameMap["GM21_EU EULA - Serbian_07-14-19.txt"] = "/raw-sr-rCS/terms_and_conditions_eu.txt"
        fileNameMap["GM21_EU EULA - Slovak_07-14-19.txt"] = "/raw-sk-rSK/terms_and_conditions_eu.txt"
        fileNameMap["GM21_EU EULA - Slovene_07-14-19.txt"] = "/raw-sl-rSI/terms_and_conditions_eu.txt"
        fileNameMap["GM21_EU EULA - Spanish_07-14-19.txt"] = "/raw-es-rES/terms_and_conditions_eu.txt"
        fileNameMap["GM21_EU EULA - Swedish_07-14-19.txt"] = "/raw-sv-rSE/terms_and_conditions_eu.txt"
        fileNameMap["GM21_EU EULA - Turkish_07-14-19.txt"] = "/raw-tr-rTR/terms_and_conditions_eu.txt"
        fileNameMap["GM21_EU EULA - Ukranian_07-14-19.txt"] = "/raw-uk-rUA/terms_and_conditions_eu.txt"

        //EU privacy
        fileNameMap["GM21_EU PRIVACY POLICY - Bulgarian_07-13-19.txt"] = "/raw-bg-rBG/privacy_statement_eu.txt"
        fileNameMap["GM21_EU PRIVACY POLICY - Croatian_07-13-19.txt"] = "/raw-hr-rHR/privacy_statement_eu.txt"
        fileNameMap["GM21_EU PRIVACY POLICY - Czech_07-13-19.txt"] = "/raw-cs-rCZ/privacy_statement_eu.txt"
        fileNameMap["GM21_EU PRIVACY POLICY - Danish_07-13-19.txt"] = "/raw-da-rDK/privacy_statement_eu.txt"
        fileNameMap["GM21_EU PRIVACY POLICY - Dutch_07-13-19.txt"] = "/raw-nl-rNL/privacy_statement_eu.txt"
        fileNameMap["GM21_EU PRIVACY POLICY - English_07-13-19.txt"] = "/raw/privacy_statement_eu.txt"
        fileNameMap["GM21_EU PRIVACY POLICY - Finnish_07-13-19.txt"] = "/raw-fi-rFI/privacy_statement_eu.txt"
        fileNameMap["GM21_EU PRIVACY POLICY - French_07-13-19.txt"] = "/raw-fr-rFR/privacy_statement_eu.txt"
        fileNameMap["GM21_EU PRIVACY POLICY - German_07-13-19.txt"] = "/raw-de-rDE/privacy_statement_eu.txt"
        fileNameMap["GM21_EU PRIVACY POLICY - Greek_07-13-19.txt"] = "/raw-el-rGR/privacy_statement_eu.txt"
        fileNameMap["GM21_EU PRIVACY POLICY - Hungarian_07-13-19.txt"] = "/raw-hu-rHU/privacy_statement_eu.txt"
        fileNameMap["GM21_EU PRIVACY POLICY - Italian_07-13-19.txt"] = "/raw-it-rIT/privacy_statement_eu.txt"
        fileNameMap["GM21_EU PRIVACY POLICY - Norwegian_07-13-19.txt"] = "/raw-no-rNO/privacy_statement_eu.txt"
        fileNameMap["GM21_EU PRIVACY POLICY - Polish_07-13-19.txt"] = "/raw-pl-rPL/privacy_statement_eu.txt"
        fileNameMap["GM21_EU PRIVACY POLICY - Portuguese_07-13-19.txt"] = "/raw-pt-rPT/privacy_statement_eu.txt"
        fileNameMap["GM21_EU PRIVACY POLICY - Romanian_07-13-19.txt"] = "/raw-ro-rRO/privacy_statement_eu.txt"
        fileNameMap["GM21_EU PRIVACY POLICY - Russian_07-13-19.txt"] = "/raw-ru-rRU/privacy_statement_eu.txt"
        fileNameMap["GM21_EU PRIVACY POLICY - Serbian_07-13-19.txt"] = "/raw-sr-rCS/privacy_statement_eu.txt"
        fileNameMap["GM21_EU PRIVACY POLICY - Slovak_07-13-19.txt"] = "/raw-sk-rSK/privacy_statement_eu.txt"
        fileNameMap["GM21_EU PRIVACY POLICY - Slovene_07-13-19.txt"] = "/raw-sl-rSI/privacy_statement_eu.txt"
        fileNameMap["GM21_EU PRIVACY POLICY - Spanish_07-13-19.txt"] = "/raw-es-rES/privacy_statement_eu.txt"
        fileNameMap["GM21_EU PRIVACY POLICY - Swedish_07-13-19.txt"] = "/raw-sv-rSE/privacy_statement_eu.txt"
        fileNameMap["GM21_EU PRIVACY POLICY - Turkish_07-13-19.txt"] = "/raw-tr-rTR/privacy_statement_eu.txt"
        fileNameMap["GM21_EU PRIVACY POLICY - Ukranian_07-13-19.txt"] = "/raw-uk-rUA/privacy_statement_eu.txt"

        //IL
        fileNameMap["GM21_Israel EULA - Arabic_05-14-19.txt"] = "/raw-ar/terms_and_conditions_il.txt"
        fileNameMap["GM21_Israel EULA - English_05-14-19.txt"] = "/raw/terms_and_conditions_il.txt"
        fileNameMap["GM21_Israel EULA - Russian_05-14-19.txt"] = "/raw-ru-rRU/terms_and_conditions_il.txt"
        fileNameMap["GM21_Israel PRIVACY POLICY - Arabic_07-14-19.txt"] = "/raw-ar/privacy_statement_il.txt"
        fileNameMap["GM21_Israel PRIVACY POLICY - English_07-14-19.txt"] = "/raw/privacy_statement_il.txt"
        fileNameMap["GM21_Israel PRIVACY POLICY - Russian_07-14-19.txt"] = "/raw-ru-rRU/privacy_statement_il.txt"

        //ME
        fileNameMap["GM21_ME EULA - Arabic_05-14-19.txt"] = "/raw-ar/terms_and_conditions_me.txt"
        fileNameMap["GM21_ME EULA - English_05-14-19.txt"] = "/raw/terms_and_conditions_me.txt"
        fileNameMap["GM21_ME PRIVACY POLICY - Arabic_07-14-19.txt"] = "/raw-ar/privacy_statement_me.txt"
        fileNameMap["GM21_ME PRIVACY POLICY - English_07-14-19.txt"] = "/raw/privacy_statement_me.txt"

        //NA
        fileNameMap["GM21_NA EULA - English_05-14-19.txt"] = "/raw/terms_and_conditions_na.txt"
        fileNameMap["GM21_NA EULA - French_05-14-19.txt"] = "/raw-fr-rCA/terms_and_conditions_na.txt"
        fileNameMap["GM21_NA EULA - Spanish_05-14-19.txt"] = "/raw-es-rMX/terms_and_conditions_na.txt"
        fileNameMap["GM21_NA PRIVACY POLICY - English_07-14-19.txt"] = "/raw/privacy_statement_na.txt"
        fileNameMap["GM21_NA PRIVACY POLICY - French_07-14-19.txt"] = "/raw-fr-rCA/privacy_statement_na.txt"
        fileNameMap["GM21_NA PRIVACY POLICY - Spanish_07-14-19.txt"] = "/raw-es-rMX/privacy_statement_na.txt"

        //SA
        fileNameMap["GM21_SA EULA - English_07-14-19.txt"] = "/raw/terms_and_conditions_sa.txt"
        fileNameMap["GM21_SA EULA - Portuguese (Brazil)_07-14-19.txt"] = "/raw-pt-rPT/terms_and_conditions_sa.txt"
        fileNameMap["GM21_SA EULA - Spanish_07-14-19.txt"] = "/raw-es-rMX/terms_and_conditions_sa.txt"
        fileNameMap["GM21_SA PRIVACY POLICY - English_07-14-19.txt"] = "/raw/privacy_statement_sa.txt"
        fileNameMap["GM21_SA PRIVACY POLICY - Portuguese (Brazil)_07-14-19.txt"] = "/raw-pt-rPT/privacy_statement_sa.txt"
        fileNameMap["GM21_SA PRIVACY POLICY - Spanish_07-14-19.txt"] = "/raw-es-rMX/privacy_statement_sa.txt"

        //SEA
        fileNameMap["GM21_SEA EULA - English_07-14-19.txt"] = "/raw/terms_and_conditions_sea.txt"
        fileNameMap["GM21_SEA EULA - Thai_07-14-19.txt"] = "/raw-th-rTH/terms_and_conditions_sea.txt"
        fileNameMap["GM21_SEA PRIVACY POLICY - English_07-14-19.txt"] = "/raw/privacy_statement_sea.txt"
        fileNameMap["GM21_SEA PRIVACY POLICY - Thai_07-14-19.txt"] = "/raw-th-rTH/privacy_statement_sea.txt"

        //TR
        fileNameMap["GM21_Turkey EULA - English_05-14-19.txt"] = "/raw/terms_and_conditions_tr.txt"
        fileNameMap["GM21_Turkey EULA - Turkish_05-14-19.txt"] = "/raw-tr-rTR/terms_and_conditions_tr.txt"
        fileNameMap["GM21_Turkey PRIVACY POLICY - English_07-14-19.txt"] = "/raw/privacy_statement_tr.txt"
        fileNameMap["GM21_Turkey PRIVACY POLICY - Turkish_07-14-19.txt"] = "/raw-tr-rTR/privacy_statement_tr.txt"
    }

    private fun copyFile(oldFile: File, newFilePath: String, newFileDir: String) {
        try {
            var byteSum = 0
            var byteRead: Int
            if (oldFile.exists()) {
                val newFile = File(newFilePath)
                createDirAndFileIfNotExist(newFile, newFileDir)
                val inStream = FileInputStream(oldFile)
                val fs = FileOutputStream(newFile)
                val buffer = ByteArray(1444)
                while (true) {
                    byteRead = inStream.read(buffer)
                    if (byteRead == -1) break
                    byteSum += byteRead
//                    println(byteSum)
                    fs.write(buffer, 0, byteRead)
                }
                inStream.close()
            } else {
                println(oldFile.absoluteFile.toString() + " not exist!")
            }
        } catch (e: Exception) {
            println("copy failed!!")
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    private fun createDirAndFileIfNotExist(target: File, targetDir: String) {
        if (!target.exists()) {
            val file = File(targetDir)
            file.mkdirs()
            target.createNewFile()
        } else {
            target.delete()
            target.createNewFile()
        }
    }
}