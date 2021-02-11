package su.levenetc.android.textsurface.animations

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.interfaces.IEndListener
import su.levenetc.android.textsurface.interfaces.ISurfaceAnimation
import su.levenetc.android.textsurface.interfaces.ITextSurfaceAnimation
import su.levenetc.android.textsurface.utils.Utils.addEndListener

/**
 * Created by Eugene Levenetc.
 */
class Scale(override val text: Text, override val duration: Long, private val from: Float, private val to: Float, private val pivot: Int) : ITextSurfaceAnimation, AnimatorUpdateListener {
    override lateinit var textSurface: TextSurface
    private var animator: ObjectAnimator? = null
    override fun setInitValues(text: Text) {}
    override fun onStart() {}
    override fun start(listener: IEndListener) {
        text.setScalePivot(pivot.toFloat(), pivot.toFloat())
        val sX = PropertyValuesHolder.ofFloat("scaleX", from, to)
        val sY = PropertyValuesHolder.ofFloat("scaleY", from, to)
        animator = ObjectAnimator.ofPropertyValuesHolder(text, sX, sY)
        addEndListener(this, animator!!, listener)
        animator!!.duration = duration.toLong()
        animator!!.addUpdateListener(this)
        animator!!.start()
    }

    override fun cancel() {
        if (animator != null && animator!!.isRunning) {
            animator!!.cancel()
            animator = null
        }
    }

    override fun onAnimationUpdate(animation: ValueAnimator) {
        textSurface.invalidate()
    }

    override fun toString(): String {
        return "Scale{" +
                "text=" + text +
                '}'
    }
}