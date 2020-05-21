package com.zay.mmgraph

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mmLineGraph.rangeToY(100 , 5)
        mmLineGraph.rangeToX(80 , 10)
        mmLineGraph.execute()
    }
}
