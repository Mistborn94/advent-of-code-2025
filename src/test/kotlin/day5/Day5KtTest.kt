package day5

import helper.Debug
import helper.printExecutionTime
import helper.printTotalTime
import helper.readDayFile
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.measureTime


internal class Day5KtTest {

    private val day = 5

    @Test
    fun sample1() {
        val text = """
        3-5
        10-14
        16-20
        12-18

        1
        5
        8
        11
        17
        32
        """.trimIndent().trimEnd()

        assertEquals(3, solveA(text, Debug.Disabled))
        assertEquals(14, solveB(text, Debug.Disabled))
    }

    @Test
    fun solve() {
        val lines = readDayFile(day, "input").readText().trimEnd()


        println("Day $day")
        val durationA = measureTime {
            val solveA = solveA(lines)
            println("A: $solveA")
            assertEquals(664, solveA)
        }
        printExecutionTime(durationA)

        val durationB = measureTime {
            val solveB = solveB(lines)
            println("B: $solveB")
            assertEquals(350780324308385, solveB)
        }
        printExecutionTime(durationB)
        printTotalTime(durationA + durationB)
    }
}