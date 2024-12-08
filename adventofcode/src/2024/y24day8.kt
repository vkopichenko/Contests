import kotlin.time.measureTimedValue

fun main() {
    data class Pos(val i: Int, val j: Int)
    fun List<String>.at(pos: Pos) = getOrNull(pos.i)?.getOrNull(pos.j)
    operator fun List<String>.contains(pos: Pos) = pos.i in this.indices && pos.j in this[pos.i].indices
    fun <T> Sequence<T>.everyPair() = flatMap { a -> map { b -> a to b } }.filter { it.first != it.second }

    val map = generateSequence { readlnOrNull() }.toList()
    val antennas = map.indices.flatMap { i ->
        map[i].indices.mapNotNull { j ->
            Pos(i, j).takeIf { map[i][j].isLetterOrDigit() }
        }
    }
    val antennaGroups = antennas.groupBy { map.at(it)!! }

    infix fun Pos.antinode(other: Pos) = Pos(2 * other.i - i, 2 * other.j - j)

    fun countAntinodes(supplier: (Pair<Pos, Pos>) -> Sequence<Pos>): Int =
        antennaGroups.asSequence().flatMap { (_, locations) ->
            locations.asSequence().everyPair()
                .flatMap { supplier(it).takeWhile { it in map } }
        }.distinct().count()

    measureTimedValue { // part 1
        countAntinodes {
            sequenceOf(it.first antinode it.second)
        }
    }.also(::println)

    measureTimedValue { // part 2
        countAntinodes {
            generateSequence(it) {
                it.second to (it.first antinode it.second)
            }.map { it.second }
        }
    }.also(::println)
}
