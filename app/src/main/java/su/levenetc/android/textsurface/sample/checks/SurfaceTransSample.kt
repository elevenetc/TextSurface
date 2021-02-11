package su.levenetc.android.textsurface.sample.checks

import su.levenetc.android.textsurface.TextBuilder.Companion.create
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.animations.Alpha.Companion.show
import su.levenetc.android.textsurface.animations.AnimationsSet
import su.levenetc.android.textsurface.animations.TransSurface
import su.levenetc.android.textsurface.contants.Align
import su.levenetc.android.textsurface.contants.Pivot
import su.levenetc.android.textsurface.contants.TYPE

/**
 * Created by Eugene Levenetc.
 */
object SurfaceTransSample {
    fun play(textSurface: TextSurface) {
        val textA = create("TextA").setPosition(Align.SURFACE_CENTER).build()
        val textB = create("TextB").setPosition(Align.RIGHT_OF or Align.BOTTOM_OF, textA).build()
        val textC = create("TextC").setPosition(Align.LEFT_OF or Align.BOTTOM_OF, textB).build()
        val textD = create("TextD").setPosition(Align.RIGHT_OF or Align.BOTTOM_OF, textC).build()
        val duration = 500L
        textSurface.play(TYPE.SEQUENTIAL,
                show(textA, duration),
                AnimationsSet(TYPE.PARALLEL, show(textB, duration), TransSurface(duration, textB, Pivot.CENTER)),
                AnimationsSet(TYPE.PARALLEL, show(textC, duration), TransSurface(duration, textC, Pivot.CENTER)),
                AnimationsSet(TYPE.PARALLEL, show(textD, duration), TransSurface(duration, textD, Pivot.CENTER)),
                TransSurface(duration, textC, Pivot.CENTER),
                TransSurface(duration, textB, Pivot.CENTER),
                TransSurface(duration, textA, Pivot.CENTER)
        )
    }
}