package su.levenetc.android.textsurface.animations

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.annotation.SuppressLint
import android.util.Log
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import su.levenetc.android.textsurface.Debug
import su.levenetc.android.textsurface.SurfaceCamera
import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.contants.Pivot
import su.levenetc.android.textsurface.interfaces.ICameraAnimation
import su.levenetc.android.textsurface.interfaces.IEndListener
import su.levenetc.android.textsurface.utils.Utils.addEndListener

/**
 * Created by Eugene Levenetc.
 */
class TransSurface : ICameraAnimation, AnimatorUpdateListener {
    var dx = 0f
    var dy = 0f

    override val duration: Long
    private var textPivot: Text? = null
    private var pivot = 0
    private var camera: SurfaceCamera? = null
    private var animator: ObjectAnimator? = null
    override lateinit var textSurface: TextSurface

    constructor(duration: Long, textPivot: Text?, pivot: Int) {
        this.duration = duration
        this.textPivot = textPivot
        this.pivot = pivot
    }

    constructor(duration: Long, textPivot: Text?) {
        this.duration = duration
        this.textPivot = textPivot
    }

    constructor(duration: Long, dx: Float, dy: Float) {
        this.duration = duration
        this.dx = dx
        this.dy = dy
    }

    override fun onStart() {}

    @SuppressLint("ObjectAnimatorBinding")
    override fun start(listener: IEndListener) {
        val fromX = camera!!.transX
        val fromY = camera!!.transY
        val toX: Float
        val toY: Float
        if (textPivot == null) {
            toX = camera!!.transX + dx
            toY = camera!!.transY + dy
        } else {
            toX = textPivot!!.position.getRelativeX(pivot, textPivot, true) * camera!!.getScale() * -1
            toY = textPivot!!.position.getRelativeY(pivot, textPivot, true) * camera!!.getScale() * -1
        }
        debugTranslation(fromX, fromY, toX, toY)
        val dxHolder = PropertyValuesHolder.ofFloat("transX", fromX, toX)
        val dyHolder = PropertyValuesHolder.ofFloat("transY", fromY, toY)
        animator = ObjectAnimator.ofPropertyValuesHolder(camera, dxHolder, dyHolder)
        animator!!.interpolator = FastOutSlowInInterpolator()
        addEndListener(this, animator!!, listener)
        animator!!.duration = duration.toLong()
        animator!!.addUpdateListener(this)
        animator!!.start()
    }

    private fun debugTranslation(fromX: Float, fromY: Float, toX: Float, toY: Float) {
        if (Debug.ENABLED && fromX == toX && fromY == toY) Log.e(javaClass.simpleName, "No translation to $textPivot from:$fromX:$fromY to:$toX:$toY")
    }

    override fun cancel() {
        if (animator != null && animator!!.isRunning) {
            animator!!.cancel()
            animator = null
        }
    }

    override fun onAnimationUpdate(animation: ValueAnimator) {
        textSurface!!.invalidate()
    }

    override fun setCamera(camera: SurfaceCamera?) {
        this.camera = camera
    }

    override fun toString(): String {
        return "TransSurface{" +
                "textPivot=" + (if (textPivot == null) "null" else textPivot.toString()) +
                '}'
    }

    companion object {
        @kotlin.jvm.JvmStatic
        fun toCenter(textPivot: Text?, duration: Long): TransSurface {
            return TransSurface(duration, textPivot, Pivot.CENTER)
        }
    }
}