package day7

import helper.Debug
import helper.point.Direction
import helper.point.base.Point
import helper.point.base.contains
import helper.point.base.get
import helper.point.base.indexOf
import kotlin.collections.contains

val down = Direction.DOWN.point
fun solveA(text: String, debug: Debug = Debug.Disabled): Int {
    val lines = text.lines()
    val toVisit = linkedSetOf(lines.indexOf('S'))
    val splitters = mutableSetOf<Point>()

    while (toVisit.isNotEmpty()) {
        val start = toVisit.removeFirst()
        var current = start

        do {
            current += down
        } while (current in lines && lines[current] == '.')

        if (current in lines && lines[current] == '^' && current !in splitters) {
            splitters.add(current)

            val left = current + Direction.LEFT.point
            val right = current + Direction.RIGHT.point
            toVisit.add(left)
            toVisit.add(right)
        }
    }

    return splitters.size
}


fun solveB(text: String, debug: Debug = Debug.Disabled): Long {
    val lines = text.lines()
    val start = lines.indexOf('S')
    return countTimelines(start, debug, lines)
}

val cache = mutableMapOf<Point, Long>()
private fun countTimelines(
    start: Point,
    debug: Debug,
    lines: List<String>
): Long {

    return cache.getOrPut(start) {
        if (start in cache) {
            return cache[start]!!
        }

        var next = start
        do {
            next += down
        } while (next in lines && lines[next] == '.')

        if (next !in lines) {
            return@getOrPut 1L
        } else {
            val left = next + Direction.LEFT.point
            val right = next + Direction.RIGHT.point
            return@getOrPut countTimelines(left, debug, lines) + countTimelines(right, debug, lines)
        }
    }
}

private fun countSplitters(
    start: Point,
    debug: Debug,
    lines: List<String>
): Long {
    return cache.getOrPut(start) {
        if (start in cache) {
            return 0
        }

        var next = start
        do {
            next += down
        } while (next in lines && lines[next] == '.')

        if (next !in lines) {
            return@getOrPut 1L
        } else {
            val left = next + Direction.LEFT.point
            val right = next + Direction.RIGHT.point
            return@getOrPut countSplitters(left, debug, lines) + countSplitters(right, debug, lines)
        }
    }
}