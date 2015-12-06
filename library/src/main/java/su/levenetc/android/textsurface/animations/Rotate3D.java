package su.levenetc.android.textsurface.animations;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;

import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextSurface;
import su.levenetc.android.textsurface.utils.Utils;
import su.levenetc.android.textsurface.contants.Axis;
import su.levenetc.android.textsurface.contants.Pivot;
import su.levenetc.android.textsurface.interfaces.IEndListener;
import su.levenetc.android.textsurface.interfaces.ISurfaceAnimation;
import su.levenetc.android.textsurface.interfaces.ITextEffect;

/**
 * Created by Eugene Levenetc.
 */
public class Rotate3D implements ITextEffect, ValueAnimator.AnimatorUpdateListener {

	private Text text;
	private int duration;
	private int pivot;
	protected final Camera camera = new Camera();
	protected final Matrix cameraMatrix = new Matrix();
	private float rotationX;
	private float rotationY;
	private TextSurface textSurface;
	private float cameraTransXPre;
	private float cameraTransYPre;
	private float cameraTransXPost;
	private float cameraTransYPost;
	private int direction;
	private int axis;
	private boolean show;
	private ObjectAnimator animator;

	public static Rotate3D showFromSide(Text text, int duration, int pivot) {
		return new Rotate3D(text, duration, pivot, 0, 0, true);
	}

	public static Rotate3D showFromCenter(Text text, int duration, int direction, int axis) {
		return new Rotate3D(text, duration, Pivot.CENTER, direction, axis, true);
	}

	public static Rotate3D hideFromSide(Text text, int duration, int pivot) {
		return new Rotate3D(text, duration, pivot, 0, 0, false);
	}

	public static Rotate3D hideFromCenter(Text text, int duration, int direction, int axis) {
		return new Rotate3D(text, duration, Pivot.CENTER, direction, axis, false);
	}

	private Rotate3D(
			Text text,
			int duration,
			int pivot,
			int direction,
			int axis,
			boolean show
	) {
		this.text = text;
		this.duration = duration;
		this.pivot = pivot;
		this.direction = direction;
		this.axis = axis;
		this.show = show;
	}

	@Override public void apply(Canvas canvas, String textValue, float x, float y, Paint paint) {

		camera.getMatrix(cameraMatrix);
		camera.save();
		camera.rotateX(rotationX);
		camera.rotateY(rotationY);
		camera.getMatrix(cameraMatrix);

		cameraMatrix.preTranslate(cameraTransXPre, cameraTransYPre);
		cameraMatrix.postTranslate(cameraTransXPost, cameraTransYPost);

		camera.restore();

		canvas.concat(cameraMatrix);
	}

	@Override public void setInitValues(@NonNull Text text) {
		if (show) {
			text.setAlpha(0);
		}
	}

	@NonNull @Override public Text getText() {
		return text;
	}

	@Override public void onStart() {
		text.addEffect(this);
	}

	@Override public void start(@Nullable final IEndListener listener) {


		PropertyValuesHolder valHolder = null;

		int fromDegree;
		int toDegree;

		text.setAlpha(255);

		if (show) {
			fromDegree = 90;
			toDegree = 0;
		} else {
			fromDegree = 0;
			toDegree = -90;
		}

		if ((pivot & Pivot.BOTTOM) == Pivot.BOTTOM) {

			valHolder = PropertyValuesHolder.ofFloat("rotationX", fromDegree, toDegree);
			cameraTransXPre = -text.getWidth() / 2;
			cameraTransXPost = text.getWidth() / 2;
			cameraTransYPre = -text.getFontDescent();
			cameraTransYPost = 0;
		} else if ((pivot & Pivot.TOP) == Pivot.TOP) {
			valHolder = PropertyValuesHolder.ofFloat("rotationX", -fromDegree, toDegree);
			cameraTransXPre = -text.getWidth() / 2;
			cameraTransXPost = text.getWidth() / 2;
			cameraTransYPre = text.getHeight() - text.getFontDescent();
			cameraTransYPost = -text.getHeight();
		}

		if ((pivot & Pivot.LEFT) == Pivot.LEFT) {
			valHolder = PropertyValuesHolder.ofFloat("rotationY", fromDegree, toDegree);
			cameraTransXPre = 0;
			cameraTransXPost = 0;
			cameraTransYPre = text.getHeight() / 2 - text.getFontDescent();
			cameraTransYPost = text.getHeight() / 2 - text.getHeight();
		} else if ((pivot & Pivot.RIGHT) == Pivot.RIGHT) {
			valHolder = PropertyValuesHolder.ofFloat("rotationY", -fromDegree, toDegree);
			cameraTransXPre = -text.getWidth();
			cameraTransXPost = text.getWidth();
			cameraTransYPre = text.getHeight() / 2 - text.getFontDescent();
			cameraTransYPost = text.getHeight() / 2 - text.getHeight();
		}

		if ((pivot & Pivot.CENTER) == Pivot.CENTER) {
			valHolder = PropertyValuesHolder.ofFloat(axis == Axis.Y ? "rotationY" : "rotationX", fromDegree, toDegree);
			cameraTransXPre = -text.getWidth() / 2;
			cameraTransXPost = text.getWidth() / 2;
			cameraTransYPre = text.getHeight() / 2 - text.getFontDescent();
			cameraTransYPost = text.getHeight() / 2 - text.getHeight();
		}

		if (valHolder != null) {
			animator = ObjectAnimator.ofPropertyValuesHolder(this, valHolder);
			animator.setInterpolator(new FastOutSlowInInterpolator());
			Utils.addEndListener(this, animator, new IEndListener() {
				@Override public void onAnimationEnd(ISurfaceAnimation animation) {
					text.removeEffect(Rotate3D.this);
					if (!show) text.setAlpha(0);
					if (listener != null) listener.onAnimationEnd(Rotate3D.this);
				}
			});
			animator.setDuration(duration);
			animator.addUpdateListener(this);
			animator.start();

		} else {
			throw new RuntimeException(getClass().getSuperclass() + " was not configured properly. Pivot:" + pivot);
		}

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

	public void setRotationX(float rotationX) {
		this.rotationX = rotationX;
	}

	public void setRotationY(float rotationY) {
		this.rotationY = rotationY;
	}

	@Override public void onAnimationUpdate(ValueAnimator animation) {
		textSurface.invalidate();
	}
}
