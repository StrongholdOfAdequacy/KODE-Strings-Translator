package ru.bonsystems.appkode.ui

import javafx.util.Pair
import ru.bonsystems.swing.LinearLayout
import ru.bonsystems.swing.Well
import ru.bonsystems.swing.utils.Wrapper
import java.awt.*
import java.util.function.BiConsumer
import javax.swing.JFileChooser
import javax.swing.JFrame

/**
 * Created by Kolomeytsev Anton on 02 Май 2016.
 * This class is a part of StringsTranslator project.
 */

class Window : JFrame {

    var fileChooser: JFileChooser = JFileChooser()
    init {
        fileChooser.isMultiSelectionEnabled = false
    }
    var container = LayoutHolder.createLayout()
    var wellCsvToStrings: Well = LayoutHolder.getWellCsvToStrings(
            { chooseFile("Choose CSV table") },
            { s: String, pair: Pair<Boolean, Boolean> -> splitPerformer.invoke(s, pair.key, pair.value) },
            { loadFragment(wellSelection) }
    )
    var wellStringsToCsv: Well = LayoutHolder.getWellStringsToCsv(
            { chooseFile("Choose Android strings file") },
            { chooseFile("Choose IOS strings file") },
            { s: String, s1: String -> mergePerformer.invoke(s, s1) },
            { loadFragment(wellSelection) }
    )
    var wellSelection: Well = LayoutHolder.getWellSelection(
            { loadFragment(wellStringsToCsv) },
            { loadFragment(wellCsvToStrings) }
    )
    private var mergePerformer: (String, String) -> Unit
    private var splitPerformer: (String, Boolean, Boolean) -> Unit

    constructor(
            mergePerformer: (android: String, ios: String) -> Unit,
            splitPerformer: (csv: String, android: Boolean, ios: Boolean) -> Unit
    ) {
        this.mergePerformer = mergePerformer
        this.splitPerformer = splitPerformer
        title = "Strings translator"
        size = Dimension(500, 300)
        background = Color(0xefefef)
        var screenSize = Toolkit.getDefaultToolkit().getScreenSize()
        location = Point((screenSize.width - width) / 2, (screenSize.height - height) / 2)
        defaultCloseOperation = EXIT_ON_CLOSE
        layout = BorderLayout()
        add(container)
        loadFragment(wellSelection)
        isVisible = true
    }

    fun loadFragment(f: Well) {
        container.removeAll()
        container.add(f)
        container.recompile()
        repaint()
    }

    fun chooseFile(title: String) : String? {
        fileChooser.dialogTitle = title;
        var result = fileChooser.showOpenDialog(null)
        if (result == JFileChooser.APPROVE_OPTION) return fileChooser.selectedFile.absolutePath
        return null
    }
}