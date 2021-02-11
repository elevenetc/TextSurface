package su.levenetc.android.textsurface.animations.reveal

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import su.levenetc.android.textsurface.Debug
import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.contants.Side
import su.levenetc.android.textsurface.utils.Utils.dpToPx

/**
 * Created by Eugene Levenetc.
 */
class SideCut private constructor(override val isToShow: Boolean, private val side: Int) : IRevealShape, AnimatorUpdateListener {
    private var diffX = 0f
    private var text: Text? = null
    private val clipPath = Path()
    private var animator: ValueAnimator? = null
    override lateinit var textSurface: TextSurface

    override fun setText(text: Text?) {
        this.text = text
    }

    override fun getAnimator(): ValueAnimator? {
        val width = text!!.width
        var toX = 0f
        var fromX = 0f
        if (isToShow) {
            if (side == Side.LEFT) {
                fromX = -(width + CUT_SIZE)
            } else if (side == Side.RIGHT) {
                fromX = width
                toX = -CUT_SIZE
            }
        } else {
            if (side == Side.LEFT) {
                fromX = -CUT_SIZE
                toX = width
            } else if (side == Side.RIGHT) {
                fromX = width
                toX = -CUT_SIZE
            }
        }
        animator = ValueAnimator.ofFloat(fromX, toX)
        animator!!.addUpdateListener(this)
        return animator
    }

    override fun clip(canvas: Canvas, textValue: String?, x: Float, y: Float, paint: Paint?) {
        val width = text!!.width
        val height = text!!.height
        if (isToShow) {
            if (side == Side.LEFT) {
                clipPath.reset()
                clipPath.moveTo(x + diffX, y - height)
                clipPath.rLineTo(width, 0f)
                clipPath.rLineTo(CUT_SIZE, height + text!!.fontDescent)
                clipPath.rLineTo(-(width + CUT_SIZE), 0f)
                clipPath.close()
            } else if (side == Side.RIGHT) {
                clipPath.reset()
                clipPath.moveTo(x + diffX, y - height)
                clipPath.rLineTo(CUT_SIZE, height)
                clipPath.rLineTo(width, 0f)
                clipPath.rLineTo(0f, -height)
                clipPath.close()
            }
        } else {
            if (side == Side.LEFT) {
                clipPath.reset()
                clipPath.moveTo(x + diffX, y - height)
                clipPath.rLineTo(width + CUT_SIZE, 0f)
                clipPath.rLineTo(0f, height + text!!.fontDescent)
                clipPath.rLineTo(-width, 0f)
                clipPath.close()
            } else if (side == Side.RIGHT) {
                clipPath.reset()
                clipPath.moveTo(x + diffX, y - height)
                clipPath.rLineTo(CUT_SIZE, height)
                clipPath.rLineTo(width, 0f)
                clipPath.rLineTo(0f, -height)
                clipPath.close()
            }
        }
        if (Debug.ENABLED) canvas.drawPath(clipPath, Debug.BLUE_STROKE!!)
        canvas.translate(0f, -text!!.fontDescent)
        canvas.clipPath(clipPath)
    }

    override fun onAnimationUpdate(animation: ValueAnimator) {
        diffX = animation.animatedValue as Float
        textSurface!!.invalidate()
    }

    companion object {
        private val CUT_SIZE = dpToPx(20f)

        @kotlin.jvm.JvmStatic
        fun show(side: Int): SideCut {
            return SideCut(true, side)
        }

        @kotlin.jvm.JvmStatic
        fun hide(side: Int): SideCut {
            return SideCut(false, side)
        }
    }
}