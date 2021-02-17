package su.levenetc.android.textsurface.sample.animations

import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.animations.effects.Rotate3D
import su.levenetc.android.textsurface.animations.sets.Sequential
import su.levenetc.android.textsurface.constants.Align
import su.levenetc.android.textsurface.constants.Axis
import su.levenetc.android.textsurface.constants.Pivot

fun rotation3DSample(textSurface: TextSurface) {
    val textA = Text.Builder("How are you?").setPosition(Align.SURFACE_CENTER).build()
    val textB = Text.Builder("I'm fine! And you?").setPosition(Align.SURFACE_CENTER, textA).build()
    val textC = Text.Builder("Haaay!").setPosition(Align.SURFACE_CENTER, textB).build()
    val duration = 2750
    textSurface.playSequential(
            Sequential(
                    Rotate3D.showFromCenter(textA, duration.toLong(), Axis.X),
                    Rotate3D.hideFromCenter(textA, duration.toLong(), Axis.Y)
            ),
            Sequential(
                    Rotate3D.showFromSide(textB, duration.toLong(), Pivot.LEFT),
                    Rotate3D.hideFromSide(textB, duration.toLong(), Pivot.RIGHT)
            ),
            Sequential(
                    Rotate3D.showFromSide(textC, duration.toLong(), Pivot.TOP),
                    Rotate3D.hideFromSide(textC, duration.toLong(), Pivot.BOTTOM)
            )
    )
}