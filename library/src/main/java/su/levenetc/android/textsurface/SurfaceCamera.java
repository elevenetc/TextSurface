package su.levenetc.android.textsurface;

import android.graphics.Canvas;
import android.graphics.PointF;

import su.levenetc.android.textsurface.common.ScaleValue;

/**
 * Created by Eugene Levenetc.
 */
public class SurfaceCamera {

	private float transX;
	private float transY;
	private float rotation;
	private PointF rotationPivot = new PointF();
	private ScaleValue scale = new ScaleValue();
	private PointF center = new PointF();

	public void onDraw(Canvas canvas) {

		if (Debug.ENABLED) {
			canvas.save();
			canvas.drawCircle(transX, transY, 10, Debug.YELLOW_STROKE);
			canvas.drawCircle(scale.getPivot().x, scale.getPivot().y, 10, Debug.GREEN_STROKE);
			canvas.drawLine(scale.getPivot().x, 0, scale.getPivot().x, canvas.getHeight(), Debug.GREEN_STROKE);
			canvas.drawLine(0, scale.getPivot().y, canvas.getWidth(), scale.getPivot().y, Debug.GREEN_STROKE);

			canvas.drawLine(canvas.getWidth() / 2, 0, canvas.getWidth() / 2, canvas.getHeight(), Debug.GREEN_STROKE);
			canvas.drawLine(0, canvas.getHeight() / 2, canvas.getWidth(), canvas.getHeight() / 2, Debug.GREEN_STROKE);

			canvas.restore();
		}

		center.set(canvas.getWidth() / 2, canvas.getHeight() / 2);

		canvas.translate(center.x + transX, center.y + transY);
		canvas.scale(scale.getScaleX(), scale.getScaleX(), scale.getPivot().x, scale.getPivot().y);
	}

	public void reset() {
		rotationPivot.set(0, 0);
		scale.reset();
		center.set(0, 0);
		transX = 0;
		transY = 0;
	}

	public float getCenterX() {
		return center.x;
	}

	public float getCenterY() {
		return center.y;
	}

	public void setTransX(float transX) {
		this.transX = transX;
	}

	public void setTransY(float transY) {
		this.transY = transY;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public PointF getRotationPivot() {
		return rotationPivot;
	}

	public float getRotation() {
		return rotation;
	}

	public float getTransY() {
		return transY;
	}

	public float getTransX() {
		return transX;
	}

	public float getScale() {
		return scale.getScaleX();
	}

	public void setScale(float scale) {
		this.scale.setValue(scale);
	}

	public void setScalePivot(float x, float y) {
		scale.getPivot().set(x, y);
	}

	public float getScalePivotX() {
		return scale.getPivot().x;
	}

	public float getScalePivotY() {
		return scale.getPivot().y;
	}

	public void setScalePivotX(float value) {
		scale.getPivot().x = value;
	}

	public void setScalePivotY(float value) {
		scale.getPivot().y = value;
	}
}