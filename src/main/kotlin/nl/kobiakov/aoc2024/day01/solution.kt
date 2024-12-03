package nl.kobiakov.aoc2024.day01

import nl.kobiakov.aoc2024.loadDay
import java.util.regex.Pattern
import kotlin.math.abs

data class Locations(val left: List<Int>, val right: List<Int>) {
    operator fun plus(pair: Pair<Int, Int>): Locations = Locations(left.plus(pair.first), right.plus(pair.second))

    fun incrementallySorted(): Locations = Locations(left.sorted(), right.sorted())

    fun toDistances(): List<Int> =
        left.zip(right).map { pair -> abs(pair.first - pair.second) }

    companion object {
        val EMPTY = Locations(emptyList(), emptyList())
    }
}

fun loadInput(s: String): Locations =
    s.splitToSequence("\n")
        .map(::stringToPairOfLocations)
        .fold(Locations.EMPTY) { acc, pair -> acc + pair }


fun stringToPairOfLocations(s: String): Pair<Int, Int> =
    with(s.split(Pattern.compile(" +"))) {
        Pair(this[0].toInt(), this[1].toInt())
    }

fun part1(locations: Locations): Int =
    locations
        .incrementallySorted()
        .toDistances()
        .sum()

fun nrOccurrences(i: Int, l: List<Int>) = l.count { it == i }

fun similarityScore(locations: Locations): Int =
    locations.left
        .map { left -> left * nrOccurrences(left, locations.right) }
        .sum()

fun part2(locations: Locations): Int = similarityScore(locations)

fun main() = with(loadInput(loadDay(1))) {
    println("part1: ${part1(this)}")
    println("part2: ${part2(this)}")
}
