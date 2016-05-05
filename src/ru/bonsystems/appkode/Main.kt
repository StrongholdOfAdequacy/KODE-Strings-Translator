package ru.bonsystems.appkode

import ru.bonsystems.appkode.ui.Window
import java.util.*
import java.util.stream.IntStream
import javax.swing.JFileChooser
import javax.swing.JOptionPane

/**
 * Created by Kolomeytsev Anton on 01 Май 2016.
 * This class is a part of StringsTranslator project.
 */

var fileChooser = JFileChooser()

fun main(args: Array<String>) {
    if (args.size > 0) {
        if (args[0] == "tocsv") {
            var csvFileName = args[1]
            var list = ArrayList<Strings>()
            IntStream.range(2, args.size).forEach {
                if (args[it].endsWith(".xml")) list.add(AndroidStrings(args[it]))
                else if (args[it].endsWith(".strings")) list.add(IosStrings(args[it]))
                else println("Can't open file ${args[it]}")
            }
            var csvStrings = CsvStrings(list)
            csvStrings.writeToFile(csvFileName)
        } else if (args[0] == "fromcsv") {
            var csvStrings = CsvStrings(args[1])
            IntStream.range(2, args.size).forEach {
                if (args[it].endsWith(".xml")) AndroidStrings(csvStrings).writeToFile(args[it])
                else if (args[it].endsWith(".strings")) IosStrings(csvStrings).writeToFile(args[it])
                else println("Can't create file ${args[it]}")
            }
        }
    } else Window({ s: String, s1: String -> merge(s, s1)}, {s: String, a: Boolean, i: Boolean -> split(s, a, i)})
}

fun split(csv: String, makeForAndroid: Boolean, makeForIos: Boolean) {
    try {
        var csvStrings = CsvStrings(csv)
        if (makeForAndroid) {
            var url = chooseSaveFile("Choose where to save Android strings")
            if (!url.isNullOrEmpty()) AndroidStrings(csvStrings).writeToFile(url)
        }
        if (makeForIos) {
            var url = chooseSaveFile("Choose where to save IOS strings")
            if (!url.isNullOrEmpty()) IosStrings(csvStrings).writeToFile(url)
        }
    } catch (e: Exception) {
        JOptionPane.showMessageDialog(null, "Oops! Something went wrong :(")
    }
}


fun merge(androidUrl: String, iosUrl: String) {
    try {
        var list = ArrayList<Strings>()
        if (!androidUrl.isNullOrEmpty()) list.add(AndroidStrings(androidUrl))
        if (!iosUrl.isNullOrEmpty()) list.add(IosStrings(iosUrl))
        var url = chooseSaveFile("Chose where to save CSV table")
        if (!url.isNullOrEmpty()) CsvStrings(list).writeToFile(url)
    } catch (e: Exception) {
        JOptionPane.showMessageDialog(null, "Oops! Something went wrong :(")
    }
}

fun chooseSaveFile(title: String) : String {
    fileChooser.dialogTitle = title
    var result = fileChooser.showSaveDialog(null)
    if (result == JFileChooser.APPROVE_OPTION) return fileChooser.selectedFile.absolutePath
    return ""
}