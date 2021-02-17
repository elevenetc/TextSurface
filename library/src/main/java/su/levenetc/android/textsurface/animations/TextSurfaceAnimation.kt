package su.levenetc.android.textsurface.animations

import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.TextSurface

/**
 * Created by Eugene Levenetc.
 */
open class TextSurfaceAnimation(val text: Text, duration: Long) : SurfaceAnimation(duration) {
    /**
     * Called by [TextSurface] on initial configuration of texts
     */
    open fun setInitValues(text: Text) {

    }
}