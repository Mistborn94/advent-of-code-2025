package day4

import helper.Debug
import helper.point.base.points
import helper.point.base.*

fun solveA(text: String, debug: Debug = Debug.Disabled): Int {
    val grid = text.lines()
    val points = grid.points()

    return points.count {
        grid[it] == '@' && (it.neighbours() + it.diagonalNeighbours()).count { n -> n in grid && grid[n] == '@' } < 4
    }
}


fun solveB(text: String, debug: Debug = Debug.Disabled): Int {
    val grid = text.lines()
    val points = grid.points().filterTo(mutableSetOf()) { grid[it] == '@'}
    val removed = mutableSetOf<Point>()

    do {
        val newRemoved = points.filterTo(mutableSetOf()) {
            grid[it] == '@' && (it.neighbours() + it.diagonalNeighbours()).count { n -> n in grid && n !in removed && grid[n] == '@' } < 4
        }
        removed.addAll(newRemoved)
        points.removeAll(newRemoved)
    } while (newRemoved.isNotEmpty())

    return removed.size
}
