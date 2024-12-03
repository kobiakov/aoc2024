package nl.kobiakov.aoc2024

fun readResourceFile(fileName: String): String =
    (Thread.currentThread().contextClassLoader.getResourceAsStream(fileName)
        ?: throw IllegalArgumentException("File not found: $fileName"))
        .bufferedReader().use { it.readText() }

fun loadDay(dayNr: Int) = readResourceFile("day${String.format("%02d", dayNr)}.txt").trimEnd()

fun <T> debug(v: T): T {
    println("debug: $v")
    return v
}
