package su.levenetc.android.textsurface.animations.sets

import su.levenetc.android.textsurface.animations.SurfaceAnimation

/**
 * Created by ogaclejapan.
 */
class Loop(vararg animations: SurfaceAnimation) : AnimationsSet(Long.MAX_VALUE, *animations) {

    private var index = 0

    override fun onCurrentAnimationFinished(animation: SurfaceAnimation) {
        if (canceled) return
        if (index > animations.size - 1) index = 0
        initCurrentAnimation(animations[index++])
    }

    override fun start() {
        super.start()
        initCurrentAnimation(animations[index++])
    }
}