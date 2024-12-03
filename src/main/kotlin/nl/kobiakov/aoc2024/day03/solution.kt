package nl.kobiakov.aoc2024.day03

import nl.kobiakov.aoc2024.debug
import nl.kobiakov.aoc2024.solveDay

typealias Mul = Pair<Int, Int>

fun processInput(s: String): List<Mul> =
    s.split("mul(")
        .map { it.split(")").first() }
        .map { it.split(",") }
        .filter { it.size == 2 && isNumber(it[0]) && isNumber(it[1]) }
        .map { Pair(it[0].toInt(), it[1].toInt()) }

fun isNumber(s: String): Boolean = s.all { it.isDigit() }

fun part1(s: String) = processInput(s).sumOf { it.first * it.second }

fun removeDisabled(s: String): String =
    if (s.indexOf("don't()") == -1 || s.indexOf("do()") == -1)
        s
    else
        removeDisabled(s.removeRange(s.indexOf("don't()"), s.indexOf("do()", startIndex = s.indexOf("don't()"))))

fun part2(s: String) = processInput(debug(removeDisabled(s))).sumOf { it.first * it.second }

fun main() = solveDay(3, partOne = ::part1, partTwo = ::part2)
