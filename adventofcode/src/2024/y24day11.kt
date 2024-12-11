import java.util.*
import kotlin.time.measureTimedValue

fun main() {
    val stones = readln().split(' ').map { it.toLong() }

    fun blinkResults(item: Long) = sequence {
        val itemStr = item.toString()
        when {
            item == 0L -> yield(1)
            (itemStr.length and 1) == 0 -> {
                val halfLength = itemStr.length shr 1
                yield(itemStr.substring(0, halfLength).toLong())
                yield(itemStr.substring(halfLength).toLong())
            }
            else -> yield(item * 2024)
        }
    }

    measureTimedValue { // part 1
        LinkedList(stones).apply {
            repeat(25) { step ->
                val iterator = listIterator()
                while (iterator.hasNext()) {
                    blinkResults(iterator.next()).forEachIndexed { i, it ->
                        if (i == 0) iterator.set(it)
                        else iterator.add(it)
                    }
                }
                //print("$step.")
                //println(joinToString())
            }
        }.count()
    }.also(::println)

    measureTimedValue { // part 2
        // TODO: arrow.core.MemoizedDeepRecursiveFunction https://arrow-kt.io/learn/collections-functions/recursive/#memoized-recursive-functions
        val cache = HashMap<Pair<Long, Int>, Long>()
        fun predictedSize(item: Long, blinks: Int): Long = cache.getOrPut(item to blinks) {
            if (blinks == 0) 1
            else blinkResults(item).sumOf { predictedSize(it, blinks - 1) }
        }
        stones.sumOf { predictedSize(it, 75) }
    }.also(::println)
}
