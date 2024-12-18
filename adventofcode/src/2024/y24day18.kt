import java.util.*
import kotlin.time.measureTimedValue

fun main() {
    data class Pos(val x: Int, val y: Int)
    fun Pos.neighbours() = sequenceOf(
        Pos(x - 1, y),
        Pos(x + 1, y),
        Pos(x, y - 1),
        Pos(x, y + 1),
    )

    val corruptedList = generateSequence { readlnOrNull() }
        .map { it.split(',').map(String::toInt) }.map { (x, y) -> Pos(x, y) }.toList()

    fun countSteps(spaceSize: Int, corruptedCount: Int, debug: Boolean = false): Int? {
        val corrupted = corruptedList.take(corruptedCount).toSet()
        val start = Pos(0, 0)
        val finish = Pos(spaceSize, spaceSize)
        val spaceRangeX = start.x..finish.x
        val spaceRangeY = start.y..finish.y
        val visited = mutableSetOf<Pos>()
        val queue = PriorityQueue<Pair<Pos, Int>>(compareBy { it.second })
        fun enqueue(step: Pair<Pos, Int>) {
            val (pos, _) = step
            if (pos.x in spaceRangeX && pos.y in spaceRangeY && pos !in visited && pos !in corrupted) {
                queue += step
                visited += pos
            }
        }
        fun debug() {
            if (!debug) return
            for (y in spaceRangeY) {
                for (x in spaceRangeX) {
                    when (Pos(x, y)) {
                        in visited -> "O"
                        in corrupted -> "#"
                        else -> "."
                    }.also(::print)
                }
                println()
            }
        }
        queue += start to 0
        while (true) {
            val (pos, steps) = queue.poll() ?: return null.also { debug() }
            if (pos == finish) return steps.also { debug() }
            pos.neighbours().forEach { nextPos ->
                enqueue(nextPos to steps + 1)
            }
        }
    }

    measureTimedValue { // part 1
        //countSteps(12, 6)
        countSteps(70, 1024)
    }.also(::println)

    measureTimedValue { // part 2, binary search
        fun IntRange.binarySearch(comparison: (Int) -> Int): Int {
            var low = start
            var high = endInclusive
            while (low <= high) {
                val mid = (low + high) ushr 1 // safe from overflows
                val cmp = comparison(mid)
                when {
                    cmp < 0 -> low = mid + 1
                    cmp > 0 -> high = mid - 1
                    else -> return mid // key found
                }
            }
            return -(low + 1) // key not found
        }
        fun IntRange.searchFirst(predicate: (Int) -> Boolean): Int =
            -1 - binarySearch { if (predicate(it)) 1 else -1 }

        (1..corruptedList.size).searchFirst { i ->
            countSteps(70, i) == null
        }.let {
            //countSteps(70, it - 1, debug = true)
            //println("-".repeat(100))
            //countSteps(70, it, debug = true)
            corruptedList[it - 1]
        }.run { "$x,$y" }
    }.also(::println)
}
