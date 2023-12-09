fun main() {
    val cards = "A, K, Q, T, 9, 8, 7, 6, 5, 4, 3, 2, J".split(", ")
        .reversed().withIndex().associate { (i, it) -> it[0] to i }
    fun String.type() = groupingBy { it }.eachCount().filterKeys { it != 'J' }.run {
        when {
            // Five of a kind
            size <= 1 -> 7
            // Four of a kind
            size == 2 && values.any { it == 1 } -> 6
            // Full house
            size == 2 -> 5
            // Three of a kind
            size == 3 && values.count { it == 1 } >= 2 -> 4
            // Two pair
            size == 3 -> 3
            // One pair
            size == 4 -> 2
            // High card
            size == 5 -> 1
            else -> error("Game over")
        }
    }
    fun String.rankingCost() = fold(type().toLong()) { acc, c ->
        (acc shl 4) + cards.getValue(c)
    }
    generateSequence { readlnOrNull() }.map { line ->
        val hand = line.substringBefore(' ')
        val bid = line.substringAfter(' ').toLong()
        hand to bid
    }
        .sortedBy { (hand, _) -> hand.rankingCost() }
        .mapIndexed { i, (_, bid) -> (i + 1) * bid }
        .sum().let(::println)
}
