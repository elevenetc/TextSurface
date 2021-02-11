package su.levenetc.android.textsurface.sample.checks

import su.levenetc.android.textsurface.TextBuilder.Companion.create
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.animations.Alpha.Companion.show
import su.levenetc.android.textsurface.contants.Align
import su.levenetc.android.textsurface.contants.TYPE

/**
 * Created by Eugene Levenetc.
 */
object AlignSample {
    fun play(textSurface: TextSurface) {
        val textCenter = create("Center")
                .setPosition(Align.SURFACE_CENTER)
                .setPadding(25f, 25f, 25f, 25f)
                .build()

        //
        val textLeft = create("L")
                .setPadding(20f, 20f, 20f, 20f)
                .setPosition(Align.LEFT_OF or Align.CENTER_OF, textCenter)
                .build()
        val textRight = create("R")
                .setPadding(20f, 20f, 20f, 20f)
                .setPosition(Align.RIGHT_OF or Align.CENTER_OF, textCenter)
                .build()
        val textTop = create("T")
                .setPosition(Align.TOP_OF or Align.CENTER_OF, textCenter)
                .build()
        val textBottom = create("B")
                .setPosition(Align.BOTTOM_OF or Align.CENTER_OF, textCenter)
                .build()

        //
        val textBottomBottom = create("BB")
                .setPosition(Align.BOTTOM_OF or Align.CENTER_OF, textBottom)
                .build()

        //
        val textLeftTop = create("LT")
                .setPosition(Align.LEFT_OF or Align.TOP_OF, textCenter)
                .build()
        val textRightTop = create("RT")
                .setPosition(Align.RIGHT_OF or Align.TOP_OF, textCenter)
                .build()
        val textLeftBottom = create("LB")
                .setPosition(Align.LEFT_OF or Align.BOTTOM_OF, textCenter)
                .build()
        val textRightBottom = create("RB")
                .setPosition(Align.BOTTOM_OF or Align.RIGHT_OF, textCenter)
                .build()
        val duration = 125
        textSurface.play(TYPE.SEQUENTIAL,
                show(textCenter, duration.toLong()),
                show(textRight, duration.toLong()),
                show(textTop, duration.toLong()),
                show(textLeft, duration.toLong()),
                show(textBottom, duration.toLong()),
                show(textLeftTop, duration.toLong()),
                show(textLeftBottom, duration.toLong()),
                show(textRightBottom, duration.toLong()),
                show(textRightTop, duration.toLong()),
                show(textBottomBottom, duration.toLong())
        )
    }
}