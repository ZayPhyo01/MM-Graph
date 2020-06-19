package com.zay.mm_graph.points

import android.graphics.Color
import android.graphics.PointF
import android.provider.CalendarContract
import com.zay.mm_graph.label.Label
import java.util.*
import kotlin.collections.ArrayList


class MMPoints {
    var listOfPoints = ArrayList<PointF>()
        private set

    var lineColor: Color = Color()
        private set

    fun add(pointF: PointF) {
        listOfPoints.add(pointF)
    }


}