package su.levenetc.android.textsurface.sample.animations

import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.animations.camera.TransSurface
import su.levenetc.android.textsurface.animations.colors.Alpha
import su.levenetc.android.textsurface.animations.effects.Rotate3D
import su.levenetc.android.textsurface.animations.effects.Slide
import su.levenetc.android.textsurface.animations.effects.reveal.Circle
import su.levenetc.android.textsurface.animations.effects.reveal.ShapeReveal
import su.levenetc.android.textsurface.animations.effects.reveal.SideCut
import su.levenetc.android.textsurface.animations.generic.Delay
import su.levenetc.android.textsurface.animations.sets.Loop
import su.levenetc.android.textsurface.animations.sets.Parallel
import su.levenetc.android.textsurface.animations.sets.Sequential
import su.levenetc.android.textsurface.contants.*

fun shapeRevealSample(textSurface: TextSurface) {
    val textA = Text.Builder("Now why you loer en kyk gelyk?").setPosition(Align.SURFACE_CENTER).build()
    val textB = Text.Builder("Is ek miskien van goud gemake?").setPosition(Align.BOTTOM_OF or Align.CENTER_OF, textA).build()
    val textC = Text.Builder("You want to fight, you come tonight.").setPosition(Align.BOTTOM_OF or Align.CENTER_OF, textB).build()
    val textD = Text.Builder("Ek moer jou sleg! So jy hardloop weg.").setPosition(Align.BOTTOM_OF or Align.CENTER_OF, textC).build()
    val flash = 1500
    textSurface.playSequential(
            Rotate3D.showFromCenter(textA, 500, Axis.X),
            Parallel(
                    ShapeReveal.reveal(textA, flash.toLong(), SideCut.hideSideCut(Side.LEFT), false),
                    Sequential(Delay.delay((flash / 4).toLong()), ShapeReveal.reveal(textA, flash.toLong(), SideCut.showSideCut(Side.LEFT), false))
            ),
            Parallel(
                    Rotate3D.showFromSide(textB, 500, Pivot.TOP),
                    TransSurface(500, textB, Pivot.CENTER)
            ),
            Delay.delay(500),
            Parallel(
                    Slide.showFrom(Side.TOP, textC, 500),
                    TransSurface(1000, textC, Pivot.CENTER)
            ),
            Delay.delay(500),
            Parallel(
                    ShapeReveal.reveal(textD, 500, Circle.show(Side.CENTER, Direction.OUT), false),
                    TransSurface(1500, textD, Pivot.CENTER)
            ),
            Delay.delay(500),
            Parallel(
                    Parallel(Alpha.hide(textD, 700), ShapeReveal.reveal(textD, 1000, SideCut.hideSideCut(Side.LEFT), true)),
                    Sequential(Delay.delay(500), Parallel(Alpha.hide(textC, 700), ShapeReveal.reveal(textC, 1000, SideCut.hideSideCut(Side.LEFT), true))),
                    Sequential(Delay.delay(1000), Parallel(Alpha.hide(textB, 700), ShapeReveal.reveal(textB, 1000, SideCut.hideSideCut(Side.LEFT), true))),
                    Sequential(Delay.delay(1500), Parallel(Alpha.hide(textA, 700), ShapeReveal.reveal(textA, 1000, SideCut.hideSideCut(Side.LEFT), true))),
                    TransSurface(4000, textA, Pivot.CENTER)
            )
    )
}

fun shapeRevealLoopSample(textSurface: TextSurface) {
    val textA = Text.Builder("Now why you loer en kyk gelyk?").setPosition(Align.SURFACE_CENTER).build()
    val textB = Text.Builder("Is ek miskien van goud gemake?").setPosition(Align.BOTTOM_OF or Align.CENTER_OF, textA).build()
    val textC = Text.Builder("You want to fight, you come tonight.").setPosition(Align.BOTTOM_OF or Align.CENTER_OF, textB).build()
    val textD = Text.Builder("Ek moer jou sleg! So jy hardloop weg.").setPosition(Align.BOTTOM_OF or Align.CENTER_OF, textC).build()
    val flash = 1500
    textSurface.play(
            Loop(
                    Rotate3D.showFromCenter(textA, 500, Axis.X),
                    Parallel(
                            ShapeReveal.reveal(textA, flash.toLong(), SideCut.hideSideCut(Side.LEFT), false),
                            Sequential(Delay.delay((flash / 4).toLong()), ShapeReveal.reveal(textA, flash.toLong(), SideCut.showSideCut(Side.LEFT), false))
                    ),
                    Parallel(
                            Rotate3D.showFromSide(textB, 500, Pivot.TOP),
                            TransSurface(500, textB, Pivot.CENTER)
                    ),
                    Delay.delay(500),
                    Parallel(
                            Slide.showFrom(Side.TOP, textC, 500),
                            TransSurface(1000, textC, Pivot.CENTER)
                    ),
                    Delay.delay(500),
                    Parallel(
                            ShapeReveal.reveal(textD, 500, Circle.show(Side.CENTER, Direction.OUT), false),
                            TransSurface(1500, textD, Pivot.CENTER)
                    ),
                    Delay.delay(500),
                    Parallel(
                            Parallel(Alpha.hide(textD, 700), ShapeReveal.reveal(textD, 1000, SideCut.hideSideCut(Side.LEFT), true)),
                            Sequential(Delay.delay(500), Parallel(Alpha.hide(textC, 700), ShapeReveal.reveal(textC, 1000, SideCut.hideSideCut(Side.LEFT), true))),
                            Sequential(Delay.delay(1000), Parallel(Alpha.hide(textB, 700), ShapeReveal.reveal(textB, 1000, SideCut.hideSideCut(Side.LEFT), true))),
                            Sequential(Delay.delay(1500), Parallel(Alpha.hide(textA, 700), ShapeReveal.reveal(textA, 1000, SideCut.hideSideCut(Side.LEFT), true))),
                            TransSurface(4000, textA, Pivot.CENTER)
                    )
            )
    )
}