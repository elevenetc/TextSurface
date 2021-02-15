package su.levenetc.android.textsurface.animations.effects

import android.graphics.Canvas
import android.graphics.Paint
import androidx.annotation.CallSuper
import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.animations.TextSurfaceAnimation

/**
 * Created by Eugene Levenetc.
 */
abstract class TextEffect(text: Text, duration: Long) : TextSurfaceAnimation(text, duration) {

    abstract fun apply(canvas: Canvas, textValue: String, x: Float, y: Float, paint: Paint)

    @CallSuper
    override fun onStart() {
        text.addEffect(this)
    }
}