package com.zay.mm_graph.line_graph

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.zay.mm_graph.BaseMMGraph
import com.zay.mm_graph.painter.MMLineGraphDrawer
import com.zay.mm_graph.points.MMPoints
import com.zay.mm_graph.points.PointsAndColor
import java.util.*
import kotlin.collections.ArrayList

class MMLineGraph(context: Context, attributeSet: AttributeSet) :
    BaseMMGraph(context, attributeSet) {
    private var xMax = 0
    private var xStep = 0
    private var yMax = 0
    private var yStep = 0
    var stepForYWhenLoop = 0
    var stepForXWhenLoop = 0
    var distanceX = 20
    var distanceY = height / 2
    private var lengthOfY = 0
    private var mmLinePoint = ArrayList<PointsAndColor>()

    private val lineDrawer by lazy { MMLineGraphDrawer() }

    fun addPoint(points: MMPoints, color: Int = Color.GREEN) {
        mmLinePoint.add(PointsAndColor(points, color))

    }

    fun rangeToX(x: Int, step: Int) {
        xMax = x
        xStep = step
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.d("onSize change", "change")
        Log.d("xmax", "$xMax")
        Log.d("total width ", "${w}")
        Log.d("sub distant", "${(w - 80) / (xMax / 10)}")
        distanceY = (oldh / 2)
        lineDrawer.setLineGraphFrame(
            LineGraphFrame(
                80f,
                (h / 2).toFloat(),
                ((w - 80) / ((xMax / xStep))).toFloat(),
                ((h / 2) / ((yMax / yStep))).toFloat()
            )
        )
    }

    fun rangeToY(y: Int, step: Int) {
        yMax = y
        yStep = step
    }

    val axisLabelPaint = Paint().apply {
        color = Color.BLACK
        textSize = 24f
    }

    val xyLinePaint = Paint().apply {
        strokeWidth = 4f
        isAntiAlias = true
    }

    val axisLinePaint = Paint().apply {
        color = Color.GRAY
    }

    val dottedLinePaint = Paint().apply {
        color = Color.GRAY
        strokeWidth = 1f
    }

    fun execute() {
        stepForYWhenLoop = distanceY / yStep
        stepForXWhenLoop = distanceX / xStep
        invalidate()
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //draw Y axis
        var startY = 0
        var countY = 0

        val endOfy = yMax / yStep
        stepForYWhenLoop = (height / 2) / (endOfy)
        val startOfy = height / 2
        distanceY = startOfy


        while (countY <= endOfy) {
            Log.d("y", startY.toString())
            canvas?.drawLine(50f, startOfy.toFloat(), 50f, 0f, axisLinePaint)
            canvas?.drawText("$startY ", 20f, distanceY.toFloat(), axisLabelPaint)
            countY++
            startY += yStep
            distanceY -= stepForYWhenLoop
        }

        var startX = 0
        var countX = 0
        val endOfx = (xMax / xStep)
        stepForXWhenLoop = (width - 80) / endOfx
        var startOfX = 70f
        while (countX <= endOfx) {
            canvas?.drawLine(
                50f,
                startOfy.toFloat(),
                width.toFloat(),
                startOfy.toFloat(),
                axisLinePaint
            )
            canvas?.drawText("$startX", startOfX, (20 + startOfy).toFloat(), axisLabelPaint)
            countX++
            startX += xStep
            startOfX += stepForXWhenLoop
        }

        canvas?.drawXYPoints()
        canvas?.drawDotttedPoint()

    }

    private fun Canvas.drawXYPoints() {
        mmLinePoint.forEach { pointAndColor ->
            lineDrawer.paintLinePoints(this, pointAndColor.listOfMMPoints, xyLinePaint.apply {
                color = pointAndColor.color
            }, xStep, yStep)
            Log.d("points", "${pointAndColor.listOfMMPoints.listOfPoints}")

        }
    }

    private fun Canvas.drawDotttedPoint() {
        lineDrawer.paintDottedPoints(this, yStep, dottedLinePaint)
    }
}