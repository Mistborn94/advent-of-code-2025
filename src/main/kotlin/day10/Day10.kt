package day10

import helper.Debug
import helper.graph.shortestPath
import kotlin.math.max

typealias Button = Set<Int>

fun solveA(text: String, debug: Debug = Debug.Disabled): Int {
    val inputs = text.lines().map { line -> parseInput(line) }
    return inputs.sumOf { minButtonPressesA(it, debug) }
}

fun minButtonPressesA(input: Input, debug: Debug): Int {
    debug {
        println("Starting $input")
    }
    return shortestPath<Set<Int>>(
        start = setOf(),
        endPredicate = { it == input.lightsTarget },
        neighbours = { current ->
            input.buttons.map { button -> applyButtonA(button, current, debug, input.size) }
        },
    ).getScore()
}


fun applyButtonA(button: Set<Int>, current: Set<Int>, debug: Debug, lightCount: Int): Set<Int> {
    val new = current.toMutableSet()
    for (light in button) {
        if (light in new) {
            new.remove(light)
        } else {
            new.add(light)
        }
    }
    debug {
        println("${render(current, lightCount)} $button -> ${render(new, lightCount)} ")
    }
    return new
}

fun render(lights: Set<Int>, size: Int): String = (0..<size).joinToString("") { if (it in lights) "#" else "." }

fun parseInput(line: String): Input {
    val parts = line.split(" ")
    val lights = parts.first().trim('[', ']')
    val targetState = lights.indices.filterTo(mutableSetOf()) { lights[it] == '#' }
    val buttons: List<Button> = parts.drop(1).dropLast(1)
        .map {
            it.trim('(', ')').split(',')
                .mapTo(mutableSetOf()) { button -> button.toInt() }
        }
        .sortedByDescending { it.size }

    val joltageTarget = parts.last().trim('{', '}').split(",").map { it.toInt() }

    return Input(lights.length, targetState, buttons, joltageTarget)
}


fun solveB(text: String, debug: Debug = Debug.Disabled): Int {
    val inputs = text.lines().map { line -> parseInput(line) }


    return inputs.sumOf { minButtonPressesB(it, debug) }
}

fun minButtonPressesB(input: Input, debug: Debug): Int {
    println("Solving $input")
    debug {
    }
    return smartDfs(inputRange = 0..<input.size, input.buttons, input.joltageTargets, 0, debug)!!
//    return dfs(input.buttons, 0, input.joltageTargets, 0) !!
}

fun smartDfs(
    inputRange: IntRange,
    buttons: List<Button>,
    currentJoltage: List<Int>,
    pushes: Int,
    debug: Debug,
    indent: String = ""
): Int? {
    debug {
        println("${indent}Buttons $buttons, Joltage $currentJoltage, pushes $pushes")
    }

    if (currentJoltage.all { it == 0 }) {
        debug {
            println("$indent |⸻Solution Found $pushes")
        }
        return pushes
    }

    if (buttons.isEmpty() || currentJoltage.any { it < 0 }) {
        debug {
            println("$indent |⸻No Solution $pushes")
        }
        return null
    }

    val unsolvedInputs = inputRange.filter { currentJoltage[it] > 0 }
    val affectedByCount = unsolvedInputs.associateWith { index -> buttons.count { button -> button.contains(index) }}.filterValues { it > 0 }

    if (affectedByCount.isEmpty()) {
        debug {
            println("$indent |⸻ Unsolvable $currentJoltage $affectedByCount")
        }
        return null
    }

    val singleButtonTargetInput = affectedByCount.entries.firstOrNull { (_, value) -> value == 1 }?.key
    if (singleButtonTargetInput != null) {
        val button = buttons.first { button -> button.contains(singleButtonTargetInput) }
        debug {
            println("$indent |⸻Target Input $singleButtonTargetInput: Only affected by one button $button")
        }
        val requiredPresses = currentJoltage[singleButtonTargetInput]
        val newJoltage = subtractJoltage(currentJoltage, button, requiredPresses)

        return smartDfs(
            inputRange,
            buttons.filter { it != button },
            newJoltage,
            pushes + requiredPresses,
            debug
        )
    } else {
        val allMaxPresses = buttons.map { button -> button.minOf { currentJoltage[it] } }
        val totalMaxPresses = allMaxPresses.sum()
        val allMinPresses = buttons.mapIndexed { i, button ->
            val remaining = totalMaxPresses - allMaxPresses[i]
            max(0, button.maxOf { currentJoltage[it] - remaining })
        }
        val chosenButtonIndex = buttons.indices.minBy { allMaxPresses[it] - allMinPresses[it] }
        val chosenButton = buttons[chosenButtonIndex]
        val minPresses = allMinPresses[chosenButtonIndex]
        val maxPresses = allMaxPresses[chosenButtonIndex]

        debug {
            println("$indent |⸻Chosen button: $chosenButton presses $minPresses .. $maxPresses ")
        }

        val remainingButtons = buttons.filter { it != chosenButton }

        return (maxPresses downTo minPresses).minOfWithOrNull(Comparator.nullsLast<Int?>(Comparator.naturalOrder<Int>())) {
            smartDfs(
                inputRange,
                remainingButtons,
                subtractJoltage(currentJoltage, chosenButton, it),
                pushes + it,
                debug,
                "$indent "
            )
        }
    }
}

fun subtractJoltage(
    current: List<Int>,
    currentButton: Button,
    pressCount: Int
): List<Int> = if (pressCount == 0)
    current
else {
    current.mapIndexed { i, value -> if (i in currentButton) value - pressCount else value }
}


data class Input(
    val size: Int,
    val lightsTarget: Set<Int>,
    val buttons: List<Button>,
    val joltageTargets: List<Int>
) {
}