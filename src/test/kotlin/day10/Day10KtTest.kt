package day10

import helper.Debug
import helper.printExecutionTime
import helper.printTotalTime
import helper.readDayFile
import org.junit.jupiter.api.Disabled
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.measureTime


internal class Day10KtTest {

    private val day = 10

    @Test
    fun sample1() {
        val text = """
        [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
        [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
        [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
        """.trimIndent()

        assertEquals(7, solveA(text, Debug.Disabled))
        assertEquals(33, solveB(text, Debug.Enabled))
    }


    @Test
    @Disabled("Too slow")
    fun solve() {
        val lines = readDayFile(day, "input").readText().trimEnd()

        println("Day $day")
        val durationA = measureTime {
            val solveA = solveA(lines)
            println("A: $solveA")
        assertEquals(473, solveA)
        }
        printExecutionTime(durationA)

        val durationB = measureTime {
            val solveB = solveB(lines)
            println("B: $solveB")
        assertEquals(18681, solveB)
        }
        printExecutionTime(durationB)
        printTotalTime(durationA + durationB)
    }
}