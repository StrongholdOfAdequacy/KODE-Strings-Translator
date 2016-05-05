package ru.bonsystems.appkode

import org.w3c.dom.Document
import org.w3c.dom.NodeList
import java.io.BufferedWriter
import java.io.File
import java.io.InputStream
import java.io.OutputStreamWriter
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

/**
 * Created by Kolomeytsev Anton on 01 Май 2016.
 * This class is a part of StringsTranslator project.
 */

class AndroidStrings : Strings {

    constructor(url: String) {
        var document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url);
        forEach(document.firstChild.childNodes) {
            if (it.nodeName == "string") {
                var key = it.attributes.getNamedItem("name")?.nodeValue ?: "undefined"
                var value = it.childNodes.item(0)?.nodeValue ?: ""
                list.add(MergeList.StringsItem(
                        key,
                        value,
                        MergeList.OperatingSystem.ANDROID
                ))
            }
        }
    }

    constructor(strings: Strings) {
        strings.list.forEach {
            if (it.os == MergeList.OperatingSystem.BOTH || it.os == MergeList.OperatingSystem.ANDROID)
                list.add(it)
        }
    }

    override fun writeToFile(url: String) {
        var stream = BufferedWriter(OutputStreamWriter(File(url).outputStream()))
        var builder = StringBuilder()
        builder.append("<resources>\n")
        list.forEach {
            builder.append("\t<string name=\"${it.key}\">${it.value}</string>\n")
        }
        builder.append("</resources>")
        stream.write(builder.toString())
        stream.close()
        System.gc()
    }
}