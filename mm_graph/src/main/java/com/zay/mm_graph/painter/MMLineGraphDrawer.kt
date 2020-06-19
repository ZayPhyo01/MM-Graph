package com.zay.mm_graph.painter

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import com.zay.mm_graph.line_graph.LineGraphFrame
import com.zay.mm_graph.points.MMPoints

class MMLineGraphDrawer {

    lateinit var frame: LineGraphFrame


    fun setLineGraphFrame(frame: LineGraphFrame) {
        this.frame = frame
    }

    fun paintLinePoints(canvas: Canvas, points: MMPoints, paint: Paint, stepX: Int, stepY: Int) {
        val xAxisDistant = frame.xDistant
        val yAxisDistant = frame.yDistant
        var startOfX = frame.startOfX
        var startOfY = frame.startOfY
        var stopOfX = calculateXDistance(xAxisDistant, points, 0, stepX)
        var stopOfY = calculateYDistance(startOfY, yAxisDistant, points, 0, stepY)
        var count = 1
        canvas.drawLine(
            startOfX,
            startOfY,
            stopOfX + 70,
            stopOfY,
            paint
        )


        while (count < points.listOfPoints.size) {
            startOfX = stopOfX
            startOfY = stopOfY
            stopOfX = calculateXDistance(xAxisDistant, points, count, stepX)
            stopOfY = calculateYDistance(frame.startOfY, yAxisDistant, points, count, stepY)

            canvas.drawLine(
                startOfX + 70,
                startOfY,
                stopOfX + 70,
                stopOfY,
                paint
            )
            count += 1
        }
    }

    fun paintDottedPoints(canvas: Canvas, stepY: Int, paint: Paint) {
        val lineOfDotted = frame.startOfY / stepY
        var count = 0
        var startOfY = frame.startOfY
        val lengthOfXAxis = (frame.startOfX * frame.xDistant)

        Log.d("length of x", lengthOfXAxis.toString())
        while (count < lineOfDotted) {
            var startOfX = frame.startOfX
            while (startOfX < lengthOfXAxis) {
                canvas.drawLine(
                    startOfX,
                    startOfY,
                    startOfX + 10,
                    startOfY,
                    paint
                )
                startOfX += 20
            }
            startOfY -= frame.yDistant
            count += 1
        }
    }

    private fun calculateXDistance(
        xAxisDistant: Float,
        points: MMPoints,
        count: Int,
        step: Int
    ): Float {
        val xPointOfList = points.listOfPoints.get(count).x
        if (xPointOfList % step != 0f) {
            return (xAxisDistant * ((xPointOfList / step))) +
                    (xAxisDistant / (xPointOfList % step))
        } else {
            return (xAxisDistant * ((xPointOfList / step)))
        }
    }

    private fun calculateYDistance(
        yHeight: Float,
        yAxisDistant: Float,
        points: MMPoints,
        count: Int,
        step: Int
    ): Float {
        val yPointOfList = points.listOfPoints.get(count).y

        if (yPointOfList % step != 0f) {
            return yHeight - ((yAxisDistant * ((yPointOfList / step))) +
                    (yAxisDistant / (yPointOfList % step)))
        } else {

            return yHeight - (yAxisDistant * ((yPointOfList / step)))
        }
    }

}