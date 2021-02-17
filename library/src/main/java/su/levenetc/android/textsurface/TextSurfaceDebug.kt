package su.levenetc.android.textsurface

import android.graphics.Color
import android.graphics.Paint

/**
 * Created by Eugene Levenetc.
 */
object TextSurfaceDebug {

    var ENABLED = false

    var RED_FILL: Paint = Paint()
    var RED_STROKE: Paint = Paint()

    var BLUE_STROKE: Paint = Paint()
    var YELLOW_STROKE: Paint = Paint()
    var GREEN_STROKE: Paint = Paint()

    init {
        RED_FILL.color = Color.RED
        RED_FILL.style = Paint.Style.FILL
        RED_FILL.isAntiAlias = true
        BLUE_STROKE.color = Color.BLUE
        BLUE_STROKE.style = Paint.Style.STROKE
        BLUE_STROKE.isAntiAlias = true
        BLUE_STROKE.strokeWidth = 1f
        YELLOW_STROKE.color = Color.YELLOW
        YELLOW_STROKE.style = Paint.Style.STROKE
        YELLOW_STROKE.isAntiAlias = true
        GREEN_STROKE.color = Color.GREEN
        GREEN_STROKE.style = Paint.Style.STROKE
        GREEN_STROKE.isAntiAlias = true
        RED_STROKE.color = Color.RED
        RED_STROKE.style = Paint.Style.STROKE
        RED_STROKE.isAntiAlias = true
    }
}