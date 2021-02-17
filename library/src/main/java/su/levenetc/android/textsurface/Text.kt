package su.levenetc.android.textsurface

import android.graphics.*
import androidx.annotation.Dimension
import su.levenetc.android.textsurface.animations.effects.TextEffect
import su.levenetc.android.textsurface.common.Position
import su.levenetc.android.textsurface.common.ScaleValue
import su.levenetc.android.textsurface.constants.Pivot
import su.levenetc.android.textsurface.utils.toEscapedString
import su.levenetc.android.textsurface.utils.toSp
import su.levenetc.android.textsurface.utils.verifiedRange

/**
 * Created by Eugene Levenetc.
 */
class Text(val value: String, var position: Position, private val padding: RectF, val paint: Paint) : Comparable<Text> {

    private var bounds: RectF
    private val currentSize = RectF()
    private val scale = ScaleValue()
    private val matrix = Matrix()
    private val effects = mutableListOf<TextEffect>()
    private var dx = 0f

    var fontDescent = 0f
        private set

    val width: Float
        get() = currentSize.width() + padding.left + padding.right
    val height: Float
        get() = currentSize.height() + padding.top + padding.bottom

    var scaleY: Float
        get() = scale.scaleY
        set(value) {
            scale.setValueY(value)
        }

    var scaleX: Float
        get() = scale.scaleX
        set(value) {
            scale.setValueX(value)
        }

    init {
        var text = value
        val trimmed = text.trim { it <= ' ' }
        if (trimmed.length < text.length) {
            val length = text.length
            val start = text.lastIndexOf(trimmed)
            val end = length - (start + trimmed.length)
            text = start.toEscapedString() + text + end.toEscapedString()
        }
        val tmp = Rect()
        paint.getTextBounds(text, 0, text.length, tmp)
        fontDescent = paint.fontMetrics.descent
        bounds = RectF(tmp)
        //a little workaround because getTextBounds returns smaller width than it is
        dx = paint.measureText(text) - tmp.width()
        bounds.left = 0f
        bounds.right = tmp.width() + dx
        bounds.top = -paint.fontSpacing
        bounds.bottom = 0f
        bounds[bounds.left, bounds.top, bounds.right] = bounds.bottom
        currentSize[bounds.left, bounds.top, bounds.right] = bounds.bottom
    }

    fun addEffect(effect: TextEffect) {
        effects.add(effect)
    }

    fun onDraw(canvas: Canvas, textSurface: TextSurface) {
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
        if (TextSurfaceDebug.ENABLED) {
            canvas.drawRect(
                    currentSize.left,
                    currentSize.top - padding.bottom - padding.top,
                    currentSize.right + padding.left + padding.right,
                    currentSize.bottom,
                    TextSurfaceDebug.RED_STROKE
            )
        }
    }

    fun layout(textSurface: TextSurface) {
        currentSize[bounds.left, bounds.top, bounds.right] = bounds.bottom
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

    fun getY(textSurface: TextSurface): Float {
        return position.getY(textSurface, height)
    }

    fun getX(textSurface: TextSurface): Float {
        return position.getX(textSurface, width)
    }

    fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    fun setScalePivot(x: Float, y: Float) {
        scale.pivot[x] = y
    }

    fun setTranslationX(value: Float) {
        position.translationX = value
    }

    fun setTranslationY(value: Float) {
        position.translationY = value
    }

    override fun compareTo(another: Text): Int {
        return value.compareTo(another.value)
    }

    fun onAnimationEnd() {

    }

    fun removeEffect(effect: TextEffect) {
        effects.remove(effect)
    }

    override fun toString(): String {
        return "Text{text='$value'}"
    }

    class Builder(private val text: String) {

        private var showTime = 0
        private var position = Position()
        private val padding = RectF()
        private var scale = 1f
        private var scalePivot = Pivot.CENTER
        private var paint = Paint().apply {
            color = Color.WHITE
            isAntiAlias = true
            textSize = 18f.toSp()
        }

        fun setShowTime(showTime: Int): Builder {
            this.showTime = showTime
            return this
        }

        fun setPosition(px: Float, py: Float): Builder {
            position = Position(point = PointF(px, py))
            return this
        }

        fun setPosition(align: Int): Builder {
            position = Position(align)
            return this
        }

        fun setPosition(align: Int, alignText: Text): Builder {
            position = Position(align, alignText = alignText)
            return this
        }

        /**
         * params are in pixels
         */
        fun setPadding(left: Float, top: Float, right: Float, bottom: Float): Builder {
            padding[left, top, right] = bottom
            return this
        }

        /**
         * params are in pixels
         */
        fun setLeftPadding(left: Float): Builder {
            padding[left, padding.top, padding.right] = padding.bottom
            return this
        }

        fun setPadding(padding: RectF): Builder {
            this.padding.set(padding)
            return this
        }

        fun setPosition(position: Position): Builder {
            this.position = position.copy()
            return this
        }

        /**
         * Overrides all previously set paint properties
         */
        fun setPaint(paint: Paint): Builder {
            this.paint = Paint(paint)
            return this
        }

        /**
         * @param alpha from 0 to 255
         */
        fun setAlpha(alpha: Int): Builder {
            paint.alpha = alpha.verifiedRange(0, 255)
            return this
        }

        fun setColor(color: Int): Builder {
            paint.color = color
            return this
        }

        fun setSize(@Dimension(unit = Dimension.SP) size: Float): Builder {
            paint.textSize = size.toSp()
            return this
        }

        fun setScale(scale: Float, scalePivot: Int): Builder {
            this.scale = scale
            this.scalePivot = scalePivot
            return this
        }

        fun build(): Text {
            val result = Text(text, position, padding, paint)
            result.scaleX = scale
            result.scaleY = scale
            result.setScalePivot(scalePivot.toFloat(), scalePivot.toFloat())
            return result
        }
    }
}