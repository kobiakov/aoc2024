package nl.kobiakov.aoc2024

fun readResourceFile(fileName: String): String =
    (Thread.currentThread().contextClassLoader.getResourceAsStream(fileName)
        ?: throw IllegalArgumentException("File not found: $fileName"))
        .bufferedReader().use { it.readText() }

fun loadDay(dayNr: Int) = readResourceFile("day${String.format("%02d", dayNr)}.txt").trimEnd()

fun <T> solveDay(
    dayNr: Int,
    inputProcessor: (String) -> T,
    partOne: (T) -> Any = { "yet to be solved" },
    partTwo: (T) -> Any = { "yet to be solved" }
) =
    with(inputProcessor(loadDay(dayNr))) {
        println("Part 1: ${partOne(this)}")
        println("Part 2: ${partTwo(this)}")
    }

fun solveDay(
    dayNr: Int,
    partOne: (String) -> Any = { "yet to be solved" },
    partTwo: (String) -> Any = { "yet to be solved" }
) = solveDay(dayNr, { it },  partOne, partTwo)

fun <T> debug(v: T, label: String = ""): T {
    println("debug ($label): $v")
    return v
}

fun <T> Iterable<T>.dropFirst(predicate: (T) -> Boolean): Iterable<T> =
    this.takeWhile { !predicate(it) } + this.dropWhile { !predicate(it) }.drop(1)
