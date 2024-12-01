package y23day23part2;

import y23day23part2.Direction.*
import java.util.*
import kotlin.time.measureTime

enum class Direction {
    left, up, right, down;
}
data class Pos(val i: Int, val j: Int)
fun Pos.move(dir: Direction) = when (dir) {
    up -> Pos(i - 1, j)
    down -> Pos(i + 1, j)
    left -> Pos(i, j - 1)
    right -> Pos(i, j + 1)
}
fun List<String>.at(pos: Pos) = this.getOrNull(pos.i)?.getOrNull(pos.j)
fun main() {
    val map = generateSequence { readlnOrNull() }.toList()
    measureTime {
        fun List<String>.findPos(row: Int, char: Char) = Pos(row, this[row].indexOf(char))
        val start = map.findPos(0, '.')
        val finish = map.findPos(map.size - 1, '.')
        sequence {
            val queue = PriorityQueue<Set<Pos>>(compareByDescending { it.size })
            queue += setOf(start)
            while (true) {
                val path = queue.poll() ?: break
                val pos = path.last()
                if (pos == finish) {
                    yield(path.size - 1)
                    continue
                }
                Direction.entries.forEach { dir ->
                    val nextPos = pos.move(dir)
                    if (map.at(nextPos).let { it == null || it == '#' } || nextPos in path) return@forEach
                    queue += path + nextPos
                }
            }
        }.onEach(::println).max().let(::println)
    }.let(::println)
}
