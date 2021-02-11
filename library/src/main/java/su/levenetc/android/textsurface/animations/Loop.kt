package su.levenetc.android.textsurface.animations

import su.levenetc.android.textsurface.interfaces.IEndListener
import su.levenetc.android.textsurface.interfaces.ISurfaceAnimation

/**
 * Created by ogaclejapan.
 */
class Loop(vararg animations: ISurfaceAnimation) : Sequential(*animations) {
    private val restartListener: IEndListener = object : IEndListener {
        override fun onAnimationEnd(animation: ISurfaceAnimation) {
            if (canceled) {
                if (endListener != null) endListener!!.onAnimationEnd(this@Loop)
            } else {
                super@Loop.start(this)
            }
        }
    }
    private var endListener: IEndListener? = null
    private var canceled = false
    override fun start(listener: IEndListener) {
        endListener = listener
        canceled = false
        super.start(restartListener)
    }

    override fun cancel() {
        canceled = true
        super.cancel()
    }
}