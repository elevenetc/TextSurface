package su.levenetc.android.textsurface.sample.checks

import android.graphics.Color
import su.levenetc.android.textsurface.TextBuilder.Companion.create
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.animations.Alpha.Companion.hide
import su.levenetc.android.textsurface.animations.Alpha.Companion.show
import su.levenetc.android.textsurface.animations.ChangeColor.Companion.fromTo
import su.levenetc.android.textsurface.animations.ChangeColor.Companion.to
import su.levenetc.android.textsurface.contants.Align
import su.levenetc.android.textsurface.contants.TYPE

/**
 * Created by Eugene Levenetc.
 */
object ColorSample {
    fun play(textSurface: TextSurface) {
        val textA = create("Now why you loer en kyk gelyk?")
                .setPosition(Align.SURFACE_CENTER)
                .setSize(100f)
                .setAlpha(0)
                .build()
        textSurface.play(TYPE.SEQUENTIAL,
                show(textA, 1000),
                to(textA, 1000, Color.RED),
                fromTo(textA, 1000, Color.BLUE, Color.CYAN),
                hide(textA, 1000)
        )
    }
}