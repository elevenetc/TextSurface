package su.levenetc.android.textsurface.animations

import su.levenetc.android.textsurface.contants.TYPE
import su.levenetc.android.textsurface.interfaces.ISurfaceAnimation

/**
 * Created by Eugene Levenetc.
 */
class Parallel(vararg animations: ISurfaceAnimation?) : AnimationsSet(TYPE.PARALLEL, *animations)