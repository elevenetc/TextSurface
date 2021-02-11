package su.levenetc.android.textsurface.interfaces

import su.levenetc.android.textsurface.Text

/**
 * Created by Eugene Levenetc.
 */
interface ITextSurfaceAnimation : ISurfaceAnimation {
    val text: Text
    fun setInitValues(text: Text)
}