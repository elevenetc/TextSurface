package su.levenetc.android.textsurface.animations.effects.reveal

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Region
import su.levenetc.android.textsurface.TextSurfaceDebug
import su.levenetc.android.textsurface.constants.Direction
import su.levenetc.android.textsurface.constants.Side
import kotlin.math.sqrt

/**
 * Created by Eugene Levenetc.
 */
class Circle private constructor(
        isToShow: Boolean,
        private val side: Int,
        private val direction: Int
) : ShapeRevealAnimation(isToShow), AnimatorUpdateListener {

    private var radius = 0f
    private val clipPath = Path()
    private var circX = 0f
    private var circY = 0f
    private var clipOp = Region.Op.INTERSECT

    override fun initAnimator(): ValueAnimator {
        val width = text.width
        val height = text.height
        var toRad = 0f
        var fromRad = 0f
        val cathA: Float
        val cathB: Float
        if (side == Side.CENTER) {
            circX = width / 2
            circY = -(height / 2)
            cathA = width / 2
            cathB = height / 2
        } else {
            if (side and Side.LEFT == Side.LEFT) {
                circX = 0f
            } else if (side and Side.RIGHT == Side.RIGHT) {
                circX = width
            }
            if (side and Side.TOP == Side.TOP) {
                circY = -height
            } else if (side and Side.BOTTOM == Side.BOTTOM) {
                circY = 0f
            }
            cathA = width
            cathB = height
        }
        val hypo = sqrt((cathA * cathA + cathB * cathB).toDouble()).toFloat()
        if (isToShow) {
            if (direction == Direction.OUT) {
                toRad = hypo
            } else {
                clipOp = Region.Op.DIFFERENCE
                fromRad = hypo
            }
        } else {
            if (direction == Direction.OUT) {
                clipOp = Region.Op.DIFFERENCE
                toRad = hypo
            } else {
                fromRad = hypo
            }
        }
        val animator = ValueAnimator.ofFloat(fromRad, toRad)
        animator.addUpdateListener(this)
        return animator
    }

    override fun clip(canvas: Canvas, textValue: String?, x: Float, y: Float, paint: Paint) {
        clipPath.reset()
        clipPath.addCircle(circX, circY, radius, Path.Direction.CCW)
        if (TextSurfaceDebug.ENABLED) {
            canvas.drawPath(clipPath, TextSurfaceDebug.BLUE_STROKE)
            canvas.drawCircle(circX, circY, 10f, TextSurfaceDebug.RED_FILL)
        }
        canvas.translate(0f, -text.fontDescent)
        canvas.clipPath(clipPath, clipOp)
    }

    override fun onAnimationUpdate(animation: ValueAnimator) {
        radius = animation.animatedValue as Float
    }

    companion object {
        /**
         * @param side      [Side]
         * @param direction [Direction] Direction.IN or Direction.OUT
         */
        fun show(side: Int, direction: Int): Circle {
            return Circle(true, side, direction)
        }

        /**
         * @param side      [Side]
         * @param direction [Direction] Direction.IN or Direction.OUT
         */
        fun hide(side: Int, direction: Int): Circle {
            return Circle(false, side, direction)
        }
    }
}