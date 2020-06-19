package com.zay.mmgraph

import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zay.mm_graph.points.MMPoints
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mmLinePoint = MMPoints()
        mmLinePoint.add(PointF(27f, 20f))
        mmLinePoint.add(PointF(40f, 50f))
        mmLinePoint.add(PointF(55f, 10f))
        mmLineGraph.addPoint(mmLinePoint, Color.BLUE)

        val mmLinePoint1 = MMPoints()
        mmLinePoint1.add(PointF(30f, 25f))
        mmLinePoint1.add(PointF(50f, 30f))
        mmLinePoint1.add(PointF(40f, 35f))
        mmLinePoint1.add(PointF(60f,40f))
        mmLineGraph.addPoint(mmLinePoint1, Color.GREEN)

        mmLineGraph.rangeToY(50, 5)
        mmLineGraph.rangeToX(80, 15)
        mmLineGraph.execute()
    }
}
