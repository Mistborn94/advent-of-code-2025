package day8

import helper.Debug
import helper.findAndRemoveFirst
import helper.pairwise
import helper.point.Point3D
import helper.product

fun solveA(text: String, connections: Int = 1000, debug: Debug = Debug.Disabled): Long {
    val points = parseInput(text)

    val circuits = mutableListOf<MutableSet<Point3D>>()

    points.asSequence().pairwise(points.asSequence())
        .sortedBy { (a, b) -> a.squaredDistance(b) }
        .take(connections)
        .forEach {  (a, b) -> circuits.connect(a, b) }

    debug {
        println("All circuits:\n${circuits.sortedByDescending { it.size }.joinToString(separator = "\n")}")
    }

    return circuits.map { it.size.toLong() }.sortedDescending().take(3).product()
}

private fun parseInput(text: String): List<Point3D> =
    text.lines().map { it.split(",") }.map { (a, b, c) -> Point3D(a.toInt(), b.toInt(), c.toInt()) }


fun solveB(text: String, debug: Debug = Debug.Disabled): Int {
    val points = parseInput(text)

    val circuits = points.mapTo(mutableListOf()) { mutableSetOf(it) }

    val pairs = points.asSequence().pairwise(points.asSequence())
        .sortedBy { (a, b) -> a.squaredDistance(b) }
        .iterator()

    lateinit var last: Pair<Point3D, Point3D>
    while (circuits.size > 1) {
        last = pairs.next()
        circuits.connect(last.first, last.second)
    }
    return last.first.x * last.second.x
}

private fun MutableList<MutableSet<Point3D>>.connect(a: Point3D, b: Point3D) {
    val circuitA = findAndRemoveFirst { a in it } ?: mutableSetOf(a, b)
    val circuitB = findAndRemoveFirst { b in it } ?: emptySet()

    circuitA.add(b)
    circuitA.addAll(circuitB)

    add(circuitA)
}
