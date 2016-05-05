package ru.bonsystems.appkode

import java.io.*

/**
 * Created by Kolomeytsev Anton on 01 Май 2016.
 * This class is a part of StringsTranslator project.
 */

class CsvStrings : Strings {

    constructor(url: String) {
        var stream = BufferedReader(InputStreamReader(File(url).inputStream()))
        stream.lines().forEach {
            var values = safeSplit(it + ";")
            var key = values.get(0)
            var value = values.get(1)
            var someOs = values.get(2).toUpperCase()
            // todo выводить предупреждение, если запись в таблице не соответствует ни одной ОС
            var os = if (someOs == "ANDROID") MergeList.OperatingSystem.ANDROID else
                if (someOs == "IOS") MergeList.OperatingSystem.IOS else MergeList.OperatingSystem.BOTH
            list.add(MergeList.StringsItem(
                    key,
                    value,
                    os
            ))
        }
    }

    constructor(vararg strings: Strings) {
        for (s in strings) {
            s.list.forEach {
                list.add(it)
            }
        }
    }

    constructor(strings: List<Strings>) {
        strings.forEach {
            it.list.forEach {
                list.add(it)
            }
        }
    }

    override fun writeToFile(url: String) {
        var stream = BufferedWriter(OutputStreamWriter(File(url).outputStream()))
        var builder = StringBuilder()
        list.forEach {
            builder.append("\"${it.key}\";\"${it.value}\";${it.os}\n")
        }
        stream.write(builder.toString())
        stream.close()
        System.gc()
    }

}