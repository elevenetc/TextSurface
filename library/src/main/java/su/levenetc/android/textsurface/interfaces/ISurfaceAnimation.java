package su.levenetc.android.textsurface.interfaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import su.levenetc.android.textsurface.TextSurface;

/**
 * Created by Eugene Levenetc.
 */
public interface ISurfaceAnimation {

	void onStart();

	void start(@Nullable IEndListener listener);

	void setTextSurface(@NonNull TextSurface textSurface);

	long getDuration();

	void cancel();

}
