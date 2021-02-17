package su.levenetc.android.textsurface.sample.animations

import android.content.res.AssetManager
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.animations.*
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

    val animation = sequential {
        showSideCut(textDaai, 750, Side.LEFT)
        parallel {
            hideSideCut(textDaai, 600, Side.LEFT)
            sequential {
                delay(300)
                showSideCut(textDaai, 600, Side.LEFT)
            }
        }
        parallel {
            showSideCut(textBraAnies, 1300, Side.LEFT)
            transSurface(textBraAnies, 500, Pivot.CENTER)
        }
        delay(500)
        parallel {
            showSliding(textFokkenGamBra, 750, Side.LEFT)
            transSurface(textFokkenGamBra, 750, Pivot.CENTER)
            changeColor(textFokkenGamBra, 750, Color.WHITE)
        }
        delay(500)
        parallel {
            rotate3d(textHaai, 750, Pivot.TOP)
            transSurfaceToCenterOf(textHaai, 500)
        }
        parallel {
            showSliding(textDaaiAnies, 500, Pivot.TOP)
            transSurfaceToCenterOf(textDaaiAnies, 500)
        }
        parallel {
            showSliding(texThyLamInnie, 500, Side.LEFT)
            transSurfaceToCenterOf(texThyLamInnie, 500)
        }
        delay(500)
        parallel {
            transSurface(textSignsInTheAir, 1500, Pivot.CENTER)
            sequential {
                showCircle(textThrowDamn, 500, Side.CENTER, Direction.OUT)
                showCircle(textDevilishGang, 500, Side.CENTER, Direction.OUT)
                showCircle(textSignsInTheAir, 500, Side.CENTER, Direction.OUT)
            }
        }
        delay(200)
        parallel {
            removeSideCut(textThrowDamn, 1500, Side.LEFT)
            sequential {
                delay(250)
                removeSideCut(textDevilishGang, 1500, Side.LEFT)
            }
            sequential {
                delay(500)
                removeSideCut(textSignsInTheAir, 1500, Side.LEFT)
            }
            hide(textHaai, 1500)
            hide(texThyLamInnie, 1500)
            hide(textDaaiAnies, 1500)
        }
    }

    textSurface.play(animation)
}