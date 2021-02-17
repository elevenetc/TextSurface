package su.levenetc.android.textsurface.animations.effects.reveal

import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Paint
import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.TextSurface

abstract class ShapeRevealAnimation(val isToShow: Boolean) {

    lateinit var textSurface: TextSurface
    lateinit var text: Text

    abstract fun initAnimator(): ValueAnimator
    abstract fun clip(canvas: Canvas, textValue: String?, x: Float, y: Float, paint: Paint)
}