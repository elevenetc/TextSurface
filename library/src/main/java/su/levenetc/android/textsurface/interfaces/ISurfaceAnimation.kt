package su.levenetc.android.textsurface.interfaces

import su.levenetc.android.textsurface.TextSurface

/**
 * Created by Eugene Levenetc.
 */
interface ISurfaceAnimation {

    val duration: Long
    var textSurface: TextSurface

    fun onStart()
    fun start(listener: IEndListener)
    fun cancel()
}