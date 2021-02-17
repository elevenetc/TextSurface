package su.levenetc.android.textsurface.animations.effects

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.constants.Axis
import su.levenetc.android.textsurface.constants.Pivot
import su.levenetc.android.textsurface.utils.setupAndStart

/**
 * Created by Eugene Levenetc.
 */
class Rotate3D private constructor(
        text: Text,
        duration: Long,
        private val pivot: Int,
        private val axis: Axis,
        private val show: Boolean
) : TextEffect(text, duration) {

    private val camera = Camera()
    private val cameraMatrix = Matrix()
    private var rotationX = 0f
    private var rotationY = 0f
    private var cameraTransXPre = 0f
    private var cameraTransYPre = 0f
    private var cameraTransXPost = 0f
    private var cameraTransYPost = 0f
    private var animator: ObjectAnimator? = null

    override fun apply(canvas: Canvas, textValue: String, x: Float, y: Float, paint: Paint) {
        camera.getMatrix(cameraMatrix)
        camera.save()
        camera.rotateX(rotationX)
        camera.rotateY(rotationY)
        camera.getMatrix(cameraMatrix)
        cameraMatrix.preTranslate(cameraTransXPre, cameraTransYPre)
        cameraMatrix.postTranslate(cameraTransXPost, cameraTransYPost)
        camera.restore()
        canvas.concat(cameraMatrix)
    }

    override fun setInitValues(text: Text) {
        if (show) text.setAlpha(0)
    }

    override fun start() {
        var valHolder: PropertyValuesHolder? = null
        val fromDegree: Int
        val toDegree: Int
        text.setAlpha(255)
        if (show) {
            fromDegree = 90
            toDegree = 0
        } else {
            fromDegree = 0
            toDegree = -90
        }
        if (pivot and Pivot.BOTTOM == Pivot.BOTTOM) {
            valHolder = PropertyValuesHolder.ofFloat("rotationX", fromDegree.toFloat(), toDegree.toFloat())
            cameraTransXPre = -text.width / 2
            cameraTransXPost = text.width / 2
            cameraTransYPre = -text.fontDescent
            cameraTransYPost = 0f
        } else if (pivot and Pivot.TOP == Pivot.TOP) {
            valHolder = PropertyValuesHolder.ofFloat("rotationX", -fromDegree.toFloat(), toDegree.toFloat())
            cameraTransXPre = -text.width / 2
            cameraTransXPost = text.width / 2
            cameraTransYPre = text.height - text.fontDescent
            cameraTransYPost = -text.height
        }
        if (pivot and Pivot.LEFT == Pivot.LEFT) {
            valHolder = PropertyValuesHolder.ofFloat("rotationY", fromDegree.toFloat(), toDegree.toFloat())
            cameraTransXPre = 0f
            cameraTransXPost = 0f
            cameraTransYPre = text.height / 2 - text.fontDescent
            cameraTransYPost = text.height / 2 - text.height
        } else if (pivot and Pivot.RIGHT == Pivot.RIGHT) {
            valHolder = PropertyValuesHolder.ofFloat("rotationY", -fromDegree.toFloat(), toDegree.toFloat())
            cameraTransXPre = -text.width
            cameraTransXPost = text.width
            cameraTransYPre = text.height / 2 - text.fontDescent
            cameraTransYPost = text.height / 2 - text.height
        }
        if (pivot and Pivot.CENTER == Pivot.CENTER) {
            valHolder = PropertyValuesHolder.ofFloat(if (axis == Axis.Y) "rotationY" else "rotationX", fromDegree.toFloat(), toDegree.toFloat())
            cameraTransXPre = -text.width / 2
            cameraTransXPost = text.width / 2
            cameraTransYPre = text.height / 2 - text.fontDescent
            cameraTransYPost = text.height / 2 - text.height
        }
        if (valHolder != null) {
            animator = ObjectAnimator.ofPropertyValuesHolder(this, valHolder)
            animator!!.interpolator = FastOutSlowInInterpolator()

            animator!!.setupAndStart(this, endDelegate = {
                text.removeEffect(this)
                if (!show) text.setAlpha(0)
            })
        } else {
            throw RuntimeException(javaClass.superclass.toString() + " was not configured properly. Pivot:" + pivot)
        }
    }

    override fun cancel() {
        super.cancel()
        animator?.cancel()
    }

    fun setRotationX(rotationX: Float) {
        this.rotationX = rotationX
    }

    fun setRotationY(rotationY: Float) {
        this.rotationY = rotationY
    }

    companion object {
        fun showFromSide(text: Text, duration: Long, pivot: Int): Rotate3D {
            return Rotate3D(text, duration, pivot, Axis.Undefined, true)
        }

        fun showFromCenter(text: Text, duration: Long, axis: Axis): Rotate3D {
            return Rotate3D(text, duration, Pivot.CENTER, axis, true)
        }

        fun hideFromSide(text: Text, duration: Long, pivot: Int): Rotate3D {
            return Rotate3D(text, duration, pivot, Axis.Undefined, false)
        }

        fun hideFromCenter(text: Text, duration: Long, axis: Axis): Rotate3D {
            return Rotate3D(text, duration, Pivot.CENTER, axis, false)
        }
    }
}