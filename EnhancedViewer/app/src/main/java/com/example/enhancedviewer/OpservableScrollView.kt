package com.example.enhancedviewer

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.ScrollView
import android.widget.TableLayout
import android.widget.TextView
import kotlin.math.abs

interface ScrollViewListener {
    fun onScrollChanged(scrollView: ObservableScrollView, x: Int, y: Int, oldx: Int, oldy: Int)
}

class ObservableScrollView : ScrollView {
    lateinit var nowLine: TextView
    lateinit var menu: TableLayout
    lateinit var converter: Converter
    private var scrollStop: Boolean = true
    private var oldX: Float = 0.0F
    private var oldY: Float = 0.0F

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

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (menu.visibility == View.VISIBLE) {
            menu.visibility = View.INVISIBLE
            return true
        }

        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                oldX = ev.x
                oldY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                return super.onTouchEvent(ev)
            }
            MotionEvent.ACTION_UP -> {
                val movementX = abs(oldX - ev.x)
                val movementY = abs(oldY - ev.y)

                if (movementX < 10 && movementY < 10) {
                    val center = height / 2

                    if (center < ev.y) {
                        page++
                    }
                    else
                        page--

                    return true
                }
            }
        }

        return super.onTouchEvent(ev)
    }
}