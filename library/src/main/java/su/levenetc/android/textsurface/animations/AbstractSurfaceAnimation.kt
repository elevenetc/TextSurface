package su.levenetc.android.textsurface.animations

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import su.levenetc.android.textsurface.Text

/**
 * Created by Eugene Levenetc.
 */
abstract class AbstractSurfaceAnimation(
        text: Text,
        duration: Long
) : TextSurfaceAnimation(text, duration), AnimatorUpdateListener {

    lateinit var animator: ValueAnimator

    override fun cancel() {
        super.cancel()
        if (this::animator.isInitialized) {
            animator.cancel()
        }
    }

    override fun onAnimationUpdate(animation: ValueAnimator) {
        textSurface.invalidate()
    }
}