import kotlin.time.measureTimedValue

fun main() {
    data class Pos(val i: Int, val j: Int)
    fun Pos.adjacentPositions() = sequenceOf(Pos(i - 1, j), Pos(i + 1, j), Pos(i, j - 1), Pos(i, j + 1))
    fun List<IntArray>.at(pos: Pos) = getOrNull(pos.i)?.getOrNull(pos.j)

    val map = generateSequence { readlnOrNull()?.map { it.digitToInt() }?.toIntArray() }.toList()

    fun <S> bfs(
        initialVisitState: () -> S,
        shouldVisit: (S, Pos, Pos) -> Boolean,
        trailheadScore: (S, Pos) -> Int
    ): Int =
        map.indices.sumOf { i ->
            map[i].indices.asSequence().filter { j ->
                map[i][j] == 0
            }.sumOf { j ->
                var trailheadScores = 0
                val visitCache = initialVisitState()
                val queue = ArrayDeque<Pos>()
                queue += Pos(i, j).also { shouldVisit(visitCache, it, it) }
                while (true) {
                    val pos = queue.removeFirstOrNull() ?: break
                    val depth = map.at(pos)!!
                    if (depth == 9) trailheadScores += trailheadScore(visitCache, pos)
                    else for (next in pos.adjacentPositions()) {
                        if (map.at(next) == depth + 1 && shouldVisit(visitCache, next, pos)) queue += next
                    }
                }
                trailheadScores
            }
        }

    measureTimedValue { // part 1
        bfs(
            initialVisitState = { Array(map.size) { BooleanArray(map[0].size) { false } } },
            shouldVisit = { visited, pos, _ ->
                pos.run { visited[i][j].not().also { if (it) visited[i][j] = true } }
            },
            trailheadScore = { _, _ -> 1 },
        )
    }.also(::println)

    measureTimedValue { // part 2
        bfs(
            initialVisitState = { Array(map.size) { IntArray(map[0].size) { 0 } } },
            shouldVisit = { visitCounts, pos, from ->
                if (pos == from) false.also { visitCounts[pos.i][pos.j] = 1 }
                else (visitCounts[pos.i][pos.j] == 0).also { visitCounts[pos.i][pos.j] += visitCounts[from.i][from.j] }
            },
            trailheadScore = { visitCounts, pos -> visitCounts[pos.i][pos.j] },
        )
    }.also(::println)
}
