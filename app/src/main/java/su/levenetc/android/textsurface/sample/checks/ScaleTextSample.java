package su.levenetc.android.textsurface.sample.checks;

import su.levenetc.android.textsurface.contants.Align;
import su.levenetc.android.textsurface.animations.Sequential;
import su.levenetc.android.textsurface.contants.TYPE;
import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextBuilder;
import su.levenetc.android.textsurface.TextSurface;
import su.levenetc.android.textsurface.animations.Just;
import su.levenetc.android.textsurface.animations.Scale;
import su.levenetc.android.textsurface.contants.Pivot;

/**
 * Created by Eugene Levenetc.
 */
public class ScaleTextSample {
	public static void run(TextSurface textSurface) {
//		Text textA = TextBuilder.create("oat cake")
////				.setScale(2.0f, Pivot.RIGHT)
//				.build();
//
//		textSurface.play(TYPE.SEQUENTIAL,
//				//Just.show(textA)
//				new Scale(textA, 1000, 0.1f, 1.5f, Pivot.RIGHT)
//		);

		Text textA = TextBuilder.create("textA")
//				.setPosition(Align.SURFACE_CENTER)
				.build();

		Text textB = TextBuilder.create("textB")
				.setPosition(Align.LEFT_OF, textA)
				.build();

		Text textC = TextBuilder.create("textC")
				.setPosition(Align.RIGHT_OF, textA)
				.build();

		Text textD = TextBuilder.create("textD")
				.setPosition(Align.LEFT_OF, textB)
				.build();

		Text textE = TextBuilder.create("textE")
				.setPosition(Align.RIGHT_OF, textC)
				.build();

		textSurface.play(TYPE.PARALLEL,
				Just.show(textA, textB),
				new Sequential(new Scale(textA, 1000, 1, 2, Pivot.CENTER), new Scale(textA, 1000, 2, 1, Pivot.CENTER))
//				new Parallel(new Scale(textA, 500, 1.5f, 1f, Pivot.LEFT), new Scale(textA, 500, 1, 1.5f, Pivot.LEFT)),
//				new Sequential(Delay.duration(250), new Parallel(new Scale(textB, 500, 1.5f, 1f, Pivot.LEFT), new Scale(textB, 500, 1, 1.5f, Pivot.LEFT))),
//				new Sequential(Delay.duration(500), new Parallel(new Scale(textC, 500, 1.5f, 1f, Pivot.LEFT), new Scale(textC, 500, 1, 1.5f, Pivot.LEFT))),
//				new Sequential(Delay.duration(750), new Parallel(new Scale(textD, 500, 1.5f, 1f, Pivot.LEFT), new Scale(textD, 500, 1, 1.5f, Pivot.LEFT)))
		);
	}
}
