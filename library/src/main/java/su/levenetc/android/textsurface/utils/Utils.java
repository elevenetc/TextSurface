package su.levenetc.android.textsurface.utils;

import android.animation.Animator;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Arrays;

import su.levenetc.android.textsurface.interfaces.IEndListener;
import su.levenetc.android.textsurface.interfaces.ISurfaceAnimation;
import su.levenetc.android.textsurface.utils.AnimatorEndListener;

/**
 * Created by Eugene Levenetc.
 */
public class Utils {

	/**
	 * Generates string of "`" char
	 * Not effective on large sizes
	 */
	public static String genString(int size) {
		char[] array = new char[size];
		Arrays.fill(array, '\'');
		return String.valueOf(array);
	}


	public static <T> T[] concat(T[] first, T[] second) {
		T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

	public static void addEndListener(final ISurfaceAnimation animation, Animator animator, final IEndListener listener) {
		if (listener == null) return;
		animator.addListener(new AnimatorEndListener() {
			@Override public void onAnimationEnd(Animator a) {
				listener.onAnimationEnd(animation);
			}
		});
	}

	public static float dpToPx(float dp) {
		DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
		return dp * (metrics.densityDpi / 160f);
	}
}
