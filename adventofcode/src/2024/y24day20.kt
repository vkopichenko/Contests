import java.util.*
import kotlin.math.abs
import kotlin.time.measureTimedValue

fun main() {
    data class Pos(val i: Int, val j: Int)
    operator fun Pos.plus(that: Pos) = Pos(i + that.i, j + that.j)
    fun Pos.neighbours(step: Int = 1) = sequenceOf(
        Pos(i - step, j),
        Pos(i + step, j),
        Pos(i, j - step),
        Pos(i, j + step),
    )
    fun Pos.allInRectangle(iDeltaRange: IntRange, jDeltaRange: IntRange) = sequence {
        for (i in iDeltaRange) {
            for (j in jDeltaRange) {
                yield(this@allInRectangle + Pos(i, j))
            }
        }
    }
    infix fun Pos.distance(that: Pos) = abs(i - that.i) + abs(j - that.j)

    operator fun List<String>.get(pos: Pos) = this.getOrNull(pos.i)?.getOrNull(pos.j)
    operator fun Array<IntArray>.get(pos: Pos) = this[pos.i][pos.j]
    operator fun Array<IntArray>.set(pos: Pos, value: Int) { this[pos.i][pos.j] = value }

    fun List<String>.posOf(char: Char) =
        indices.firstNotNullOf { i ->
            this[i].indices.firstNotNullOfOrNull { j ->
                Pos(i, j).takeIf { this[it] == char }
            }
        }

    val maze = generateSequence { readlnOrNull() }.toList()
    val start = maze.posOf('S')
    val finish = maze.posOf('E')

    fun bfs(fromPos: Pos, toPos: Pos, onEnqueue: (Pos, Int) -> Unit) {
        val queue = PriorityQueue<Pair<Pos, Int>>(compareBy { it.second })
        val visited = mutableSetOf<Pos>()
        queue += (fromPos to 0).also { (nextPos, distance) ->
            visited += nextPos
            onEnqueue(nextPos, distance)
        }
        while (true) {
            val (pos, distance) = queue.poll() ?: break
            if (pos == toPos) continue
            pos.neighbours().forEach { nextPos ->
                if (maze[nextPos] != '#' && nextPos !in visited) {
                    queue += (nextPos to distance + 1).also { (nextPos, distance) ->
                        visited += nextPos
                        onEnqueue(nextPos, distance)
                    }
                }
            }
        }
    }

    fun countCheats(debug: Boolean = false, possibleCheatPositions: (Pos) -> Sequence<Pos>): Int {
        val shortestPathsToFinish = Array(maze.size) { IntArray(maze[0].length) { -1 } }
        bfs(fromPos = finish, toPos = start, onEnqueue = { pos, distance -> shortestPathsToFinish[pos] = distance })
        val shortestPathsToStart = Array(maze.size) { IntArray(maze[0].length) { -1 } }
        bfs(fromPos = start, toPos = finish, onEnqueue = { pos, distance -> shortestPathsToStart[pos] = distance })
        val bestDistanceFromStart = shortestPathsToFinish[start]
        var cheatsCount = 0
        val cheatsBySave = mutableMapOf<Int, Int>()
        Pos(0, 0).allInRectangle(maze.indices, maze[0].indices)
            .filter { maze[it] != '#' }
            .forEach { pos ->
                val distanceToFinish = shortestPathsToFinish[pos]
                if (shortestPathsToStart[pos] + distanceToFinish == bestDistanceFromStart) { // we're on the best path
                    possibleCheatPositions(pos).filter { p ->
                        maze[p].let { it != null && it != '#' }
                    }.forEach { cheatPos ->
                        val cheatSaving = distanceToFinish - shortestPathsToFinish[cheatPos] - (pos distance cheatPos)
                        if (debug && cheatSaving >= 1) cheatsBySave.compute(cheatSaving) { _, prev -> (prev ?: 0) + 1 }
                        if (cheatSaving >= 100) ++cheatsCount
                    }
                }
            }
        if (debug) println(cheatsBySave.entries.sortedBy { it.key })
        return cheatsCount
    }

    measureTimedValue { // part 1
        countCheats(possibleCheatPositions = { pos -> pos.neighbours(2) })
    }.also(::println)

    measureTimedValue { // part 2
        val maxCheatDistance = 20
        val maxCheatRange = -maxCheatDistance..maxCheatDistance
        countCheats(/*debug = true,*/ possibleCheatPositions = { pos ->
            pos.allInRectangle(maxCheatRange, maxCheatRange).filter { it distance pos <= maxCheatDistance }
        })
    }.also(::println)
}
