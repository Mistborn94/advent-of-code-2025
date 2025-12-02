package day2

import helper.Debug

fun solveA(text: String, debug: Debug = Debug.Disabled): Long {
    return text.split(",", "-").asSequence()
        .windowed(2, 2)
        .onEach { (a, b) -> debug{ println("Testing range $a-$b")}}
        .flatMap { (a,b) -> a.toLong()..b.toLong() }
        .filter { it > 9 && isInvalidA(it.toString()) }
        .onEach { debug{ println("$it is invalid")}}
        .sum()
}

private fun isInvalidA(s: String, count: Int = 2): Boolean {
    val size = s.length / count
    val windowed = s.windowed(size, size)
    return s.length % count == 0 && windowed.all { it == windowed.first() }
}


fun solveB(text: String, debug: Debug = Debug.Disabled): Long {
    return text.split(",", "-").asSequence()
        .windowed(2, 2)
        .onEach { (a, b) -> debug{ println("Testing range $a-$b")}}
        .flatMap { (a,b) -> a.toLong()..b.toLong() }
        .filter { it > 9 && isInvalidB(it.toString()) }
        .onEach { debug{ println("$it is invalid")}}
        .sum()
}

private fun isInvalidB(s: String): Boolean {
    return (2..s.length).any { isInvalidA(s, it) }
}
