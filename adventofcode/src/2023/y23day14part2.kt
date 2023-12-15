package y23day14part2

import y23day14part2.Direction.*

enum class Direction(val stepI: Int, val stepJ: Int) {
    north(-1, 0), west(0, -1), south(1, 0), east(0, 1);
    fun next(step: Int = 1) = entries.run { get((ordinal + step) % size) }
}
data class Pos(val i: Int, val j: Int) {
    fun move(dir: Direction, step: Int = 1) = Pos(i + dir.stepI * step, j + dir.stepJ * step)
}
class CharArray2D(val rows: Array<CharArray>) {
    val rowsCount get() = rows.size
    val colsCount get() = rows[0].size
    fun size(dir: Direction) = if (dir == north || dir == south) rowsCount else colsCount
    fun at(pos: Pos) = rows[pos.i][pos.j]
    fun set(pos: Pos, value: Char) { rows[pos.i][pos.j] = value }
    fun deepClone() = CharArray2D(Array(rowsCount) { rows[it].clone() })
    override fun equals(other: Any?) = rows.contentDeepEquals((other as? CharArray2D)?.rows)
    override fun hashCode() = rows.contentDeepHashCode()
    fun print() = rows.forEach(::println)
}
fun main() {
    val plane = generateSequence { readlnOrNull()?.toCharArray() }.toList().toTypedArray().let(::CharArray2D)
    generateSequence (plane) { cycleState ->
        generateSequence (cycleState to north) { (stateToTilt, dir) ->
            stateToTilt.deepClone().apply {
                val startCorner = when (dir) {
                    north -> Pos(0, colsCount - 1)
                    west -> Pos(0, 0)
                    south -> Pos(rowsCount - 1, 0)
                    east -> Pos(rowsCount - 1, colsCount - 1)
                }
                val rowsDir = dir.next(2)
                val colsDir = dir.next()
                generateSequence(startCorner) { it.move(colsDir) }.take(size(colsDir)).forEach { colStartPos ->
                    var lastRock = colStartPos.move(rowsDir, -1)
                    generateSequence(colStartPos) { it.move(rowsDir) }.take(size(rowsDir)).forEach { pos ->
                        when (at(pos)) {
                            '#' -> lastRock = pos
                            'O' -> generateSequence(lastRock) { it.move(rowsDir) }.drop(1).takeWhile { it != pos }
                                .find { at(it) == '.' }?.let { emptyPos ->
                                    set(emptyPos, 'O'); set(pos, '.')
                                }
                        }
                    }
                }
            } to dir.next()
        }.drop(Direction.entries.size).map { it.first }.first()
    }.run {
        val uniqueStates = mutableSetOf<CharArray2D>()
        var loopFound = false
        takeWhile { uniqueStates.add(it) || !loopFound.also { loopFound = true } }
//            .onEachIndexed { i, it -> println(i); it.print() }
    }.toList().run {
        val loopStart = indexOf(last())
        val loopLength = size - 1 - loopStart
//        println("loopStart=$loopStart, loopLength=$loopLength")
        val cycles = 1000000000
        get(loopStart + (cycles - loopStart) % loopLength)
    }.run {
        rows.asSequence().mapIndexed { i, row ->
            row.count { it == 'O' } * (rowsCount - i)
        }.sum()
    }.let(::println)
}
