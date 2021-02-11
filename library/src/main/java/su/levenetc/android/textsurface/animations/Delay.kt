package su.levenetc.android.textsurface.animations

import android.graphics.Paint
import android.graphics.RectF
import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.common.Position
import su.levenetc.android.textsurface.interfaces.IEndListener


class Delay(duration: Long) : AbstractSurfaceAnimation(EMPTY_TEXT, duration) {
    private var action: Runnable? = null
    override lateinit var textSurface: TextSurface

    override fun start(listener: IEndListener) {
        action = Runnable { listener?.onAnimationEnd(this@Delay) }
        textSurface!!.postDelayed(action, duration.toLong())
    }

    override fun cancel() {
        if (action != null) {
            textSurface!!.removeCallbacks(action)
            action = null
        }
    }

    override fun toString(): String {
        return "Delay{" +
                "duration=" + duration +
                '}'
    }

    companion object {

        private val EMPTY_TEXT = Text("", Position(), RectF(), Paint())

        @kotlin.jvm.JvmStatic
        fun duration(duration: Long): Delay {
            return Delay(duration)
        }
    }
}