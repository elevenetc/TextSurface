package su.levenetc.android.textsurface.sample.checks;

import android.graphics.Color;

import su.levenetc.android.textsurface.contants.Align;
import su.levenetc.android.textsurface.contants.TYPE;
import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextBuilder;
import su.levenetc.android.textsurface.TextSurface;
import su.levenetc.android.textsurface.animations.Alpha;
import su.levenetc.android.textsurface.animations.ChangeColor;

/**
 * Created by Eugene Levenetc.
 */
public class ColorSample {
	public static void play(TextSurface textSurface) {

		Text textA = TextBuilder
				.create("Now why you loer en kyk gelyk?")
				.setPosition(Align.SURFACE_CENTER)
				.setSize(100)
				.setAlpha(0)
				.build();

		textSurface.play(TYPE.SEQUENTIAL,
				Alpha.show(textA, 1000),
				ChangeColor.to(textA, 1000, Color.RED),
				ChangeColor.fromTo(textA, 1000, Color.BLUE, Color.CYAN),
				Alpha.hide(textA, 1000)
		);
	}
}
