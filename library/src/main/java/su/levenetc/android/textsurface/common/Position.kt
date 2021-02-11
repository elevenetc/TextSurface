package su.levenetc.android.textsurface.common

import android.graphics.PointF
import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.contants.Align
import su.levenetc.android.textsurface.contants.Pivot

/**
 * Created by Eugene Levenetc.
 */
class Position {
    private var align = 0
    private var alignText: Text? = null
    var point: PointF? = PointF()
        private set
    private var translationX = 0f
    private var translationY = 0f

    constructor() {
        point = PointF()
    }

    constructor(src: Position) {
        align = src.align
        alignText = src.alignText
        point!!.set(src.point!!)
        translationX = src.translationX
        translationY = src.translationY
    }

    constructor(point: PointF?) {
        this.point = point
    }

    constructor(align: Int) {
        this.align = align
    }

    constructor(align: Int, alignText: Text?) {
        this.align = align
        this.alignText = alignText
    }

    fun set(src: Position) {
        align = src.align
        alignText = src.alignText
        point!!.set(src.point!!)
        translationX = src.translationX
        translationY = src.translationY
    }

    fun getRelativeX(pivot: Int, text: Text?, global: Boolean): Float {
        var result = 0f
        if (pivot and Pivot.LEFT == Pivot.LEFT) {
//			result = point.x;
        } else if (pivot and Pivot.RIGHT == Pivot.RIGHT) {
            result += text!!.width
        } else if (pivot and Pivot.CENTER == Pivot.CENTER) {
            result += text!!.width / 2
        }
        return if (global) result + point!!.x + translationX else result
    }

    fun getRelativeY(pivot: Int, text: Text?, global: Boolean): Float {
        var result = 0f
        if (pivot and Pivot.BOTTOM == Pivot.BOTTOM) {
            //result = text.getY(textSurface);
        } else if (pivot and Pivot.TOP == Pivot.TOP) {
            result -= text!!.height
        } else if (pivot and Pivot.CENTER == Pivot.CENTER) {
            result -= text!!.height / 2
        }
        return if (global) result + point!!.y + translationY else result
    }

    fun getX(textSurface: TextSurface?, textWidth: Float): Float {
        if (isAligned) {
            if (alignedWith(Align.SURFACE_CENTER)) {
                point!!.x = -textWidth / 2
            } else if (alignedWith(Align.RIGHT_OF)) {
                point!!.x = alignText!!.getX(textSurface) + alignText!!.width
            } else if (alignedWith(Align.LEFT_OF)) {
                point!!.x = alignText!!.getX(textSurface) - textWidth
            } else if (alignedWith(Align.CENTER_OF)) {
                point!!.x = alignText!!.getX(textSurface) + (alignText!!.width - textWidth) / 2
            } else {
                point!!.x = alignText!!.getX(textSurface)
            }
        }
        return point!!.x + translationX
    }

    private fun alignedWith(align: Int): Boolean {
        return this.align and align == align
    }

    fun getY(textSurface: TextSurface?, textHeight: Float): Float {
        if (isAligned) {
            if (alignedWith(Align.SURFACE_CENTER)) {
                point!!.y = textHeight / 2
            } else if (alignedWith(Align.TOP_OF)) {
                point!!.y = alignText!!.getY(textSurface) - alignText!!.height
            } else if (alignedWith(Align.BOTTOM_OF)) {
                point!!.y = alignText!!.getY(textSurface) + textHeight
            } else if (alignedWith(Align.CENTER_OF)) {
                point!!.y = alignText!!.getY(textSurface) - (alignText!!.height - textHeight) / 2
            } else {
                point!!.y = alignText!!.getY(textSurface)
            }
        }
        return point!!.y + translationY
    }

    val isAligned: Boolean
        get() = align != 0

    fun setY(y: Float) {
        if (point != null) point!!.y = y
    }

    fun setX(x: Float) {
        if (point != null) point!!.x = x
    }

    fun setTranslationX(x: Float) {
        translationX = x
    }

    fun setTranslationY(y: Float) {
        translationY = y
    }

    fun onAnimationEnd() {}
}