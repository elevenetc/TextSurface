package su.levenetc.android.textsurface.interfaces;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Eugene Levenetc.
 */
public interface ITextEffect extends ITextSurfaceAnimation {
	void apply(Canvas canvas, String textValue, float x, float y, Paint paint);
}
