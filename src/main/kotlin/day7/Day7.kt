package day7

import helper.Debug
import helper.point.Direction
import helper.point.base.Point
import helper.point.base.contains
import helper.point.base.get
import helper.point.base.indexOf

val down = Direction.DOWN.point
fun solveA(text: String, debug: Debug = Debug.Disabled): Int {
    val seenSplitters = mutableSetOf<Point>()

    val lines = text.lines()
    val start = lines.indexOf('S')

    fun countSplits(start: Point, depth: String = ""): Int {
        debug {
            println("${depth}Evaluating $start: ")
        }

        var next = start
        do {
            next += down
        } while (next in lines && lines[next] == '.')


        if (next !in lines || next in seenSplitters) {
            debug {
                println("$depth  Walked to $next [outside]")
            }
            return 0
        } else {
            seenSplitters.add(next)
            val left = next + Direction.LEFT.point
            val right = next + Direction.RIGHT.point
            return 1 + countSplits(left) + countSplits(right)
        }
    }

    return countSplits(start)
}


fun solveB(text: String, debug: Debug = Debug.Disabled): Long {
    val cacheB = mutableMapOf<Point, Long>()

    val lines = text.lines()
    val start = lines.indexOf('S')

    fun countTimelines(start: Point): Long {
        return cacheB.getOrPut(start) {
            var next = start
            do {
                next += down
            } while (next in lines && lines[next] == '.')

            if (next !in lines) {
                return@getOrPut 1L
            } else {
                val left = next + Direction.LEFT.point
                val right = next + Direction.RIGHT.point
                return@getOrPut countTimelines(left) + countTimelines(right)
            }
        }
    }

    return countTimelines(start)
}


