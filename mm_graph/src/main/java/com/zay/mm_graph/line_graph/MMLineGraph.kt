package com.zay.mm_graph.line_graph

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.zay.mm_graph.BaseMMGraph
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

    fun rangeToX(x: Int, step: Int) {
        xMax = x
        xStep = step
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        distanceY = (oldh / 2)
    }

    fun rangeToY(y: Int, step: Int) {
        yMax = y
        yStep = step
    }

    val axisLabelPaint = Paint().apply {
        color = Color.BLACK
        textSize = 24f
    }

    val axisLinePaint = Paint().apply {
        color = Color.GRAY
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
        stepForYWhenLoop = (height) / (endOfy)
        val startOfy = height
        distanceY = startOfy
        Log.d("stepForLoop", stepForYWhenLoop.toString())

        while (countY < endOfy) {
            Log.d("y", startY.toString())
            canvas?.drawLine(50f, startOfy.toFloat(), 50f, 0f, axisLinePaint)
            canvas?.drawText("$startY ", 20f, distanceY.toFloat(), axisLabelPaint)
            countY++
            startY += yStep
            distanceY -= stepForYWhenLoop
        }

        var startX = 0
        var countX = 0
        val endOfx = xMax / xStep
        stepForXWhenLoop = (width-80) / endOfx
        var startOfX = 70f
        while (countX < endOfx) {
            canvas?.drawLine(50f , startOfy.toFloat() ,width.toFloat() , startOfy.toFloat() , axisLinePaint)
            canvas?.drawText("$startX" ,startOfX , (20+startOfy).toFloat() ,axisLabelPaint)
            countX ++
            startX += xStep
            startOfX += stepForXWhenLoop
        }
    }
}