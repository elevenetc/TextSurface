package su.levenetc.android.textsurface.animations.generic

import android.graphics.Paint
import android.graphics.RectF
import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.animations.AbstractSurfaceAnimation
import su.levenetc.android.textsurface.common.Position


class Delay(duration: Long) : AbstractSurfaceAnimation(EMPTY_TEXT, duration) {

    private var action: Runnable? = null

    override fun start() {
        action = Runnable { endListener(this) }
        textSurface.postDelayed(action, duration)
    }

    override fun cancel() {
        super.cancel()
        action?.let {
            textSurface.removeCallbacks(action)
        }
    }

    override fun toString(): String {
        return "Delay{duration=$duration}"
    }

    companion object {

        private val EMPTY_TEXT = Text("", Position(), RectF(), Paint())

        fun delay(duration: Long): Delay {
            return Delay(duration)
        }
    }
}