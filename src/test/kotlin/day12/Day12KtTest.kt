package day12

import helper.Debug
import helper.printExecutionTime
import helper.readDayFile
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.measureTime


internal class Day12KtTest {

    private val day = 12

    @Test
    fun sample1() {
        val text = """
        0:
        ###
        ##.
        ##.

        1:
        ###
        ##.
        .##

        2:
        .##
        ###
        ##.

        3:
        ##.
        ###
        ##.

        4:
        ###
        #..
        ###

        5:
        ###
        .#.
        ###

        4x4: 0 0 0 0 2 0
        12x5: 1 0 1 0 2 2
        12x5: 1 0 1 0 3 2
        """.trimIndent()

        assertEquals(2, solveA(text, Debug.Disabled))
        assertEquals(0, solveB(text, Debug.Enabled))
    }

    @Test
    fun solve() {
        val lines = readDayFile(day, "input").readText().trimEnd()

        println("Day $day")
        val durationA = measureTime {
            val solveA = solveA(lines)
            println("A: $solveA")
            assertEquals(517, solveA)
        }
        printExecutionTime(durationA)
    }
}