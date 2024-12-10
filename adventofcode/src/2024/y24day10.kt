package y24day10;

import y24day10.Direction.*
import java.util.*
import kotlin.time.measureTimedValue

enum class Direction {
    left, up, right, down
}
data class Pos(val i: Int, val j: Int)
fun Pos.move(dir: Direction) = when (dir) {
    up -> Pos(i - 1, j)
    down -> Pos(i + 1, j)
    left -> Pos(i, j - 1)
    right -> Pos(i, j + 1)
}
fun List<IntArray>.at(pos: Pos) = getOrNull(pos.i)?.getOrNull(pos.j)
fun Pos.directions() = entries.asSequence().map(::move)

fun main() {
    val map = generateSequence { readlnOrNull()?.map { it.digitToInt() }?.toIntArray() }.toList()

    measureTimedValue { // part 1
        map.indices.sumOf { i ->
            map[i].indices.asSequence().filter { j ->
                map[i][j] == 0
            }.sumOf { j ->
                var trailheadScores = 0
                val visited = Array(map.size) { BooleanArray(map[0].size) { false } }
                visited[i][j] = true
                val queue = ArrayDeque<Pos>()
                queue += Pos(i, j)
                while (true) {
                    val pos = queue.poll() ?: break
                    val depth = map.at(pos)!!
                    if (depth == 9) ++trailheadScores
                    else for (next in pos.directions()) {
                        if (map.at(next) != depth + 1 || visited[next.i][next.j]) continue
                        visited[next.i][next.j] = true
                        queue += next
                    }
                }
                trailheadScores
            }
        }
    }.also(::println)

    measureTimedValue { // part 2
        map.indices.sumOf { i ->
            map[i].indices.asSequence().filter { j ->
                map[i][j] == 0
            }.sumOf { j ->
                var trailheadScores = 0
                val visitCounts = Array(map.size) { IntArray(map[0].size) { 0 } }
                visitCounts[i][j] = 1
                val queue = ArrayDeque<Pos>()
                queue += Pos(i, j)
                while (true) {
                    val pos = queue.poll() ?: break
                    val depth = map.at(pos)!!
                    if (depth == 9) trailheadScores += visitCounts[pos.i][pos.j]
                    else for (next in pos.directions()) {
                        if (map.at(next) != depth + 1) continue
                        val visited = visitCounts[next.i][next.j] > 0
                        visitCounts[next.i][next.j] += visitCounts[pos.i][pos.j]
                        if (!visited) queue += next
                    }
                }
                trailheadScores
            }
        }
    }.also(::println)
}
