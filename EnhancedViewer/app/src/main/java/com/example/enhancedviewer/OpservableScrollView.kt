package com.example.enhancedviewer

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView
import android.widget.TextView

interface ScrollViewListener {
    fun onScrollChanged(scrollView: ObservableScrollView, x: Int, y: Int, oldx: Int, oldy: Int)
}

class ObservableScrollView : ScrollView {
    lateinit var nowLine: TextView
    var lineHeight: Int = 0
    var realLineHeight: Int = 0

    constructor(context: Context?) :
            super(context) {

    }
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) :
            super(context, attrs, defStyle) {

    }

    constructor(context: Context?, attrs: AttributeSet?) :
            super(context, attrs) {
    }

    override fun onScrollChanged(x: Int, y: Int, oldx: Int, oldy: Int) {
        super.onScrollChanged(x, y, oldx, oldy)
        nowLine.text = y.toString()
    }

    fun convertToLine(value: Int): Int {
        return value / 83
    }
}