package su.levenetc.android.textsurface.animations.effects.reveal

import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Paint
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.animations.effects.TextEffect
import su.levenetc.android.textsurface.utils.setupAndStart

/**
 * Created by Eugene Levenetc.
 */
class ShapeReveal private constructor(text: Text, duration: Long, private val shapeRevealShape: ShapeRevealAnimation, private val hideOnEnd: Boolean) : TextEffect(text, duration) {

    init {
        shapeRevealShape.text = text
    }

    private var animator: ValueAnimator? = null

    override fun apply(canvas: Canvas, textValue: String, x: Float, y: Float, paint: Paint) {
        shapeRevealShape.clip(canvas, textValue, x, y, paint)
    }

    override fun setInitValues(text: Text) {
        if (shapeRevealShape.isToShow) text.setAlpha(0)
    }

    override fun start() {
        text.setAlpha(255)
        shapeRevealShape.textSurface = textSurface
        animator = shapeRevealShape.initAnimator()
        animator!!.interpolator = FastOutSlowInInterpolator()
        animator!!.setupAndStart(this, endDelegate = {
            text.removeEffect(this)
            if (hideOnEnd) text.setAlpha(0)
        })
    }

    override fun cancel() {
        super.cancel()
        animator?.cancel()
    }


    companion object {
        fun reveal(text: Text, duration: Long, shapeRevealShape: ShapeRevealAnimation, hideOnEnd: Boolean = false): ShapeReveal {
            return ShapeReveal(text, duration, shapeRevealShape, hideOnEnd)
        }
    }
}