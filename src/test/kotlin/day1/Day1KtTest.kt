package day1

import helper.Debug
import helper.printExecutionTime
import helper.printTotalTime
import helper.readDayFile
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.time.measureTime


internal class Day1KtTest {

    private val day = 1

    @Test
    fun sample1() {
        val text = """
        L68
        L30
        R48
        L5
        R60
        L55
        L1
        L99
        R14
        L82
        """.trimIndent().trimEnd()

        assertEquals(3, solveA(text, Debug.Disabled))
        assertEquals(6, solveB(text, Debug.Disabled))
    }

    @Test
    fun sample2() {

        assertEquals(10, solveB("R1000", Debug.Disabled))
    }

    @Test
    fun solve() {
        val lines = readDayFile(day, "input").readText().trimEnd()

        println("Day $day")
        val durationA = measureTime {
            val solveA = solveA(lines)
            println("A: $solveA")
            assertEquals(984, solveA)
        }
        printExecutionTime(durationA)

        val durationB = measureTime {
            val solveB = solveB(lines)
            println("B: $solveB")
            assertEquals(5657, solveB)
        }

        printExecutionTime(durationB)
        printTotalTime(durationA + durationB)
    }
}