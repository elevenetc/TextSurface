package su.levenetc.android.textsurface.animations.colors

import android.animation.ValueAnimator
import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.animations.AbstractSurfaceAnimation
import su.levenetc.android.textsurface.utils.setupAndStart

/**
 * Created by Eugene Levenetc.
 */
class Alpha(
        text: Text,
        duration: Long,
        private val from: Int,
        private val to: Int
) : AbstractSurfaceAnimation(text, duration) {

    override fun start() {
        animator = ValueAnimator.ofInt(from, to)
        animator.setupAndStart(this, {
            text.setAlpha(it.animatedValue as Int)
        })
    }

    companion object {

        fun hide(text: Text, duration: Long): Alpha {
            return Alpha(text, duration, 255, 0)
        }

        fun show(text: Text, duration: Long): Alpha {
            return Alpha(text, duration, 0, 255)
        }
    }
}