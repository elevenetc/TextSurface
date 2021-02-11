package su.levenetc.android.textsurface.animations

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.graphics.*
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.contants.Axis
import su.levenetc.android.textsurface.contants.Pivot
import su.levenetc.android.textsurface.interfaces.IEndListener
import su.levenetc.android.textsurface.interfaces.ISurfaceAnimation
import su.levenetc.android.textsurface.interfaces.ITextEffect
import su.levenetc.android.textsurface.utils.Utils.addEndListener

/**
 * Created by Eugene Levenetc.
 */
class Rotate3D private constructor(
        override val text: Text,
        override var duration: Long,
        private val pivot: Int,
        private val direction: Int,
        private val axis: Int,
        private val show: Boolean
) : ITextEffect, AnimatorUpdateListener {
    protected val camera = Camera()
    protected val cameraMatrix = Matrix()
    private var rotationX = 0f
    private var rotationY = 0f
    override lateinit var textSurface: TextSurface
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
        if (show) {
            text.setAlpha(0)
        }
    }

    override fun onStart() {
        text.addEffect(this)
    }

    override fun start(listener: IEndListener) {
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
            addEndListener(this, animator!!, object : IEndListener {
                override fun onAnimationEnd(animation: ISurfaceAnimation) {
                    text.removeEffect(this@Rotate3D)
                    if (!show) text.setAlpha(0)
                    listener.onAnimationEnd(this@Rotate3D)
                }
            })
            animator!!.duration = duration.toLong()
            animator!!.addUpdateListener(this)
            animator!!.start()
        } else {
            throw RuntimeException(javaClass.superclass.toString() + " was not configured properly. Pivot:" + pivot)
        }
    }

    override fun cancel() {
        if (animator != null && animator!!.isRunning) {
            animator!!.cancel()
            animator = null
        }
    }

    fun setRotationX(rotationX: Float) {
        this.rotationX = rotationX
    }

    fun setRotationY(rotationY: Float) {
        this.rotationY = rotationY
    }

    override fun onAnimationUpdate(animation: ValueAnimator) {
        textSurface!!.invalidate()
    }

    companion object {
        @kotlin.jvm.JvmStatic
        fun showFromSide(text: Text, duration: Long, pivot: Int): Rotate3D {
            return Rotate3D(text, duration, pivot, 0, 0, true)
        }

        @kotlin.jvm.JvmStatic
        fun showFromCenter(text: Text, duration: Long, direction: Int, axis: Int): Rotate3D {
            return Rotate3D(text, duration, Pivot.CENTER, direction, axis, true)
        }

        @kotlin.jvm.JvmStatic
        fun hideFromSide(text: Text, duration: Long, pivot: Int): Rotate3D {
            return Rotate3D(text, duration, pivot, 0, 0, false)
        }

        @kotlin.jvm.JvmStatic
        fun hideFromCenter(text: Text, duration: Long, direction: Int, axis: Int): Rotate3D {
            return Rotate3D(text, duration, Pivot.CENTER, direction, axis, false)
        }
    }
}