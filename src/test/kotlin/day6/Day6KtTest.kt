package day6

import helper.Debug
import helper.readDayFile
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals


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

        assertEquals(4277556, solveA(text, Debug.Enabled))
        assertEquals(3263827, solveB(text, Debug.Enabled))
    }

    @Test
    fun solve() {
        val lines = readDayFile(day, "input").readText().trimEnd('\n')

        val solveA = solveA(lines)
        println("A: $solveA")
        assertEquals(5977759036837, solveA)

        val solveB = solveB(lines)
        println("B: $solveB")
        assertEquals(9630000828442, solveB)
    }
}