package su.levenetc.android.textsurface.utils

import android.view.View
import android.view.ViewGroup


internal fun measureDimension(desiredSize: Int, measureSpec: Int): Int {
    var result: Int
    val mode = View.MeasureSpec.getMode(measureSpec)
    val size = View.MeasureSpec.getSize(measureSpec)
    if (mode == View.MeasureSpec.EXACTLY) {
        result = size
    } else {
        result = desiredSize
        if (mode == View.MeasureSpec.AT_MOST) {
            result = result.coerceAtMost(size)
        }
    }
    return result
}

internal fun getDesiredSize(layoutParamValue: Int, minSize: Int, spec: Int): Int {
    return if (minSize != 0) {
        minSize
    } else {
        when (layoutParamValue) {
            ViewGroup.LayoutParams.MATCH_PARENT -> {
                View.MeasureSpec.getSize(spec)
            }
            ViewGroup.LayoutParams.WRAP_CONTENT -> {
                0
            }
            else -> {
                layoutParamValue
            }
        }
    }
}