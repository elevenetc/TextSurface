package su.levenetc.android.textsurface.sample.animations

import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.animations.camera.ScaleSurface
import su.levenetc.android.textsurface.animations.camera.TransSurface
import su.levenetc.android.textsurface.animations.colors.Alpha
import su.levenetc.android.textsurface.animations.generic.Delay
import su.levenetc.android.textsurface.animations.sets.Parallel
import su.levenetc.android.textsurface.contants.Align
import su.levenetc.android.textsurface.contants.Fit
import su.levenetc.android.textsurface.contants.Pivot

fun surfaceScaleSample(textSurface: TextSurface) {
    val textA = Text.Builder("How are you?").setPosition(Align.SURFACE_CENTER).build()
    val textB = Text.Builder("Would you mind?").setPosition(Align.BOTTOM_OF or Align.CENTER_OF, textA).build()
    val textC = Text.Builder("Yes!").setPosition(Align.BOTTOM_OF or Align.CENTER_OF, textB).build()
    textSurface.playSequential(
            Alpha.show(textA, 500),
            Parallel(
                    Parallel(Alpha.show(textB, 500), Alpha.hide(textA, 500)),
                    ScaleSurface(500, textB, Fit.WIDTH)
            ),
            Delay.delay(1000),
            Parallel(
                    Parallel(Alpha.show(textC, 500), Alpha.hide(textB, 500)),
                    ScaleSurface(500, textC, Fit.HEIGHT)
            )
    )
}

fun surfaceTransSample(textSurface: TextSurface) {
    val textA = Text.Builder("TextA").setPosition(Align.SURFACE_CENTER).build()
    val textB = Text.Builder("TextB").setPosition(Align.RIGHT_OF or Align.BOTTOM_OF, textA).build()
    val textC = Text.Builder("TextC").setPosition(Align.LEFT_OF or Align.BOTTOM_OF, textB).build()
    val textD = Text.Builder("TextD").setPosition(Align.RIGHT_OF or Align.BOTTOM_OF, textC).build()
    val duration = 500L
    textSurface.playSequential(
            Alpha.show(textA, duration),
            Parallel(Alpha.show(textB, duration), TransSurface(duration, textB, Pivot.CENTER)),
            Parallel(Alpha.show(textC, duration), TransSurface(duration, textC, Pivot.CENTER)),
            Parallel(Alpha.show(textD, duration), TransSurface(duration, textD, Pivot.CENTER)),
            TransSurface(duration, textC, Pivot.CENTER),
            TransSurface(duration, textB, Pivot.CENTER),
            TransSurface(duration, textA, Pivot.CENTER)
    )
}