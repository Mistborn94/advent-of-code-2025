package day8

import helper.Debug
import helper.printExecutionTime
import helper.printTotalTime
import helper.readDayFile
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.measureTime


internal class Day8KtTest {

    private val day = 8

    @Test
    fun sample1() {
        val text = """
        162,817,812
        57,618,57
        906,360,560
        592,479,940
        352,342,300
        466,668,158
        542,29,236
        431,825,988
        739,650,466
        52,470,668
        216,146,977
        819,987,18
        117,168,530
        805,96,715
        346,949,466
        970,615,88
        941,993,340
        862,61,35
        984,92,344
        425,690,689
        """.trimIndent()

        assertEquals(40, solveA(text, 10, Debug.Enabled))
        assertEquals(25272, solveB(text, Debug.Enabled))
    }

    @Test
    fun solve() {
        val lines = readDayFile(day, "input").readText().trimEnd()

        println("Day $day")
        val durationA = measureTime {
            val solveA = solveA(lines, 1000)
            assertEquals(102816, solveA)
            println("A: $solveA")
        }
        printExecutionTime(durationA)

        val durationB = measureTime {
            val solveB = solveB(lines)
            println("B: $solveB")
            assertEquals(100011612, solveB)
        }
        printExecutionTime(durationB)
        printTotalTime(durationA + durationB)
    }
}