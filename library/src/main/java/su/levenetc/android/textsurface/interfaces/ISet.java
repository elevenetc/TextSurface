package su.levenetc.android.textsurface.interfaces;

import java.util.LinkedList;

import su.levenetc.android.textsurface.TYPE;

/**
 * Created by Eugene Levenetc.
 */
public interface ISet extends ISurfaceAnimation {
	TYPE getType();

	LinkedList<ISurfaceAnimation> getAnimations();
}
