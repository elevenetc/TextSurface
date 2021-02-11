package su.levenetc.android.textsurface.utils

import android.animation.Animator
import android.content.res.Resources
import su.levenetc.android.textsurface.interfaces.IEndListener
import su.levenetc.android.textsurface.interfaces.ISurfaceAnimation
import java.util.*

/**
 * Created by Eugene Levenetc.
 */
object Utils {
    /**
     * Generates string of "`" char
     * Not effective on large sizes
     */
    fun genString(size: Int): String {
        val array = CharArray(size)
        Arrays.fill(array, '\'')
        return String(array)
    }

    fun <T> concat(first: Array<T>, second: Array<T>): Array<T> {
        val result = Arrays.copyOf(first, first.size + second.size)
        System.arraycopy(second, 0, result, first.size, second.size)
        return result
    }

    @JvmStatic
    fun addEndListener(animation: ISurfaceAnimation, animator: Animator, listener: IEndListener? = null) {
        if (listener == null) return
        animator.addListener(object : AnimatorEndListener() {
            override fun onAnimationEnd(a: Animator) {
                listener.onAnimationEnd(animation)
            }
        })
    }

    @JvmStatic
    fun dpToPx(dp: Float): Float {
        val metrics = Resources.getSystem().displayMetrics
        return dp * (metrics.densityDpi / 160f)
    }
}