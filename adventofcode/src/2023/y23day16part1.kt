package y23day16part1;

import y23day16part1.Direction.*
import java.util.*

enum class Direction {
    left, up, right, down;
    val vertical get() = this == up || this == down
    fun turn(step: Int = 1) = entries.run { get((ordinal + step + size) % size) }
}
class PosFrom(val from: Direction, val i: Int, val j: Int) {
    fun move(dir: Direction = from) = when (dir) {
        up -> PosFrom(dir, i + 1, j)
        down -> PosFrom(dir, i - 1, j)
        left -> PosFrom(dir, i, j + 1)
        right -> PosFrom(dir, i, j - 1)
    }
    fun turn(step: Int = 1) = PosFrom(from.turn(step), i, j)
}
fun main() {
    val tiles = generateSequence { readlnOrNull() }.toList()
    val visitedTiles = Array(tiles.size) { Array(tiles[0].length) { EnumSet.noneOf(Direction::class.java) } }
    fun visit(pos: PosFrom): Boolean {
        val visited = visitedTiles[pos.i][pos.j]
        if (pos.from in visited) return false
        visited += pos.from
        return true
    }
    val queue = ArrayDeque<PosFrom>()
    queue += PosFrom(left, 0, 0)
    visit(queue.peek())
    while (true) {
        val pos = queue.poll() ?: break
        fun plan(from: PosFrom) {
            val next = from.move()
            if (next.i in tiles.indices && next.j in tiles[0].indices && visit(next)) {
                queue += next
            }
        }
        when (tiles[pos.i][pos.j]) {
            '.' -> plan(pos)
            '|' -> if (pos.from.vertical) plan(pos) else { plan(pos.turn(1)); plan(pos.turn(-1)) }
            '-' -> if (!pos.from.vertical) plan(pos) else { plan(pos.turn(1)); plan(pos.turn(-1)) }
            '/' -> plan(pos.turn(if (pos.from.vertical) 1 else -1))
            '\\' -> plan(pos.turn(if (!pos.from.vertical) 1 else -1))
        }
    }
    visitedTiles.sumOf { it.count(EnumSet<Direction>::isNotEmpty) }.let(::println)
}
