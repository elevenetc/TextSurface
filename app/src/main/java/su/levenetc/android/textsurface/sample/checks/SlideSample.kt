package su.levenetc.android.textsurface.sample.checks

import su.levenetc.android.textsurface.TextBuilder.Companion.create
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.animations.AnimationsSet
import su.levenetc.android.textsurface.animations.Slide.Companion.hideFrom
import su.levenetc.android.textsurface.animations.Slide.Companion.showFrom
import su.levenetc.android.textsurface.animations.TransSurface
import su.levenetc.android.textsurface.contants.*

/**
 * Created by Eugene Levenetc.
 */
object SlideSample {
    fun play(textSurface: TextSurface) {
        val textA = create(" How are you?").build()
        val textB = create("I'm fine! ").setPosition(Align.LEFT_OF, textA).build()
        val textC = create("Are you sure?").setPosition(Align.BOTTOM_OF or Align.CENTER_OF, textB).build()
        val textD = create("Totally!").setPadding(10f, 10f, 10f, 10f).setPosition(Align.BOTTOM_OF or Align.CENTER_OF, textC).build()
        val duration = 1250L
        textSurface.play(
                TYPE.SEQUENTIAL,
                AnimationsSet(TYPE.PARALLEL,
                        AnimationsSet(TYPE.SEQUENTIAL,
                                AnimationsSet(TYPE.PARALLEL, showFrom(Side.LEFT, textA, duration.toLong()), showFrom(Side.RIGHT, textB, duration.toLong())),
                                showFrom(Side.TOP, textC, duration.toLong()),
                                showFrom(Side.BOTTOM, textD, duration.toLong())
                        ),
                        TransSurface(duration * 3, textD, Pivot.CENTER)
                ),
                AnimationsSet(TYPE.PARALLEL,
                        AnimationsSet(TYPE.SEQUENTIAL,
                                AnimationsSet(TYPE.PARALLEL, hideFrom(Side.LEFT, textD, duration.toLong()), hideFrom(Side.RIGHT, textC, duration.toLong())),
                                hideFrom(Side.TOP, textB, duration.toLong()),
                                hideFrom(Side.BOTTOM, textA, duration.toLong())
                        ),
                        TransSurface(duration * 3, textA, Pivot.CENTER)
                )
        )
    }
}