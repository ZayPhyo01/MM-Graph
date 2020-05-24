package com.arduia.qgraph

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.arduia.qgraph.ext.*
import com.arduia.qgraph.model.LinePoint

/**
 * Created by Arduia 24/5/2020 6:23 PM
 */
class QGraph @JvmOverloads constructor(ctx:Context,
                                       attrs:AttributeSet?=null,
                                       styleAttrs:Int=0 ):View(ctx,attrs,styleAttrs){

    /**
     * Graph Margins
     */
    private val graphMarginTop by lazy { px(10) }
    private val graphMarginLeft by lazy { px(10) }
    private val graphMarginRight by lazy { px(10) }
    private val graphMarginBottom by lazy { px(100) }

    /**
     * label Rectangle Size for X-axis and Y-axis
     */
    private val yLabelWidth by lazy { px(30f) }
    private val yLabelHeight by lazy { px(30f) }
    private val xLabelWidth by lazy { px(30f) }
    private val xLabelHeight by lazy { px(30f)  }

    /**
     * Axis Width
     */
    private val axisWidth by lazy { px(2f) }
    private val dashLength by lazy { px(5f) }

    /**
     * Graph Size
     */
    private val graphWidth by lazy { px(300f) }
    private val graphHeight by lazy { px(300f) }

    /**
     * pointQueue
     */

    private val tmpFirst by lazy {
        genFirstLine()
    }



    /**
     * QGraph View's Drawable Float Rectangle (RectF) = R
     */
    private val viewR by lazy {createViewRect()}

    /**
     * Graph Canvas Float Rectangle
     */
    private val graphR by lazy { createGraphRect() }

    /**
     * RectF that will be drawn Graph Lines
     */
    private val lineCanvasR by lazy { createLineCanvasRect() }

    private val testPaint by lazy { Paint().apply { style = Paint.Style.STROKE; color = Color.BLUE; strokeWidth = px(1.5f)} }
    private val axisPaint by lazy { Paint().apply { style = Paint.Style.STROKE;color = Color.BLACK;strokeWidth = axisWidth } }
    private val labelPaint by lazy {
        Paint().apply {
            style = Paint.Style.STROKE;
            color = Color.BLACK;
            textSize = px(12f);
            textAlign= Paint.Align.CENTER

        } }
    private val linePaint by lazy { Paint().apply { style = Paint.Style.STROKE; color = Color.BLUE; strokeWidth = px(1.5f)} }

    /**
     * Steps
     */
    var totalXStep = 10
    var totalYStep = 10

    /**
     * Creation methods
     */

    private fun createViewRect() = RectF(0f,0f,width.toFloat(),height.toFloat())

    private fun createGraphRect():RectF{

        //graph Rectangle is between CustomView's padding and Graph's margin
        val left = viewR.left + paddingLeft + graphMarginLeft
        val top = viewR.top + paddingTop + graphMarginTop

        return RectF(left,top,graphWidth,graphHeight)
    }

    private fun createLineCanvasRect():RectF{

        //Line Canvas Rectangle is between the width of Y-axis label and X-axis label Size
        val left = graphR.left + yLabelWidth
        val right = graphR.right
        val top = graphR.top
        val bottom = graphR.bottom - xLabelHeight
        return RectF(left,top,right,bottom)
    }

    /**
     * Temp methods
     */

    private fun genFirstLine() = mutableListOf<LinePoint>().apply {

        add(linePoint(0f))
        add(linePoint(0.1f))
        add(linePoint(0.3f))
        add(linePoint(0.2f))
        add(linePoint(0.4f))
    }

    private fun genSecondLine() = mutableListOf<LinePoint>().apply {
        add(linePoint(1f))
        add(linePoint(0.3f))
        add(linePoint(0.6f))
        add(linePoint(0.8f))
        add(linePoint(0.1f))
    }


    private fun getTempXList():List<RectF>{
        val result = mutableListOf<RectF>()

        val intervalWidth = lineCanvasR.width()/totalXStep
        val halfXWidth = xLabelWidth/2

        for(step in 1 until totalXStep){
            val xPhrase = step * intervalWidth
            val left = lineCanvasR.left+xPhrase
            val frame= RectF(left,lineCanvasR.bottom,left+xLabelWidth,lineCanvasR.bottom+xLabelHeight)
            frame.shiftLeft(halfXWidth)
            result.add(frame)
        }

        return result
    }


    private fun getTempYList():List<RectF>{
        val result = mutableListOf<RectF>()

        val intervalHeight = lineCanvasR.height()/totalYStep
        val leftPosition= lineCanvasR.left- yLabelWidth
        val rightPosition = lineCanvasR.left

        val halfYHeight= yLabelHeight/2

        for(step in 1 until totalYStep){

            val xPhrase = step * intervalHeight
            val bottom = lineCanvasR.bottom-xPhrase

            val frame = RectF(leftPosition,bottom-yLabelHeight,rightPosition,bottom)
            frame.shiftBottom(halfYHeight)

            result.add(frame)
        }

        return result
    }

    /**
     * Draw Section
     */


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawXAxis()
        canvas?.drawYAxis()
        canvas?.drawXLabels()
        canvas?.drawYLabels()
        canvas?.drawGraphLines()
    }
    private fun Canvas.drawGraphLines(){
        drawGraphLine(genFirstLine())
        drawGraphLine(genSecondLine(),color = Color.MAGENTA)
    }

    private fun Canvas.drawGraphLine(lines:List<LinePoint>,color:Int = Color.GREEN){

        if(lines.isEmpty()) return
        var oldPoint = lines.first()

        val intervalXWidth = lineCanvasR.width()/lines.size
        val totalHeight = lineCanvasR.height()
        val bottom = lineCanvasR.bottom
        val left = lineCanvasR.left

        linePaint.color = color

        lines.forEachIndexed { i, point ->

            if(i>0){
                val startX =left+( (i-1 ) * intervalXWidth)
                val startY = bottom - (totalHeight * oldPoint.level)

                val endX = left+ (i * intervalXWidth)
                val endY = bottom - (totalHeight * point.level)

                drawLine(startX,startY,endX,endY,linePaint)
            }

            oldPoint = point
        }

    }




    private fun Canvas.drawXLabels(){
        getTempXList().forEachIndexed{i,frameX->
            drawXLabel(frameX,"$i")
        }
    }

    private fun Canvas.drawYLabels(){
        getTempYList().forEachIndexed{i,frameY->
            drawYLabel(frameY,"$i")
        }
    }

    private fun Canvas.drawXAxis() = drawLine(lineCanvasR.left,lineCanvasR.bottom,lineCanvasR.left,lineCanvasR.top,axisPaint)

    private fun Canvas.drawYAxis()= drawLine(lineCanvasR.left,lineCanvasR.bottom,lineCanvasR.right,lineCanvasR.bottom,axisPaint)

    private fun Canvas.drawXLabel(rectF:RectF,text:String){
        val dashTop = rectF.top
        val dashBottom = rectF.top + dashLength
        drawLine(rectF.centerX(),dashTop,rectF.centerX(),dashBottom,axisPaint)
        drawText(text,rectF.centerX(),rectF.centerY(),labelPaint)
    }

    private fun Canvas.drawYLabel(rectF:RectF,text:String){
        val dashStartX =rectF.right- dashLength
        val dashStartY = rectF.centerY()
        val dashEndX = rectF.right
        val dashEndY = rectF.centerY()

        drawLine(dashEndX,dashEndY,dashStartX,dashStartY,axisPaint)
        drawText(text,rectF.centerX(),rectF.centerY(),labelPaint)
    }

}
