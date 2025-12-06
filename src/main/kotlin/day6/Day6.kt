package day6

import helper.Debug
import helper.product

fun solveA(text: String, debug: Debug = Debug.Disabled): Long {
    val worksheet = text.lines().map { it.split(" ").filter { it.isNotEmpty() } }

    var total = 0L
    for (c in worksheet[0].indices) {
        val problem = worksheet.map { it[c] }
        val values = problem.dropLast(1).map { it.toLong() }
        val answer = when (val operator = problem.last()) {
            "+" -> values.sum()
            "*" -> values.product()
            else -> throw IllegalArgumentException("Unknown operator $operator")
        }
        total += answer
    }
    return total
}


fun solveB(text: String, debug: Debug = Debug.Disabled): Long {
    val lines = text.lines().map { "$it  " }
    val numberLines = lines.dropLast(1)
    val operatorLine = lines.last()

    var currentIndex = 0
    var total = 0L
    do {
        val next = nextIndex(operatorLine, currentIndex)
        val values = mutableListOf<Long>()

        for (d in currentIndex.. next-2) {
            var number = 0L
            for (r in numberLines) {
                val char = r[d]
                if (char != ' ') {
                    number = number * 10 + (char.digitToInt())
                }
            }
            values.add(number)
        }
        val operator = operatorLine[currentIndex]
        val answer = when (operator) {
            '+' -> values.sum()
            '*' -> values.product()
            else -> throw IllegalArgumentException("Unknown operator $operator at $currentIndex")
        }
        debug {
            println("Index: ${currentIndex}..<$next, Values: $values $operator = $answer")
        }
        total += answer
        currentIndex = next
    } while (currentIndex < operatorLine.length)

   return total
}

private fun nextIndex(operatorLine: String, currentIndex: Int): Int {
    val indexOfAny =
        operatorLine.indexOfAny(chars = charArrayOf('*', '+'), startIndex = currentIndex + 1, ignoreCase = false)
    return  if (indexOfAny != -1) indexOfAny else operatorLine.length + 1
}
