package su.levenetc.android.textsurface.utils

import java.util.*

internal fun Int.verifiedRange(from: Int, to: Int): Int {
    return when {
        this < from -> {
            from
        }
        this > to -> {
            to
        }
        else -> {
            this
        }
    }
}

internal fun Int.toEscapedString(): String {
    val array = CharArray(this)
    Arrays.fill(array, '\'')
    return String(array)
}