package su.levenetc.android.textsurface.animations.camera

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.annotation.SuppressLint
import android.util.Log
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.TextSurfaceDebug
import su.levenetc.android.textsurface.constants.Pivot
import su.levenetc.android.textsurface.utils.setupAndStart

/**
 * Created by Eugene Levenetc.
 */
class TransSurface : CameraAnimation, AnimatorUpdateListener {

    private var pivot = 0
    private var animator: ObjectAnimator? = null
    private val textPivot: Text

    constructor(duration: Long, textPivot: Text, pivot: Int) : super(duration) {
        this.textPivot = textPivot
        this.pivot = pivot
    }

    constructor(duration: Long, textPivot: Text) : super(duration) {
        this.textPivot = textPivot
    }

    @SuppressLint("ObjectAnimatorBinding")
    override fun start() {
        val fromX = camera.transX
        val fromY = camera.transY
        val toX: Float = textPivot.position.getRelativeX(pivot, textPivot, true) * camera.getScale() * -1
        val toY: Float = textPivot.position.getRelativeY(pivot, textPivot, true) * camera.getScale() * -1
        debugTranslation(fromX, fromY, toX, toY)
        val dxHolder = PropertyValuesHolder.ofFloat("transX", fromX, toX)
        val dyHolder = PropertyValuesHolder.ofFloat("transY", fromY, toY)
        animator = ObjectAnimator.ofPropertyValuesHolder(camera, dxHolder, dyHolder)
        animator!!.interpolator = FastOutSlowInInterpolator()
        animator!!.setupAndStart(this)
    }

    private fun debugTranslation(fromX: Float, fromY: Float, toX: Float, toY: Float) {
        if (TextSurfaceDebug.ENABLED && fromX == toX && fromY == toY) Log.e(javaClass.simpleName, "No translation to $textPivot from:$fromX:$fromY to:$toX:$toY")
    }

    override fun cancel() {
        super.cancel()
        animator?.cancel()
    }

    override fun onAnimationUpdate(animation: ValueAnimator) {
        textSurface.invalidate()
    }

    override fun toString(): String {
        return "TransSurface{textPivot=$textPivot}"
    }

    companion object {
        fun toCenterOf(textPivot: Text, duration: Long): TransSurface {
            return TransSurface(duration, textPivot, Pivot.CENTER)
        }
    }
}