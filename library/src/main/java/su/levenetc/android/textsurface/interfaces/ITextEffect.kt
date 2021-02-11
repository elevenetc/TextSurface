package su.levenetc.android.textsurface.interfaces

import android.graphics.Canvas
import android.graphics.Paint

/**
 * Created by Eugene Levenetc.
 */
interface ITextEffect : ITextSurfaceAnimation {
    fun apply(canvas: Canvas, textValue: String, x: Float, y: Float, paint: Paint)
}