package su.levenetc.android.textsurface.animations

import android.animation.Animator
import android.animation.ValueAnimator
import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.interfaces.IEndListener
import su.levenetc.android.textsurface.utils.AnimatorEndListener

/**
 * Created by Eugene Levenetc.
 */
class Alpha(text: Text, duration: Long, private val from: Int, private val to: Int) : AbstractSurfaceAnimation(text, duration) {

    private var animator: ValueAnimator? = null
    override lateinit var textSurface: TextSurface

    override fun start(listener: IEndListener) {
        animator = ValueAnimator.ofInt(from, to)
        animator?.duration = duration
        animator?.addUpdateListener(this)
        animator?.addListener(object : AnimatorEndListener() {
            override fun onAnimationEnd(animation: Animator) {
                listener.onAnimationEnd(this@Alpha)
            }
        })
        animator?.start()
    }

    override fun onAnimationUpdate(animation: ValueAnimator) {
        super.onAnimationUpdate(animation)
        text.setAlpha(animation.animatedValue as Int)
    }

    override fun cancel() {
        if (animator != null && animator!!.isRunning) {
            animator!!.cancel()
            animator = null
        }
    }

    companion object {
        @kotlin.jvm.JvmStatic
        fun hide(text: Text, duration: Long): Alpha {
            return Alpha(text, duration, 255, 0)
        }

        @kotlin.jvm.JvmStatic
        fun show(text: Text, duration: Long): Alpha {
            return Alpha(text, duration, 0, 255)
        }
    }
}