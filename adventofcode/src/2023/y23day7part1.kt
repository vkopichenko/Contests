@OptIn(ExperimentalStdlibApi::class)
fun main() {
    val cards = "A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, 2".split(", ")
        .reversed().withIndex().associate { (i, it) -> it[0] to i + 1 }
    fun type(hand: String) = with (hand.groupingBy { it }.eachCount()) {
        when {
            // Five of a kind
            size == 1 -> 7
            // Four of a kind
            size == 2 && values.any { it == 4 } -> 6
            // Full house
            size == 2 -> 5
            // Three of a kind
            size == 3 && values.any { it == 3 } -> 4
            // Two pair
            size == 3 -> 3
            // One pair
            size == 4 -> 2
            // High card
            size == 5 -> 1
            else -> error("Game over")
        }
    }
    fun rankingCost(hand: String) = buildString {
        append(type(hand).toHexString())
        hand.forEach { append(cards.getValue(it).toHexString()) }
    }
    generateSequence { readlnOrNull() }.map { line ->
        val hand = line.substringBefore(' ')
        val bid = line.substringAfter(' ').toLong()
        hand to bid
    }
        .sortedBy { (hand, _) -> rankingCost(hand) }
        .mapIndexed { i, (_, bid) -> (i + 1) * bid }
        .sum().let(::println)
}
