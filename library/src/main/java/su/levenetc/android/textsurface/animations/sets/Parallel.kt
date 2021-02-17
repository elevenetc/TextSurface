package su.levenetc.android.textsurface.animations.sets

import su.levenetc.android.textsurface.animations.SurfaceAnimation

/**
 * Created by Eugene Levenetc.
 */
class Parallel(vararg animations: SurfaceAnimation) : AnimationsSet(getDuration(animations), * animations) {

    private var lastAnimation: SurfaceAnimation? = null

    override fun onCurrentAnimationFinished(animation: SurfaceAnimation) {
        if (animation === lastAnimation) finalizeAnimation()
    }

    override fun start() {
        lastAnimation = animations.maxByOrNull { it.duration }
        animations.forEach { initCurrentAnimation(it) }
        super.start()
    }

    companion object {
        private fun getDuration(animations: Array<out SurfaceAnimation>): Long {
            return animations.maxByOrNull { a -> a.duration }?.duration ?: 0
        }
    }

}