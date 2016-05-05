package ru.bonsystems.appkode

import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.util.*
import java.util.regex.Pattern
import java.util.stream.IntStream

/**
 * Created by Kolomeytsev Anton on 01 Май 2016.
 * This class is a part of StringsTranslator project.
 */

abstract class Strings {
    var list: MergeList = MergeList()

    abstract fun writeToFile(url: String)

    /**
     * Вспомогательные функции
     */
    protected fun forEach(childNodes: NodeList?, function: (node: Node) -> Unit) {
        IntStream.range(0, childNodes?.length ?: 0).forEach {
            function(childNodes!!.item(it))
        }
    }

    protected fun find(line: String, pattern: String) : Pair<Int, Int>? {
        var m = Pattern.compile(pattern).matcher(line);
        if (m.find())
            return Pair(m.start(), m.end())
        else
            return null
    }

    protected fun safeSplit(it: String): List<String> {
        var list = ArrayList<String>()
        var i = 0
        var oldPosition = 0
        var inQuotes = false
        while (i < it.length) {
            if (it.elementAt(i) == '\"') inQuotes = !inQuotes
            if (!inQuotes && it.elementAt(i) == ';') {
                var crop = it.substring(oldPosition, i).trim().replace("\"\"", "\"")
                if (crop.startsWith('\"') && crop.endsWith('\"')) if (crop.length > 2) crop = crop.substring(1, crop.length - 1)
                list.add(crop)
                oldPosition = i + 1
            }
            if (it.elementAt(i) == '\\') i++
            i++
        }
        return list
    }
}