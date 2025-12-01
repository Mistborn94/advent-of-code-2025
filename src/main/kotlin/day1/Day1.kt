package day1

import helper.Debug

fun solveA(text: String, debug: Debug = Debug.Disabled): Int {
    var position = 50
    var zeroCount = 0

    text.lines().forEach { line ->
        val direction = line.first()
        val distance = line.substring(1).toInt()
        val rotation = distance % 100

        if (direction == 'L') {
            position -= rotation
        } else {
            position += rotation
        }

        if (position < 0) {
            position += 100
        }

        if (position > 99) {
            position -= 100
        }

        debug {
            println("The dial is rotated $line to point at $position.")
        }

        if (position == 0) {
            zeroCount += 1
        }
    }
    return zeroCount
}


fun solveB(text: String, debug: Debug = Debug.Disabled): Int {
    var position = 50
    var zeroCount = 0

    text.lines().forEach { line ->
        val oldPosition = position
        val direction = line.first()
        val distance = line.substring(1).toInt()
        val rotationCount = distance / 100
        val rotationDistance = distance % 100

        if (direction == 'L') {
            position -= rotationDistance
        } else {
            position += rotationDistance
        }

        if (distance > 100) {
            zeroCount += rotationCount
        }

        if (position < 0) {
            position += 100
            if (oldPosition != 0) {
                zeroCount += 1
            }
        } else if (position > 99) {
            position -= 100
            if (oldPosition != 0) {
                zeroCount += 1
            }
        } else if (position == 0) {
            zeroCount += 1
        }

        debug {
            println("The dial is rotated $line to point at $position. Zero hit $zeroCount times")
        }

    }
    return zeroCount
}