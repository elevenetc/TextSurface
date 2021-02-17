package su.levenetc.android.textsurface.animations.generic

import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.animations.AbstractSurfaceAnimation
import su.levenetc.android.textsurface.animations.sets.AnimationsSet
import su.levenetc.android.textsurface.animations.sets.Parallel

/**
 * Created by Eugene Levenetc.
 */
class Just private constructor(text: Text) : AbstractSurfaceAnimation(text, 0) {

    override fun start() {
        textSurface.invalidate()
        endListener(this)
    }

    companion object {
        fun show(vararg texts: Text): AnimationsSet {
            return Parallel(* texts.map {
                show(it)
            }.toTypedArray())
        }

        fun show(text: Text): Just {
            return Just(text)
        }
    }
}