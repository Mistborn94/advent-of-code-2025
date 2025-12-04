package day4

import helper.Debug
import helper.point.base.points
import helper.point.base.*
import helper.removeFirst

fun solveA(text: String, debug: Debug = Debug.Disabled): Int {
    val grid = text.lines()
    val points = grid.points()

    return points.count {
        grid[it] == '@' && (it.neighbours() + it.diagonalNeighbours()).count { n -> n in grid && grid[n] == '@' } < 4
    }
}


fun solveB(text: String, debug: Debug = Debug.Disabled): Int {
    val grid = text.lines()
    val toVisit = grid.points().filterTo(mutableSetOf()) { grid[it] == '@'}
    val removed = mutableSetOf<Point>()

    do {
        val next = toVisit.removeFirst()
        val pointNeighbours = (next.neighbours() + next.diagonalNeighbours()).filter { n -> n in grid && n !in removed && grid[n] == '@' }
        if (pointNeighbours.size < 4) {
            removed.add(next)
            toVisit.addAll(pointNeighbours)
        }
    } while (toVisit.isNotEmpty())

    return removed.size
}
