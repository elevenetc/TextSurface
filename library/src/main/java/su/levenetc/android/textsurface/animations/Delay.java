package su.levenetc.android.textsurface.animations;

import android.support.annotation.Nullable;

import su.levenetc.android.textsurface.interfaces.IEndListener;

/**
 * Created by Eugene Levenetc.
 */
public class Delay extends AbstractSurfaceAnimation {

	public static Delay duration(int duration) {
		return new Delay(duration);
	}

	private Runnable action = null;

	public Delay(int duration) {
		super(null, duration);
	}

	@Override public void start(@Nullable final IEndListener listener) {
		action = new Runnable() {
			@Override public void run() {
				if (listener != null) listener.onAnimationEnd(Delay.this);
			}
		};
		textSurface.postDelayed(action, duration);
	}

	@Override public void cancel() {
		if (action != null) {
			textSurface.removeCallbacks(action);
			action = null;
		}
	}

	@Override public String toString() {
		return "Delay{" +
				"duration=" + duration +
				'}';
	}
}
