package su.levenetc.android.textsurface

import android.content.res.Resources
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.util.TypedValue
import su.levenetc.android.textsurface.common.Position
import su.levenetc.android.textsurface.contants.Pivot

/**
 * Created by Eugene Levenetc.
 */
class TextBuilder private constructor(private val text: String) {
    private var showTime = 0
    private var position = Position()
    private val padding = RectF()
    private var paint = Paint()
    private var scale = 1f
    private var scalePivot = Pivot.CENTER
    fun setShowTime(showTime: Int): TextBuilder {
        this.showTime = showTime
        return this
    }

    fun setPosition(px: Float, py: Float): TextBuilder {
        position = Position(PointF(px, py))
        return this
    }

    fun setPosition(align: Int): TextBuilder {
        position = Position(align)
        return this
    }

    fun setPosition(align: Int, alignText: Text?): TextBuilder {
        position = Position(align, alignText)
        return this
    }

    /**
     * params are in pixels
     */
    fun setPadding(left: Float, top: Float, right: Float, bottom: Float): TextBuilder {
        padding[left, top, right] = bottom
        return this
    }

    /**
     * params are in pixels
     */
    fun setLeftPadding(left: Float): TextBuilder {
        padding[left, padding.top, padding.right] = padding.bottom
        return this
    }

    fun setPadding(padding: RectF?): TextBuilder {
        this.padding.set(padding!!)
        return this
    }

    fun setPosition(position: Position): TextBuilder {
        this.position.set(position)
        return this
    }

    /**
     * Overrides all previously set paint properties
     */
    fun setPaint(paint: Paint?): TextBuilder {
        this.paint = Paint(paint)
        return this
    }

    /**
     * @param alpha from 0 to 255
     */
    fun setAlpha(alpha: Int): TextBuilder {
        paint.alpha = alpha
        return this
    }

    fun setColor(color: Int): TextBuilder {
        paint.color = color
        return this
    }

    fun setSize(sp: Float): TextBuilder {
        paint.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sp, Resources.getSystem().displayMetrics)
        return this
    }

    fun setScale(scale: Float, scalePivot: Int): TextBuilder {
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

    companion object {
        @JvmStatic
        fun create(text: String): TextBuilder {
            return TextBuilder(text)
        }
    }

    init {
        paint.color = Color.WHITE
        paint.isAntiAlias = true
        paint.textSize = 110f
        setSize(18f)
    }
}