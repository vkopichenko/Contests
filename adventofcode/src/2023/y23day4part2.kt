fun main() {
    generateSequence { readlnOrNull() }.map { line ->
        val colonIndex = line.indexOf(':')
        val pipeIndex = line.indexOf('|')
        fun String.parseNumbers() =
            splitToSequence(' ').filter { it.isNotEmpty() }.map { it.toInt() }
        val id = line.substring(line.indexOf(' '), colonIndex).trim().toInt()
        val winningNumbers = line.substring(colonIndex + 1, pipeIndex).parseNumbers().toSet()
        val haveNumbers = line.substring(pipeIndex + 1).parseNumbers()
        val winCount = haveNumbers.count { it in winningNumbers }
        id to winCount
    }.toList().let { cards ->
        val cardCounts = IntArray(cards.count()) { 1 }
        cards.forEach { (id, winCount) ->
            ((id + 1)..(id + winCount).coerceAtMost(cardCounts.size))
                .forEach { cardCounts[it - 1] += cardCounts[id - 1] }
        }
        cardCounts.onEach(::println).sum().let(::println)
    }
}
