package su.levenetc.android.textsurface.sample.checks;

import android.graphics.Color;

import su.levenetc.android.textsurface.Align;
import su.levenetc.android.textsurface.TYPE;
import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextBuilder;
import su.levenetc.android.textsurface.TextSurface;
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
				.build();

		textSurface.play(TYPE.SEQUENTIAL,
				ChangeColor.to(textA, 1000, Color.RED),
				ChangeColor.fromTo(textA, 1000, Color.BLUE, Color.CYAN)
		);
	}
}
