package day9

import helper.Debug
import helper.combinations
import helper.pairwise
import helper.point.base.*
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

fun solveA(text: String, debug: Debug = Debug.Disabled): Long {
    val points = parsePoints(text)

    return points.pairwise(points).maxOf { (a, b) -> calcArea(a, b) }
}

private fun parsePoints(text: String): List<Point> =
    text.lines().map { Point(it.substringBefore(",").toInt(), it.substringAfter(",").toInt()) }

fun calcArea(a: Point, b: Point): Long {
    val diff = a - b
    return (diff.x.absoluteValue.toLong() + 1) * (diff.y.absoluteValue + 1)
}

fun solveB(text: String, debug: Debug = Debug.Disabled): Long {
    val red = parsePoints(text)
    val hBorders = mutableListOf<HorizontalRange>()
    val vBorders = mutableListOf<VerticalRange>()
    (red + red.first()).asSequence().zipWithNext().forEach { (a, b) ->
        if (a.x == b.x) {
            vBorders.add(VerticalRange.create(a, b))
        } else {
            hBorders.add(HorizontalRange.create(a, b))
        }
    }

    return red.combinations()
        .filter { (a, b) -> validRectangle(a, b, hBorders, vBorders, debug) }
        .maxOf { (a, b) -> calcArea(a, b) }

}

fun validRectangle(
    a: Point,
    b: Point,
    hBorders: List<HorizontalRange>,
    vBorders: List<VerticalRange>,
    debug: Debug
): Boolean {
    val c = Point(a.x, b.y)
    val d = Point(b.x, a.y)
    val (topLeft, topRight, bottomLeft, bottomRight) = listOf(a, b, c, d).sortedWith(Point.comparatorYFirst)

    val hSides = listOf(
        HorizontalRange.create(topLeft, topRight),
        HorizontalRange.create(bottomLeft, bottomRight)
    )
    val vSides = listOf(
        VerticalRange.create(topLeft, bottomLeft),
        VerticalRange.create(topRight, bottomRight)
    )

    debug {
        println("Testing rectangle between $a and $b with area ${calcArea(a, b)}")
        println("  Corners not in polygon: ${listOf(c, d).filterNot { it.isInsidePolygon(hBorders) } }")
    }

    return c.isInsidePolygon(hBorders)
            && d.isInsidePolygon(hBorders)
            && hSides.none { rectangleSide -> vBorders.any { polygonSide -> rectangleSide.intersectsWith(polygonSide) } }
            && vSides.none { rectangleSide -> hBorders.any { polygonSide -> rectangleSide.intersectsWith(polygonSide) } }
}

data class VerticalRange(val x: Int, private val y: IntRange)  {
    val internalY = (y.first + 1)..y.last

    fun intersectsWith(other: HorizontalRange) = x in other.internalX && other.y in this.internalY
    operator fun contains(point: Point) = point.x == this.x && point.y in this.y
    override fun toString() = "($x, $y)"

    companion object {
        fun create(a: Point, b: Point) = VerticalRange(a.x, min(a.y, b.y)..< max(a.y, b.y))
    }
}

data class HorizontalRange(val x: IntRange, val y: Int)  {
    val internalX = (x.first + 1) .. x.last

    fun intersectsWith(other: VerticalRange) = y in other.internalY && other.x in this.internalX
    fun isBelow(point: Point): Boolean = x.contains(point.x) && y > point.y
    operator fun contains(point: Point) = point.y == this.y && point.x in this.x
    override fun toString() = "($x, $y)"

    companion object {
        fun create(a: Point, b: Point) = HorizontalRange(min(a.x, b.x)..< max(a.x, b.x), a.y)
    }
}

fun Point.isInsidePolygon(horizontalEdges: List<HorizontalRange>): Boolean {
    return horizontalEdges.any { this in it } || horizontalEdges.count { it.isBelow(this) } % 2 == 1
}
