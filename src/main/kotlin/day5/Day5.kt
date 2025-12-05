package day5

import helper.Debug
import helper.*

fun solveA(text: String, debug: Debug = Debug.Disabled): Int {
    val (ranges, ids) = text.split("\n\n")
    val rangeList = ranges.lines().map { it.substringBefore('-').toLong()..it.substringAfter('-').toLong() }

    return ids.lines().count { id -> rangeList.any { id.toLong() in it } }
}


fun solveB(text: String, debug: Debug = Debug.Disabled): Long {
    val (ranges, _) = text.split("\n\n")
    val rangeList = ranges.lines().map { it.substringBefore('-').toLong()..it.substringAfter('-').toLong() }

    return CompositeRange.newLongRange(rangeList).size()
}
