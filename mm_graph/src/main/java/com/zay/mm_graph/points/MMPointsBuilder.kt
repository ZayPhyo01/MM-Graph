package com.zay.mm_graph.points

import android.graphics.Color
import android.graphics.PointF
import android.provider.CalendarContract
import com.zay.mm_graph.label.Label
import java.util.*


class MMPoints {
    var listOfPoints = PriorityQueue<PointF>()
        private set
    var lineColor : Color = Color()

    fun add(pointF: PointF) {
        listOfPoints.add(pointF)
    }

}