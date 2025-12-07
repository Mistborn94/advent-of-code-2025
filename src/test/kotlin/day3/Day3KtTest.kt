package day3

import helper.Debug
import helper.printExecutionTime
import helper.printTotalTime
import helper.readDayFile
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.measureTime


internal class Day3KtTest {

    private val day = 3

    @Test
    fun sample1() {
        val text = """
        987654321111111
        811111111111119
        234234234234278
        818181911112111
        """.trimIndent().trimEnd()

        assertEquals(357, solveA(text, Debug.Disabled))
        assertEquals(3121910778619, solveB(text, Debug.Disabled))
    }

    @Test
    fun solve() {
        val lines = readDayFile(day, "input").readText().trimEnd()

        println("Day $day")
        val durationA = measureTime {
            val solveA = solveA(lines)
            println("A: $solveA")
            assertEquals(17316, solveA)
        }
        printExecutionTime(durationA)

        val durationB = measureTime {
            val solveB = solveB(lines)
            println("B: $solveB")
            assertEquals(171741365473332, solveB)
        }
        printExecutionTime(durationB)
        printTotalTime(durationA + durationB)
    }
}