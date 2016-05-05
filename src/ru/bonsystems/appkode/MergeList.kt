package ru.bonsystems.appkode

import java.util.*

/**
 * Created by Kolomeytsev Anton on 01 Май 2016.
 * This class is a part of StringsTranslator project.
 */

class MergeList : ArrayList<MergeList.StringsItem>() {

    override fun add(element: StringsItem): Boolean {
        for (it in this) {
            if (element.key == it.key) {
                if (element.os != it.os) {
                    if (element.value == it.value) {
                        it.os = OperatingSystem.BOTH
                        return false
                    } else {
                        return super.add(element)
                    }
                }
            }
        }
        return super.add(element)
    }

    data class StringsItem(var key: String, var value: String, var os: OperatingSystem)
    enum class OperatingSystem { ANDROID, IOS, BOTH }
}