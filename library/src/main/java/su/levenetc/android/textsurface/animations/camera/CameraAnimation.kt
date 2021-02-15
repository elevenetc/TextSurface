package su.levenetc.android.textsurface.animations.camera

import su.levenetc.android.textsurface.animations.SurfaceAnimation
import su.levenetc.android.textsurface.camera.SurfaceCamera

/**
 * Created by Eugene Levenetc.
 */
open class CameraAnimation(duration: Long) : SurfaceAnimation(duration) {
    lateinit var camera: SurfaceCamera
}