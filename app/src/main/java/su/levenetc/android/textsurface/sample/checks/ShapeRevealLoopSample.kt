package su.levenetc.android.textsurface.sample.checks

import su.levenetc.android.textsurface.TextBuilder.Companion.create
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.animations.Alpha.Companion.hide
import su.levenetc.android.textsurface.animations.AnimationsSet
import su.levenetc.android.textsurface.animations.reveal.Circle.Companion.show
import su.levenetc.android.textsurface.animations.Delay.Companion.duration
import su.levenetc.android.textsurface.animations.Loop
import su.levenetc.android.textsurface.animations.Rotate3D.Companion.showFromCenter
import su.levenetc.android.textsurface.animations.Rotate3D.Companion.showFromSide
import su.levenetc.android.textsurface.animations.reveal.ShapeReveal.Companion.create
import su.levenetc.android.textsurface.animations.reveal.SideCut.Companion.hide
import su.levenetc.android.textsurface.animations.reveal.SideCut.Companion.show
import su.levenetc.android.textsurface.animations.Slide.Companion.showFrom
import su.levenetc.android.textsurface.animations.TransSurface
import su.levenetc.android.textsurface.contants.*

/**
 * Created by Eugene Levenetc.
 */
object ShapeRevealLoopSample {
    fun play(textSurface: TextSurface) {
        val textA = create("Now why you loer en kyk gelyk?").setPosition(Align.SURFACE_CENTER).build()
        val textB = create("Is ek miskien van goud gemake?").setPosition(Align.BOTTOM_OF or Align.CENTER_OF, textA).build()
        val textC = create("You want to fight, you come tonight.").setPosition(Align.BOTTOM_OF or Align.CENTER_OF, textB).build()
        val textD = create("Ek moer jou sleg! So jy hardloop weg.").setPosition(Align.BOTTOM_OF or Align.CENTER_OF, textC).build()
        val flash = 1500
        textSurface.play(
                Loop(
                        showFromCenter(textA, 500, Direction.CLOCK, Axis.X),
                        AnimationsSet(TYPE.PARALLEL,
                                create(textA, flash.toLong(), hide(Side.LEFT), false),
                                AnimationsSet(TYPE.SEQUENTIAL, duration((flash / 4).toLong()), create(textA, flash.toLong(), show(Side.LEFT), false))
                        ),
                        AnimationsSet(TYPE.PARALLEL,
                                showFromSide(textB, 500, Pivot.TOP),
                                TransSurface(500, textB, Pivot.CENTER)
                        ),
                        duration(500),
                        AnimationsSet(TYPE.PARALLEL,
                                showFrom(Side.TOP, textC, 500),
                                TransSurface(1000, textC, Pivot.CENTER)
                        ),
                        duration(500),
                        AnimationsSet(TYPE.PARALLEL,
                                create(textD, 500, show(Side.CENTER, Direction.OUT), false),
                                TransSurface(1500, textD, Pivot.CENTER)
                        ),
                        duration(500),
                        AnimationsSet(TYPE.PARALLEL,
                                AnimationsSet(TYPE.PARALLEL, hide(textD, 700), create(textD, 1000, hide(Side.LEFT), true)),
                                AnimationsSet(TYPE.SEQUENTIAL, duration(500), AnimationsSet(TYPE.PARALLEL, hide(textC, 700), create(textC, 1000, hide(Side.LEFT), true))),
                                AnimationsSet(TYPE.SEQUENTIAL, duration(1000), AnimationsSet(TYPE.PARALLEL, hide(textB, 700), create(textB, 1000, hide(Side.LEFT), true))),
                                AnimationsSet(TYPE.SEQUENTIAL, duration(1500), AnimationsSet(TYPE.PARALLEL, hide(textA, 700), create(textA, 1000, hide(Side.LEFT), true))),
                                TransSurface(4000, textA, Pivot.CENTER)
                        )
                )
        )
    }
}