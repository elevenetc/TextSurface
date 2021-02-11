package su.levenetc.android.textsurface.sample.checks

import su.levenetc.android.textsurface.TextBuilder.Companion.create
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.animations.Just
import su.levenetc.android.textsurface.animations.Scale
import su.levenetc.android.textsurface.animations.Sequential
import su.levenetc.android.textsurface.contants.Align
import su.levenetc.android.textsurface.contants.Pivot
import su.levenetc.android.textsurface.contants.TYPE

/**
 * Created by Eugene Levenetc.
 */
object ScaleTextSample {
    fun play(textSurface: TextSurface) {
//		Text textA = TextBuilder.create("oat cake")
////				.setScale(2.0f, Pivot.RIGHT)
//				.build();
//
//		textSurface.play(TYPE.SEQUENTIAL,
//				//Just.show(textA)
//				new Scale(textA, 1000, 0.1f, 1.5f, Pivot.RIGHT)
//		);
        val textA = create("textA") //				.setPosition(Align.SURFACE_CENTER)
                .build()
        val textB = create("textB")
                .setPosition(Align.LEFT_OF, textA)
                .build()
        val textC = create("textC")
                .setPosition(Align.RIGHT_OF, textA)
                .build()
        val textD = create("textD")
                .setPosition(Align.LEFT_OF, textB)
                .build()
        val textE = create("textE")
                .setPosition(Align.RIGHT_OF, textC)
                .build()
        textSurface.play(TYPE.PARALLEL,
                Just.show(textA, textB),
                Sequential(Scale(textA, 1000, 1f, 2f, Pivot.CENTER), Scale(textA, 1000, 2f, 1f, Pivot.CENTER)) //				new Parallel(new Scale(textA, 500, 1.5f, 1f, Pivot.LEFT), new Scale(textA, 500, 1, 1.5f, Pivot.LEFT)),
                //				new Sequential(Delay.duration(250), new Parallel(new Scale(textB, 500, 1.5f, 1f, Pivot.LEFT), new Scale(textB, 500, 1, 1.5f, Pivot.LEFT))),
                //				new Sequential(Delay.duration(500), new Parallel(new Scale(textC, 500, 1.5f, 1f, Pivot.LEFT), new Scale(textC, 500, 1, 1.5f, Pivot.LEFT))),
                //				new Sequential(Delay.duration(750), new Parallel(new Scale(textD, 500, 1.5f, 1f, Pivot.LEFT), new Scale(textD, 500, 1, 1.5f, Pivot.LEFT)))
        )
    }
}