package su.levenetc.android.textsurface.animations.effects.reveal

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import su.levenetc.android.textsurface.TextSurfaceDebug
import su.levenetc.android.textsurface.constants.Side
import su.levenetc.android.textsurface.utils.toScaledPx

/**
 * Created by Eugene Levenetc.
 */
class SideCut private constructor(isToShow: Boolean, private val side: Int) : ShapeRevealAnimation(isToShow), AnimatorUpdateListener {

    private var diffX = 0f
    private val clipPath = Path()

    override fun initAnimator(): ValueAnimator {
        val width = text.width
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
        val animator = ValueAnimator.ofFloat(fromX, toX)
        animator.addUpdateListener(this)
        return animator
    }

    override fun clip(canvas: Canvas, textValue: String?, x: Float, y: Float, paint: Paint) {
        val width = text.width
        val height = text.height
        if (isToShow) {
            if (side == Side.LEFT) {
                clipPath.reset()
                clipPath.moveTo(x + diffX, y - height)
                clipPath.rLineTo(width, 0f)
                clipPath.rLineTo(CUT_SIZE, height + text.fontDescent)
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
                clipPath.rLineTo(0f, height + text.fontDescent)
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
        if (TextSurfaceDebug.ENABLED) canvas.drawPath(clipPath, TextSurfaceDebug.BLUE_STROKE)
        canvas.translate(0f, -text.fontDescent)
        canvas.clipPath(clipPath)
    }

    override fun onAnimationUpdate(animation: ValueAnimator) {
        diffX = animation.animatedValue as Float
        textSurface.invalidate()
    }

    companion object {

        private val CUT_SIZE = 20f.toScaledPx()

        fun showSideCut(side: Int): SideCut {
            return SideCut(true, side)
        }

        fun hideSideCut(side: Int): SideCut {
            return SideCut(false, side)
        }
    }
}