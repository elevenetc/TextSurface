package su.levenetc.android.textsurface.common

import android.graphics.PointF
import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.constants.Align
import su.levenetc.android.textsurface.constants.Pivot

/**
 * Created by Eugene Levenetc.
 */
data class Position(
        var align: Int = 0,
        var point: PointF = PointF(),
        var translationX: Float = 0f,
        var translationY: Float = 0f,
        var alignText: Text? = null
) {

    fun getRelativeX(pivot: Int, text: Text, global: Boolean): Float {
        var result = 0f
        when {
            pivot and Pivot.LEFT == Pivot.LEFT -> {
                //result = point.x;
            }
            pivot and Pivot.RIGHT == Pivot.RIGHT -> {
                result += text.width
            }
            pivot and Pivot.CENTER == Pivot.CENTER -> {
                result += text.width / 2
            }
        }
        return if (global) result + point.x + translationX else result
    }

    fun getRelativeY(pivot: Int, text: Text, global: Boolean): Float {
        var result = 0f
        when {
            pivot and Pivot.BOTTOM == Pivot.BOTTOM -> {
                //result = text.getY(textSurface);
            }
            pivot and Pivot.TOP == Pivot.TOP -> {
                result -= text.height
            }
            pivot and Pivot.CENTER == Pivot.CENTER -> {
                result -= text.height / 2
            }
        }
        return if (global) result + point.y + translationY else result
    }

    fun getX(textSurface: TextSurface, textWidth: Float): Float {

        if (align != Align.UNDEFINED) {
            if (alignedWith(Align.SURFACE_CENTER)) {
                point.x = -textWidth / 2
            } else {
                alignText?.let { alignText ->
                    if (align != Align.UNDEFINED) {
                        when {
                            alignedWith(Align.RIGHT_OF) -> {
                                point.x = alignText.getX(textSurface) + alignText.width
                            }
                            alignedWith(Align.LEFT_OF) -> {
                                point.x = alignText.getX(textSurface) - textWidth
                            }
                            alignedWith(Align.CENTER_OF) -> {
                                point.x = alignText.getX(textSurface) + (alignText.width - textWidth) / 2
                            }
                            else -> {
                                point.x = alignText.getX(textSurface)
                            }
                        }
                    }
                }
            }
        }
        return point.x + translationX
    }

    fun getY(textSurface: TextSurface, textHeight: Float): Float {

        if (align != Align.UNDEFINED) {
            if (alignedWith(Align.SURFACE_CENTER)) {
                point.y = textHeight / 2
            } else {
                alignText?.let { alignText ->
                    if (align != Align.UNDEFINED) {
                        when {
                            alignedWith(Align.TOP_OF) -> {
                                point.y = alignText.getY(textSurface) - alignText.height
                            }
                            alignedWith(Align.BOTTOM_OF) -> {
                                point.y = alignText.getY(textSurface) + textHeight
                            }
                            alignedWith(Align.CENTER_OF) -> {
                                point.y = alignText.getY(textSurface) - (alignText.height - textHeight) / 2
                            }
                            else -> {
                                point.y = alignText.getY(textSurface)
                            }
                        }
                    }
                }
            }
        }
        return point.y + translationY
    }

    fun setY(y: Float) {
        point.y = y
    }

    fun setX(x: Float) {
        point.x = x
    }

    private fun alignedWith(align: Int): Boolean {
        return this.align and align == align
    }
}