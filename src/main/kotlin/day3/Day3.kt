package day3

import helper.Debug

fun solveA(text: String, debug: Debug = Debug.Disabled): Long {
    return solve(text, 2)
}

fun solveB(text: String, debug: Debug = Debug.Disabled): Long {
    return solve(text, 12)
}

private fun solve(text: String, digits: Int): Long =
    text.lines().sumOf { bank -> maxJoultage(bank.toList().map { it.digitToInt().toLong() }, digits) }

private tailrec fun maxJoultage(bank: List<Long>, remainingBatteries: Int, runningTotal: Long = 0): Long {
    val selectedDigit = bank.dropLast(remainingBatteries - 1).max()
    val index = bank.indexOf(selectedDigit)
    val nextTotal = runningTotal * 10 + selectedDigit

    return  if (remainingBatteries == 1) {
        nextTotal
    } else {
        return maxJoultage(bank.drop(index + 1), remainingBatteries - 1, nextTotal)
    }
}


