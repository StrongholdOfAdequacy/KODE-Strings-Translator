package ru.bonsystems.appkode

import java.io.*

/**
 * Created by Kolomeytsev Anton on 01 Май 2016.
 * This class is a part of StringsTranslator project.
 */

class IosStrings : Strings {

    constructor(url: String) {
        var stream = BufferedReader(InputStreamReader(File(url).inputStream()))
        stream.lines().forEach {
            var findResult = find(it, "\".*\"\\s*\\=")
            var key = if (findResult != null) it.substring(findResult.first, findResult.second - 1).trim() else "undefined"
            var value = if (findResult != null) it.substring(findResult.second, it.length).trim() else ""
            key = key.substring(1, key.length - 1)
            value = value.substring(1, value.length - if (value.endsWith(';', false)) 2 else 1 )
            list.add(MergeList.StringsItem(
                    key,
                    value,
                    MergeList.OperatingSystem.IOS
            ))
        }
    }

    constructor(strings: Strings) {
        strings.list.forEach {
            if (it.os == MergeList.OperatingSystem.BOTH || it.os == MergeList.OperatingSystem.IOS)
                list.add(it)
        }
    }

    override fun writeToFile(url: String) {
        var stream = BufferedWriter(OutputStreamWriter(File(url).outputStream()))
        var builder = StringBuilder()
        list.forEach {
            builder.append("\"${it.key}\" = \"${it.value}\"\n")
        }
        stream.write(builder.toString())
        stream.close()
        System.gc()
    }

}