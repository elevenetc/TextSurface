package su.levenetc.android.textsurface

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import su.levenetc.android.textsurface.animations.SurfaceAnimation
import su.levenetc.android.textsurface.animations.TextSurfaceAnimation
import su.levenetc.android.textsurface.animations.camera.CameraAnimation
import su.levenetc.android.textsurface.animations.sets.AnimationsSet
import su.levenetc.android.textsurface.animations.sets.Parallel
import su.levenetc.android.textsurface.animations.sets.Sequential
import su.levenetc.android.textsurface.camera.SurfaceCamera
import su.levenetc.android.textsurface.utils.getDesiredSize
import su.levenetc.android.textsurface.utils.measureDimension

/**
 * Created by Eugene Levenetc.
 */
class TextSurface : View {

    private val camera = SurfaceCamera()
    private val texts = mutableSetOf<Text>()
    private lateinit var currentAnimation: SurfaceAnimation

    init {
        setWillNotDraw(false)
    }

    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)

    fun playParallel(vararg animations: SurfaceAnimation) {
        play(Parallel(* animations))
    }

    fun playSequential(vararg animations: SurfaceAnimation) {
        play(Sequential(* animations))
    }

    fun play(animation: SurfaceAnimation) {
        configAnimations(animation)
        animation.textSurface = this
        layout()
        currentAnimation = animation
        currentAnimation.start()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        camera.onDraw(canvas)
        texts.forEach { it.onDraw(canvas, this) }
    }

    fun reset() {
        if (this::currentAnimation.isInitialized) currentAnimation.cancel()
        texts.clear()
        camera.reset()
        invalidate()
    }

    private fun layout() {
        texts.forEach { it.layout(this) }
    }

    private fun configAnimations(animation: SurfaceAnimation) {
        if (animation is CameraAnimation) {
            animation.camera = camera
        } else if (animation is AnimationsSet) {
            animation.animations.forEach { configAnimations(it) }
        } else if (animation is TextSurfaceAnimation) {
            val text = animation.text
            if (texts.add(text)) animation.setInitValues(text)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = getDesiredSize(layoutParams.width, suggestedMinimumWidth, widthMeasureSpec)
        val desiredHeight = getDesiredSize(layoutParams.height, suggestedMinimumHeight, heightMeasureSpec)

        setMeasuredDimension(
                measureDimension(desiredWidth, widthMeasureSpec),
                measureDimension(desiredHeight, heightMeasureSpec)
        )
    }
}