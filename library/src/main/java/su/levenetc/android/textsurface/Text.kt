package su.levenetc.android.textsurface

import android.graphics.*
import su.levenetc.android.textsurface.common.Position
import su.levenetc.android.textsurface.common.ScaleValue
import su.levenetc.android.textsurface.interfaces.ITextEffect
import su.levenetc.android.textsurface.utils.Utils
import java.util.*

/**
 * Created by Eugene Levenetc.
 */
class Text(val value: String, var position: Position, private val padding: RectF, val paint: Paint) : Comparable<Text> {
    private var defaultSize: RectF? = null
    private val currentSize = RectF()
    var index = 0
    private val scale = ScaleValue()
    private val matrix = Matrix()
    private val effects = ArrayList<ITextEffect>()
    private var dx = 0f
    var fontDescent = 0f
        private set

    fun addEffect(effect: ITextEffect) {
        effects.add(effect)
    }

    private fun initBounds(text: String) {
        var text = text
        val trimmed = text.trim { it <= ' ' }
        if (trimmed.length < text.length) {
            val length = text.length
            val start = text.lastIndexOf(trimmed)
            val end = length - (start + trimmed.length)
            text = Utils.genString(start) + text + Utils.genString(end)
        }
        val tmp = Rect()
        paint.getTextBounds(text, 0, text.length, tmp)
        fontDescent = paint.fontMetrics.descent
        defaultSize = RectF(tmp)
        //a little workaround because getTextBounds returns smaller width than it is
        dx = paint.measureText(text) - tmp.width()
        defaultSize!!.left = 0f
        defaultSize!!.right = tmp.width() + dx
        defaultSize!!.top = -paint.fontSpacing
        defaultSize!!.bottom = 0f
        defaultSize!![defaultSize!!.left, defaultSize!!.top, defaultSize!!.right] = defaultSize!!.bottom
        currentSize[defaultSize!!.left, defaultSize!!.top, defaultSize!!.right] = defaultSize!!.bottom
    }

    fun onDraw(canvas: Canvas, textSurface: TextSurface?) {
        layout(textSurface)
        canvas.save()
        canvas.concat(matrix)
        val finalX = padding.left
        if (effects.isEmpty()) {
            canvas.drawText(value, finalX, -padding.bottom - fontDescent, paint)
        } else {
            for (effect in effects) {
                canvas.save()
                effect.apply(canvas, value, finalX, -padding.bottom, paint)
                canvas.drawText(value, finalX, -padding.bottom, paint)
                canvas.restore()
            }
        }
        canvas.restore()
        if (Debug.ENABLED) {
            canvas.drawRect(
                    currentSize.left,
                    currentSize.top - padding.bottom - padding.top,
                    currentSize.right + padding.left + padding.right,
                    currentSize.bottom,
                    Debug.RED_STROKE
            )
        }
    }

    fun layout(textSurface: TextSurface?) {
        currentSize[defaultSize!!.left, defaultSize!!.top, defaultSize!!.right] = defaultSize!!.bottom
        val sx = scale.scaleX
        val sy = scale.scaleY
        val sPivotX = position.getRelativeX(scale.pivot.x.toInt(), this, false)
        val sPivotY = position.getRelativeY(scale.pivot.y.toInt(), this, false)
        val x = position.getX(textSurface, width * sx)
        val y = position.getY(textSurface, height * sy)
        matrix.reset()
        matrix.preTranslate(x, y)
        matrix.preScale(sx, sy, sPivotX, sPivotY)
        matrix.mapRect(currentSize)
    }

    fun getY(textSurface: TextSurface?): Float {
        return position.getY(textSurface, height)
    }

    fun getX(textSurface: TextSurface?): Float {
        return position.getX(textSurface, width)
    }

    fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    fun bounds(): RectF? {
        return defaultSize
    }

    fun setScalePivot(x: Float, y: Float) {
        scale.pivot[x] = y
    }

    var scaleY: Float
        get() = scale.scaleY
        set(value) {
            scale.setValueY(value)
        }

    fun setTranslationX(value: Float) {
        position.setTranslationX(value)
    }

    fun setTranslationY(value: Float) {
        position.setTranslationY(value)
    }

    override fun compareTo(another: Text): Int {
        return value.compareTo(another.value)
    }

    val width: Float
        get() = currentSize.width() + padding.left + padding.right
    val height: Float
        get() = currentSize.height() + padding.top + padding.bottom

    fun onAnimationEnd() {
        position.onAnimationEnd()
    }

    var scaleX: Float
        get() = scale.scaleX
        set(value) {
            scale.setValueX(value)
        }

    override fun toString(): String {
        return "Text{" +
                "text='" + value + '\'' +
                '}'
    }

    fun removeEffect(effect: ITextEffect) {
        effects.remove(effect)
    }

    init {
        initBounds(value)
    }
}