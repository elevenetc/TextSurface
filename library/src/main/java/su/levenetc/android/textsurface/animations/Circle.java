package su.levenetc.android.textsurface.animations;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;

import su.levenetc.android.textsurface.Debug;
import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextSurface;
import su.levenetc.android.textsurface.contants.Direction;
import su.levenetc.android.textsurface.contants.Side;

/**
 * Created by Eugene Levenetc.
 */
public class Circle implements ShapeReveal.IRevealShape, ValueAnimator.AnimatorUpdateListener {

	private float radius;
	private Text text;
	private Path clipPath = new Path();
	private ValueAnimator animator;
	private TextSurface textSurface;
	private final boolean toShow;
	private final int side;
	private int direction;
	private float circX;
	private float circY;

	/**
	 * @param side      {@link Side}
	 * @param direction {@link Direction} Direction.IN or Direction.OUT
	 */
	public static Circle show(int side, int direction) {
		return new Circle(true, side, direction);
	}

	/**
	 * @param side      {@link Side}
	 * @param direction {@link Direction} Direction.IN or Direction.OUT
	 */
	public static Circle hide(int side, int direction) {
		return new Circle(false, side, direction);
	}

	private Circle(boolean toShow, int side, int direction) {
		this.toShow = toShow;
		this.side = side;
		this.direction = direction;
	}

	public void setText(Text text) {
		this.text = text;
	}

	@Override public void setTextSurface(TextSurface textSurface) {
		this.textSurface = textSurface;
	}

	public ValueAnimator getAnimator() {

		final float width = text.getWidth();
		final float height = text.getHeight();
		final float textX = text.getX(textSurface);
		final float textY = text.getY(textSurface);

		float toRad = 0;
		float fromRad = 0;
		float cathA, cathB;

		if (side == Side.CENTER) {
			this.circX = width / 2;
			this.circY = -(height / 2);
			cathA = width / 2;
			cathB = height / 2;
		} else {

			if ((side & Side.LEFT) == Side.LEFT) {
				this.circX = 0;
			} else if ((side & Side.RIGHT) == Side.RIGHT) {
				this.circX = width;
			}

			if ((side & Side.TOP) == Side.TOP) {
				this.circY = -height;
			} else if ((side & Side.BOTTOM) == Side.BOTTOM) {
				this.circY = 0;
			}

			cathA = width;
			cathB = height;

		}

		final float hypo = (float) Math.sqrt(cathA * cathA + cathB * cathB);

		if (toShow) {
			if (direction == Direction.OUT) {
				toRad = hypo;
			} else {
				clipOp = Region.Op.DIFFERENCE;
				fromRad = hypo;
			}
		} else {
			if (direction == Direction.OUT) {
				clipOp = Region.Op.DIFFERENCE;
				toRad = hypo;
			} else {
				fromRad = hypo;
			}
		}

		animator = ValueAnimator.ofFloat(fromRad, toRad);
		animator.addUpdateListener(this);
		return animator;
	}

	@Override public void clip(Canvas canvas, String textValue, float x, float y, Paint paint) {

		clipPath.reset();
		clipPath.addCircle(circX, circY, radius, Path.Direction.CCW);

		if (Debug.ENABLED) {
			canvas.drawPath(clipPath, Debug.BLUE_STROKE);
			canvas.drawCircle(circX, circY, 10, Debug.RED_FILL);
		}

		canvas.translate(0, -(text.getFontDescent()));
		canvas.clipPath(clipPath, clipOp);
	}

	private Region.Op clipOp = Region.Op.INTERSECT;

	@Override public boolean isToShow() {
		return toShow;
	}

	@Override public void onAnimationUpdate(ValueAnimator animation) {
		radius = (float) animation.getAnimatedValue();
		textSurface.invalidate();
	}
}
