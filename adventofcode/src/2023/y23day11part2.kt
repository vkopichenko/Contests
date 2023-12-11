import kotlin.math.abs

fun main() {
    val input = generateSequence { readlnOrNull() }.toList()
    val emptyRows = input.indices.filter { i -> input[i].all { it == '.' } }.toSet()
    val emptyCols = input[0].indices.filter { j -> input.all { it[j] == '.' } }.toSet()
    data class Pos(val i: Int, val j: Int)
    val galaxies = buildList {
        var extraRows = 0
        input.forEachIndexed { i, row ->
            if (i in emptyRows) extraRows += 1_000_000 - 1
            var extraCols = 0
            row.forEachIndexed { j, c ->
                if (j in emptyCols) extraCols += 1_000_000 - 1
                if (c == '#') add(Pos(i + extraRows, j + extraCols))
            }
        }
    }
    fun <T> Sequence<T>.pairs(): Sequence<Pair<T, T>> =
        flatMapIndexed { i, a -> drop(i + 1).map { b -> Pair(a, b) } }
    infix fun Pos.distanceTo(that: Pos) = abs(that.i - i) + abs(that.j - j)
    galaxies.asSequence().pairs().sumOf { (a, b) -> (a distanceTo b).toLong() }.let(::println)
}
