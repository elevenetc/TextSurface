package su.levenetc.android.textsurface.animations;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextSurface;
import su.levenetc.android.textsurface.utils.Utils;
import su.levenetc.android.textsurface.interfaces.IEndListener;
import su.levenetc.android.textsurface.interfaces.ITextSurfaceAnimation;

/**
 * Created by Eugene Levenetc.
 */
public class Scale implements ITextSurfaceAnimation, ValueAnimator.AnimatorUpdateListener {

	private int duration;
	private final float from;
	private final float to;
	private final int pivot;
	private Text text;
	private TextSurface textSurface;
	private ObjectAnimator animator;

	public Scale(Text text, int duration, float from, float to, int pivot) {
		this.text = text;
		this.duration = duration;
		this.from = from;
		this.to = to;
		this.pivot = pivot;
	}

	@Override public void setInitValues(@NonNull Text text) {

	}

	@NonNull @Override public Text getText() {
		return text;
	}

	@Override public void onStart() {

	}

	@Override public void start(@Nullable IEndListener listener) {
		text.setScalePivot(pivot, pivot);
		PropertyValuesHolder sX = PropertyValuesHolder.ofFloat("scaleX", from, to);
		PropertyValuesHolder sY = PropertyValuesHolder.ofFloat("scaleY", from, to);
		animator = ObjectAnimator.ofPropertyValuesHolder(text, sX, sY);
		Utils.addEndListener(this, animator, listener);
		animator.setDuration(duration);
		animator.addUpdateListener(this);
		animator.start();
	}

	@Override public void setTextSurface(@NonNull TextSurface textSurface) {
		this.textSurface = textSurface;
	}

	@Override public long getDuration() {
		return duration;
	}

	@Override public void cancel() {
		if (animator != null && animator.isRunning()) {
			animator.cancel();
			animator = null;
		}
	}

	@Override public void onAnimationUpdate(ValueAnimator animation) {
		textSurface.invalidate();
	}

	@Override public String toString() {
		return "Scale{" +
				"text=" + text +
				'}';
	}
}
