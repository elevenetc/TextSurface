package su.levenetc.android.textsurface.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.sample.animations.surfaceTransSample

class ScrollViewActivity : AppCompatActivity(R.layout.scrollview_activity) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textSurface: TextSurface = findViewById(R.id.text_surface)
        surfaceTransSample(textSurface)
    }
}