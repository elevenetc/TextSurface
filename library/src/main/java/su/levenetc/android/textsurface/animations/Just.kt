package su.levenetc.android.textsurface.animations

import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.contants.TYPE
import su.levenetc.android.textsurface.interfaces.IEndListener

/**
 * Created by Eugene Levenetc.
 */
class Just private constructor(text: Text) : AbstractSurfaceAnimation(text, 0) {
    //TODO: check unused textSurface
    override lateinit var textSurface: TextSurface

    override fun start(listener: IEndListener) {
        textSurface!!.invalidate()
        listener?.onAnimationEnd(this)
    }

    companion object {
        fun show(vararg texts: Text): AnimationsSet {
            val justs = arrayOfNulls<Just>(texts.size)
            for (i in texts.indices) justs[i] = show(texts[i])
            return AnimationsSet(TYPE.PARALLEL, *justs)
        }

        fun show(text: Text): Just {
            return Just(text)
        }
    }
}