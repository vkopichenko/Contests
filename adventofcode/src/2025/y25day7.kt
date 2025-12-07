import kotlin.time.measureTimedValue

fun main() {
    val input = generateSequence(::readlnOrNull).toList()

    measureTimedValue { // part 1
        input.drop(1).fold(0 to input[0].map { it == 'S' }) { (beamCount, beams), line ->
            beamCount + beams.zip(line.asIterable()).count { it.first && it.second == '^' } to
                beams.mapIndexed { i, it -> it && line[i] != '^' || i > 0 && beams[i - 1] && line[i - 1] == '^' || i < beams.lastIndex && beams[i + 1] && line[i + 1] == '^' }
        }.first
    }.also(::println)

    measureTimedValue { // part 2
        input.drop(1).fold(input[0].map { if (it == 'S') 1L else 0L }) { timelineCounts, line ->
            timelineCounts.mapIndexed { i, it ->
                if (line[i] == '^') 0L else it + (if (i > 0 && line[i - 1] == '^') timelineCounts[i - 1] else 0L) + (if (i < line.lastIndex && line[i + 1] == '^') timelineCounts[i + 1] else 0L)
            }
        }.sum()
    }.also(::println)
}
