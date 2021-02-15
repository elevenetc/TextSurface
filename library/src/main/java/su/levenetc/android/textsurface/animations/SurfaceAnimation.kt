package su.levenetc.android.textsurface.animations

import androidx.annotation.CallSuper
import su.levenetc.android.textsurface.TextSurface

/**
 * Created by Eugene Levenetc.
 */
open class SurfaceAnimation(open val duration: Long) {

    open lateinit var textSurface: TextSurface
    var endListener: (SurfaceAnimation) -> Unit = {}
    var canceled = false

    open fun onStart() {

    }

    open fun start() {

    }

    @CallSuper
    open fun cancel() {
        canceled = true
    }
}