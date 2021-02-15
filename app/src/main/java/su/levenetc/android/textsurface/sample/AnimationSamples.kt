package su.levenetc.android.textsurface.sample

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.TextSurfaceDebug
import su.levenetc.android.textsurface.sample.animations.playCookieThumper

/**
 * Created by Eugene Levenetc.
 */
class AnimationSamples : AppCompatActivity() {

    private lateinit var textSurface: TextSurface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sample_activity)
        textSurface = findViewById<View>(R.id.text_surface) as TextSurface
        textSurface.postDelayed({ show() }, 1000)
        findViewById<View>(R.id.btn_refresh).setOnClickListener { show() }
        val checkDebug = findViewById<View>(R.id.check_debug) as CheckBox
        checkDebug.isChecked = TextSurfaceDebug.ENABLED
        checkDebug.setOnCheckedChangeListener { _, isChecked ->
            TextSurfaceDebug.ENABLED = isChecked
            textSurface.invalidate()
        }
    }

    private fun show() {
        textSurface.reset()
        playCookieThumper(textSurface, assets)
    }
}