package y23day18part1

import y23day18part1.Direction.*

enum class Direction { R, D, L, U }
data class Pos(val i: Int, val j: Int)
fun Pos.move(dir: Direction) = when (dir) {
    U -> Pos(i - 1, j)
    D -> Pos(i + 1, j)
    L -> Pos(i, j - 1)
    R -> Pos(i, j + 1)
}
fun Pos.walk(dir: Direction, steps: Int) =
    generateSequence(this) { it.move(dir) }.drop(1).take(steps)
fun Array<CharArray>.at(pos: Pos) = getOrNull(pos.i)?.getOrNull(pos.j)
fun Array<CharArray>.set(pos: Pos, value: Char) { this[pos.i][pos.j] = value }
fun Array<CharArray>.size(dir: Direction) = if (dir == U || dir == D) size else this[0].size
fun <T> Sequence<T>.then(appendToLast: (T) -> Sequence<T>) = sequence {
    var current: T? = null
    forEach {
        current = it
        yield(it)
    }
    current?.let {
        yieldAll(appendToLast(it))
    }
}
fun main() {
    val trench = generateSequence { readlnOrNull() }.map {
        Direction.valueOf(it.take(1)) to it.substring(1..3).trim().toInt()
    }.let { moves ->
        sequence {
            moves.fold(Pos(0, 0)) { pos, (dir, steps) ->
                pos.walk(dir, steps).also { yieldAll(it) }.last()
            }
        }
    }.toList()

    val minI = trench.minOf { it.i }
    val maxI = trench.maxOf { it.i }
    val minJ = trench.minOf { it.j }
    val maxJ = trench.maxOf { it.j }

    val terrain = Array(maxI - minI + 1) { CharArray(maxJ - minJ + 1) { '.' } }
    trench.forEach { terrain[it.i - minI][it.j - minJ] = '#' }

    val queue = ArrayDeque<Pos>()
    fun enqueue(pos: Pos) {
        if (terrain.at(pos) == '.') {
            terrain.set(pos, '_')
            queue += pos
        }
    }
    Direction.entries.fold(sequenceOf(Pos(0, 0))) { seq, dir ->
        seq.then { it.walk(dir, terrain.size(dir) - 1) }
    }.forEach(::enqueue)
    while (true) {
        val pos = queue.removeFirstOrNull() ?: break
        Direction.entries.forEach { dir ->
            enqueue(pos.move(dir))
        }
    }
    terrain.sumOf { row -> row.count { it != '_' } }.let(::println)
}
