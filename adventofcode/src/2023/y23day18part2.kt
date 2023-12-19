package y23day18part2

import y23day18part2.Direction.*
import kotlin.math.abs
import kotlin.math.absoluteValue

enum class Direction { R, D, L, U }
data class Pos(val i: Int, val j: Int)
fun Pos.move(dir: Direction, steps: Int = 1) = when (dir) {
    U -> Pos(i - steps, j)
    D -> Pos(i + steps, j)
    L -> Pos(i, j - steps)
    R -> Pos(i, j + steps)
}
infix fun Pos.distanceTo(that: Pos) = abs(that.i - i) + abs(that.j - j)
infix fun Pos.areaAboveAxisX(that: Pos) = if (j == that.j) 0L else (i + that.i) / 2L * (that.j - j)
fun main() {
    val trenchCorners = generateSequence { readlnOrNull() }.map {
        val len = it.substring(it.indexOf('#') + 1, it.length - 2).hexToInt()
        val dir = Direction.entries[it[it.length - 2].digitToInt()]
        len to dir
    }.runningFold(Pos(0, 0)) { pos, (steps, dir) ->
        pos.move(dir, steps)
    }.toList().apply { require(last() == Pos(0, 0)) }
    // If we move each coordinate of each corner by 0.5
    // then the resulting polygon includes the complete inner area
    // and the half of the trench's think edges except the one forth of the four outer corner cells.
    val trenchLength = trenchCorners.windowed(2) { (from, to) -> from distanceTo to }.sum()
    val areaWithHalfEdges = trenchCorners.windowed(2) { (from, to) -> from areaAboveAxisX to }.sum().absoluteValue
    val areaWithFullEdges = areaWithHalfEdges + trenchLength / 2 + 1
    println(areaWithFullEdges)
}
