package com.arduia.qgraph.ext

import android.graphics.RectF

fun RectF.shiftLeft(phrase:Float){
    left -=phrase
    right -=phrase
}

fun RectF.shiftTop(phrase: Float){
    top -= phrase
    bottom -= phrase
}

fun RectF.shiftBottom(phrase: Float){
    top+=phrase
    bottom+=phrase
}
