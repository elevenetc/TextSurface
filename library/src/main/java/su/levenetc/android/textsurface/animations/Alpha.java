package su.levenetc.android.textsurface.animations;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.annotation.Nullable;

import su.levenetc.android.textsurface.utils.AnimatorEndListener;
import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.interfaces.IEndListener;

/**
 * Created by Eugene Levenetc.
 */
public class Alpha extends AbstractSurfaceAnimation {

	private int to;
	private int from;
	private ValueAnimator animator;

	public static Alpha hide(Text text, int duration) {
		return new Alpha(text, duration, 255, 0);
	}

	public static Alpha show(Text text, int duration) {
		return new Alpha(text, duration, 0, 255);
	}

	public Alpha(Text text, int duration, int from, int to) {
		super(text, duration);
		this.from = from;
		this.to = to;
	}

	@Override public void start(@Nullable final IEndListener listener) {
		animator = ValueAnimator.ofInt(from, to);
		animator.setDuration(duration);
		animator.addUpdateListener(this);

		animator.addListener(new AnimatorEndListener() {
			@Override public void onAnimationEnd(Animator animation) {
				if (listener != null) listener.onAnimationEnd(Alpha.this);
			}
		});

		animator.start();
	}

	@Override public void onAnimationUpdate(ValueAnimator animation) {
		super.onAnimationUpdate(animation);
		text.setAlpha((int) animation.getAnimatedValue());
	}

	@Override public void cancel() {
		if (animator != null && animator.isRunning()) {
			animator.cancel();
			animator = null;
		}
	}
}
