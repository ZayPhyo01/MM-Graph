package com.arduia.qgraph.ext

import android.view.View

fun View.dp(px:Int) = ( px/ resources.displayMetrics.density ).toInt()

fun View.dp(px:Float) = px /  resources.displayMetrics.density


fun View.px(dp:Int) = (dp * resources.displayMetrics.density).toInt()

fun View.px(dp:Float ) = (dp * resources.displayMetrics.density)
