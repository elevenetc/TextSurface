package su.levenetc.android.textsurface.animations.reveal

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.graphics.Canvas
import android.graphics.Paint
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.interfaces.IEndListener
import su.levenetc.android.textsurface.interfaces.ISurfaceAnimation
import su.levenetc.android.textsurface.interfaces.ITextEffect
import su.levenetc.android.textsurface.utils.Utils.addEndListener

/**
 * Created by Eugene Levenetc.
 */
class ShapeReveal private constructor(override val text: Text, override var duration: Long, private val revealShape: IRevealShape, private val hideOnEnd: Boolean) : ITextEffect, AnimatorUpdateListener {

    init {
        revealShape.setText(text)
    }

    override lateinit var textSurface: TextSurface
    private var animator: ValueAnimator? = null
    override fun apply(canvas: Canvas, textValue: String, x: Float, y: Float, paint: Paint) {
        revealShape.clip(canvas, textValue, x, y, paint)
    }

    override fun setInitValues(text: Text) {
        if (revealShape.isToShow) text.setAlpha(0)
    }

    override fun onStart() {
        text.addEffect(this)
    }

    override fun start(listener: IEndListener) {
        text.setAlpha(255)
        revealShape.textSurface = textSurface
        animator = revealShape.getAnimator()
        animator!!.interpolator = FastOutSlowInInterpolator()
        addEndListener(this, animator!!, object : IEndListener {
            override fun onAnimationEnd(animation: ISurfaceAnimation) {
                text.removeEffect(this@ShapeReveal)
                if (hideOnEnd) text.setAlpha(0)
                listener?.onAnimationEnd(this@ShapeReveal)
            }
        })
        animator!!.duration = duration
        animator!!.start()
    }

    override fun cancel() {
        if (animator != null && animator!!.isRunning) {
            animator!!.cancel()
            animator = null
        }
    }

    override fun onAnimationUpdate(animation: ValueAnimator) {
        textSurface!!.invalidate()
    }


    companion object {
        @kotlin.jvm.JvmStatic
        fun create(text: Text, duration: Long, revealShape: IRevealShape, hideOnEnd: Boolean): ShapeReveal {
            return ShapeReveal(text, duration, revealShape, hideOnEnd)
        }
    }
}