package template

import helper.Debug
import helper.printExecutionTime
import helper.printTotalTime
import helper.readDayFile
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.measureTime

@Ignore //Ignore Class
internal class DayTKtTest {

    private val day = 0

    @Test
    fun sample1() {
        val text = """
        
        """.trimIndent()

        assertEquals(0, solveA(text, Debug.Enabled))
        assertEquals(0, solveB(text, Debug.Enabled))
    }

    @Test
    @Ignore
    fun sample2() {
//        val text = readDayFile(day, "sample2.in").readText().trimEnd()

        val text = """
        
        """.trimIndent().trimEnd('\n')

        assertEquals(0, solveA(text, Debug.Enabled))
        assertEquals(0, solveB(text, Debug.Disabled))
    }

    @Test
    fun solve() {
        val lines = readDayFile(day, "input").readText().trimEnd()

        println("Day $day")
        val durationA = measureTime {
            val solveA = solveA(lines)
            println("A: $solveA")
//        assertEquals(0, solveA)
        }
        printExecutionTime(durationA)

        val durationB = measureTime {
            val solveB = solveB(lines)
            println("B: $solveB")
//        assertEquals(0, solveB)
        }
        printExecutionTime(durationB)
        printTotalTime(durationA + durationB)
    }
}