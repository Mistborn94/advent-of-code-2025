package day4

import helper.Debug
import helper.readDayFile
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals


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

        assertEquals(13, solveA(text, Debug.Enabled))
        assertEquals(43, solveB(text, Debug.Enabled))
    }

    @Test
    fun solve() {
        val lines = readDayFile(day, "input").readText().trimEnd()

        val solveA = solveA(lines)
        println("A: $solveA")
        assertEquals(1478, solveA)

        val solveB = solveB(lines)
        println("B: $solveB")
        assertEquals(9120, solveB)
    }
}