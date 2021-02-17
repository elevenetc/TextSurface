package su.levenetc.android.textsurface.animations.sets

import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.animations.SurfaceAnimation

/**
 * Created by Eugene Levenetc.
 */
abstract class AnimationsSet(duration: Long, vararg animations: SurfaceAnimation) : SurfaceAnimation(duration) {

    val animations = animations.toMutableList()
    private var currentAnimation: SurfaceAnimation? = null

    override var textSurface: TextSurface
        get() = super.textSurface
        set(value) {
            animations.forEach { it.textSurface = value }
        }

    override fun cancel() {
        super.cancel()
        animations.forEach { it.cancel() }
    }

    protected abstract fun onCurrentAnimationFinished(animation: SurfaceAnimation)

    protected fun finalizeAnimation() {
        endListener(this)
    }

    protected fun initCurrentAnimation(animation: SurfaceAnimation) {
        currentAnimation = animation
        currentAnimation!!.onStart()
        currentAnimation!!.endListener = {
            if (canceled) {
                finalizeAnimation()
            } else {
                onCurrentAnimationFinished(it)
            }
        }
        currentAnimation!!.start()
    }
}