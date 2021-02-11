package su.levenetc.android.textsurface.interfaces

import su.levenetc.android.textsurface.SurfaceCamera

/**
 * Created by Eugene Levenetc.
 */
interface ICameraAnimation : ISurfaceAnimation {
    fun setCamera(camera: SurfaceCamera?)
}