package day12

import helper.Debug
import helper.point.base.Point
import helper.point.base.get
import helper.point.base.points
import kotlin.math.min

fun solveA(text: String, debug: Debug = Debug.Disabled): Int {
    val chunks = text.split("\n\n")
    val shapes: List<Set<Pair<Shape, Point>>> = chunks.dropLast(1).map { buildShape(it) }
    val regions = chunks.last().lines().map { line ->
        val width = line.substringBefore("x").toInt()
        val length = line.substringBefore(":").substringAfter("x").toInt()
        val requirements =
            line.substringAfter(": ").split(" ").mapIndexed { index, string -> index to string.toInt() }.toMap()
                .filterValues { it > 0 }
        Region(width, length, requirements)
    }
    return regions.count {
        debug {
            println("Evaluating region $it")
        }
        val canFit = canFitArea(it, shapes) && canFitRequiredShapes(it, shapes, debug, 'A')
        debug {
            println(if (canFit) "Success!" else "Fail!")
        }
        canFit
    }
}

fun canFitArea(
    region: Region,
    shapes: List<Set<ShapeWithDimensions>>
): Boolean {
    val area = region.width * region.height

    val required = region.requirements.map { (key, value) ->
        val (shape, dimensions) = shapes[key].first()
        value * shape.size
    }.sum()

    return required <= area
}

fun canFitRequiredShapes(
    region: Region,
    shapes: List<Set<ShapeWithDimensions>>,
    debug: Debug,
    ch: Char
): Boolean {
    if (region.requirements.isEmpty()) {
        return true
    }

    debug {
        println("Region before start, shape $ch")
        for (y in 0..<region.height) {
            for (x in 0..region.width) {
                val point = Point(x, y)
                when (point) {
                    in region.filled -> print('#')
                    else -> print('.')
                }
            }
            println()
        }
        println()
    }

    val possibleShapes = region.requirements.keys.flatMap { i -> shapes[i].map { shape -> i to shape } }

    return possibleShapes.any { (i, shape) ->
        val newRegion = tryFit(region, shape, i, debug)

        if (newRegion == null) {
            false
        } else {
            debug {
                println("  Successfully fit shape $i")
                for (y in 0..<region.height) {
                    print("  ")
                    for (x in 0..region.width) {
                        val point = Point(x, y)
                        when (point) {
                            in shape.first -> print(ch)
                            in region.filled -> print('#')
                            else -> print('.')
                        }
                    }
                    println()
                }
                println()
            }
            canFitRequiredShapes(newRegion, shapes, debug, ch + 1)
        }
    }
}

fun tryFit(region: Region, shapeWithDimensions: ShapeWithDimensions, shapeIndex: Int, debug: Debug): Region? {
    val (shape, dimensions) = shapeWithDimensions
    debug {
        println("Testing shape $shape")
        shapeWithDimensions.print()
    }

    val maxX = region.firstUnfilledX  + dimensions.x
    val maxY = region.firstUnfilledY + dimensions.y

    val fittingShape: Shape? = pointsSequence(0..< min(maxX, region.width), 0 ..< min(maxY, region.height))
        .filter { it !in region.filled }
        .firstNotNullOfOrNull { start ->
        val offsetShape: Shape = shape.offset(start.x, start.y)
        debug {
            println("  Trying at position $start")
            println("  New Coordinates $offsetShape")
        }

        if (start.x + dimensions.x <= region.width
            && start.y + dimensions.y <= region.height
            && offsetShape.all { region.canFit(it) }
        ) {
            offsetShape
        } else {
            null
        }
    }

    return fittingShape?.let { placedShape -> region.withShape(shapeIndex, placedShape) }
}

fun pointsSequence(xs: IntRange, ys: IntRange) =
    xs.asSequence().flatMap { x -> ys.asSequence().map { y -> Point(x, y) } }

private fun ShapeWithDimensions.print() {
    val (shape, dimensions) = this
    for (y in 0 until dimensions.x) {
        for (x in 0 until dimensions.y) {
            val point = Point(x, y)
            if (point in shape) {
                print("#")
            } else {
                print(".")
            }
        }
        println()
    }
}

fun buildShape(text: String): Set<ShapeWithDimensions> {
    val charGrid = text.trim().lines().drop(1)
    val baseShape: Shape = points(charGrid)
    val flipped = baseShape.mapTo(mutableSetOf()) { (x, y) -> Point(-x, y) }.normalize()

    return listOf(
        baseShape,
        baseShape.rotate(90),
        baseShape.rotate(180),
        baseShape.rotate(270),
        flipped,
        flipped.rotate(90),
        flipped.rotate(180),
        flipped.rotate(270)
    ).mapTo(mutableSetOf()) { it to it.dimensions() }
}

 fun Shape.dimensions(): Point {
    val maxX = maxOf { it.x }
    val maxY = maxOf { it.y }
    return Point(maxX + 1, maxY + 1)
}

private fun Shape.rotate(rotation: Int): Shape = mapTo(mutableSetOf()) { it.clockwise(rotation) }.normalize()

private fun Shape.normalize(): Shape {
    val minX = minOf { it.x }
    val minY = minOf { it.y }
    return this.offset(-minX, -minY)
}

private fun Shape.offset(xOffset: Int, yOffset: Int): Shape =
    mapTo(mutableSetOf()) { (x, y) -> Point(x + xOffset, y + yOffset) }

private fun points(charGrid: List<String>): MutableSet<Point> =
    charGrid.points().filterTo(mutableSetOf()) { charGrid[it] == '#' }

data class Region(
    val width: Int,
    val height: Int,
    val requirements: Map<Int, Int>,
    val filled: Set<Point> = emptySet()
) {

    fun withShape(shapeIndex: Int, shape: Shape): Region {
        val newRequirements = requirements.toMutableMap().apply { set(shapeIndex, requirements[shapeIndex]!! - 1) }
            .filterValues { it > 0 }
        return copy(requirements = newRequirements, filled = this.filled + shape)
    }

    fun canFit(point: Point): Boolean {
        return point !in filled && point.x < width && point.y < height
    }

    val firstUnfilledX = (filled.maxOfOrNull { it.x } ?: -1)  + 1
    val firstUnfilledY = (filled.maxOfOrNull { it.y } ?: -1)  + 1

//    val pointsYFirst: Sequence<Point> =
//        (0..<height).asSequence().flatMap { y -> (0..<width).asSequence().map { x -> Point(x, y) } }
}
typealias Shape = Set<Point>
typealias ShapeWithDimensions = Pair<Shape, Point>

fun solveB(text: String, debug: Debug = Debug.Disabled): Int {
    return 0
}
