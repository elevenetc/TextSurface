package su.levenetc.android.textsurface.common

import android.graphics.PointF

/**
 * Created by Eugene Levenetc.
 */
data class ScaleValue(val scale: PointF = PointF(1f, 1f), val pivot: PointF = PointF()) {

    fun setValue(scale: Float) {
        this.scale[scale] = scale
    }

    fun setValueX(x: Float) {
        scale[x] = scale.y
    }

    fun setValueY(y: Float) {
        scale[scale.x] = y
    }

    val scaleX: Float
        get() = scale.x
    val scaleY: Float
        get() = scale.y

    fun reset() {
        scale[1f] = 1f
        pivot[0f] = 0f
    }
}