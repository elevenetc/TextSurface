package su.levenetc.android.textsurface.animations

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.annotation.SuppressLint
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
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
class ScaleSurface : ICameraAnimation, AnimatorUpdateListener {
    private var camera: SurfaceCamera? = null
    override lateinit var textSurface: TextSurface
    override var duration: Long
    private val textPivot: Text?
    private var fit = -1
    private var pivot = 0
    private var toScale = 0f
    private var animator: ObjectAnimator? = null

    constructor(duration: Long, textPivot: Text?, pivot: Int, toScale: Float) {
        this.duration = duration
        this.textPivot = textPivot
        this.pivot = pivot
        this.toScale = toScale
    }

    constructor(duration: Long, textPivot: Text?, fit: Int) {
        this.duration = duration
        this.textPivot = textPivot
        this.fit = fit
    }

    override fun setCamera(camera: SurfaceCamera?) {
        this.camera = camera
    }

    override fun onStart() {}

    @SuppressLint("ObjectAnimatorBinding")
    override fun start(listener: IEndListener) {
        val pivotX: Float
        val pivotY: Float
        if (fit == -1) {
            pivotX = textPivot!!.position.getRelativeX(pivot, textPivot, true)
            pivotY = textPivot.position.getRelativeY(pivot, textPivot, true)
        } else {
            val surfaceWidth = textSurface!!.width
            val textWidth = textPivot!!.width
            toScale = surfaceWidth / textWidth
            pivotX = textPivot.position.getRelativeX(Pivot.CENTER, textPivot, true)
            pivotY = textPivot.position.getRelativeY(Pivot.CENTER, textPivot, true)
        }
        val scaleHolder = PropertyValuesHolder.ofFloat("scale", camera!!.getScale(), toScale)
        val pivotXHolder = PropertyValuesHolder.ofFloat("scalePivotX", camera!!.scalePivotX, pivotX)
        val pivotYHolder = PropertyValuesHolder.ofFloat("scalePivotY", camera!!.scalePivotY, pivotY)
        animator = ObjectAnimator.ofPropertyValuesHolder(camera, scaleHolder, pivotXHolder, pivotYHolder)
        animator!!.interpolator = FastOutSlowInInterpolator()
        animator!!.duration = duration.toLong()
        animator!!.addUpdateListener(this)
        addEndListener(this, animator!!, listener)
        animator!!.start()
    }

    override fun cancel() {
        if (animator != null && animator!!.isRunning) {
            animator!!.cancel()
        }
    }

    override fun onAnimationUpdate(animation: ValueAnimator) {
        textSurface!!.invalidate()
    }

    override fun toString(): String {
        return "ScaleSurface{" +
                "textPivot=" + (textPivot?.toString() ?: "null") +
                '}'
    }
}