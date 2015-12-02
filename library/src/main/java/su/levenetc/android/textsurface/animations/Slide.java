package su.levenetc.android.textsurface.animations;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Region;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;

import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextSurface;
import su.levenetc.android.textsurface.utils.Utils;
import su.levenetc.android.textsurface.contants.Side;
import su.levenetc.android.textsurface.interfaces.IEndListener;
import su.levenetc.android.textsurface.interfaces.ISurfaceAnimation;
import su.levenetc.android.textsurface.interfaces.ITextEffect;

/**
 * Created by Eugene Levenetc.
 */
public class Slide implements ITextEffect, ValueAnimator.AnimatorUpdateListener {

	private Text text;
	private int duration;
	private int side;
	private float xOffset;
	private float yOffset;
	private TextSurface textSurface;
	private boolean toShow;
	private ObjectAnimator animator;

	public static Slide showFrom(int side, Text text, int duration) {
		return new Slide(side, text, duration, true);
	}

	public static Slide hideFrom(int side, Text text, int duration) {
		return new Slide(side, text, duration, false);
	}

	private Slide(int side, Text text, int duration, boolean toShow) {
		this.text = text;
		this.side = side;
		this.duration = duration;
		this.toShow = toShow;
	}

	@Override public void setInitValues(@NonNull Text text) {
		if (toShow) {
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

		text.setAlpha(255);

		PropertyValuesHolder hHolder = null;
		PropertyValuesHolder vHolder = null;
		float fromX = 0;
		float toX = 0;
		float fromY = 0;
		float toY = 0;

		if ((side & Side.LEFT) == side) {

			if (toShow) {
				fromX = -text.getWidth();
			} else {
				toX = -text.getWidth();
			}

			hHolder = PropertyValuesHolder.ofFloat("xOffset", fromX, toX);
		} else if ((side & Side.RIGHT) == side) {

			if (toShow) {
				fromX = text.getWidth();
			} else {
				toX = text.getWidth();
			}

			hHolder = PropertyValuesHolder.ofFloat("xOffset", fromX, toX);
		}

		if ((side & Side.TOP) == side) {

			if (toShow) {
				fromY = -text.getHeight();
			} else {
				toY = -text.getHeight();
			}

			vHolder = PropertyValuesHolder.ofFloat("yOffset", fromY, toY);
		} else if ((side & Side.BOTTOM) == side) {

			if (toShow) {
				fromY = text.getHeight();
			} else {
				toY = text.getHeight();
			}

			vHolder = PropertyValuesHolder.ofFloat("yOffset", fromY, toY);
		}

		if (hHolder != null && vHolder != null) {
			animator = ObjectAnimator.ofPropertyValuesHolder(this, hHolder, vHolder);
		} else if (hHolder != null) {
			animator = ObjectAnimator.ofPropertyValuesHolder(this, hHolder);
		} else {
			animator = ObjectAnimator.ofPropertyValuesHolder(this, vHolder);
		}

		animator.setInterpolator(new FastOutSlowInInterpolator());
		Utils.addEndListener(this, animator, new IEndListener() {
			@Override public void onAnimationEnd(ISurfaceAnimation animation) {
				text.removeEffect(Slide.this);
				if (!toShow) text.setAlpha(0);
				if (listener != null) listener.onAnimationEnd(Slide.this);
			}
		});
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

	@Override public void apply(Canvas canvas, String textValue, float x, float y, Paint paint) {

		float width = this.text.getWidth();
		float height = this.text.getHeight();

		//canvas.save();
		canvas.clipRect(x, y - height, width, 0, Region.Op.REPLACE);
		canvas.translate(xOffset, yOffset - text.getFontDescent());
		//canvas.drawText(textValue, x, y, paint);
		//canvas.restore();
	}

	public void setXOffset(float value) {
		xOffset = value;
	}

	public void setYOffset(float value) {
		yOffset = value;
	}

	@Override public void onAnimationUpdate(ValueAnimator animation) {
		textSurface.invalidate();
	}
}
