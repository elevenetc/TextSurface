package su.levenetc.android.textsurface.sample.checks;

import su.levenetc.android.textsurface.contants.Align;
import su.levenetc.android.textsurface.contants.TYPE;
import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextBuilder;
import su.levenetc.android.textsurface.TextSurface;
import su.levenetc.android.textsurface.animations.Alpha;

/**
 * Created by Eugene Levenetc.
 */
public class AlignSample {
	public static void play(TextSurface textSurface) {

		Text textCenter = TextBuilder.create("Center")
				.setPosition(Align.SURFACE_CENTER)
				.setPadding(25, 25, 25, 25)
				.build();

		//

		Text textLeft = TextBuilder.create("L")
				.setPadding(20, 20, 20, 20)
				.setPosition(Align.LEFT_OF | Align.CENTER_OF, textCenter)
				.build();

		Text textRight = TextBuilder.create("R")
				.setPadding(20, 20, 20, 20)
				.setPosition(Align.RIGHT_OF | Align.CENTER_OF, textCenter)
				.build();

		Text textTop = TextBuilder.create("T")
				.setPosition(Align.TOP_OF | Align.CENTER_OF, textCenter)
				.build();

		Text textBottom = TextBuilder.create("B")
				.setPosition(Align.BOTTOM_OF | Align.CENTER_OF, textCenter)
				.build();

		//

		Text textBottomBottom = TextBuilder.create("BB")
				.setPosition(Align.BOTTOM_OF | Align.CENTER_OF, textBottom)
				.build();

		//

		Text textLeftTop = TextBuilder.create("LT")
				.setPosition(Align.LEFT_OF | Align.TOP_OF, textCenter)
				.build();

		Text textRightTop = TextBuilder.create("RT")
				.setPosition(Align.RIGHT_OF | Align.TOP_OF, textCenter)
				.build();

		Text textLeftBottom = TextBuilder.create("LB")
				.setPosition(Align.LEFT_OF | Align.BOTTOM_OF, textCenter)
				.build();

		Text textRightBottom = TextBuilder.create("RB")
				.setPosition(Align.BOTTOM_OF | Align.RIGHT_OF, textCenter)
				.build();

		final int duration = 125;

		textSurface.play(TYPE.SEQUENTIAL,
				Alpha.show(textCenter, duration),
				Alpha.show(textRight, duration),
				Alpha.show(textTop, duration),
				Alpha.show(textLeft, duration),
				Alpha.show(textBottom, duration),

				Alpha.show(textLeftTop, duration),
				Alpha.show(textLeftBottom, duration),
				Alpha.show(textRightBottom, duration),
				Alpha.show(textRightTop, duration),

				Alpha.show(textBottomBottom, duration)
		);
	}
}
