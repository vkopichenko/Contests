fun main() {
    fun readNumbers() = readln().splitToSequence("\\s+".toRegex()).drop(1).map { it.toInt() }.toList()
    val timeLimits = readNumbers()
    val bestDistances = readNumbers()
    fun countWinWays(time: Int, distance: Int): Int =
        (1 until time).count { t -> (time - t) * t > distance }
    timeLimits.indices.fold(1L) { product, i ->
        product * countWinWays(timeLimits[i], bestDistances[i])
    }.let(::println)
}
