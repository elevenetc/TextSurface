package su.levenetc.android.textsurface.animations.effects

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Region
import android.os.Build
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.constants.Side
import su.levenetc.android.textsurface.utils.setupAndStart

/**
 * Created by Eugene Levenetc.
 */
class Slide private constructor(private val side: Int, text: Text, duration: Long, private val toShow: Boolean) : TextEffect(text, duration) {

    private var xOffset = 0f
    private var yOffset = 0f
    private var animator: ObjectAnimator? = null

    override fun setInitValues(text: Text) {
        if (toShow) text.setAlpha(0)
    }

    override fun start() {
        text.setAlpha(255)
        var hHolder: PropertyValuesHolder? = null
        var vHolder: PropertyValuesHolder? = null
        var fromX = 0f
        var toX = 0f
        var fromY = 0f
        var toY = 0f
        if (side and Side.LEFT == side) {
            if (toShow) {
                fromX = -text.width
            } else {
                toX = -text.width
            }
            hHolder = PropertyValuesHolder.ofFloat("xOffset", fromX, toX)
        } else if (side and Side.RIGHT == side) {
            if (toShow) {
                fromX = text.width
            } else {
                toX = text.width
            }
            hHolder = PropertyValuesHolder.ofFloat("xOffset", fromX, toX)
        }
        if (side and Side.TOP == side) {
            if (toShow) {
                fromY = -text.height
            } else {
                toY = -text.height
            }
            vHolder = PropertyValuesHolder.ofFloat("yOffset", fromY, toY)
        } else if (side and Side.BOTTOM == side) {
            if (toShow) {
                fromY = text.height
            } else {
                toY = text.height
            }
            vHolder = PropertyValuesHolder.ofFloat("yOffset", fromY, toY)
        }
        animator = if (hHolder != null && vHolder != null) {
            ObjectAnimator.ofPropertyValuesHolder(this, hHolder, vHolder)
        } else if (hHolder != null) {
            ObjectAnimator.ofPropertyValuesHolder(this, hHolder)
        } else {
            ObjectAnimator.ofPropertyValuesHolder(this, vHolder)
        }

        animator!!.interpolator = FastOutSlowInInterpolator()
        animator!!.setupAndStart(this, endDelegate = {
            text.removeEffect(this)
            if (!toShow) text.setAlpha(0)
        })
    }

    override fun cancel() {
        super.cancel()
        animator?.cancel()
    }

    override fun apply(canvas: Canvas, textValue: String, x: Float, y: Float, paint: Paint) {
        val width = text.width
        val height = text.height

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            canvas.clipRect(x, y - height, width, 0f)
        } else {
            canvas.clipRect(x, y - height, width, 0f, Region.Op.REPLACE)
        }
        canvas.translate(xOffset, yOffset - text.fontDescent)
    }

    fun setXOffset(value: Float) {
        xOffset = value
    }

    fun setYOffset(value: Float) {
        yOffset = value
    }

    companion object {

        fun showFrom(side: Int, text: Text, duration: Long): Slide {
            return Slide(side, text, duration, true)
        }

        fun hideFrom(side: Int, text: Text, duration: Long): Slide {
            return Slide(side, text, duration, false)
        }
    }
}