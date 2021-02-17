package su.levenetc.android.textsurface.sample.animations

import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.animations.camera.TransSurface
import su.levenetc.android.textsurface.animations.effects.Slide
import su.levenetc.android.textsurface.animations.sets.Parallel
import su.levenetc.android.textsurface.animations.sets.Sequential
import su.levenetc.android.textsurface.constants.Align
import su.levenetc.android.textsurface.constants.Pivot
import su.levenetc.android.textsurface.constants.Side

fun slideSample(textSurface: TextSurface) {
    val textA = Text.Builder(" How are you?").build()
    val textB = Text.Builder("I'm fine! ").setPosition(Align.LEFT_OF, textA).build()
    val textC = Text.Builder("Are you sure?").setPosition(Align.BOTTOM_OF or Align.CENTER_OF, textB).build()
    val textD = Text.Builder("Totally!").setPadding(10f, 10f, 10f, 10f).setPosition(Align.BOTTOM_OF or Align.CENTER_OF, textC).build()
    val duration = 1250L
    textSurface.playSequential(
            Parallel(
                    Sequential(
                            Parallel(Slide.showFrom(Side.LEFT, textA, duration), Slide.showFrom(Side.RIGHT, textB, duration)),
                            Slide.showFrom(Side.TOP, textC, duration),
                            Slide.showFrom(Side.BOTTOM, textD, duration)
                    ),
                    TransSurface(duration * 3, textD, Pivot.CENTER)
            ),
            Parallel(
                    Sequential(
                            Parallel(Slide.hideFrom(Side.LEFT, textD, duration), Slide.hideFrom(Side.RIGHT, textC, duration)),
                            Slide.hideFrom(Side.TOP, textB, duration),
                            Slide.hideFrom(Side.BOTTOM, textA, duration)
                    ),
                    TransSurface(duration * 3, textA, Pivot.CENTER)
            )
    )
}