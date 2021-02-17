package su.levenetc.android.textsurface.sample.animations

import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.animations.generic.Just
import su.levenetc.android.textsurface.animations.generic.Scale
import su.levenetc.android.textsurface.animations.sets.Sequential
import su.levenetc.android.textsurface.constants.Align
import su.levenetc.android.textsurface.constants.Pivot

fun scaleTextSample(textSurface: TextSurface) {
    val textA = Text.Builder("textA")
            .build()
    val textB = Text.Builder("textB")
            .setPosition(Align.LEFT_OF, textA)
            .build()
    val textC = Text.Builder("textC")
            .setPosition(Align.RIGHT_OF, textA)
            .build()
    val textD = Text.Builder("textD")
            .setPosition(Align.LEFT_OF, textB)
            .build()
    val textE = Text.Builder("textE")
            .setPosition(Align.RIGHT_OF, textC)
            .build()
    textSurface.playParallel(
            Just.show(textA, textB),
            Sequential(Scale(textA, 1000, 1f, 2f, Pivot.CENTER), Scale(textA, 1000, 2f, 1f, Pivot.CENTER)) //				new Parallel(new Scale(textA, 500, 1.5f, 1f, Pivot.LEFT), new Scale(textA, 500, 1, 1.5f, Pivot.LEFT)),
            //				new Sequential(Delay.duration(250), new Parallel(new Scale(textB, 500, 1.5f, 1f, Pivot.LEFT), new Scale(textB, 500, 1, 1.5f, Pivot.LEFT))),
            //				new Sequential(Delay.duration(500), new Parallel(new Scale(textC, 500, 1.5f, 1f, Pivot.LEFT), new Scale(textC, 500, 1, 1.5f, Pivot.LEFT))),
            //				new Sequential(Delay.duration(750), new Parallel(new Scale(textD, 500, 1.5f, 1f, Pivot.LEFT), new Scale(textD, 500, 1, 1.5f, Pivot.LEFT)))
    )
}