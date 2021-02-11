package su.levenetc.android.textsurface.animations

import android.util.Log
import su.levenetc.android.textsurface.Debug
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.contants.TYPE
import su.levenetc.android.textsurface.interfaces.IEndListener
import su.levenetc.android.textsurface.interfaces.ISet
import su.levenetc.android.textsurface.interfaces.ISurfaceAnimation
import java.util.*

/**
 * Created by Eugene Levenetc.
 */
open class AnimationsSet(override val type: TYPE, vararg animations: ISurfaceAnimation?) : IEndListener, ISet {
    override val animations: LinkedList<ISurfaceAnimation>
    private var currentAnimation: ISurfaceAnimation? = null
    private var lastAnimation //type == PARALLEL
            : ISurfaceAnimation? = null
    private var index = 0
    private var listener: IEndListener? = null
    private var canceled = false
    override fun onAnimationEnd(animation: ISurfaceAnimation) {
        if (Debug.ENABLED) Log.i("endAnimation", animation.toString())
        if (canceled) {
            finalizeAnimation()
            return
        }
        if (type == TYPE.SEQUENTIAL) {
            if (index < animations.size) {
                setCurrentAnimation(animations[index++])
            } else {
                finalizeAnimation()
            }
        } else {
            if (animation === lastAnimation) finalizeAnimation()
        }
    }

    private fun finalizeAnimation() {
        if (listener != null) listener!!.onAnimationEnd(this)
    }

    override fun onStart() {}
    override fun start(listener: IEndListener) {
        this.listener = listener
        index = 0
        canceled = false
        if (type == TYPE.SEQUENTIAL) {
            setCurrentAnimation(animations[index++])
        } else if (type == TYPE.PARALLEL) {
            Collections.sort(animations, DURATION_COMPARATOR)
            lastAnimation = animations.last
            for (animation in animations) setCurrentAnimation(animation)
        }
    }

    private fun setCurrentAnimation(animation: ISurfaceAnimation) {
        if (Debug.ENABLED) Log.i("startedAnimation", animation.toString())
        currentAnimation = animation
        currentAnimation!!.onStart()
        currentAnimation!!.start(this)
    }

    override val duration: Long
        get() = 0
    override var textSurface: TextSurface
        get() = null!!
        set(value) {
            animations.forEach { it.textSurface = value }
        }

    override fun cancel() {
        canceled = true
        if (type == TYPE.SEQUENTIAL) {
            if (currentAnimation != null) {
                currentAnimation!!.cancel()
            }
        } else {
            for (animation in animations) animation.cancel()
        }
    }

    private class DurationComparator : Comparator<ISurfaceAnimation> {
        override fun compare(lhs: ISurfaceAnimation, rhs: ISurfaceAnimation): Int {
            return (lhs.duration - rhs.duration).toInt()
        }
    }

    companion object {
        private val DURATION_COMPARATOR = DurationComparator()
    }

    init {
        this.animations = LinkedList(listOf(*animations))
    }
}