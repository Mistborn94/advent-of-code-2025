package day5

import helper.Debug
import helper.readDayFile
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals


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

        assertEquals(3, solveA(text, Debug.Enabled))
        assertEquals(14, solveB(text, Debug.Enabled))
    }


    @Test
    fun solve() {
        val lines = readDayFile(day, "input").readText().trimEnd()

        val solveA = solveA(lines)
        println("A: $solveA")
        assertEquals(664, solveA)

        val solveB = solveB(lines)
        println("B: $solveB")
        assertEquals(350780324308385, solveB)
    }
}