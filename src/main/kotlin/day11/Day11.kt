package day11

import helper.Debug

fun solveA(text: String, debug: Debug = Debug.Disabled): Long {
    val map = text.lines().associate { it.substringBefore(":") to it.substringAfter(":").trim().split(" ") }


    return getNumberOfPaths(map, "you", "out", debug)
}

private fun getNumberOfPaths(map: Map<String, List<String>>, start: String, end: String, debug: Debug): Long {
    debug {
        println("Finding path $start -> $end")
    }
    val cache = mutableMapOf<String, Long>()
    fun dfs(map: Map<String, List<String>>, current: String): Long {
        debug {
            println(" Visit $current")
        }
        return cache.getOrPut(current) {
            if (current == end) {
                return 1L
            } else {
                map[current]?.sumOf { dfs(map, it) } ?: 0L
            }
        }
    }

    return dfs(map, start)
}


fun solveB(text: String, debug: Debug = Debug.Disabled): Long {
    val map = text.lines().associate { it.substringBefore(":") to it.substringAfter(":").trim().split(" ") }

    val dacToFft = getNumberOfPaths(map, "dac", "fft", debug)

    if (dacToFft > 0) {
        val startToDac = getNumberOfPaths(map, "svr", "dac", debug)
        val fftToEnd = getNumberOfPaths(map, "fft", "out", debug)
        return startToDac * dacToFft * fftToEnd
    } else {
        val startToFFt = getNumberOfPaths(map, "svr", "fft", debug)
        val fftToDac = getNumberOfPaths(map, "fft", "dac", debug)
        val dacToEnd = getNumberOfPaths(map, "dac", "out", debug)

        return startToFFt * dacToEnd * fftToDac
    }
}
