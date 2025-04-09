import kotlin.math.abs

fun main() {
    repeat(readln().toInt()) {
        val n = readln().toInt()
        val ratings = readln().split(' ').map { it.toInt() }
        val deltas = ratings.sorted().zipWithNext { a, b -> abs(b - a) }
        fun List<Int>.bestPairs() = windowed(2, 2) { (a, b) -> a <= b }.all { it }
        val answer = deltas.bestPairs() && deltas.asReversed().bestPairs()
        println(if (answer) "YES" else "NO")
    }
}
