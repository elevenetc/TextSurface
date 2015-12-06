package su.levenetc.android.textsurface.animations;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;

import su.levenetc.android.textsurface.contants.Pivot;
import su.levenetc.android.textsurface.SurfaceCamera;
import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextSurface;
import su.levenetc.android.textsurface.utils.Utils;
import su.levenetc.android.textsurface.interfaces.ICameraAnimation;
import su.levenetc.android.textsurface.interfaces.IEndListener;

/**
 * Created by Eugene Levenetc.
 */
public class ScaleSurface implements ICameraAnimation, ValueAnimator.AnimatorUpdateListener {

	private SurfaceCamera camera;
	private TextSurface textSurface;
	private int duration;
	private final Text textPivot;
	private int fit = -1;
	private int pivot;
	private float toScale;
	private ObjectAnimator animator;

	public ScaleSurface(int duration, Text textPivot, int pivot, float toScale) {
		this.duration = duration;
		this.textPivot = textPivot;
		this.pivot = pivot;
		this.toScale = toScale;
	}

	public ScaleSurface(int duration, Text textPivot, int fit) {
		this.duration = duration;
		this.textPivot = textPivot;
		this.fit = fit;
	}

	@Override public void setCamera(SurfaceCamera camera) {
		this.camera = camera;
	}

	@Override public void onStart() {

	}

	@Override public void start(@Nullable IEndListener listener) {

		float pivotX;
		float pivotY;

		if (fit == -1) {
			pivotX = textPivot.getPosition().getRelativeX(pivot, textPivot, true);
			pivotY = textPivot.getPosition().getRelativeY(pivot, textPivot, true);
		} else {
			int surfaceWidth = textSurface.getWidth();
			float textWidth = textPivot.getWidth();
			toScale = surfaceWidth / textWidth;
			pivotX = textPivot.getPosition().getRelativeX(Pivot.CENTER, textPivot, true);
			pivotY = textPivot.getPosition().getRelativeY(Pivot.CENTER, textPivot, true);
		}

		PropertyValuesHolder scaleHolder = PropertyValuesHolder.ofFloat("scale", camera.getScale(), toScale);
		PropertyValuesHolder pivotXHolder = PropertyValuesHolder.ofFloat("scalePivotX", camera.getScalePivotX(), pivotX);
		PropertyValuesHolder pivotYHolder = PropertyValuesHolder.ofFloat("scalePivotY", camera.getScalePivotY(), pivotY);

		animator = ObjectAnimator.ofPropertyValuesHolder(camera, scaleHolder, pivotXHolder, pivotYHolder);
		animator.setInterpolator(new FastOutSlowInInterpolator());
		animator.setDuration(duration);
		animator.addUpdateListener(this);
		Utils.addEndListener(this, animator, listener);
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
		}
	}

	@Override public void onAnimationUpdate(ValueAnimator animation) {
		textSurface.invalidate();
	}

	@Override public String toString() {
		return "ScaleSurface{" +
				"textPivot=" + ((textPivot == null) ? "null" : textPivot.toString()) +
				'}';
	}
}