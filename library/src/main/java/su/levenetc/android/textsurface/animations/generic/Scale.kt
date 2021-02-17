package su.levenetc.android.textsurface.animations.generic

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.animations.TextSurfaceAnimation
import su.levenetc.android.textsurface.utils.setupAndStart

/**
 * Created by Eugene Levenetc.
 */
class Scale(text: Text, duration: Long, private val from: Float, private val to: Float, private val pivot: Int) : TextSurfaceAnimation(text, duration) {

    private var animator: ObjectAnimator? = null

    override fun start() {
        text.setScalePivot(pivot.toFloat(), pivot.toFloat())
        val sX = PropertyValuesHolder.ofFloat("scaleX", from, to)
        val sY = PropertyValuesHolder.ofFloat("scaleY", from, to)
        animator = ObjectAnimator.ofPropertyValuesHolder(text, sX, sY)
        animator!!.setupAndStart(this)
    }

    override fun cancel() {
        super.cancel()
        animator?.cancel()
    }

    override fun toString(): String {
        return "Scale{text=$text}"
    }
}