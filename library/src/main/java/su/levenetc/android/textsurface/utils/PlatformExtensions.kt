package su.levenetc.android.textsurface.utils

import android.content.res.Resources
import android.util.TypedValue

internal fun Float.toScaledPx(): Float {
    val metrics = Resources.getSystem().displayMetrics
    return this * (metrics.densityDpi / 160f)
}

internal fun Float.toSp(): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)
}