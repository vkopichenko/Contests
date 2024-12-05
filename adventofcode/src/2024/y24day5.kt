fun main() {
    val rules = generateSequence { readlnOrNull()?.takeIf { it.isNotEmpty() } }
        .map { it.split('|').let { it[0].toInt() to it[1].toInt() } }
        .groupBy({ it.second }, { it.first })
    val updates = generateSequence { readlnOrNull() }
        .map { it.split(',').map { it.toInt() } }.toList()

    fun isCorrectOrder(pages: List<Int>): Boolean {
        val prevPages = mutableSetOf<Int>()
        val pagesSet = pages.toSet()
        return pages.all { page ->
            (rules[page]?.let { prevPages.containsAll(it.intersect(pagesSet)) } ?: true)
                .also { prevPages += page }
        }
    }
    fun Sequence<List<Int>>.midSum() = sumOf { it[it.size / 2] }

    // part 1
    updates.asSequence().filter(::isCorrectOrder)
        .midSum().also(::println)

    // part 2
    updates.asSequence().filterNot(::isCorrectOrder).map { pages ->
        pages.sortedWith { a, b ->
            when {
                rules[b]?.contains(a) == true -> -1
                rules[a]?.contains(b) == true -> 1
                else -> 0
            }
        }
    }.midSum().also(::println)
}
