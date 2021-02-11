package su.levenetc.android.textsurface.sample.checks

import su.levenetc.android.textsurface.TextBuilder.Companion.create
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.animations.AnimationsSet
import su.levenetc.android.textsurface.animations.Rotate3D.Companion.hideFromCenter
import su.levenetc.android.textsurface.animations.Rotate3D.Companion.hideFromSide
import su.levenetc.android.textsurface.animations.Rotate3D.Companion.showFromCenter
import su.levenetc.android.textsurface.animations.Rotate3D.Companion.showFromSide
import su.levenetc.android.textsurface.contants.*

/**
 * Created by Eugene Levenetc.
 */
object Rotation3DSample {
    fun play(textSurface: TextSurface) {
        val textA = create("How are you?").setPosition(Align.SURFACE_CENTER).build()
        val textB = create("I'm fine! And you?").setPosition(Align.SURFACE_CENTER, textA).build()
        val textC = create("Haaay!").setPosition(Align.SURFACE_CENTER, textB).build()
        val duration = 2750
        textSurface.play(TYPE.SEQUENTIAL,
                AnimationsSet(TYPE.SEQUENTIAL,
                        showFromCenter(textA, duration.toLong(), Direction.CLOCK, Axis.X),
                        hideFromCenter(textA, duration.toLong(), Direction.CLOCK, Axis.Y)
                ),
                AnimationsSet(TYPE.SEQUENTIAL,
                        showFromSide(textB, duration.toLong(), Pivot.LEFT),
                        hideFromSide(textB, duration.toLong(), Pivot.RIGHT)
                ),
                AnimationsSet(TYPE.SEQUENTIAL,
                        showFromSide(textC, duration.toLong(), Pivot.TOP),
                        hideFromSide(textC, duration.toLong(), Pivot.BOTTOM)
                )
        )
    }
}