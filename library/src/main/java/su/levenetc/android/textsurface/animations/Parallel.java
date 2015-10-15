package su.levenetc.android.textsurface.animations;

import su.levenetc.android.textsurface.contants.TYPE;
import su.levenetc.android.textsurface.interfaces.ISurfaceAnimation;

/**
 * Created by Eugene Levenetc.
 */
public class Parallel extends AnimationsSet {
	public Parallel(ISurfaceAnimation... animations) {
		super(TYPE.PARALLEL, animations);
	}
}
