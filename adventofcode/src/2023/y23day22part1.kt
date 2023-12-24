fun main() {
    infix fun IntRange.overlaps(that: IntRange) =
        first in that || last in that || that.first in this || that.last in this
    operator fun IntRange.minus(shift: Int) = first - shift .. last - shift
    data class Brick(val x: IntRange, val y: IntRange, val z: IntRange) {
        infix fun overlaps(that: Brick) = x overlaps that.x && y overlaps that.y
        infix fun zGap(that: Brick) = that.z.first - this.z.last - 1
        fun fallOnTop(level: Int) = Brick(x, y, z - (z.first - level - 1))
        infix fun laysOnTop(that: Brick) = that zGap this == 0 && this overlaps that
    }
    val fallingBricks = generateSequence { readlnOrNull() }.map { line ->
        val c = line.split(',', '~').map { it.toInt() }
        Brick(c[0]..c[3], c[1]..c[4], c[2]..c[5])
    }.sortedBy { it.z.first }.toList()
    val fallenBricks = buildList<Brick> {
        fallingBricks.forEach { brick ->
            val fallLevel = asSequence().filter { it overlaps brick }.maxOfOrNull { it.z.last } ?: 0
            add(brick.fallOnTop(fallLevel))
        }
    }
    val supporterCounts = fallenBricks.associateWith { brick ->
        fallenBricks.count { brick laysOnTop it }
    }
    fallenBricks.count { brick ->
        fallenBricks.none { it laysOnTop brick && supporterCounts[it] == 1 }
    }.let(::println)
}
