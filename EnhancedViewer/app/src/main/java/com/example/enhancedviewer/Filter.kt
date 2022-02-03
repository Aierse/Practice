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
        private val bracketClose: CharArray = charArrayOf('}', ']', ')')
        private val termination: CharArray = charArrayOf('.', ',', '!', '?')

        private fun isQuota(value: Char): Boolean = quota.contains(value)
        private fun isBracket(value: Char): Boolean = bracket.contains(value)
        private fun isBracketClose(value: Char): Boolean = bracketClose.contains(value)
        private fun isTermination(value: Char): Boolean = termination.contains(value)

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
            if (isBracket(value[i])) {
                bracketOpen = true
            }
            else if (isBracketClose(value[i])) {
                bracketOpen = false
            }
            else if (!duringOpen) { // 개행중이 아닐 때
                val nextChar = value[i + 1]

                if (isTermination(nextChar)) // 연속된 ... 일때 넘기기
                    continue

                if (sb.length < 15 && isQuota(nextChar)) // 문장의 길이가 짧을 때 다음으로 미룸
                    continue

                temp.add(sb.toString())
                sb.setLength(0)
            }
            else if (isQuota(value[i])) {

            }
        }
    }
}