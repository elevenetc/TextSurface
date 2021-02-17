package su.levenetc.android.textsurface.animations.sets

import su.levenetc.android.textsurface.animations.SurfaceAnimation

/**
 * Created by Eugene Levenetc.
 */
open class Sequential(vararg animations: SurfaceAnimation) : AnimationsSet(getDuration(animations), *animations) {

    private var index = 0

    override fun start() {
        super.start()
        initCurrentAnimation(animations[index++])
    }

    override fun onCurrentAnimationFinished(animation: SurfaceAnimation) {
        if (index < animations.size) {
            initCurrentAnimation(animations[index++])
        } else {
            finalizeAnimation()
        }
    }

    companion object {
        private fun getDuration(animations: Array<out SurfaceAnimation>): Long {
            return animations.sumOf { it.duration }
        }
    }
}