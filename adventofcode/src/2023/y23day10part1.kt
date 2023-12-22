package y23day10part1;
import y23day10part1.Direction.*
import y23day21part1.Pos

private enum class Direction { up, down, left, right }
fun main() {
    val maze = generateSequence { readlnOrNull() }.toList()
    data class Pos(val i: Int, val j: Int, val from: Direction? = null) {
        val cell get() = maze[i][j]
        fun move(dir: Direction) = when (dir) {
            up -> Pos(i - 1, j, dir)
            down -> Pos(i + 1, j, dir)
            left -> Pos(i, j - 1, dir)
            right -> Pos(i, j + 1, dir)
        }
        fun startDirections() = listOfNotNull(
            move(up).takeIf { it.i != -1 && it.cell in "|F7" },
            move(down).takeIf { it.i != maze.size && it.cell in "|LJ" },
            move(left).takeIf { it.j != -1 && it.cell in "-FL" },
            move(right).takeIf { it.j != maze[i].length && it.cell in "-7J" },
        )
        fun next() = when {
            from == up && cell == '|' -> move(up)
            from == up && cell == 'F' -> move(right)
            from == up && cell == '7' -> move(left)
            from == down && cell == '|' -> move(down)
            from == down && cell == 'L' -> move(right)
            from == down && cell == 'J' -> move(left)
            from == left && cell == '-' -> move(left)
            from == left && cell == 'F' -> move(down)
            from == left && cell == 'L' -> move(up)
            from == right && cell == '-' -> move(right)
            from == right && cell == '7' -> move(down)
            from == right && cell == 'J' -> move(up)
            else -> error("Game over")
        }
        override fun equals(other: Any?): Boolean = other is Pos && i == other.i && j == other.j
    }
    fun <E> Collection<E>.allEqual(): Boolean = all { it == first() }

    val S = maze.indices.firstNotNullOf { i ->
        maze[i].indices.firstNotNullOfOrNull { j ->
            Pos(i, j).takeIf { maze[i][j] == 'S' }
        }
    }.also(::println)
    generateSequence(S.startDirections()) { it.map { it.next() } }
        .onEach(::println)
        .takeWhile { !it.allEqual() }
        .count().inc().let(::println)
}
