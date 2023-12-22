package y23day21part1

import y23day21part1.Direction.*

enum class Direction { R, D, L, U }
data class Pos(val i: Int, val j: Int)
fun Pos.move(dir: Direction) = when (dir) {
    U -> Pos(i - 1, j)
    D -> Pos(i + 1, j)
    L -> Pos(i, j - 1)
    R -> Pos(i, j + 1)
}
fun Pos.directions() = Direction.entries.map(::move)
fun List<String>.at(pos: Pos) = getOrNull(pos.i)?.getOrNull(pos.j)
fun main() {
    val maze = generateSequence { readlnOrNull() }.toList()
    val posS = maze.indices.firstNotNullOf { i ->
        maze[i].indices.firstNotNullOfOrNull { j ->
            Pos(i, j).takeIf { maze.at(it) == 'S' }
        }
    }
    generateSequence(setOf(posS)) { positions ->
        buildSet {
            positions.forEach { pos ->
                pos.directions().forEach { newPos ->
                    if ((maze.at(newPos) ?: ' ') in ".S") add(newPos)
                }
            }
        }
    }.drop(64).first().size.let(::println)
}
