package su.levenetc.android.textsurface.animations

import su.levenetc.android.textsurface.Text
import su.levenetc.android.textsurface.animations.camera.TransSurface
import su.levenetc.android.textsurface.animations.colors.Alpha
import su.levenetc.android.textsurface.animations.colors.ChangeColor
import su.levenetc.android.textsurface.animations.effects.Rotate3D.Companion.showFromSide
import su.levenetc.android.textsurface.animations.effects.Slide.Companion.showFrom
import su.levenetc.android.textsurface.animations.effects.reveal.Circle
import su.levenetc.android.textsurface.animations.effects.reveal.ShapeReveal.Companion.reveal
import su.levenetc.android.textsurface.animations.effects.reveal.SideCut
import su.levenetc.android.textsurface.animations.generic.Delay
import su.levenetc.android.textsurface.animations.sets.AnimationsSet
import su.levenetc.android.textsurface.animations.sets.Loop
import su.levenetc.android.textsurface.animations.sets.Parallel
import su.levenetc.android.textsurface.animations.sets.Sequential

fun sequential(init: Sequential.() -> Unit) = initRootSet(Sequential(), init)
fun parallel(init: Parallel.() -> Unit) = initRootSet(Parallel(), init)
fun loop(init: Loop.() -> Unit) = initRootSet(Loop(), init)

fun AnimationsSet.parallel(init: Parallel.() -> Unit): Parallel = initSet(Parallel(), init)
fun AnimationsSet.sequential(init: Sequential.() -> Unit): Sequential = initSet(Sequential(), init)
fun AnimationsSet.loop(init: Loop.() -> Unit): Loop = initSet(Loop(), init)

fun AnimationsSet.hide(text: Text, duration: Long): SurfaceAnimation = initAnimation(Alpha.hide(text, duration))
fun AnimationsSet.show(text: Text, duration: Long): SurfaceAnimation = initAnimation(Alpha.show(text, duration))
fun AnimationsSet.showCircle(text: Text, duration: Long, side: Int, direction: Int): SurfaceAnimation = initAnimation(reveal(text, duration, Circle.show(side, direction)))
fun AnimationsSet.showSideCut(text: Text, duration: Long, side: Int): SurfaceAnimation = initAnimation(reveal(text, duration, SideCut.showSideCut(side)))
fun AnimationsSet.removeSideCut(text: Text, duration: Long, side: Int): SurfaceAnimation = initAnimation(reveal(text, duration, SideCut.hideSideCut(side), true))
fun AnimationsSet.hideSideCut(text: Text, duration: Long, side: Int): SurfaceAnimation = initAnimation(reveal(text, duration, SideCut.hideSideCut(side), false))
fun AnimationsSet.showSliding(text: Text, duration: Long, side: Int): SurfaceAnimation = initAnimation(showFrom(side, text, duration))
fun AnimationsSet.rotate3d(text: Text, duration: Long, pivot: Int): SurfaceAnimation = initAnimation(showFromSide(text, duration, pivot))
fun AnimationsSet.transSurface(text: Text, duration: Long, pivot: Int): SurfaceAnimation = initAnimation(TransSurface(duration, text, pivot))
fun AnimationsSet.transSurfaceToCenterOf(text: Text, duration: Long): SurfaceAnimation = initAnimation(TransSurface.toCenterOf(text, duration))
fun AnimationsSet.changeColor(text: Text, duration: Long, color: Int): SurfaceAnimation = initAnimation(ChangeColor.to(text, duration, color))
fun AnimationsSet.delay(duration: Long): SurfaceAnimation = initAnimation(Delay(duration))

internal fun <T : SurfaceAnimation> AnimationsSet.initAnimation(animation: T): T {
    animations.add(animation)
    return animation
}

internal fun <T : AnimationsSet> initRootSet(animation: T, init: T.() -> Unit): T {
    animation.init()
    return animation
}

internal fun <T : AnimationsSet> AnimationsSet.initSet(animation: T, init: T.() -> Unit): T {
    animation.init()
    animations.add(animation)
    return animation
}