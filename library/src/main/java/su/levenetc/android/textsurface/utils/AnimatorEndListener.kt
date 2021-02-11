package su.levenetc.android.textsurface.utils

import android.animation.Animator
import android.animation.Animator.AnimatorListener

/**
 * Created by Eugene Levenetc.
 */
abstract class AnimatorEndListener : AnimatorListener {
    override fun onAnimationStart(animation: Animator) {}
    override fun onAnimationEnd(animation: Animator) {}
    override fun onAnimationCancel(animation: Animator) {}
    override fun onAnimationRepeat(animation: Animator) {}
}