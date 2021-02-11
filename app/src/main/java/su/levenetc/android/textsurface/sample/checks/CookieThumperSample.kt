package su.levenetc.android.textsurface.sample.checks

import android.content.res.AssetManager
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import su.levenetc.android.textsurface.TextBuilder.Companion.create
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.animations.Alpha.Companion.hide
import su.levenetc.android.textsurface.animations.ChangeColor.Companion.to
import su.levenetc.android.textsurface.animations.reveal.Circle.Companion.show
import su.levenetc.android.textsurface.animations.Delay.Companion.duration
import su.levenetc.android.textsurface.animations.Parallel
import su.levenetc.android.textsurface.animations.Rotate3D.Companion.showFromSide
import su.levenetc.android.textsurface.animations.Sequential
import su.levenetc.android.textsurface.animations.reveal.ShapeReveal.Companion.create
import su.levenetc.android.textsurface.animations.reveal.SideCut.Companion.hide
import su.levenetc.android.textsurface.animations.reveal.SideCut.Companion.show
import su.levenetc.android.textsurface.animations.Slide.Companion.showFrom
import su.levenetc.android.textsurface.animations.TransSurface
import su.levenetc.android.textsurface.animations.TransSurface.Companion.toCenter
import su.levenetc.android.textsurface.contants.Align
import su.levenetc.android.textsurface.contants.Direction
import su.levenetc.android.textsurface.contants.Pivot
import su.levenetc.android.textsurface.contants.Side

/**
 * Created by Eugene Levenetc.
 */
object CookieThumperSample {
    fun play(textSurface: TextSurface?, assetManager: AssetManager?) {
        val robotoBlack = Typeface.createFromAsset(assetManager, "fonts/Roboto-Black.ttf")
        val paint = Paint()
        paint.isAntiAlias = true
        paint.typeface = robotoBlack
        val textDaai = create("Daai")
                .setPaint(paint)
                .setSize(64f)
                .setAlpha(0)
                .setColor(Color.WHITE)
                .setPosition(Align.SURFACE_CENTER).build()
        val textBraAnies = create("bra Anies")
                .setPaint(paint)
                .setSize(44f)
                .setAlpha(0)
                .setColor(Color.RED)
                .setPosition(Align.BOTTOM_OF, textDaai).build()
        val textFokkenGamBra = create(" hy's n fokken gam bra.")
                .setPaint(paint)
                .setSize(44f)
                .setAlpha(0)
                .setColor(Color.RED)
                .setPosition(Align.RIGHT_OF, textBraAnies).build()
        val textHaai = create("Haai!!")
                .setPaint(paint)
                .setSize(74f)
                .setAlpha(0)
                .setColor(Color.RED)
                .setPosition(Align.BOTTOM_OF, textFokkenGamBra).build()
        val textDaaiAnies = create("Daai Anies")
                .setPaint(paint)
                .setSize(44f)
                .setAlpha(0)
                .setColor(Color.WHITE)
                .setPosition(Align.BOTTOM_OF or Align.CENTER_OF, textHaai).build()
        val texThyLamInnie = create(" hy lam innie mang ja.")
                .setPaint(paint)
                .setSize(44f)
                .setAlpha(0)
                .setColor(Color.WHITE)
                .setPosition(Align.RIGHT_OF, textDaaiAnies).build()
        val textThrowDamn = create("Throw damn")
                .setPaint(paint)
                .setSize(44f)
                .setAlpha(0)
                .setColor(Color.RED)
                .setPosition(Align.BOTTOM_OF or Align.CENTER_OF, texThyLamInnie).build()
        val textDevilishGang = create("devilish gang")
                .setPaint(paint)
                .setSize(44f)
                .setAlpha(0)
                .setColor(Color.RED)
                .setPosition(Align.BOTTOM_OF or Align.CENTER_OF, textThrowDamn).build()
        val textSignsInTheAir = create("signs in the air.")
                .setPaint(paint)
                .setSize(44f)
                .setAlpha(0)
                .setColor(Color.RED)
                .setPosition(Align.BOTTOM_OF or Align.CENTER_OF, textDevilishGang).build()
        textSurface!!.play(
                Sequential(
                        create(textDaai, 750, show(Side.LEFT), false),
                        Parallel(create(textDaai, 600, hide(Side.LEFT), false), Sequential(duration(300), create(textDaai, 600, show(Side.LEFT), false))),
                        Parallel(TransSurface(500, textBraAnies, Pivot.CENTER), create(textBraAnies, 1300, show(Side.LEFT), false)),
                        duration(500),
                        Parallel(TransSurface(750, textFokkenGamBra, Pivot.CENTER), showFrom(Side.LEFT, textFokkenGamBra, 750), to(textFokkenGamBra, 750, Color.WHITE)),
                        duration(500),
                        Parallel(toCenter(textHaai, 500), showFromSide(textHaai, 750, Pivot.TOP)),
                        Parallel(toCenter(textDaaiAnies, 500), showFrom(Side.TOP, textDaaiAnies, 500)),
                        Parallel(toCenter(texThyLamInnie, 750), showFrom(Side.LEFT, texThyLamInnie, 500)),
                        duration(500),
                        Parallel(
                                TransSurface(1500, textSignsInTheAir, Pivot.CENTER),
                                Sequential(
                                        Sequential(create(textThrowDamn, 500, show(Side.CENTER, Direction.OUT), false)),
                                        Sequential(create(textDevilishGang, 500, show(Side.CENTER, Direction.OUT), false)),
                                        Sequential(create(textSignsInTheAir, 500, show(Side.CENTER, Direction.OUT), false))
                                )
                        ),
                        duration(200),
                        Parallel(
                                create(textThrowDamn, 1500, hide(Side.LEFT), true),
                                Sequential(duration(250), create(textDevilishGang, 1500, hide(Side.LEFT), true)),
                                Sequential(duration(500), create(textSignsInTheAir, 1500, hide(Side.LEFT), true)),
                                hide(texThyLamInnie, 1500),
                                hide(textDaaiAnies, 1500)
                        )
                )
        )
    }
}