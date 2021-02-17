package su.levenetc.android.textsurface.utils

import android.animation.ValueAnimator
import androidx.core.animation.doOnEnd
import su.levenetc.android.textsurface.animations.SurfaceAnimation

internal fun ValueAnimator.setupAndStart(
        animation: SurfaceAnimation,
        updateDelegate: (ValueAnimator) -> Unit = {},
        endDelegate: (SurfaceAnimation) -> Unit = {}) {
    duration = animation.duration
    addUpdateListener {
        updateDelegate(it)
        animation.textSurface.invalidate()
    }
    doOnEnd {
        animation.endListener(animation)
        endDelegate(animation)
    }
    start()
}