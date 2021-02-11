package su.levenetc.android.textsurface.interfaces

import su.levenetc.android.textsurface.contants.TYPE
import java.util.*

/**
 * Created by Eugene Levenetc.
 */
interface ISet : ISurfaceAnimation {
    val type: TYPE
    val animations: LinkedList<ISurfaceAnimation>
}