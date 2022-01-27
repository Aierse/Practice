package com.example.enhancedviewer

import kotlin.math.round

class Converter(val viewSize: Int,val scrollViewSize: Int ,val lineCount: Int) {
    var realLineHeight: Double = 0.0
    var canShowLineCount: Int = 0

    init {
        realLineHeight = viewSize / lineCount.toDouble()
        canShowLineCount = (scrollViewSize / realLineHeight).toInt()
    }

    fun pixelToLine(pixel: Int): Int {
        return (round(pixel / realLineHeight)).toInt()
    }

    fun lineToPixel(line: Int): Int {
        return (round(line * realLineHeight)).toInt()
    }

    fun pixelToPage(pixel: Int):Int {
        return pixel / scrollViewSize
    }

    fun pageToPixel(page: Int): Int {
        return page * scrollViewSize
    }
}