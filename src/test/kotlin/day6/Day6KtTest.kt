package day6

import helper.Debug
import helper.printExecutionTime
import helper.printTotalTime
import helper.readDayFile
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.measureTime


internal class Day6KtTest {

    private val day = 6

    @Test
    fun sample1() {
        val text = """
        |123 328  51 64 
        | 45 64  387 23 
        |  6 98  215 314
        |*   +   *   +  
        """.trimMargin()

        assertEquals(4277556, solveA(text, Debug.Disabled))
        assertEquals(3263827, solveB(text, Debug.Disabled))
    }

    @Test
    fun solve() {
        val lines = readDayFile(day, "input").readText().trimEnd('\n')

        println("Day $day")
        val durationA = measureTime {
            val solveA = solveA(lines)
            println("A: $solveA")
            assertEquals(5977759036837, solveA)
        }
        printExecutionTime(durationA)

        val durationB = measureTime {
            val solveB = solveB(lines)
            println("B: $solveB")
            assertEquals(9630000828442, solveB)
        }
        printExecutionTime(durationB)
        printTotalTime(durationA + durationB)
    }
}