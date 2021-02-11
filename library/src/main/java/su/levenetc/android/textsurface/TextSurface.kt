package su.levenetc.android.textsurface

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.FrameLayout
import su.levenetc.android.textsurface.animations.AnimationsSet
import su.levenetc.android.textsurface.contants.TYPE
import su.levenetc.android.textsurface.interfaces.*
import java.util.*

/**
 * Created by Eugene Levenetc.
 */
class TextSurface : FrameLayout {
    private val textsTree = TreeSet<Text>()
    val camera = SurfaceCamera()
    private var currentAnimation: ISurfaceAnimation? = null

    constructor(context: Context?) : super(context!!) {
        config()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        config()
    }

    private fun config() {
        setWillNotDraw(false)
    }

    fun play(type: TYPE, vararg animations: ISurfaceAnimation) {
        play(AnimationsSet(type, *animations))
    }

    fun play(vararg animations: ISurfaceAnimation?) {
        play(AnimationsSet(TYPE.PARALLEL, *animations))
    }

    fun play(animation: ISurfaceAnimation) {
        configAnimations(animation)
        animation.textSurface = this
        layout()
        currentAnimation = animation
        currentAnimation!!.start(object : IEndListener {
            override fun onAnimationEnd(animation: ISurfaceAnimation) {
                //TODO: remove empty listener
            }

        })
    }

    private fun layout() {
        val iterator: Iterator<Text> = textsTree.iterator()
        while (iterator.hasNext()) iterator.next().layout(this)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    private fun configAnimations(animation: ISurfaceAnimation) {
        if (animation is ICameraAnimation) {
            animation.setCamera(camera)
        } else if (animation is ISet) {
            val animations = animation.animations
            for (a in animations) configAnimations(a)
        } else if (animation is ITextSurfaceAnimation) {
            val textAnimation = animation
            val text = textAnimation.text
            if (text != null && textsTree.add(text)) textAnimation.setInitValues(text)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        camera.onDraw(canvas)
        for (text in textsTree) text.onDraw(canvas, this)
    }

    fun reset() {
        if (currentAnimation != null) {
            currentAnimation!!.cancel()
            currentAnimation = null
        }
        textsTree.clear()
        camera.reset()
        invalidate()
    }
}