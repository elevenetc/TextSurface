package su.levenetc.android.textsurface.animations.reveal

import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Paint
import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.TextSurface

interface IRevealShape {

    val isToShow: Boolean
    var textSurface: TextSurface

    fun setText(text: Text?)
    fun getAnimator(): ValueAnimator?
    fun clip(canvas: Canvas, textValue: String?, x: Float, y: Float, paint: Paint?)
}