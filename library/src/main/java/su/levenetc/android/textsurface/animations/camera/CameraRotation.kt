package su.levenetc.android.textsurface.animations.camera

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.PointF
import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.constants.Pivot
import su.levenetc.android.textsurface.utils.setupAndStart
import kotlin.math.ceil
import kotlin.math.cos
import kotlin.math.sin

/**
 * Created by Eugene Levenetc.
 */
class CameraRotation : CameraAnimation {

    private var rotationDelta = 0f
    private var pivotX = 0f
    private var pivotY = 0f
    private var pivotAlign = 0
    private var textPivot: Text? = null
    private var animator: ObjectAnimator? = null

    constructor(duration: Long, rotationDelta: Float) : super(duration) {
        this.rotationDelta = rotationDelta
    }

    constructor(duration: Long, rotationDelta: Float, pivotX: Float, pivotY: Float) : super(duration) {
        this.rotationDelta = rotationDelta
        this.pivotX = pivotX
        this.pivotY = pivotY
    }

    constructor(duration: Long, rotationDelta: Float, textPivot: Text?, pivotAlign: Int) : super(duration) {
        this.rotationDelta = rotationDelta
        this.textPivot = textPivot
        this.pivotAlign = pivotAlign
    }

    @SuppressLint("ObjectAnimatorBinding")
    override fun start() {
        setPivot()
        val from = camera.rotation
        val to = camera.rotation + rotationDelta
        animator = ObjectAnimator.ofFloat(camera, "rotation", from, to)
        animator!!.setupAndStart(this)
    }

    private fun setPivot() {

        val rotationPivot = camera.rotationPivot
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
        val tmpPoint = PointF()
        tmpPoint[pivotX] = pivotY
        rotatePoint2(tmpPoint, camera.rotationPivot, camera.rotation)
        rotationPivot[tmpPoint.x] = tmpPoint.y
    }

    override fun cancel() {
        super.cancel()
        animator?.cancel()
    }

    companion object {
        private fun rotatePoint2(point: PointF, center: PointF, angle: Float) {
            val centerX = center.x
            val centerY = center.y
            val point2x = point.x
            val point2y = point.y
            val newX = centerX + (point2x - centerX) * cos(angle.toDouble()) - (point2y - centerY) * sin(angle.toDouble())
            val newY = centerY + (point2x - centerX) * sin(angle.toDouble()) + (point2y - centerY) * cos(angle.toDouble())
            point[newX.toFloat()] = newY.toFloat()
        }

        private fun rotatePoint(point: PointF, center: PointF, angle: Float) {
            if (angle == 0f) return
            var x1 = (point.x - center.x).toDouble()
            var y1 = (point.y - center.y).toDouble()

            //APPLY ROTATION
            val xTemp = x1
            x1 = x1 * cos(angle.toDouble()) - y1 * sin(angle.toDouble())
            y1 = xTemp * sin(angle.toDouble()) + y1 * cos(angle.toDouble())

            //TRANSLATE BACK
            point.x = ceil(x1).toInt() + center.x
            point.y = ceil(y1).toInt() + center.y
        }
    }
}