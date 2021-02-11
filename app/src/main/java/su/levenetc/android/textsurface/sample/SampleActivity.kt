package su.levenetc.android.textsurface.sample;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import su.levenetc.android.textsurface.Debug;
import su.levenetc.android.textsurface.TextSurface;
import su.levenetc.android.textsurface.sample.checks.CookieThumperSample;

/**
 * Created by Eugene Levenetc.
 */
public class SampleActivity extends AppCompatActivity {

	private TextSurface textSurface;

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
		CookieThumperSample.play(textSurface, getAssets());
	}

}