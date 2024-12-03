import java.net.HttpURLConnection
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDate
import java.util.*

plugins {
    kotlin("jvm") version "2.0.21"
}

group = "nl.kobiakov"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

tasks.register("downloadAdventOfCodeAssignment") {
    description = "Downloads the Advent of Code assignment for a given day."
    group = "utility"

    doLast {
        val envFile = project.file(".env")
        if (!envFile.exists()) {
            throw GradleException(".env file not found in project root.")
        }

        val properties = Properties().apply {
            envFile.inputStream().use { load(it) }
        }

        val token = properties.getProperty("AOC_TOKEN")
            ?: throw GradleException("AOC_TOKEN not found in .env file.")

        val day = if (project.hasProperty("day")) {
            (project.property("day") as String).toInt()
        } else {
            LocalDate.now().dayOfMonth
        }

        if (day !in 1..25) {
            throw GradleException("Day must be between 1 and 25.")
        }

        val url = "https://adventofcode.com/2024/day/$day/input"
        val paddedDay = String.format("%02d", day)
        val outputFile = project.file("src/main/resources/day$paddedDay.txt")

        print("Downloading the assignment to ${outputFile.absolutePath} ...")

        try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.setRequestProperty("Cookie", "session=$token")

            if (connection.responseCode == 200) {
                connection.inputStream.bufferedReader().use { reader ->
                    Files.writeString(outputFile.toPath(), reader.readText())
                }
                println("done!")
            } else {
                println("failed!")
                throw GradleException("Failed to download input: ${connection.responseCode} ${connection.responseMessage}")
            }
        } catch (e: Exception) {
            throw GradleException("Something went wrong: ${e.message}", e)
        }
    }
}
