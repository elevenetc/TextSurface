package su.levenetc.android.textsurface.sample

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class LauncherActivity : AppCompatActivity(R.layout.launcher_activity) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<View>(R.id.btn_animations_samples).setOnClickListener {
            startActivity(Intent(this, AnimationSamples::class.java))
        }

        findViewById<View>(R.id.btn_scrollview).setOnClickListener {
            startActivity(Intent(this, ScrollViewActivity::class.java))
        }
    }
}