package com.example.enhancedviewer

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ScrollView
import android.widget.TableLayout
import android.widget.TextView
import androidx.core.view.GestureDetectorCompat

interface ScrollViewListener {
    fun onScrollChanged(scrollView: ObservableScrollView, x: Int, y: Int, oldx: Int, oldy: Int)
}

class ObservableScrollView : ScrollView, GestureDetector.OnGestureListener {
    lateinit var mDetector: GestureDetectorCompat
    lateinit var nowLine: TextView
    lateinit var menu: TableLayout
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
        smoothScrollTo(0, scrollY + converter.pageToPixel(value - page) +
                if (value > page)
                    -converter.realLineHeight.toInt()
                else
                    converter.realLineHeight.toInt()
        )
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

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (mDetector.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    override fun onDown(event: MotionEvent): Boolean {
        return false
    }

    override fun onFling(event1: MotionEvent, event2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        return false
    }

    override fun onLongPress(event: MotionEvent) {
    }

    override fun onScroll(event1: MotionEvent, event2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
        return false
    }

    override fun onShowPress(event: MotionEvent) {
    }

    override fun onSingleTapUp(event: MotionEvent): Boolean {
        if (menu.visibility == View.VISIBLE) {
            menu.visibility = View.INVISIBLE
            return true
        }

        val center = height / 2

        page += if (center < event.y) 1 else -1

        return false
    }
}