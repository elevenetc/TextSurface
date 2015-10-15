package su.levenetc.android.textsurface.common;

import android.graphics.PointF;

import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextSurface;
import su.levenetc.android.textsurface.contants.Align;
import su.levenetc.android.textsurface.contants.Pivot;

/**
 * Created by Eugene Levenetc.
 */
public class Position {

	private int align;
	private Text alignText;
	private PointF point = new PointF();
	private float translationX;
	private float translationY;

	public Position() {
		point = new PointF();
	}

	public Position(Position src) {
		align = src.align;
		alignText = src.alignText;
		point.set(src.point);
		translationX = src.translationX;
		translationY = src.translationY;
	}

	public Position(PointF point) {
		this.point = point;
	}

	public Position(int align) {
		this.align = align;
	}

	public Position(int align, Text alignText) {
		this.align = align;
		this.alignText = alignText;
	}

	public void set(Position src) {
		align = src.align;
		alignText = src.alignText;
		point.set(src.point);
		translationX = src.translationX;
		translationY = src.translationY;
	}

	public PointF getPoint() {
		return point;
	}

	public float getRelativeX(int pivot, Text text, boolean global) {
		float result = 0;

		if ((pivot & Pivot.LEFT) == Pivot.LEFT) {
//			result = point.x;
		} else if ((pivot & Pivot.RIGHT) == Pivot.RIGHT) {
			result += text.getWidth();
		} else if ((pivot & Pivot.CENTER) == Pivot.CENTER) {
			result += text.getWidth() / 2;
		}

		return global ? (result + point.x + translationX) : result;
	}

	public float getRelativeY(int pivot, Text text, boolean global) {
		float result = 0;


		if ((pivot & Pivot.BOTTOM) == Pivot.BOTTOM) {
			//result = text.getY(textSurface);
		} else if ((pivot & Pivot.TOP) == Pivot.TOP) {
			result -= text.getHeight();
		} else if ((pivot & Pivot.CENTER) == Pivot.CENTER) {
			result -= text.getHeight() / 2;
		}

		return global ? (result + point.y + translationY) : result;
	}

	public float getX(TextSurface textSurface, float textWidth) {


		if (isAligned()) {

			if (alignedWith(Align.SURFACE_CENTER)) {
				point.x = -textWidth / 2;
			} else if (alignedWith(Align.RIGHT_OF)) {
				point.x = alignText.getX(textSurface) + alignText.getWidth();
			} else if (alignedWith(Align.LEFT_OF)) {
				point.x = alignText.getX(textSurface) - textWidth;
			} else if (alignedWith(Align.CENTER_OF)) {
				point.x = alignText.getX(textSurface) + (alignText.getWidth() - textWidth) / 2;
			} else {
				point.x = alignText.getX(textSurface);
			}
		}

		return point.x + translationX;
	}

	private boolean alignedWith(int align) {
		return (this.align & align) == align;
	}

	public float getY(TextSurface textSurface, float textHeight) {

		if (isAligned()) {
			if (alignedWith(Align.SURFACE_CENTER)) {
				point.y = textHeight / 2;
			} else if (alignedWith(Align.TOP_OF)) {
				point.y = alignText.getY(textSurface) - alignText.getHeight();
			} else if (alignedWith(Align.BOTTOM_OF)) {
				point.y = alignText.getY(textSurface) + textHeight;
			} else if (alignedWith(Align.CENTER_OF)) {
				point.y = alignText.getY(textSurface) - (alignText.getHeight() - textHeight) / 2;
			} else {
				point.y = alignText.getY(textSurface);
			}
		}

		return point.y + translationY;
	}

	public boolean isAligned() {
		return align != 0;
	}

	public void setY(float y) {
		if (point != null) point.y = y;
	}

	public void setX(float x) {
		if (point != null) point.x = x;
	}

	public void setTranslationX(float x) {
		translationX = x;
	}

	public void setTranslationY(float y) {
		translationY = y;
	}

	public void onAnimationEnd() {

	}
}
