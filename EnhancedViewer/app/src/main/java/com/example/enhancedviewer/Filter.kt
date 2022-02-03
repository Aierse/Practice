package com.example.enhancedviewer

class Filter {
    private var bracketOpen: Boolean = false
    private var quataOpen: Boolean = false

    companion object {
        private val bracket: CharArray = charArrayOf('{', '[', '(')
        private val termination: CharArray = charArrayOf('.', ',', '!', '?')

        fun isBracket(value: Char): Boolean = bracket.contains(value)
        fun isTermination(value: Char): Boolean = termination.contains(value)

        fun deleteGarbageText(value: String): String {
            var temp = value
            temp = value.replace("‘", "'")
            temp = value.replace("’", "'")
            temp = value.replace("“", "\"")
            temp = value.replace("”", "\"")
            while(value.contains("  "))
                temp = value.replace("  ", " ");

            return temp
        }
    }



    fun arragement(value: String) {
        val sb = StringBuilder()
        val temp = arrayListOf<String>()

        for (i in 0..value.length) {

        }
    }
}