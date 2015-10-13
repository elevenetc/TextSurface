package su.levenetc.android.textsurface.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import su.levenetc.android.textsurface.Debug;
import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextSurface;
import su.levenetc.android.textsurface.sample.checks.AlignSample;
import su.levenetc.android.textsurface.sample.checks.CheckRotation3D;
import su.levenetc.android.textsurface.sample.checks.CheckShapeReveal;
import su.levenetc.android.textsurface.sample.checks.ColorSample;
import su.levenetc.android.textsurface.sample.checks.ScaleTextSample;
import su.levenetc.android.textsurface.sample.checks.SlideCheck;
import su.levenetc.android.textsurface.sample.checks.SurfaceScaleCheck;
import su.levenetc.android.textsurface.sample.checks.SurfaceTransCheck;

/**
 * Created by Eugene Levenetc.
 */
public class SampleActivity extends AppCompatActivity {


	private TextSurface textSurface;
	private Text text1;
	private Text text2;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sample_activity);
		textSurface = (TextSurface) findViewById(R.id.text_surface);

		textSurface.postDelayed(new Runnable() {
			@Override public void run() {
				show();
			}
		}, 1000);

		findViewById(R.id.btn_refresh).setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				show();
			}
		});

		CheckBox checkDebug = (CheckBox) findViewById(R.id.check_debug);
		checkDebug.setChecked(Debug.ENABLED);
		checkDebug.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Debug.ENABLED = isChecked;
				textSurface.invalidate();
			}
		});
	}

	private void show() {

		textSurface.reset();

//		AlignSample.play(textSurface);
//		SurfaceTransCheck.play(textSurface);
//		SurfaceScaleCheck.play(textSurface);
//		SlideCheck.play(textSurface);
//		CheckRotation3D.play(textSurface);
//		CheckShapeReveal.play(textSurface);
		ColorSample.play(textSurface);
	}

}