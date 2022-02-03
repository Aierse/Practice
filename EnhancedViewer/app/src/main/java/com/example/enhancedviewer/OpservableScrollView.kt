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
    lateinit var converter: Converter

    var line: Int
    get() {
        return converter.pixelToLine(scrollY)
    }
    set(value: Int) {
        scrollY = converter.lineToPixel(value)
    }

    var page: Int
    get() {
        return converter.pixelToPage(scrollY)
    }
    set(value: Int) {
        scrollY += converter.pageToPixel(value - page) - converter.realLineHeight.toInt()
    }

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
        nowLine.text = (line + 1).toString()
    }
}