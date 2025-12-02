package day2

import helper.Debug
import helper.readDayFile
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals


internal class Day2KtTest {

    private val day = 2

    @Test
    fun sample1() {
        val text = """
        11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124
        """.trimIndent().trimEnd()

        assertEquals(1227775554, solveA(text, Debug.Enabled))
        assertEquals(4174379265, solveB(text, Debug.Enabled))
    }



    @Test
    fun solve() {
        val lines = readDayFile(day, "input").readText().trimEnd()

        val solveA = solveA(lines)
        println("A: $solveA")
        assertEquals(64215794229, solveA)

        val solveB = solveB(lines)
        println("B: $solveB")
        assertEquals(85513235135, solveB)
    }
}