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
    val lines = text.lines().map { "$it " }
    val numberLines = lines.dropLast(1)
    val operatorLine = lines.last()

    return lines[0].indices.fold(Triple(' ', 0L, 0L)) { (operator, answer, total), c ->
        val number = numberLines.map { it[c] }.joinToString(separator = "").trim()
        if (operatorLine[c] != ' ') {
            debug {
                println("Index $c: Next operator ${operatorLine[c]}")
            }
           Triple(operatorLine[c], number.toLong(), total)
        } else if (number.isEmpty()) {
            Triple(' ', 0L, total + answer)
        } else {
            val newAnswer = when (operator) {
                '+' -> answer + number.toLong()
                '*' -> answer * number.toLong()
                else -> throw IllegalArgumentException("Unknown operator $operator at $c")
            }
            debug {
                println("Index $c: Next answer is $answer $operator $number = $newAnswer")
            }
            Triple(operator, newAnswer, total)
        }
    }.third
}

