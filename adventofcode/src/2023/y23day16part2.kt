package y23day16part2;

import y23day16part2.Direction.*
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
    fun from(dir: Direction) = PosFrom(dir, i, j)
}
fun main() {
    val tiles = generateSequence { readlnOrNull() }.toList()
    Direction.entries.asSequence().flatMap { dir ->
        val startPos = when (dir) {
            left -> PosFrom(dir.turn(), 0, 0)
            up -> PosFrom(dir.turn(), 0, tiles[0].length - 1)
            right -> PosFrom(dir.turn(), tiles.size - 1, tiles[0].length - 1)
            down -> PosFrom(dir.turn(), tiles.size - 1, 0)
        }
        generateSequence(startPos) { it.move() }
            .map { it.from(dir) }
            .take(if (dir.vertical) tiles[0].length else tiles.size)
    }.map { beamEntryPos ->
        val visitedTiles = Array(tiles.size) { Array(tiles[0].length) { EnumSet.noneOf(Direction::class.java) } }
        fun visit(pos: PosFrom): Boolean {
            val visited = visitedTiles[pos.i][pos.j]
            if (pos.from in visited) return false
            visited += pos.from
            return true
        }
        val queue = ArrayDeque<PosFrom>()
        queue += beamEntryPos
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
        visitedTiles.sumOf { it.count(EnumSet<Direction>::isNotEmpty) }
    }.max().let(::println)
}
