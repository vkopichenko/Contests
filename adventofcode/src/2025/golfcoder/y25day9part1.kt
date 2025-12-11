fun main() = println(
    generateSequence(::readLine)
        .map { it.substringBefore(",").toLong() to it.substringAfter(",").toLong() }.toList()
        .run { flatMapIndexed { i, a -> drop(i + 1).map { a to it } } } // pairs
        .maxOf { (a, b) -> (a.first - b.first + 1) * ((a.second - b.second + 1)) }
)
