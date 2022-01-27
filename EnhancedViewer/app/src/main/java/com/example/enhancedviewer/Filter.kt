package com.example.enhancedviewer

class Filter {
    private var bracketOpen: Boolean = false
    private var quataOpen: Boolean = false

    companion object {
        private val bracket: CharArray = charArrayOf('{', '[', '(')
        private val termination: CharArray = charArrayOf('.', ',', '!', '?')

        fun isBracket(value: Char): Boolean = bracket.contains(value)
        fun isTermination(value: Char): Boolean = termination.contains(value)
    }

    fun arragement(value: String) {

    }
}