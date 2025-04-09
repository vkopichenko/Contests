fun main() {
    val coins = readln().toList()
    val coinCounts = Array(coins.size + 1) { mutableMapOf<Char, Int>() }.apply {
        for (i in 1..indices.last) {
            this[i] = this[i - 1].toMutableMap().apply {
                compute(coins[i - 1]) { _, v -> (v ?: 0) + 1 }
            }
        }
    }
    repeat(readln().toInt()) {
        val (l, r) = readln().split(' ').map { it.toInt() }
        val prevCounts = coinCounts[l - 1]
        val counts = coinCounts[r].map { it.value - (prevCounts[it.key] ?: 0) }.sortedDescending()
        println(counts.first() + counts.elementAtOrElse(2) { 0 })
    }
}
