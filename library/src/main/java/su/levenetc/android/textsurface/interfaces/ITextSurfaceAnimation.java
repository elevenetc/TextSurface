package su.levenetc.android.textsurface.interfaces;

import android.support.annotation.NonNull;

import su.levenetc.android.textsurface.Text;

/**
 * Created by Eugene Levenetc.
 */
public interface ITextSurfaceAnimation extends ISurfaceAnimation {

	void setInitValues(@NonNull Text text);

	Text getText();

}