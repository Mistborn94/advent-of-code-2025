package day7

import helper.Debug
import helper.printExecutionTime
import helper.printTotalTime
import helper.readDayFile
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.DurationUnit
import kotlin.time.measureTime

internal class Day7KtTest {

    private val day = 7

    @Test
    fun sample1() {
        val text = """
        .......S.......
        ...............
        .......^.......
        ...............
        ......^.^......
        ...............
        .....^.^.^.....
        ...............
        ....^.^...^....
        ...............
        ...^.^...^.^...
        ...............
        ..^...^.....^..
        ...............
        .^.^.^.^.^...^.
        ...............
        """.trimIndent()

        assertEquals(21, solveA(text, Debug.Disabled))
        assertEquals(40, solveB(text, Debug.Disabled))
    }

    @Test
    fun minimalCase() {
        assertEquals(4, solveA("""
            |...S...
            |...^...
            |..^....
            |....^..
            |...^...
        """.trimMargin(), Debug.Disabled))
    }

    @Test
    fun solve() {
        val lines = readDayFile(day, "input").readText().trimEnd()

        println("Day $day")
        val durationA = measureTime {
            val solveA = solveA(lines)
            println("A: $solveA")
            assertEquals(1678, solveA)
        }
        printExecutionTime(durationA)

        val durationB = measureTime {
            val solveB = solveB(lines)
            println("B: $solveB")
            assertEquals(357525737893560, solveB)
        }
        printExecutionTime(durationB)
        printTotalTime(durationA + durationB)
    }
}