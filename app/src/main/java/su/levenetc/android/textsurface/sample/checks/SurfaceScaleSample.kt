package su.levenetc.android.textsurface.sample.checks

import su.levenetc.android.textsurface.TextBuilder.Companion.create
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.animations.Alpha.Companion.hide
import su.levenetc.android.textsurface.animations.Alpha.Companion.show
import su.levenetc.android.textsurface.animations.AnimationsSet
import su.levenetc.android.textsurface.animations.Delay.Companion.duration
import su.levenetc.android.textsurface.animations.ScaleSurface
import su.levenetc.android.textsurface.contants.Align
import su.levenetc.android.textsurface.contants.Fit
import su.levenetc.android.textsurface.contants.TYPE

/**
 * Created by Eugene Levenetc.
 */
object SurfaceScaleSample {
    fun play(textSurface: TextSurface) {
        val textA = create("How are you?").setPosition(Align.SURFACE_CENTER).build()
        val textB = create("Would you mind?").setPosition(Align.BOTTOM_OF or Align.CENTER_OF, textA).build()
        val textC = create("Yes!").setPosition(Align.BOTTOM_OF or Align.CENTER_OF, textB).build()
        textSurface.play(TYPE.SEQUENTIAL,
                show(textA, 500),
                AnimationsSet(TYPE.PARALLEL,
                        AnimationsSet(TYPE.PARALLEL, show(textB, 500), hide(textA, 500)),
                        ScaleSurface(500, textB, Fit.WIDTH)
                ),
                duration(1000),
                AnimationsSet(TYPE.PARALLEL,
                        AnimationsSet(TYPE.PARALLEL, show(textC, 500), hide(textB, 500)),
                        ScaleSurface(500, textC, Fit.WIDTH)
                )
        )
    }
}