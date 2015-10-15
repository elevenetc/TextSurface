package su.levenetc.android.textsurface;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import su.levenetc.android.textsurface.common.Position;
import su.levenetc.android.textsurface.common.ScaleValue;
import su.levenetc.android.textsurface.interfaces.ITextEffect;
import su.levenetc.android.textsurface.utils.Utils;

/**
 * Created by Eugene Levenetc.
 */
public class Text implements Comparable<Text> {

	private Paint paint;
	private final String text;
	private RectF defaultSize;
	private RectF currentSize = new RectF();
	private Position position;
	private RectF padding;
	private int index;
	private ScaleValue scale = new ScaleValue();
	private Matrix matrix = new Matrix();
	private ArrayList<ITextEffect> effects = new ArrayList<>();
	private float dx;
	private float fontDescent;

	public Text(String text, Position position, RectF padding, Paint paint) {
		this.text = text;
		this.position = position;
		this.padding = padding;
		this.paint = paint;

		initBounds(text);
	}

	public void addEffect(ITextEffect effect) {
		effects.add(effect);
	}

	public float getFontDescent() {
		return fontDescent;
	}

	private void initBounds(String text) {
		String trimmed = text.trim();
		if (trimmed.length() < text.length()) {

			int length = text.length();
			int start = text.lastIndexOf(trimmed);
			int end = length - (start + trimmed.length());

			text = Utils.genString(start) + text + Utils.genString(end);
		}

		Rect tmp = new Rect();
		paint.getTextBounds(text, 0, text.length(), tmp);

		fontDescent = paint.getFontMetrics().descent;
		defaultSize = new RectF(tmp);
		//a little workaround because getTextBounds returns smaller width than it is
		dx = paint.measureText(text) - tmp.width();
		defaultSize.left = 0;
		defaultSize.right = tmp.width() + dx;
		defaultSize.top = -paint.getFontSpacing();
		defaultSize.bottom = 0;

		defaultSize.set(
				defaultSize.left,
				defaultSize.top,
				defaultSize.right,
				defaultSize.bottom
		);

		currentSize.set(
				defaultSize.left,
				defaultSize.top,
				defaultSize.right,
				defaultSize.bottom
		);
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void onDraw(Canvas canvas, TextSurface textSurface) {

		layout(textSurface);

		canvas.save();
		canvas.concat(matrix);

		final float finalX = padding.left;

		if (effects.isEmpty()) {
			canvas.drawText(text, finalX, -padding.bottom - fontDescent, paint);
		} else {
			for (ITextEffect effect : effects) {
				canvas.save();
				effect.apply(canvas, text, finalX, -padding.bottom, paint);
				canvas.drawText(text, finalX, -padding.bottom, paint);
				canvas.restore();
			}

		}

		canvas.restore();

		if (Debug.ENABLED) {
			canvas.drawRect(
					currentSize.left,
					currentSize.top - padding.bottom - padding.top,
					currentSize.right + padding.left + padding.right,
					currentSize.bottom,
					Debug.RED_STROKE
			);
		}
	}

	void layout(TextSurface textSurface) {

		currentSize.set(defaultSize.left, defaultSize.top, defaultSize.right, defaultSize.bottom);

		final float sx = scale.getScaleX();
		final float sy = scale.getScaleY();
		final float sPivotX = position.getRelativeX((int) scale.getPivot().x, this, false);
		final float sPivotY = position.getRelativeY((int) scale.getPivot().y, this, false);
		final float x = position.getX(textSurface, getWidth() * sx);
		final float y = position.getY(textSurface, getHeight() * sy);

		matrix.reset();
		matrix.preTranslate(x, y);
		matrix.preScale(sx, sy, sPivotX, sPivotY);
		matrix.mapRect(currentSize);
	}

	public float getY(TextSurface textSurface) {
		return position.getY(textSurface, getHeight());
	}

	public float getX(TextSurface textSurface) {
		return position.getX(textSurface, getWidth());
	}

	public void setAlpha(int alpha) {
		paint.setAlpha(alpha);
	}

	public RectF bounds() {
		return defaultSize;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public void setScaleX(float value) {
		scale.setValueX(value);
	}

	public void setScaleY(float value) {
		scale.setValueY(value);
	}

	public void setScalePivot(float x, float y) {
		scale.getPivot().set(x, y);
	}

	public float getScaleY() {
		return scale.getScaleY();
	}

	public void setTranslationX(float value) {
		position.setTranslationX(value);
	}

	public void setTranslationY(float value) {
		position.setTranslationY(value);
	}

	@Override public int compareTo(@NonNull Text another) {
		return text.compareTo(another.text);
	}

	public float getWidth() {
		return (currentSize.width() + padding.left + padding.right);
	}

	public float getHeight() {
		return (currentSize.height() + padding.top + padding.bottom);
	}

	public Position getPosition() {
		return position;
	}

	public void onAnimationEnd() {
		position.onAnimationEnd();
	}

	public float getScaleX() {
		return scale.getScaleX();
	}

	@Override public String toString() {
		return "Text{" +
				"text='" + text + '\'' +
				'}';
	}

	public void removeEffect(ITextEffect effect) {
		effects.remove(effect);
	}

	public String getValue() {
		return text;
	}

	public Paint getPaint() {
		return paint;
	}
}
