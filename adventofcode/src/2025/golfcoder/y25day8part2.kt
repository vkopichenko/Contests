fun main() = println(
    generateSequence(::readLine)
        .map { it.split(",").map(String::toLong).run { Triple(get(0), get(1), get(2)) } }.toList().run {
            asSequence().flatMapIndexed { i, a -> drop(i + 1).map { a to it } } // pairs
                .sortedBy { (a, b) -> Triple(a.first - b.first, a.second - b.second, a.third - b.third).run { first * first + second * second + third * third } }
                .runningFold(get(0) to get(0) to withIndex().associate { it.value to it.index }) { (_, circuits), (a, b) ->
                    a to b to circuits + circuits.filterValues { it == circuits[b] }.mapValues { circuits[a]!! }
                }.dropWhile { it.second.values.toSet().size > 1 }.first().first.run { first.first * second.first }
        }
)
