package su.levenetc.android.textsurface.animations

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.annotation.SuppressLint
import android.graphics.PointF
import su.levenetc.android.textsurface.SurfaceCamera
import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.contants.Pivot
import su.levenetc.android.textsurface.interfaces.ICameraAnimation
import su.levenetc.android.textsurface.interfaces.IEndListener
import su.levenetc.android.textsurface.utils.AnimatorEndListener

/**
 * Created by Eugene Levenetc.
 */
class CamRot : ICameraAnimation, AnimatorUpdateListener {
    var rotationDelta: Float
    private var pivotX = 0f
    private var pivotY = 0f
    override var duration: Long
    override lateinit var textSurface: TextSurface
    private var camera: SurfaceCamera? = null
    private var textPivot: Text? = null
    private var pivotAlign = 0
    private var animator: ObjectAnimator? = null

    constructor(duration: Long, rotationDelta: Float) {
        this.duration = duration
        this.rotationDelta = rotationDelta
    }

    constructor(duration: Long, rotationDelta: Float, pivotX: Float, pivotY: Float) {
        this.duration = duration
        this.rotationDelta = rotationDelta
        this.pivotX = pivotX
        this.pivotY = pivotY
    }

    constructor(duration: Long, rotationDelta: Float, textPivot: Text?, pivotAlign: Int) {
        this.duration = duration
        this.rotationDelta = rotationDelta
        this.textPivot = textPivot
        this.pivotAlign = pivotAlign
    }

    override fun onStart() {}

    @SuppressLint("ObjectAnimatorBinding")
    override fun start(listener: IEndListener) {
        setPivot()
        val from = camera!!.rotation
        val to = camera!!.rotation + rotationDelta
        animator = ObjectAnimator.ofFloat(camera, "rotation", from, to)
        animator!!.setDuration(duration.toLong())
        animator!!.addUpdateListener(this)
        animator!!.addListener(object : AnimatorEndListener() {
            override fun onAnimationEnd(animation: Animator) {
                listener.onAnimationEnd(this@CamRot)
            }
        })
        animator!!.start()
    }

    private val tmpPoint = PointF()
    private fun setPivot() {
        val rotationPivot = camera!!.rotationPivot
        if (textPivot != null) {
            if (pivotAlign and Pivot.LEFT == Pivot.LEFT) {
                pivotX = textPivot!!.getX(textSurface)
            } else if (pivotAlign and Pivot.RIGHT == Pivot.RIGHT) {
                pivotX = textPivot!!.getX(textSurface) + textPivot!!.width
            }
            if (pivotAlign and Pivot.BOTTOM == Pivot.BOTTOM) {
                pivotY = textPivot!!.getY(textSurface)
            } else if (pivotAlign and Pivot.TOP == Pivot.TOP) {
                pivotY = textPivot!!.getY(textSurface) - textPivot!!.height
            }
            if (pivotAlign and Pivot.CENTER == Pivot.CENTER) {
                pivotX = textPivot!!.getX(textSurface) + textPivot!!.width / 2
                pivotY = textPivot!!.getY(textSurface) - textPivot!!.height / 2
            }
        }
        tmpPoint[pivotX] = pivotY
        //		rotatePoint(tmpPoint, camera.getRotationPivot(), camera.getRotation());
        rotatePoint2(tmpPoint, camera!!.rotationPivot, camera!!.rotation)
        rotationPivot[tmpPoint.x] = tmpPoint.y
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

    companion object {
        private fun rotatePoint2(point: PointF, center: PointF, angle: Float) {
            val centerX = center.x
            val centerY = center.y
            val point2x = point.x
            val point2y = point.y
            val newX = centerX + (point2x - centerX) * Math.cos(angle.toDouble()) - (point2y - centerY) * Math.sin(angle.toDouble())
            val newY = centerY + (point2x - centerX) * Math.sin(angle.toDouble()) + (point2y - centerY) * Math.cos(angle.toDouble())
            point[newX.toFloat()] = newY.toFloat()
        }

        private fun rotatePoint(point: PointF, center: PointF, angle: Float) {
            if (angle == 0f) return
            var x1 = (point.x - center.x).toDouble()
            var y1 = (point.y - center.y).toDouble()

            //APPLY ROTATION
            val xTemp = x1
            x1 = x1 * Math.cos(angle.toDouble()) - y1 * Math.sin(angle.toDouble())
            y1 = xTemp * Math.sin(angle.toDouble()) + y1 * Math.cos(angle.toDouble())

            //TRANSLATE BACK
            point.x = Math.ceil(x1).toInt() + center.x
            point.y = Math.ceil(y1).toInt() + center.y
        }
    }
}