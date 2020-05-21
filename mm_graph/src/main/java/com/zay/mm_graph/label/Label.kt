package com.zay.mm_graph.label

import android.graphics.Color

data class Label(
    val labelText : String ,
    val labelMarkColor : Color ,
    var labelMarkPadding : Int = 18 ,
    var labelTextSize : Int = 24
)