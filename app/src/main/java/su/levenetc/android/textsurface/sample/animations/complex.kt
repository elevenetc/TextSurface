package su.levenetc.android.textsurface.sample.animations

import android.content.res.AssetManager
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.animations.camera.TransSurface
import su.levenetc.android.textsurface.animations.colors.Alpha
import su.levenetc.android.textsurface.animations.colors.ChangeColor
import su.levenetc.android.textsurface.animations.effects.Rotate3D
import su.levenetc.android.textsurface.animations.effects.Slide
import su.levenetc.android.textsurface.animations.effects.reveal.Circle
import su.levenetc.android.textsurface.animations.effects.reveal.ShapeReveal
import su.levenetc.android.textsurface.animations.effects.reveal.SideCut
import su.levenetc.android.textsurface.animations.generic.Delay
import su.levenetc.android.textsurface.animations.sets.Parallel
import su.levenetc.android.textsurface.animations.sets.Sequential
import su.levenetc.android.textsurface.constants.Align
import su.levenetc.android.textsurface.constants.Direction
import su.levenetc.android.textsurface.constants.Pivot
import su.levenetc.android.textsurface.constants.Side

fun playCookieThumper(textSurface: TextSurface, assetManager: AssetManager) {

    val paint = Paint().apply {
        isAntiAlias = true
        typeface = Typeface.createFromAsset(assetManager, "fonts/Roboto-Black.ttf")
    }

    val textDaai = Text.Builder("Daai")
            .setPaint(paint)
            .setSize(64f)
            .setAlpha(0)
            .setColor(Color.WHITE)
            .setPosition(Align.SURFACE_CENTER).build()
    val textBraAnies = Text.Builder("bra Anies")
            .setPaint(paint)
            .setSize(44f)
            .setAlpha(0)
            .setColor(Color.RED)
            .setPosition(Align.BOTTOM_OF, textDaai).build()
    val textFokkenGamBra = Text.Builder(" hy's n fokken gam bra.")
            .setPaint(paint)
            .setSize(44f)
            .setAlpha(0)
            .setColor(Color.RED)
            .setPosition(Align.RIGHT_OF, textBraAnies).build()
    val textHaai = Text.Builder("Haai!!")
            .setPaint(paint)
            .setSize(74f)
            .setAlpha(0)
            .setColor(Color.RED)
            .setPosition(Align.BOTTOM_OF, textFokkenGamBra).build()
    val textDaaiAnies = Text.Builder("Daai Anies")
            .setPaint(paint)
            .setSize(44f)
            .setAlpha(0)
            .setColor(Color.WHITE)
            .setPosition(Align.BOTTOM_OF or Align.CENTER_OF, textHaai).build()
    val texThyLamInnie = Text.Builder(" hy lam innie mang ja.")
            .setPaint(paint)
            .setSize(44f)
            .setAlpha(0)
            .setColor(Color.WHITE)
            .setPosition(Align.RIGHT_OF, textDaaiAnies).build()
    val textThrowDamn = Text.Builder("Throw damn")
            .setPaint(paint)
            .setSize(44f)
            .setAlpha(0)
            .setColor(Color.RED)
            .setPosition(Align.BOTTOM_OF or Align.CENTER_OF, texThyLamInnie).build()
    val textDevilishGang = Text.Builder("devilish gang")
            .setPaint(paint)
            .setSize(44f)
            .setAlpha(0)
            .setColor(Color.RED)
            .setPosition(Align.BOTTOM_OF or Align.CENTER_OF, textThrowDamn).build()
    val textSignsInTheAir = Text.Builder("signs in the air.")
            .setPaint(paint)
            .setSize(44f)
            .setAlpha(0)
            .setColor(Color.RED)
            .setPosition(Align.BOTTOM_OF or Align.CENTER_OF, textDevilishGang).build()
    textSurface.play(
            Sequential(
                    ShapeReveal.reveal(textDaai, 750, SideCut.showSideCut(Side.LEFT)),
                    Parallel(ShapeReveal.reveal(textDaai, 600, SideCut.hideSideCut(Side.LEFT)), Sequential(Delay.delay(300), ShapeReveal.reveal(textDaai, 600, SideCut.showSideCut(Side.LEFT)))),
                    Parallel(TransSurface(500, textBraAnies, Pivot.CENTER), ShapeReveal.reveal(textBraAnies, 1300, SideCut.showSideCut(Side.LEFT))),
                    Delay.delay(500),
                    Parallel(TransSurface(750, textFokkenGamBra, Pivot.CENTER), Slide.showFrom(Side.LEFT, textFokkenGamBra, 750), ChangeColor.to(textFokkenGamBra, 750, Color.WHITE)),
                    Delay.delay(500),
                    Parallel(TransSurface.toCenterOf(textHaai, 500), Rotate3D.showFromSide(textHaai, 750, Pivot.TOP)),
                    Parallel(TransSurface.toCenterOf(textDaaiAnies, 500), Slide.showFrom(Side.TOP, textDaaiAnies, 500)),
                    Parallel(TransSurface.toCenterOf(texThyLamInnie, 750), Slide.showFrom(Side.LEFT, texThyLamInnie, 500)),
                    Delay.delay(500),
                    Parallel(
                            TransSurface(1500, textSignsInTheAir, Pivot.CENTER),
                            Sequential(
                                    Sequential(ShapeReveal.reveal(textThrowDamn, 500, Circle.show(Side.CENTER, Direction.OUT))),
                                    Sequential(ShapeReveal.reveal(textDevilishGang, 500, Circle.show(Side.CENTER, Direction.OUT))),
                                    Sequential(ShapeReveal.reveal(textSignsInTheAir, 500, Circle.show(Side.CENTER, Direction.OUT)))
                            )
                    ),
                    Delay.delay(200),
                    Parallel(
                            ShapeReveal.reveal(textThrowDamn, 1500, SideCut.hideSideCut(Side.LEFT), true),
                            Sequential(Delay.delay(250), ShapeReveal.reveal(textDevilishGang, 1500, SideCut.hideSideCut(Side.LEFT), true)),
                            Sequential(Delay.delay(500), ShapeReveal.reveal(textSignsInTheAir, 1500, SideCut.hideSideCut(Side.LEFT), true)),
                            Alpha.hide(texThyLamInnie, 1500),
                            Alpha.hide(textDaaiAnies, 1500)
                    )
            )
    )
}