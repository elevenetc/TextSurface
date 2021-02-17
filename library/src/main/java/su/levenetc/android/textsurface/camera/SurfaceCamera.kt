package su.levenetc.android.textsurface.camera

import android.graphics.Canvas
import android.graphics.PointF
import su.levenetc.android.textsurface.TextSurfaceDebug
import su.levenetc.android.textsurface.common.ScaleValue

/**
 * Created by Eugene Levenetc.
 */
class SurfaceCamera {

    var rotation = 0f
    val rotationPivot = PointF()
    var transX = 0f
    var transY = 0f

    var scalePivotX: Float
        get() = scale.pivot.x
        set(value) {
            scale.pivot.x = value
        }
    var scalePivotY: Float
        get() = scale.pivot.y
        set(value) {
            scale.pivot.y = value
        }

    val centerX: Float
        get() = center.x
    val centerY: Float
        get() = center.y

    private val scale = ScaleValue()
    private val center = PointF()

    fun onDraw(canvas: Canvas) {
        if (TextSurfaceDebug.ENABLED) {
            canvas.save()
            canvas.drawCircle(transX, transY, 10f, TextSurfaceDebug.YELLOW_STROKE)
            canvas.drawCircle(scale.pivot.x, scale.pivot.y, 10f, TextSurfaceDebug.GREEN_STROKE)
            canvas.drawLine(scale.pivot.x, 0f, scale.pivot.x, canvas.height.toFloat(), TextSurfaceDebug.GREEN_STROKE)
            canvas.drawLine(0f, scale.pivot.y, canvas.width.toFloat(), scale.pivot.y, TextSurfaceDebug.GREEN_STROKE)
            canvas.drawLine((canvas.width / 2).toFloat(), 0f, (canvas.width / 2).toFloat(), canvas.height.toFloat(), TextSurfaceDebug.GREEN_STROKE)
            canvas.drawLine(0f, (canvas.height / 2).toFloat(), canvas.width.toFloat(), (canvas.height / 2).toFloat(), TextSurfaceDebug.GREEN_STROKE)
            canvas.restore()
        }
        center[(canvas.width / 2).toFloat()] = (canvas.height / 2).toFloat()
        canvas.translate(center.x + transX, center.y + transY)
        canvas.scale(scale.scaleX, scale.scaleX, scale.pivot.x, scale.pivot.y)
    }

    fun reset() {
        rotationPivot[0f] = 0f
        scale.reset()
        center[0f] = 0f
        transX = 0f
        transY = 0f
    }

    fun getScale(): Float {
        return scale.scaleX
    }

    fun setScale(scale: Float) {
        this.scale.setValue(scale)
    }

    fun setScalePivot(x: Float, y: Float) {
        scale.pivot[x] = y
    }
}