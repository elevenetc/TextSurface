package su.levenetc.android.textsurface.animations;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import su.levenetc.android.textsurface.utils.AnimatorEndListener;
import su.levenetc.android.textsurface.interfaces.ICameraAnimation;
import su.levenetc.android.textsurface.interfaces.IEndListener;
import su.levenetc.android.textsurface.contants.Pivot;
import su.levenetc.android.textsurface.SurfaceCamera;
import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextSurface;

/**
 * Created by Eugene Levenetc.
 */
public class CamRot implements ICameraAnimation, ValueAnimator.AnimatorUpdateListener {

	public float rotationDelta;
	private float pivotX;
	private float pivotY;
	private int duration;
	private TextSurface textSurface;
	private SurfaceCamera camera;
	private Text textPivot;
	private int pivotAlign;
	private ObjectAnimator animator;

	public CamRot(int duration, float rotationDelta) {
		this.duration = duration;
		this.rotationDelta = rotationDelta;
	}

	public CamRot(int duration, float rotationDelta, float pivotX, float pivotY) {
		this.duration = duration;
		this.rotationDelta = rotationDelta;
		this.pivotX = pivotX;
		this.pivotY = pivotY;
	}

	public CamRot(int duration, float rotationDelta, Text textPivot, int pivotAlign) {
		this.duration = duration;
		this.rotationDelta = rotationDelta;
		this.textPivot = textPivot;
		this.pivotAlign = pivotAlign;
	}

	@Override public void onStart() {

	}

	@Override public void start(@Nullable final IEndListener listener) {

		setPivot();
		float from = camera.getRotation();
		float to = camera.getRotation() + rotationDelta;
		animator = ObjectAnimator.ofFloat(camera, "rotation", from, to);
		animator.setDuration(duration);
		animator.addUpdateListener(this);
		animator.addListener(new AnimatorEndListener() {
			@Override public void onAnimationEnd(Animator animation) {
				if (listener != null) listener.onAnimationEnd(CamRot.this);
			}
		});

		animator.start();
	}

	private PointF tmpPoint = new PointF();

	private void setPivot() {

		PointF rotationPivot = camera.getRotationPivot();

		if (textPivot != null) {
			if ((pivotAlign & Pivot.LEFT) == Pivot.LEFT) {
				pivotX = textPivot.getX(textSurface);
			} else if ((pivotAlign & Pivot.RIGHT) == Pivot.RIGHT) {
				pivotX = textPivot.getX(textSurface) + textPivot.getWidth();
			}

			if ((pivotAlign & Pivot.BOTTOM) == Pivot.BOTTOM) {
				pivotY = textPivot.getY(textSurface);
			} else if ((pivotAlign & Pivot.TOP) == Pivot.TOP) {
				pivotY = textPivot.getY(textSurface) - textPivot.getHeight();
			}

			if ((pivotAlign & Pivot.CENTER) == Pivot.CENTER) {
				pivotX = textPivot.getX(textSurface) + textPivot.getWidth() / 2;
				pivotY = textPivot.getY(textSurface) - textPivot.getHeight() / 2;
			}

		}

		tmpPoint.set(pivotX, pivotY);
//		rotatePoint(tmpPoint, camera.getRotationPivot(), camera.getRotation());
		rotatePoint2(tmpPoint, camera.getRotationPivot(), camera.getRotation());
		rotationPivot.set(tmpPoint.x, tmpPoint.y);
	}

	private static void rotatePoint2(PointF point, PointF center, float angle) {

		float x = angle;
		float centerX = center.x;
		float centerY = center.y;
		float point2x = point.x;
		float point2y = point.y;

		double newX = centerX + (point2x - centerX) * Math.cos(x) - (point2y - centerY) * Math.sin(x);
		double newY = centerY + (point2x - centerX) * Math.sin(x) + (point2y - centerY) * Math.cos(x);
		point.set((float) newX, (float) newY);
	}

	private static void rotatePoint(PointF point, PointF center, float angle) {

		if (angle == 0) return;

		double x1 = point.x - center.x;
		double y1 = point.y - center.y;

		//APPLY ROTATION
		double xTemp = x1;
		x1 = x1 * Math.cos(angle) - y1 * Math.sin(angle);
		y1 = xTemp * Math.sin(angle) + y1 * Math.cos(angle);

		//TRANSLATE BACK
		point.x = (int) Math.ceil(x1) + center.x;
		point.y = (int) Math.ceil(y1) + center.y;
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

	@Override public void setCamera(SurfaceCamera camera) {
		this.camera = camera;
	}
}
