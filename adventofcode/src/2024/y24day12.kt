import kotlin.time.measureTimedValue

fun main() {
    data class Pos(val i: Int, val j: Int)
    operator fun Pos.plus(that: Pos) = Pos(i + that.i, j + that.j)
    operator fun Pos.minus(that: Pos) = Pos(i - that.i, j - that.j)
    fun Pos.adjacentPositions() =
        sequenceOf(Pos(i - 1, j), Pos(i, j + 1), Pos(i + 1, j), Pos(i, j - 1))
    fun Pos.adjacentDiagPositions() =
        sequenceOf(Pos(i - 1, j - 1), Pos(i - 1, j + 1), Pos(i + 1, j - 1), Pos(i + 1, j + 1))
    fun Pos.opposite(from: Pos) = this + (this - from)
    fun Pos.turn(step: Int = 1) =
        sequenceOf(this, Pos(j, -i), Pos(-i, -j), Pos(-j, i)).elementAt((step + 4) % 4)
    fun Pos.turn(vec: Pos, step: Int = 1) = this + vec.turn(step)
    fun Pos.turnFrom(from: Pos, step: Int = 1) = from.turn(from - this, step)
    fun Pos.behindPositions(from: Pos) =
        sequenceOf(from.turn(this - from, -1), from, from.turn(this - from, 1))
    fun Pos.aheadPositions(from: Pos) = behindPositions(opposite(from))

    fun List<String>.at(pos: Pos) = getOrNull(pos.i)?.getOrNull(pos.j)
    fun <T> Array<Array<T>>.at(pos: Pos) = getOrNull(pos.i)?.getOrNull(pos.j)

    data class Region(val type: Char, var area: Int = 0, var perimeter: Int = 0)

    val map = generateSequence { readlnOrNull() }.toList()

    fun regionScores(perimeterDelta: (pos: Pos, from: Pos, isSameRegion: Pos.() -> Boolean) -> Int): Int {
        val regions = mutableListOf<Region>()
        val regionMap = Array(map.size) { Array<Region?>(map[it].length) { null } }
        fun visit(pos: Pos, from: Pos): Boolean {
            if (regionMap[pos.i][pos.j] != null) return false
            val region = regionMap[from.i][from.j] ?: Region(map.at(pos)!!).also { regions += it }
            regionMap[pos.i][pos.j] = region.apply {
                area += 1
                perimeter += perimeterDelta(pos, from) { region === regionMap.at(this) }
            }
            return true
        }
        map.indices.forEach { i ->
            map[i].indices.forEach { j ->
                val queue = ArrayDeque<Pos>()
                queue += Pos(i, j).also { visit(it, it) }
                while (true) {
                    val pos = queue.removeFirstOrNull() ?: break
                    val cell = map.at(pos)!!
                    for (next in pos.adjacentPositions()) {
                        if (map.at(next) == cell && visit(next, pos)) queue += next
                    }
                }
            }
        }
        return regions.sumOf { it.area * it.perimeter }
    }

    measureTimedValue { // part 1
        regionScores(perimeterDelta = { pos, _, isSameRegion ->
            when (pos.adjacentPositions().filter { it.isSameRegion() }.count()) {
                0 -> 4
                1 -> 2
                2 -> 0
                3 -> -2
                4 -> -4
                else -> error("Unreachable")
            }
        })
    }.also(::println)

    measureTimedValue { // part 2
        regionScores(perimeterDelta = { pos, from, isSameRegion ->
            fun Pos.countBy(supplier: Pos.() -> Sequence<Pos>) =
                supplier().filter { it.isSameRegion() }.count()
            when (pos.countBy { adjacentPositions() }) {
                0 -> 4
                1 -> when (pos.countBy { behindPositions(from) }) {
                    1 -> 0
                    2 -> 2
                    3 -> 4
                    else -> error("Unreachable")
                }
                2 -> {
                    if (pos.opposite(from).isSameRegion()) {
                        when (pos.countBy { adjacentDiagPositions() }) {
                            0 -> -4
                            1 -> -2
                            2 -> 0
                            3 -> 2
                            4 -> 4
                            else -> error("Unreachable")
                        }
                    } else {
                        val (index, nonEmptyPos) = from.aheadPositions(from.opposite(pos)).withIndex().first { it.value.isSameRegion() }
                        val step = index - 1
                        when (pos.countBy { sequenceOf(turnFrom(nonEmptyPos, step), turnFrom(opposite(nonEmptyPos), step)) }) {
                            0 -> -2
                            1 -> 0
                            2 -> 2
                            else -> error("Unreachable")
                        }
                    }
                }
                3 -> {
                    val emptyPos = pos.adjacentPositions().first { !it.isSameRegion() }
                    when (pos.countBy { behindPositions(emptyPos) }) {
                        0 -> -4
                        1 -> -2
                        2 -> 0
                        else -> error("Unreachable")
                    }
                }
                4 -> -4
                else -> error("Unreachable")
            }
        })
    }.also(::println)
}
