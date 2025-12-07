package day4

import helper.Debug
import helper.printExecutionTime
import helper.printTotalTime
import helper.readDayFile
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.measureTime


internal class Day4KtTest {

    private val day = 4

    @Test
    fun sample1() {
        val text = """
        ..@@.@@@@.
        @@@.@.@.@@
        @@@@@.@.@@
        @.@@@@..@.
        @@.@@@@.@@
        .@@@@@@@.@
        .@.@.@.@@@
        @.@@@.@@@@
        .@@@@@@@@.
        @.@.@@@.@.
        """.trimIndent().trimEnd()

        assertEquals(13, solveA(text, Debug.Disabled))
        assertEquals(43, solveB(text, Debug.Disabled))
    }

    @Test
    fun solve() {
        val lines = readDayFile(day, "input").readText().trimEnd()

        println("Day $day")
        val durationA = measureTime {
            val solveA = solveA(lines)
            println("A: $solveA")
            assertEquals(1478, solveA)
        }
        printExecutionTime(durationA)

        val durationB = measureTime {
            val solveB = solveB(lines)
            println("B: $solveB")
            assertEquals(9120, solveB)
        }
        printExecutionTime(durationB)
        printTotalTime(durationA + durationB)
    }
}