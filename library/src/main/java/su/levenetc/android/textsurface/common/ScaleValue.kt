package su.levenetc.android.textsurface.common

import android.graphics.PointF

/**
 * Created by Eugene Levenetc.
 */
class ScaleValue {
    private val scale = PointF(1f, 1f)
    val pivot = PointF()
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