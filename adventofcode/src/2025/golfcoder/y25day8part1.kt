fun main() = println(
    generateSequence(::readLine)
        .map { it.split(",").map(String::toLong).run { Triple(get(0), get(1), get(2)) } }.toList().run {
            flatMapIndexed { i, a -> drop(i + 1).map { a to it } } // pairs
                .sortedBy { (a, b) -> Triple(a.first - b.first, a.second - b.second, a.third - b.third).run { first * first + second * second + third * third } }
                .take(1000)
                .fold(withIndex().associate { it.value to it.index }) { circuits, (a, b) ->
                    circuits + circuits.filterValues { it == circuits[b] }.mapValues { circuits[a]!! }
                }.values.groupingBy { it }.eachCount().values.sortedDescending().take(3).reduce(Int::times)
        }
)
