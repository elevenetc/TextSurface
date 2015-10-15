package su.levenetc.android.textsurface;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.TypedValue;

import su.levenetc.android.textsurface.common.Position;
import su.levenetc.android.textsurface.contants.Pivot;

/**
 * Created by Eugene Levenetc.
 */
public class TextBuilder {

	private String text;
	private int showTime;
	private Position position = new Position();
	private RectF padding = new RectF();
	private Paint paint = new Paint();
	private float scale = 1;
	private int scalePivot = Pivot.CENTER;

	private TextBuilder(String text) {
		this.text = text;
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		paint.setTextSize(110);
		setSize(18);
	}

	public static TextBuilder create(String text) {
		return new TextBuilder(text);
	}

	public TextBuilder setShowTime(int showTime) {
		this.showTime = showTime;
		return this;
	}

	public TextBuilder setPosition(float px, float py) {
		position = new Position(new PointF(px, py));
		return this;
	}

	public TextBuilder setPosition(int align) {
		position = new Position(align);
		return this;
	}

	public TextBuilder setPosition(int align, Text alignText) {
		position = new Position(align, alignText);
		return this;
	}

	/**
	 * params are in pixels
	 */
	public TextBuilder setPadding(float left, float top, float right, float bottom) {
		padding.set(left, top, right, bottom);
		return this;
	}

	/**
	 * params are in pixels
	 */
	public TextBuilder setLeftPadding(float left) {
		padding.set(left, padding.top, padding.right, padding.bottom);
		return this;
	}

	public TextBuilder setPadding(RectF padding) {
		this.padding.set(padding);
		return this;
	}

	public TextBuilder setPosition(Position position) {
		this.position.set(position);
		return this;
	}

	/**
	 * Overrides all previously set paint properties
	 */
	public TextBuilder setPaint(Paint paint) {
		this.paint = new Paint(paint);
		return this;
	}

	/**
	 * @param alpha from 0 to 255
	 */
	public TextBuilder setAlpha(int alpha) {
		this.paint.setAlpha(alpha);
		return this;
	}

	public TextBuilder setColor(int color) {
		this.paint.setColor(color);
		return this;
	}

	public TextBuilder setSize(float sp) {
		paint.setTextSize(
				TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sp, Resources.getSystem().getDisplayMetrics())
		);
		return this;
	}

	public TextBuilder setScale(float scale, int scalePivot) {
		this.scale = scale;
		this.scalePivot = scalePivot;
		return this;
	}

	public Text build() {
		Text result = new Text(this.text, position, padding, paint);
		result.setScaleX(scale);
		result.setScaleY(scale);
		result.setScalePivot(scalePivot, scalePivot);
		return result;
	}

}
