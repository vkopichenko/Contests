package y24day5;

import y24day5.Direction.*
import java.util.*
import kotlin.time.measureTimedValue

enum class Direction {
    left, up, right, down;
    fun turn(step: Int = 1) = entries.run { get((ordinal + step + size) % size) }
}
data class Pos(val i: Int, val j: Int)
fun Pos.move(dir: Direction) = when (dir) {
    up -> Pos(i - 1, j)
    down -> Pos(i + 1, j)
    left -> Pos(i, j - 1)
    right -> Pos(i, j + 1)
}
fun List<String>.at(pos: Pos) = getOrNull(pos.i)?.getOrNull(pos.j)

fun main() {
    val map = generateSequence { readlnOrNull() }.toList()
    val startPos = map.indices.firstNotNullOf { i ->
        map[i].indices.firstNotNullOfOrNull { j ->
            Pos(i, j).takeIf { map.at(it) == '^' }
        }
    }
    val guardVisits = Array(map.size) { BooleanArray(map[0].length) { false } }

    measureTimedValue { // part 1
        var pos = startPos
        var dir = up
        while (true) {
            guardVisits[pos.i][pos.j] = true
            val next = pos.move(dir)
            if ((map.at(next) ?: break) == '#') dir = dir.turn() else pos = next
        }
        guardVisits.sumOf { it.count { it } }
    }.also(::println)

    measureTimedValue { // part 2
        map.indices.asSequence().flatMap { i ->
            map[i].indices.asSequence().map { j -> Pos(i, j) }
        }.filter {
            guardVisits[it.i][it.j] && it != startPos
        }.count { obstruction ->
            val directedVisits = Array(map.size) { Array(map[0].length) { EnumSet.noneOf(Direction::class.java) } }
            var pos = startPos
            var dir = up
            var looped = false
            while (true) {
                if (dir in directedVisits[pos.i][pos.j]) { looped = true; break }
                directedVisits[pos.i][pos.j] += dir
                val next = pos.move(dir)
                if ((map.at(next) ?: break) == '#' || next == obstruction) dir = dir.turn() else pos = next
            }
            looped
        }
    }.also(::println)
}
