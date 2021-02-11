package su.levenetc.android.textsurface.animations

import android.animation.Animator
import android.animation.ValueAnimator
import android.graphics.Color
import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.animations.ChangeColor
import su.levenetc.android.textsurface.interfaces.IEndListener
import su.levenetc.android.textsurface.utils.AnimatorEndListener

/**
 * Created by Eugene Levenetc.
 */
class ChangeColor(text: Text, override var duration: Long, private var from: Int, private val to: Int) : AbstractSurfaceAnimation(text, duration) {
    val fromTriplet = FloatArray(3)
    val toTriplet = FloatArray(3)
    val hsv = FloatArray(3)
    private var animator: ValueAnimator? = null
    override lateinit var textSurface: TextSurface

    override fun start(listener: IEndListener) {
        if (from == -1) from = text.paint.color
        Color.colorToHSV(from, fromTriplet)
        Color.colorToHSV(to, toTriplet)
        animator = ValueAnimator.ofFloat(0f, 1f)
        animator!!.duration = duration
        animator!!.addUpdateListener(this)
        animator!!.addListener(object : AnimatorEndListener() {
            override fun onAnimationEnd(animation: Animator) {
                listener?.onAnimationEnd(this@ChangeColor)
            }
        })
        animator!!.start()
    }

    override fun onAnimationUpdate(animation: ValueAnimator) {
        hsv[0] = fromTriplet[0] + (toTriplet[0] - fromTriplet[0]) * animation.animatedFraction
        hsv[1] = fromTriplet[1] + (toTriplet[1] - fromTriplet[1]) * animation.animatedFraction
        hsv[2] = fromTriplet[2] + (toTriplet[2] - fromTriplet[2]) * animation.animatedFraction
        text.paint.color = Color.HSVToColor(hsv)
        super.onAnimationUpdate(animation)
    }

    override fun cancel() {
        if (animator != null && animator!!.isRunning) {
            animator!!.cancel()
            animator = null
        }
    }

    companion object {
        @kotlin.jvm.JvmStatic
        fun fromTo(text: Text, duration: Long, from: Int, to: Int): ChangeColor {
            return ChangeColor(text, duration, from, to)
        }

        @kotlin.jvm.JvmStatic
        fun to(text: Text, duration: Long, to: Int): ChangeColor {
            return ChangeColor(text, duration, -1, to)
        }
    }
}