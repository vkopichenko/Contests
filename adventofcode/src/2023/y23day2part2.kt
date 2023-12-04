import kotlin.math.max

fun main() {
    fun <K, V, R> Pair<Map<K, V>, Map<K, V>>.merge(merger: (V?, V?) -> R): Map<K, R> =
        (first.keys.asSequence() + second.keys.asSequence()).distinct()
            .associateWith { merger(first[it], second[it]) }
    generateSequence { readlnOrNull() }.map { game ->
        game.splitToSequence(':', ';').drop(1).map { round ->
            round.splitToSequence(',').map { it.split(' ') }
                .associate { (_, count, color) -> color to count.toInt() }
        }.reduce { a, b ->
            (a to b).merge { x, y -> max(x ?: 0, y ?: 0) }
        }.values.reduce { a, b -> a * b }
    }.sum().let(::println)
}
