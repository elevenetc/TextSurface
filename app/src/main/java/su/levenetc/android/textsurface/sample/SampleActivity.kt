package su.levenetc.android.textsurface.sample

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import su.levenetc.android.textsurface.Debug
import su.levenetc.android.textsurface.TextSurface
import su.levenetc.android.textsurface.sample.checks.*

/**
 * Created by Eugene Levenetc.
 */
class SampleActivity : AppCompatActivity() {
    private lateinit var textSurface: TextSurface
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sample_activity)
        textSurface = findViewById<View>(R.id.text_surface) as TextSurface
        textSurface!!.postDelayed({ show() }, 1000)
        findViewById<View>(R.id.btn_refresh).setOnClickListener { show() }
        val checkDebug = findViewById<View>(R.id.check_debug) as CheckBox
        checkDebug.isChecked = Debug.ENABLED
        checkDebug.setOnCheckedChangeListener { _, isChecked ->
            Debug.ENABLED = isChecked
            textSurface!!.invalidate()
        }
    }

    private fun show() {
        textSurface.reset()
        CookieThumperSample.play(textSurface, assets)
        //AlignSample.play(textSurface)
        //Rotation3DSample.play(textSurface)
        //ScaleTextSample.play(textSurface)
        //ShapeRevealLoopSample.play(textSurface)
        //ShapeRevealSample.play(textSurface)
        //SlideSample.play(textSurface)
        //SurfaceScaleSample.play(textSurface)
        //SurfaceTransSample.play(textSurface)
    }
}