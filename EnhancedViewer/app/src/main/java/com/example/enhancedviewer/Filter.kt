package com.example.enhancedviewer

class Filter {
    private var bracketOpen: Boolean = false
    private var quotaOpen: Boolean = false
    private val duringOpen: Boolean
    get() {
        return bracketOpen || quotaOpen
    }

    companion object {
        private val quota: CharArray = charArrayOf('\'', '\"')
        private val bracket: CharArray = charArrayOf('{', '[', '(')
        private val termination: CharArray = charArrayOf('.', ',', '!', '?')

        fun isBracket(value: Char): Boolean = bracket.contains(value)
        fun isTermination(value: Char): Boolean = termination.contains(value)

        fun deleteGarbageText(value: String): String {
            var temp = value.trim()
            temp = temp.replace("‘", "'")
            temp = temp.replace("’", "'")
            temp = temp.replace("“", "\"")
            temp = temp.replace("”", "\"")
            while(temp.contains("  "))
                temp = temp.replace("  ", " ");

            return temp
        }
    }

    fun arragement(value: String) {
        val sb = StringBuilder()
        val temp = arrayListOf<String>()

        for (i in value.indices) {
            
        }
    }
}