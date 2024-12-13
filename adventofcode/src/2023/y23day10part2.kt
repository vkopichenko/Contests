package y23day10part2;

import y23day10part2.Direction.*
import java.util.*

private enum class Direction { up, down, left, right }
fun main() {
    val originalMaze = generateSequence { readlnOrNull() }.toList()

    fun <T> Iterable<T>.interpose(combine: (T, T) -> T): Sequence<T> = sequence {
        //if (isEmpty()) return@sequence
        yield(first())
        reduce { a, b ->
            yield(combine(a, b))
            yield(b)
            b
        }
    }

    // doubling the original maze to normalize squeezed paths along pipes
    val maze = originalMaze.map { row ->
        row.asIterable().interpose { prevChar, nextChar ->
            if (prevChar in "S-FL" && nextChar in "S-7J") '-' else '.'
        }.joinToString("")
    }.interpose { prevRow, nextRow ->
        prevRow.indices.asSequence().map { i ->
            if (prevRow[i] in "S|7F" && nextRow[i] in "S|JL") '|' else '.'
        }.joinToString("")
    }.toList()

    val tileMap = Array(maze.size) { IntArray(maze[0].length) { 0 } }

    open class Pos(val i: Int, val j: Int) {
        override fun equals(other: Any?): Boolean = other is Pos && i == other.i && j == other.j
    }
    fun <E> Collection<E>.allEqual(): Boolean = all { it == first() }
    fun Pos.cell() = maze[i][j]
    fun Pos.markMainLoopBoundary() { tileMap[i][j] = -1 }
    fun Pos.onMainLoopBoundary() = tileMap[i][j] == -1
    fun Pos.move(dir: Direction) = when (dir) {
        up -> Pos(i - 1, j)
        down -> Pos(i + 1, j)
        left -> Pos(i, j - 1)
        right -> Pos(i, j + 1)
    }
    fun Pos.directions() = Direction.entries.asSequence().map(::move)

    class PosFrom(val from: Direction, pos: Pos) : Pos(pos.i, pos.j)

    fun Pos.moveForm(dir: Direction) = PosFrom(dir, move(dir))
    fun Pos.startDirections() = listOfNotNull(
        moveForm(up).takeIf { it.i != -1 && it.cell() in "|F7" },
        moveForm(down).takeIf { it.i != maze.size && it.cell() in "|LJ" },
        moveForm(left).takeIf { it.j != -1 && it.cell() in "-FL" },
        moveForm(right).takeIf { it.j != maze[i].length && it.cell() in "-7J" },
    )
    fun PosFrom.next() = when {
        from == up && cell() == '|' -> moveForm(up)
        from == up && cell() == 'F' -> moveForm(right)
        from == up && cell() == '7' -> moveForm(left)
        from == down && cell() == '|' -> moveForm(down)
        from == down && cell() == 'L' -> moveForm(right)
        from == down && cell() == 'J' -> moveForm(left)
        from == left && cell() == '-' -> moveForm(left)
        from == left && cell() == 'F' -> moveForm(down)
        from == left && cell() == 'L' -> moveForm(up)
        from == right && cell() == '-' -> moveForm(right)
        from == right && cell() == '7' -> moveForm(down)
        from == right && cell() == 'J' -> moveForm(up)
        else -> error("Game over")
    }

    val S = maze.mapIndexedNotNull { i, line ->
        line.mapIndexedNotNull { j, c -> j.takeIf { c == 'S' } }
            .takeIf { it.isNotEmpty() }?.map { Pos(i, it) }
    }[0][0]
    S.markMainLoopBoundary()
    generateSequence(S.startDirections()) { it.map(PosFrom::next) }
        .onEach { it.forEach(PosFrom::markMainLoopBoundary) }
        .takeWhile { !it.allEqual() }
        .count()

    data class Tile(val num: Int, val touchesMainLoop: Boolean, val touchesVoid: Boolean)

    fun demarcateTile(tileNum: Int, start: Pos): Tile? { // BFS
        fun Pos.inVoid() = i !in 0 until tileMap.size || j !in 0 until tileMap[0].size
        fun Pos.notVisited() = tileMap[i][j] == 0
        fun Pos.markVisited() = apply { tileMap[i][j] = tileNum }
        if (!start.notVisited()) return null
        var touchesMainLoop = false
        var touchesVoid = false
        val queue = ArrayDeque<Pos>()
        queue += start.markVisited()
        while (!queue.isEmpty()) {
            for (next in queue.poll()!!.directions()) {
                if (next.inVoid()) touchesVoid = true
                else if (next.onMainLoopBoundary()) touchesMainLoop = true
                else if (next.notVisited()) queue += next.markVisited()
            }
        }
        return Tile(tileNum, touchesMainLoop, touchesVoid)
    }

    val tiles = mutableListOf<Tile>()
    tileMap.indices.forEach { i ->
        tileMap[i].indices.forEach { j ->
            demarcateTile(tiles.size + 1, Pos(i, j))?.let(tiles::add)
        }
    }

    val innerTileNums = tiles.asSequence().filter { it.touchesMainLoop && !it.touchesVoid }.map { it.num }.toSet()
    fun Int.isEven() = this and 1 == 0
    tileMap.indices.filter(Int::isEven).sumOf { i ->
        tileMap[i].indices.filter(Int::isEven).count { j ->
            tileMap[i][j] in innerTileNums
        }
    }.let(::println)
/*
    println()
    maze.forEachIndexed { i, row ->
        row.forEachIndexed { j, char ->
            print(tileMap[i][j].takeIf { it != -1 } ?: char)
        }
        println()
    }
    println()
    originalMaze.forEachIndexed { i, row ->
        row.forEachIndexed { j, char ->
            print(tileMap[2*i][2*j].takeIf { it != -1 } ?: char)
        }
        println()
    }
*/
}
