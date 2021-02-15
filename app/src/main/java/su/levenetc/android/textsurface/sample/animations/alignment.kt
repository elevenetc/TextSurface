package su.levenetc.android.textsurface.sample.animations

import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.animations.colors.Alpha
import su.levenetc.android.textsurface.constants.Align

fun alignSample(textSurface: TextSurface) {
    val textCenter = Text.Builder("Center")
            .setPosition(Align.SURFACE_CENTER)
            .setPadding(25f, 25f, 25f, 25f)
            .build()

    //
    val textLeft = Text.Builder("L")
            .setPadding(20f, 20f, 20f, 20f)
            .setPosition(Align.LEFT_OF or Align.CENTER_OF, textCenter)
            .build()
    val textRight = Text.Builder("R")
            .setPadding(20f, 20f, 20f, 20f)
            .setPosition(Align.RIGHT_OF or Align.CENTER_OF, textCenter)
            .build()
    val textTop = Text.Builder("T")
            .setPosition(Align.TOP_OF or Align.CENTER_OF, textCenter)
            .build()
    val textBottom = Text.Builder("B")
            .setPosition(Align.BOTTOM_OF or Align.CENTER_OF, textCenter)
            .build()

    //
    val textBottomBottom = Text.Builder("BB")
            .setPosition(Align.BOTTOM_OF or Align.CENTER_OF, textBottom)
            .build()

    //
    val textLeftTop = Text.Builder("LT")
            .setPosition(Align.LEFT_OF or Align.TOP_OF, textCenter)
            .build()
    val textRightTop = Text.Builder("RT")
            .setPosition(Align.RIGHT_OF or Align.TOP_OF, textCenter)
            .build()
    val textLeftBottom = Text.Builder("LB")
            .setPosition(Align.LEFT_OF or Align.BOTTOM_OF, textCenter)
            .build()
    val textRightBottom = Text.Builder("RB")
            .setPosition(Align.BOTTOM_OF or Align.RIGHT_OF, textCenter)
            .build()
    val duration = 125
    textSurface.playSequential(
            Alpha.show(textCenter, duration.toLong()),
            Alpha.show(textRight, duration.toLong()),
            Alpha.show(textTop, duration.toLong()),
            Alpha.show(textLeft, duration.toLong()),
            Alpha.show(textBottom, duration.toLong()),
            Alpha.show(textLeftTop, duration.toLong()),
            Alpha.show(textLeftBottom, duration.toLong()),
            Alpha.show(textRightBottom, duration.toLong()),
            Alpha.show(textRightTop, duration.toLong()),
            Alpha.show(textBottomBottom, duration.toLong())
    )
}