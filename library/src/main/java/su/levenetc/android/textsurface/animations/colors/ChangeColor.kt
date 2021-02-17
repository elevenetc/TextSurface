package su.levenetc.android.textsurface.animations.colors

import android.animation.ValueAnimator
import android.graphics.Color
import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.animations.AbstractSurfaceAnimation
import su.levenetc.android.textsurface.utils.setupAndStart

/**
 * Created by Eugene Levenetc.
 */
class ChangeColor(text: Text, duration: Long, private var from: Int, private val to: Int) : AbstractSurfaceAnimation(text, duration) {

    private val fromTriplet = FloatArray(3)
    private val toTriplet = FloatArray(3)
    private val hsv = FloatArray(3)

    override fun start() {
        if (from == -1) from = text.paint.color
        Color.colorToHSV(from, fromTriplet)
        Color.colorToHSV(to, toTriplet)
        animator = ValueAnimator.ofFloat(0f, 1f)

        animator.setupAndStart(this, {
            hsv[0] = fromTriplet[0] + (toTriplet[0] - fromTriplet[0]) * it.animatedFraction
            hsv[1] = fromTriplet[1] + (toTriplet[1] - fromTriplet[1]) * it.animatedFraction
            hsv[2] = fromTriplet[2] + (toTriplet[2] - fromTriplet[2]) * it.animatedFraction
            text.paint.color = Color.HSVToColor(hsv)
        })
    }

    companion object {
        fun fromTo(text: Text, duration: Long, from: Int, to: Int): ChangeColor {
            return ChangeColor(text, duration, from, to)
        }

        fun to(text: Text, duration: Long, to: Int): ChangeColor {
            return ChangeColor(text, duration, -1, to)
        }
    }
}