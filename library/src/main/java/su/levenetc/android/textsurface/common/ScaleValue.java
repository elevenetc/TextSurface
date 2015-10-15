package su.levenetc.android.textsurface.common;

import android.graphics.PointF;

/**
 * Created by Eugene Levenetc.
 */
public class ScaleValue {

	private PointF scale = new PointF(1, 1);
	private PointF pivot = new PointF();

	public void setValue(float scale) {
		this.scale.set(scale, scale);
	}

	public void setValueX(float x) {
		scale.set(x, scale.y);
	}

	public void setValueY(float y) {
		scale.set(scale.x, y);
	}

	public float getScaleX() {
		return scale.x;
	}

	public float getScaleY() {
		return scale.y;
	}

	public PointF getPivot() {
		return pivot;
	}

	public void reset() {
		scale.set(1, 1);
		pivot.set(0, 0);
	}
}
