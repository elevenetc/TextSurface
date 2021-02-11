package su.levenetc.android.textsurface.animations

import su.levenetc.android.textsurface.contants.TYPE
import su.levenetc.android.textsurface.interfaces.ISurfaceAnimation

/**
 * Created by Eugene Levenetc.
 */
open class Sequential(vararg animations: ISurfaceAnimation) : AnimationsSet(TYPE.SEQUENTIAL, *animations)