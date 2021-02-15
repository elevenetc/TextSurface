package su.levenetc.android.textsurface.animations.camera

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.constants.Fit
import su.levenetc.android.textsurface.constants.Pivot
import su.levenetc.android.textsurface.utils.setupAndStart

/**
 * Created by Eugene Levenetc.
 */
class ScaleSurface : CameraAnimation {

    private var fit: Fit = Fit.UNDEFINED
    private var pivot = Pivot.UNDEFINED
    private var toScale = 0f
    private val textPivot: Text
    private var animator: ObjectAnimator? = null

    constructor(duration: Long, textPivot: Text, pivot: Int, toScale: Float) : super(duration) {
        this.textPivot = textPivot
        this.pivot = pivot
        this.toScale = toScale
    }

    constructor(duration: Long, textPivot: Text, fit: Fit) : super(duration) {
        this.textPivot = textPivot
        this.fit = fit
    }

    @SuppressLint("ObjectAnimatorBinding")
    override fun start() {
        val pivotX: Float
        val pivotY: Float
        if (fit == Fit.UNDEFINED) {
            pivotX = textPivot.position.getRelativeX(pivot, textPivot, true)
            pivotY = textPivot.position.getRelativeY(pivot, textPivot, true)
        } else {
            val surfaceWidth = textSurface.width
            val textWidth = textPivot.width
            toScale = surfaceWidth / textWidth
            pivotX = textPivot.position.getRelativeX(Pivot.CENTER, textPivot, true)
            pivotY = textPivot.position.getRelativeY(Pivot.CENTER, textPivot, true)
        }
        val scaleHolder = PropertyValuesHolder.ofFloat("scale", camera.getScale(), toScale)
        val pivotXHolder = PropertyValuesHolder.ofFloat("scalePivotX", camera.scalePivotX, pivotX)
        val pivotYHolder = PropertyValuesHolder.ofFloat("scalePivotY", camera.scalePivotY, pivotY)
        animator = ObjectAnimator.ofPropertyValuesHolder(camera, scaleHolder, pivotXHolder, pivotYHolder)
        animator!!.interpolator = FastOutSlowInInterpolator()
        animator!!.setupAndStart(this)
    }

    override fun cancel() {
        super.cancel()
        animator?.cancel()
    }

    override fun toString(): String {
        return "ScaleSurface{textPivot=$textPivot}"
    }
}