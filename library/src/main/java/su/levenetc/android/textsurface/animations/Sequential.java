package su.levenetc.android.textsurface.animations;

import su.levenetc.android.textsurface.contants.TYPE;
import su.levenetc.android.textsurface.interfaces.ISurfaceAnimation;

/**
 * Created by Eugene Levenetc.
 */
public class Sequential extends AnimationsSet {
	public Sequential(ISurfaceAnimation... animations) {
		super(TYPE.SEQUENTIAL, animations);
	}
}
