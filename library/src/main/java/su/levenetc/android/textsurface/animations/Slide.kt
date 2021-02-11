package su.levenetc.android.textsurface.animations

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Region
import android.os.Build
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.contants.Side
import su.levenetc.android.textsurface.interfaces.IEndListener
import su.levenetc.android.textsurface.interfaces.ISurfaceAnimation
import su.levenetc.android.textsurface.interfaces.ITextEffect
import su.levenetc.android.textsurface.utils.Utils

/**
 * Created by Eugene Levenetc.
 */
class Slide private constructor(private val side: Int, override val text: Text, override val duration: Long, private val toShow: Boolean) : ITextEffect, AnimatorUpdateListener {

    private var xOffset = 0f
    private var yOffset = 0f
    private var animator: ObjectAnimator? = null
    override lateinit var textSurface: TextSurface

    override fun setInitValues(text: Text) {
        if (toShow) {
            text.setAlpha(0)
        }
    }

    override fun onStart() {
        text.addEffect(this)
    }

    override fun start(listener: IEndListener) {
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

        animator?.run {
            this.interpolator = FastOutSlowInInterpolator()
            Utils.addEndListener(this@Slide, this, object : IEndListener {
                override fun onAnimationEnd(animation: ISurfaceAnimation) {
                    text.removeEffect(this@Slide)
                    if (!toShow) text.setAlpha(0)
                    listener.onAnimationEnd(this@Slide)
                }
            })
            this.duration = duration
            this.addUpdateListener(this@Slide)
            this.start()
        }


    }

    override fun cancel() {
        if (animator != null && animator!!.isRunning) {
            animator!!.cancel()
            animator = null
        }
    }

    override fun apply(canvas: Canvas, textValue: String, x: Float, y: Float, paint: Paint) {
        val width = text.width
        val height = text.height

        //canvas.save();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            canvas.clipRect(x, y - height, width, 0f)
        } else {
            canvas.clipRect(x, y - height, width, 0f, Region.Op.REPLACE)
        }
        canvas.translate(xOffset, yOffset - text.fontDescent)
        //canvas.drawText(textValue, x, y, paint);
        //canvas.restore();
    }

    fun setXOffset(value: Float) {
        xOffset = value
    }

    fun setYOffset(value: Float) {
        yOffset = value
    }

    override fun onAnimationUpdate(animation: ValueAnimator) {
        textSurface!!.invalidate()
    }

    companion object {
        @JvmStatic
        fun showFrom(side: Int, text: Text, duration: Long): Slide {
            return Slide(side, text, duration, true)
        }

        @JvmStatic
        fun hideFrom(side: Int, text: Text, duration: Long): Slide {
            return Slide(side, text, duration, false)
        }
    }
}