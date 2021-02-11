package su.levenetc.android.textsurface.animations.reveal

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Region
import su.levenetc.android.textsurface.Debug
import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.contants.Direction
import su.levenetc.android.textsurface.contants.Side

/**
 * Created by Eugene Levenetc.
 */
class Circle private constructor(override val isToShow: Boolean, private val side: Int, private val direction: Int) : IRevealShape, AnimatorUpdateListener {
    private var radius = 0f
    private var text: Text? = null
    private val clipPath = Path()
    private var animator: ValueAnimator? = null
    override lateinit var textSurface: TextSurface
    private var circX = 0f
    private var circY = 0f
    private var clipOp = Region.Op.INTERSECT

    override fun setText(text: Text?) {
        this.text = text
    }

    override fun getAnimator(): ValueAnimator? {
        val width = text!!.width
        val height = text!!.height
        val textX = text!!.getX(textSurface)
        val textY = text!!.getY(textSurface)
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
        val hypo = Math.sqrt((cathA * cathA + cathB * cathB).toDouble()).toFloat()
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
        animator = ValueAnimator.ofFloat(fromRad, toRad)
        animator!!.addUpdateListener(this)
        return animator
    }

    override fun clip(canvas: Canvas, textValue: String?, x: Float, y: Float, paint: Paint?) {
        clipPath.reset()
        clipPath.addCircle(circX, circY, radius, Path.Direction.CCW)
        if (Debug.ENABLED) {
            canvas.drawPath(clipPath, Debug.BLUE_STROKE!!)
            canvas.drawCircle(circX, circY, 10f, Debug.RED_FILL!!)
        }
        canvas.translate(0f, -text!!.fontDescent)
        canvas.clipPath(clipPath, clipOp)
    }

    override fun onAnimationUpdate(animation: ValueAnimator) {
        radius = animation.animatedValue as Float
        textSurface!!.invalidate()
    }

    companion object {
        /**
         * @param side      [Side]
         * @param direction [Direction] Direction.IN or Direction.OUT
         */
        @kotlin.jvm.JvmStatic
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