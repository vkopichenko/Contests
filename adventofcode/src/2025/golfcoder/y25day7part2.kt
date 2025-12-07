fun main() = println(
    generateSequence(::readLine).toList().run {
        drop(1).fold(elementAt(0).map { if (it == 'S') 1L else 0L }) { timelineCounts, line ->
            timelineCounts.mapIndexed { i, it ->
                if (line[i] == '^') 0L else it + (if (i > 0 && line[i - 1] == '^') timelineCounts[i - 1] else 0L) + (if (i < line.lastIndex && line[i + 1] == '^') timelineCounts[i + 1] else 0L)
            }
        }.sum()
    }
)
