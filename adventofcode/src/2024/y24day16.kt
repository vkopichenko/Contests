package y24day16

import y24day16.Direction.*
import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.time.measureTimedValue

enum class Direction {
    west, north, east, south;
    fun turn(step: Int = 1) = entries.run { get(Math.floorMod(ordinal + step, size)) }
}
data class Pos(val i: Int, val j: Int)
fun Pos.move(from: Direction) = when (from) {
    north -> Pos(i - 1, j)
    south -> Pos(i + 1, j)
    west -> Pos(i, j - 1)
    east -> Pos(i, j + 1)
}

operator fun List<String>.get(pos: Pos) = this[pos.i][pos.j]
operator fun <T> Array<Array<T>>.get(pos: Pos) = this[pos.i][pos.j]
operator fun <T> Array<Array<T>>.set(pos: Pos, value: T) { this[pos.i][pos.j] = value }

fun main() {
    val maze = generateSequence { readlnOrNull() }.toList()

    fun List<String>.posOf(char: Char) =
        indices.firstNotNullOf { i ->
            this[i].indices.firstNotNullOfOrNull { j ->
                Pos(i, j).takeIf { maze[it] == char }
            }
        }
    val start = maze.posOf('S')
    val finish = maze.posOf('E')
    val bestScores = HashMap<Pair<Pos, Direction>, Int>()

    val bestScore = measureTimedValue { // part 1
        val queue = PriorityQueue<Triple<Pos, Direction, Int>>(compareBy { it.third })
        fun enqueue(step: Triple<Pos, Direction, Int>) {
            val (pos, dir, score) = step
            bestScores.compute(pos to dir) { _, prevScore ->
                if (prevScore != null && prevScore < score) prevScore
                else score.also { queue += step }
            }
        }
        enqueue(Triple(start, east, 0))
        while (true) {
            val (pos, dir, score) = queue.poll() ?: break
            if (pos == finish) continue
            val nextPos = pos.move(dir)
            if (maze[nextPos] != '#') enqueue(Triple(nextPos, dir, score + 1))
            enqueue(Triple(pos, dir.turn(-1), score + 1000))
            enqueue(Triple(pos, dir.turn(1), score + 1000))
        }
        Direction.entries.mapNotNull { bestScores[finish to it] }.min()
    }.also(::println).value

    measureTimedValue { // part 2
        val bestPathTiles = mutableSetOf<Pos>()
        val queue = ArrayDeque<Pair<Pos, Direction>>()
        fun enqueue(step: Pair<Pos, Direction>) {
            queue += step
            bestPathTiles += step.first
        }
        Direction.entries.filter { bestScores[finish to it] == bestScore }.forEach {
            enqueue(finish to it)
        }
        while (true) {
            val (pos, dir) = queue.removeFirstOrNull() ?: break
            if (pos == start) continue
            val score = bestScores[pos to dir]!!
            pos.move(dir.turn(2)).let { prevPos ->
                if (score - 1 == bestScores[prevPos to dir]) {
                    enqueue(prevPos to dir)
                }
            }
            sequenceOf(dir.turn(-1), dir.turn(1)).forEach { prevDir ->
                if (score - 1000 == bestScores[pos to prevDir]) {
                    enqueue(pos to prevDir)
                }
            }
        }
        bestPathTiles.count()
    }.also(::println)
}
