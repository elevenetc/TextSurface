package su.levenetc.android.textsurface.animations

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.interfaces.IEndListener
import su.levenetc.android.textsurface.interfaces.ITextSurfaceAnimation

/**
 * Created by Eugene Levenetc.
 */
abstract class AbstractSurfaceAnimation(
        override val text: Text,
        override val duration: Long
) : ITextSurfaceAnimation, AnimatorUpdateListener {

    abstract override var textSurface: TextSurface

    override fun setInitValues(text: Text) {}
    override fun onStart() {}
    override fun start(listener: IEndListener) {}
    override fun cancel() {}

    override fun onAnimationUpdate(animation: ValueAnimator) {
        textSurface.invalidate()
    }
}