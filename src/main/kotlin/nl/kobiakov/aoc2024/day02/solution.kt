package nl.kobiakov.aoc2024.day02

import nl.kobiakov.aoc2024.loadDay
import kotlin.math.abs

typealias Level = Int
typealias Report = List<Level>
typealias Reports = List<Report>

fun isSafe(report: Report): Boolean =
    isAllIncreasingOrDecreasing(report) && hasOnlySafeLevelChanges(report)

fun isAllIncreasingOrDecreasing(report: Report): Boolean =
    (report.sorted() == report) || (report.sortedDescending() == report)

fun hasOnlySafeLevelChanges(report: Report): Boolean = report.zipWithNext()
    .count { pair -> !isSafeLevelChange(pair.first, pair.second) } == 0

fun isSafeLevelChange(first: Level, second: Level): Boolean =
    with(abs(first - second)) {
        this in 1..3
    }

fun isSafeWithoutOneLevel(report: Report): Boolean =
    report.indices
        .map { elementIndexToTryWithout -> report.filterIndexed { index, _ -> index != elementIndexToTryWithout } }
        .any(::isSafe)

fun loadInput(s: String): Reports =
    s.split("\n").map { line -> line.split(" ").map { it.toInt() } }

fun part1(reports: Reports): Int = reports.count(::isSafe)

fun part2(reports: Reports): Int = reports.count(::isSafeWithoutOneLevel)

fun main() = with(loadInput(loadDay(2))) {
    println("part1: ${part1(this)}")
    println("part2: ${part2(this)}")
}
