package y23day17part2;

import y23day17part2.Direction.*
import java.util.*
import kotlin.time.measureTime

enum class Direction {
    left, up, right, down;
    fun turn(step: Int = 1) = entries.run { get((ordinal + step + size) % size) }
}
data class Pos(val i: Int, val j: Int)
fun Pos.move(from: Direction) = when (from) {
    up -> Pos(i + 1, j)
    down -> Pos(i - 1, j)
    left -> Pos(i, j + 1)
    right -> Pos(i, j - 1)
}
fun <T> Array<Array<T>>.at(pos: Pos) = this[pos.i][pos.j]
fun List<IntArray>.at(pos: Pos) = this[pos.i][pos.j]
operator fun List<IntArray>.contains(pos: Pos) = pos.i in this.indices && pos.j in this[0].indices
data class State(val from: Direction, val straightSteps: Int)
fun State.valid() = straightSteps in 4..10
fun main() {
    val map = generateSequence { readlnOrNull()?.map { it.digitToInt() }?.toIntArray() }.toList()
    measureTime {
        val start = Pos(0, 0)
        val finish = Pos(map.size - 1, map[0].size - 1)
        val heatLosses = Array(map.size) { Array(map[0].size) { mutableMapOf<State, Int>() } }
        val queue = PriorityQueue<Triple<Pos, State, Int>>(compareBy { it.third })
        queue += Triple(start, State(up, 0), 0)
        queue += Triple(start, State(left, 0), 0)
        while (true) {
            val (pos, state, heatLoss) = queue.poll() ?: break
            if (pos == finish && state.valid()) break
            (-1..1).forEach { turn ->
                if (!state.valid() && turn != 0) return@forEach
                val nextDir = state.from.turn(turn)
                val nextPos = pos.move(nextDir).takeIf { it in map } ?: return@forEach
                val nextStraightSteps = (if (turn == 0) state.straightSteps + 1 else 1).takeIf { it <= 10 } ?: return@forEach
                val nextState = State(nextDir, nextStraightSteps)
                val heatLossByState = heatLosses.at(nextPos)
                val nextHeatLoss = heatLoss + map.at(nextPos)
                if (heatLossByState[nextState]?.let { it <= nextHeatLoss } == true) return@forEach
                heatLossByState[nextState] = nextHeatLoss
                queue += Triple(nextPos, nextState, nextHeatLoss)
            }
        }
        heatLosses.at(finish).filterKeys { it.valid() }.values.min().let(::println)
    }.let(::println)
}
